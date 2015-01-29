import actors._

import akka.actor.Props

import play.api.Play.current
import play.api._
import play.api.libs.concurrent.Akka

object Global extends GlobalSettings {

  override def onStart(app: Application) {

    // Leds
    val greenLedActor = Akka.system.actorOf(LedActor.props(0), "greenLedActor") //GPIO 0
    val redLedActor = Akka.system.actorOf(LedActor.props(1), "redLedActor") //GPIO 1

    // Parking actor
    val parkingActor = Akka.system.actorOf(ParkingActor.props(2, greenLedActor,
      redLedActor), "parking")

    // Udp
    val udpActor = Akka.system.actorOf(UPDMulticastWrapperActor.props(parkingActor), "udp")

    Logger.info("*********Application has started.****************************")
  }

}
