package study._00_quick_start

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}

object Greeter {
  def props(message: String, printerActor: ActorRef): Props = Props(new Greeter(message, printerActor))
  final case class WhoToGreet(who: String)
  case object Greet
}

class Greeter(message: String, printActor: ActorRef) extends Actor with ActorLogging {
  import Greeter._
  import Printer._

  var greeting = ""

  override def receive: Receive = {
    case WhoToGreet(who) =>
      log.info("_0_quick_start.Greeter received who to greet: " + who)
      greeting = message + ", " + who
    case Greet =>
      log.info("_0_quick_start.Greeter received greet ")
      printActor ! Greeting(greeting)
  }
}

object Printer {
  def props: Props = Props[Printer]
  final case class Greeting(greeting: String)
}

class Printer extends Actor with ActorLogging {
  import Printer._
  override def receive: Receive = {
    case Greeting(greeting) =>
      log.info("_0_quick_start.Printer received (from " + sender() + "): " + greeting)
  }
}

object ActorDemo extends App {
  import Greeter._
  val system: ActorSystem = ActorSystem("helloAkka")
  val printer: ActorRef = system.actorOf(Printer.props, "printActor")

  val howdyGreeter: ActorRef =
    system.actorOf(Greeter.props("Howdy", printer), "howdyGreeter")
  howdyGreeter ! WhoToGreet("Akka")
  howdyGreeter ! Greet


}


