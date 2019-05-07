package demo

import akka.actor.{Actor, ActorLogging}

class Assembler extends Actor with ActorLogging{
  override def receive: Receive = {
    case _ => log.info("test Assembler")
  }
}
