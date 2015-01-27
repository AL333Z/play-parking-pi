import actors._
import akka.actor.Props
import play.api.Play.current
import play.api._
import play.api.libs.concurrent.Akka

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    Logger.info("**********************************************************")
    Logger.info("******************Application has started.****************")
    Logger.info("**********************************************************")

    val udpActor = Akka.system.actorOf(UPDMulticastWrapperActor.props, "udp")
    val parkingActor = Akka.system.actorOf(ParkingActor.props(2), "parking")

    // starts the udpActor work, sending him the parkingActor ref
    udpActor ! parkingActor
  }

}
