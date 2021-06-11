package akka_eg

import akka.actor._

case object PingMessage
case object PongMessage
case object StartMessage
case object StopMessage

class Ping(pong: ActorRef) extends Actor {
  var count = 0
  def incrementAndPrint: Unit ={ count += 1; println(s"ping $count") }
  def receive = {
    case StartMessage =>
      incrementAndPrint
      pong ! PingMessage
    case PongMessage =>
      incrementAndPrint
      if (count > 10) {
        sender ! StopMessage
        println("ping stopped")
        context.stop(self)
      } else {
        sender ! PingMessage
      }
    case _ => println("Ping got something unexpected.")
  }
}

class Pong extends Actor {
  def receive = {
    case PingMessage =>
      println(" pong")
      sender ! PongMessage
    case StopMessage =>
      println("pong stopped")
//      context.stop(self)
//      context.system.stop(self)
        context.system.terminate()
//      OR
//      System.exit(0)
    case _ => println("Pong got something unexpected.")
  }
}

object PingPongTest extends App {

  val system = ActorSystem("PingPongSystem")

//  Way 1
  val pong = system.actorOf(Props[Pong], name = "pong")
  val ping = system.actorOf(Props(new Ping(pong)), name = "ping")

//  Way 2
//  val pong = system.actorOf(Props(new Pong()), name = "pong")
//  val ping = system.actorOf(Props(new Ping(pong)), name = "ping")

  ping ! StartMessage


}
