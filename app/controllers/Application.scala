package controllers

import akka.util.Timeout
import akka.pattern.ask
import akka.actor.ActorRef

import play.api._
import play.api.mvc._
import play.api.libs.concurrent.Akka
import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._

import scala.concurrent.Future
import scala.concurrent.duration._

import actors.ParkingActor._

object Application extends Controller {

  val parking = Akka.system.actorSelection("user/parking")
  implicit val timeout = Timeout(2 seconds)

  def index = Action.async {
    val parkingStatusFuture = (parking ? GetParkingStatus).mapTo[Int]
    parkingStatusFuture.map {
      case res: Int => Ok(Json.obj(
        "status" -> "OK",
        "freeSlots" -> res))
    }

  }

}