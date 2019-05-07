package study._01_create_actor

import akka.actor.Actor
import akka.event.Logging

class MyActor extends Actor{
  val log = Logging(context.system, this)

  override def receive= {
    case "test" => log.info("Received \"test\"")
    case _  => log.info("Received unknown message")
  }
}
