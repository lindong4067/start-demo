package study._01_create_actor

import akka.actor.{Actor, ActorRef, ActorSystem, PoisonPill, Props}

case object Ping
case object Pong

class Pinger extends Actor {
  var countDown: Int = 200

  override def receive: Receive = {
    case Pong =>
      println(s"${self.path} received pong, count down $countDown ")
      if (countDown > 0) {
        countDown -= 1
        sender() ! Ping
      } else {
        sender() ! PoisonPill
        self ! PoisonPill
      }
  }
}

class Ponger(pinger: ActorRef) extends Actor {
  override def receive: Receive = {
    case Ping =>
      println(s"${self.path} received ping")
      pinger ! Pong
  }
}

object PingerPonger extends App {
  val system = ActorSystem("PingPang")
  var pinger = system.actorOf(Props[Pinger], "pinger")
  var ponger = system.actorOf(Props(classOf[Ponger], pinger), "ponger")

  ponger ! Ping
}
