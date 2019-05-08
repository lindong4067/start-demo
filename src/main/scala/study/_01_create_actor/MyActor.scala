package study._01_create_actor

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, IndirectActorProducer, Props}
import akka.event.Logging

class MyActor extends Actor{
  val log = Logging(context.system, this)

  override def receive= {
    case "test" => log.info("Received \"test\"")
    case _  => log.info("Received unknown message")
  }

  override def preStart(): Unit = super.preStart()

  override def postStop(): Unit = super.postStop()

  override def preRestart(reason: Throwable, message: Option[Any]): Unit = super.preRestart(reason, message)

  override def postRestart(reason: Throwable): Unit = super.postRestart(reason)
}

class MyActor2(args: String*) extends Actor {
  val log = Logging(context.system, this)

  override def receive: Receive = {
    case "test" => log.info("Received \"test\"")
    case _  => log.info("Received unknown message")
  }
}

object MyActor3 {
  case class Greeting(from: String)
  case object Goodbye
}
class MyActor3 extends Actor with ActorLogging {
  import MyActor3._
  override def receive: Receive = {
    case Greeting(greeter) => log.info(s"I was greeted by $greeter")
    case Goodbye => log.info("Someone said goodbye to me")
  }
}

class FirstActor extends Actor {
  val myChild: ActorRef = context.actorOf(Props[MyActor3], "myChild")
  override def receive: Receive = {
    case x => sender() ! x; myChild ! x
  }
}

class Argument(val value: String) extends AnyVal
class ValueClassActor(argument: Argument) extends Actor {
  override def receive: Receive = {
    case _  => ()
  }
}
object ValueClassActor {
  //def props1(arg: Argument) = Props(classOf[ValueClassActor], arg) // fails at runtime
  def props2(arg: Argument) = Props(classOf[ValueClassActor], arg.value) // ok
  def props3(arg: Argument) = Props(new ValueClassActor(arg)) // ok
}

//DI
class DependencyInjector(applicationContext: AnyRef, beanName: String) extends IndirectActorProducer {

  override def actorClass: Class[Actor] = classOf[Actor]
//  override def produce = new Echo(beanName)
  def this(beanName: String) = this("", beanName)

  override def produce(): Actor = new MyActor3
}

object MyActorDemo extends App {
  val props1 = Props[MyActor]
  val props2 = Props(new MyActor2("AAA", "BBB"))
  val props3 = Props(classOf[MyActor2], List("AAA", "BBB"))

  //Create actor in "ActorSystem" and "ActorContext"
  val system = ActorSystem("mySystem")
  val myActor3 = system.actorOf(Props[MyActor3], "myActor3")
  val other = ActorSystem("otherSystem")
  //DI
  val actorRef = system.actorOf(Props(classOf[DependencyInjector], other, "hello"), "helloBean")

}
