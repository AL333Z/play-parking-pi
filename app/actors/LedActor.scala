package actors

import akka.actor.ActorLogging
import akka.actor.Actor
import akka.actor.ActorRef
import akka.event.LoggingReceive
import akka.actor.Props
import actors.LedActor._
import devices.Led

/**
 * Companion object for LedActor.
 * Contains the messages it accepts
 */
object LedActor {
  def props(pin: Int): Props = Props(classOf[LedActor], pin: Int)

  object SwitchOn
  object SwitchOff
}

/**
 * An parking with a fixed number of slots.
 */
class LedActor(pin: Int) extends Actor with ActorLogging {

  val led = new Led(pin)
  
  led.switchOn() //TODO remove this..

  def receive = LoggingReceive {
    case SwitchOn  => led.switchOn()
    case SwitchOff => led.switchOff()
  }

}