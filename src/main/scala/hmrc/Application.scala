package hmrc

import akka.actor.ActorSystem
import akka.event.LoggingAdapter
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import org.json4s.{DefaultFormats, Formats, Serialization, native}

import scala.concurrent.ExecutionContext

object Application extends App {

  implicit val system: ActorSystem = ActorSystem("challenge")
  implicit val mat: ActorMaterializer = ActorMaterializer()
  implicit val log: LoggingAdapter = system.log
  implicit val serialization: Serialization = native.Serialization
  implicit val formats: Formats = DefaultFormats + Items
  implicit val ec: ExecutionContext = ExecutionContext.global

  val checkout = Checkout()

  val route = HttpRoute(checkout)

  Http().bindAndHandle(route, "0.0.0.0", 8080)

}
