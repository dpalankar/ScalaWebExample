package akka_eg

import java.util.concurrent.TimeUnit

import akka.actor.{Actor, ActorSystem, Props}

import scala.concurrent.Future

class SimpleActor extends Actor {
  override def receive = {
    case anyMessage@_ => {
      println(s"Received ${anyMessage}")
    }
  }
}

class AnotherActor extends Actor {
  override def receive = {
    case anyMessage@_ => {
      println(s"I am the second one ${anyMessage}")
    }
  }
}

object SimpleActorApp extends App {
  implicit val executionContext = scala.concurrent.ExecutionContext.global

  val system = ActorSystem()

//  val actorRef = system.actorOf(classOf[SimpleActor])
//  val actorRef = system.actorOf(Props(new SimpleActor()), name = "SimpleActor")
  val actorRef = system.actorOf(Props[SimpleActor], name = "SimpleActor")
  for (a <- 1 to 1000) {
    Future { //try sending message concurrently to actor
      actorRef ! s"Hello Actor${a}"
    }
  }


//  val anotherActorRef = system.actorOf(classOf[AnotherActor])
  val anotherActorRef = system.actorOf(Props(new AnotherActor()), name = "AnotherActor")
  for (a <- 1 to 1000) {
    Future { //try sending message concurrently to actor
      anotherActorRef ! s"Hello Second Actor${a}"
    }
  }

  system.terminate()
//  system.awaitTermination(10, TimeUnit.SECONDS) //figure out why we need to do this.
}