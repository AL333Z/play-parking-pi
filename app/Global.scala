import actors._

import akka.actor.Props

import play.api.Play.current
import play.api._
import play.api.libs.concurrent.Akka

object Global extends GlobalSettings {

  override def onStart(app: Application) {

    Logger.info("*********Application has started.****************************")

    val udpActor = Akka.system.actorOf(UPDMulticastWrapperActor.props, "udp")

    val greenLedActor = Akka.system.actorOf(LedActor.props(0), "greenLedActor")
    val redLedActor = Akka.system.actorOf(LedActor.props(1), "redLedActor")

    val parkingActor = Akka.system.actorOf(ParkingActor.props(2, greenLedActor,
      redLedActor), "parking")

    // starts the udpActor work, sending him the parkingActor ref
    udpActor ! parkingActor
  }

}
