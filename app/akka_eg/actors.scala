package akka_eg

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

class actors (number : Int)extends Actor {
    def receive = {
      case x : Int => println(s"got Integer : $x  from customer no. : $number")
      case y: String => println(s"got string $y from customer no. : $number")
      case _ => println("got nothing meaningful ")
    }
}

object main extends App {
  val system = ActorSystem("HelloSystem")
  val helloActor = system.actorOf(Props(new actors(4)), name = "helloactor")

  helloActor ! 22
  helloActor ! "dasd"
  helloActor ! true

  //system.stop(helloActor)


}


