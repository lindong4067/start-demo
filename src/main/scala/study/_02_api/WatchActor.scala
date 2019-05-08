package study._02_api

import akka.actor.{Actor, ActorRef, Props, Terminated}

class WatchActor extends Actor {
  val child: ActorRef = context.actorOf(Props.empty, "child");
  context.watch(child)
  var lastSender: ActorRef = context.system.deadLetters

  override def receive: Receive = {
    case "kill" =>
      context.stop(child); lastSender = sender()
    case Terminated(`child`) => lastSender ! "finished"
  }
}
