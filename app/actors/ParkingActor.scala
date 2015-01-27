package actors

import akka.actor.ActorLogging
import akka.actor.Actor
import akka.actor.ActorRef
import akka.event.LoggingReceive
import akka.actor.Props

import actors.ParkingActor._

/**
 * Companion object for ParkingActor.
 * Contains the messages it accepts
 */
object ParkingActor {
  def props(capacity: Int): Props = Props(classOf[ParkingActor], capacity: Int)

  object GetParkingStatus
  object CarGone
  object CarArrived
}

/**
 * An parking with a fixed number of slots.
 */
class ParkingActor(capacity: Int) extends Actor with ActorLogging {

  def receive = LoggingReceive { parkingStatus(capacity) }

  def parkingStatus(freeSlots: Int): Receive = {
    case CarArrived       => context.become(parkingStatus(freeSlots - 1))
    case CarGone          => context.become(parkingStatus(freeSlots + 1))
    case GetParkingStatus => sender ! freeSlots
  }

}