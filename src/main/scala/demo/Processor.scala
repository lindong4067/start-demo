package demo

import akka.actor.{Actor, ActorLogging}

class Processor extends Actor with ActorLogging{
  override def receive: Receive = {
    case _ => log.info("test Processor")
  }
}
