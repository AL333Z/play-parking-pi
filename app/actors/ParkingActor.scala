package actors

import akka.actor.ActorLogging
import akka.actor.Actor
import akka.actor.ActorRef
import akka.event.LoggingReceive
import akka.actor.Props

import actors.ParkingActor._
import actors.LedActor._

/**
 * Companion object for ParkingActor.
 * Contains the messages it accepts
 */
object ParkingActor {
  def props(capacity: Int, redActor: ActorRef, greenActor: ActorRef): Props =
    Props(classOf[ParkingActor], capacity: Int, redActor: ActorRef, greenActor: ActorRef)

  object GetParkingStatus
  object CarGone
  object CarArrived
}

/**
 * An parking with a fixed number of slots.
 */
class ParkingActor(capacity: Int, redActor: ActorRef, greenActor: ActorRef)
  extends Actor with ActorLogging {

  def receive = LoggingReceive { parkingStatus(capacity) }

  def parkingStatus(freeSlots: Int): Receive = {
    case CarArrived =>
      if (freeSlots == 1) { // had 1 free slot, and a car is arrived..
        greenActor ! SwitchOff
        redActor ! SwitchOn
      }
      context.become(parkingStatus(freeSlots - 1))
    case CarGone =>
      if (freeSlots == 0) { // was full, and a car is gone
        greenActor ! SwitchOn
        redActor ! SwitchOff
      }
      context.become(parkingStatus(freeSlots + 1))
    case GetParkingStatus => sender ! freeSlots
  }

}