package demo

import akka.actor.{Actor, ActorLogging}

class Distributor extends Actor with ActorLogging{
  override def receive: Receive = {
    case _ => log.info("test Distributor")
  }
}
