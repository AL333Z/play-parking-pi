package actors

import akka.actor.Actor
import akka.actor.ActorRef
import akka.event.LoggingReceive
import akka.actor.Props
import akka.actor.PoisonPill

import actors.ParkingActor._

import msglib._

/**
 * Companion object for UPDMulticastWrapperActor.
 */
object UPDMulticastWrapperActor {
  def props(): Props = Props(classOf[UPDMulticastWrapperActor])
}

/**
 * An actor that redirects udp messages from the bridge to the parking actor.
 * This actor only receive one message that starts its work and then enter in an
 *  infite loop, mapping udp messages in command to send to parking actor.
 *
 *  NOTE: normally this is not a good practice, but the APIs used to receive a
 *  msg from UDP are blocking, so wasting one thread here may be reasonable.
 */
class UPDMulticastWrapperActor() extends Actor {

  def receive: Receive = {
    case parkingActor: ActorRef => {

      println("UPD listener started.")

      val msgService = new MsgServiceUDPMulticast()
      msgService.init("centralunit")

      while (true) {
        try {
          val msg = msgService.receiveMsg(new MsgToMePattern("centralunit"))
          println(msg.getSender() + ": " + msg.getContent())

          msg.getContent match {
            case "ARRIVED" => parkingActor ! CarArrived
            case "GONE"    => parkingActor ! CarGone
          }

        } catch {
          case t: Throwable =>
            println("Failed receiveMsg. Killing myself.." + t.toString())
            self ! PoisonPill
        }

      }
    }
  }
}