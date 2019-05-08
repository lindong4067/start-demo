package study._01_create_actor

import java.util

import akka.actor.{Actor, ActorRef, ActorSystem, PoisonPill, Props}

case object GiveMeOne
case object Done
case object NoMore
case object Stop

class Distributor(assembler: ActorRef, processors: ActorRef*) extends Actor {
  var rawNumber: Int = 100000

  override def receive: Receive = {
    case GiveMeOne =>
      if (rawNumber > 1) {
        rawNumber -= 1
        for (processor <- processors) {
          processor ! rawNumber
          println(s"$self received [GiveMeOne], distribute $rawNumber to " + processor)
        }
      }
    case Done =>
      if (rawNumber > 1) {
        rawNumber -= 1
        sender() ! rawNumber
      } else {
        assembler ! Stop
      }
      println(s"$self received [Done], distribute $rawNumber to " + sender())
    case _ =>
      println(s"$self received unknown message")

    if (rawNumber <= 1) {
      sender() ! NoMore
      sender() ! PoisonPill
      self ! PoisonPill
    }
  }
}

class Processor(assembler: ActorRef) extends Actor {
  override def receive: Receive = {
    case num: Int =>
      println(s"$self received [$num]")
      if (isPrime(num)) {
        assembler ! num
      }
      sender() ! Done
    case NoMore =>
      println(s"$self received [NoMore]")
      self ! PoisonPill
    case _ =>
      println(s"$self received unknown message")
  }

  def isPrime(num: Int): Boolean = {
    if (num <= 3) return num > 1
    if (num % 6 != 1 && num % 6 != 5) return false
    val sqrt = Math.sqrt(num)
    var i: Int = 5
    if (i <= sqrt) {
      if (num % i == 0 || num % (i + 2) == 0) return false
      i += 6
    }
    true
  }

}

class Assembler extends Actor {
  var raws = new util.ArrayList[Int]()
  override def receive: Receive = {
    case num: Int =>
      raws.add(num)
      println(s"$self received num $num")
    case Stop =>
      println(raws)
    case _ =>
      println(s"$self received unknown message")
  }
}

object ProcessorDemo extends App {
  var system = ActorSystem("processorDemo")
  var assembler = system.actorOf(Props[Assembler], "assembler")
  var processor1 = system.actorOf(Props(classOf[Processor], assembler), "processor1")
//  var processor2 = system.actorOf(Props(classOf[Processor], assembler), "processor2")
//  var processor3 = system.actorOf(Props(classOf[Processor], assembler), "processor3")
//  var processor4 = system.actorOf(Props(classOf[Processor], assembler), "processor4")
  var distributor = system.actorOf(Props(classOf[Distributor], assembler, List(processor1)), "distributor")

  distributor ! GiveMeOne
}
