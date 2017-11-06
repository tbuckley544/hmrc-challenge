package hmrc

import akka.event.LoggingAdapter
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import akka.util.ByteString
import org.json4s.native.Serialization.read
import org.json4s.{Formats, Serialization}

import scala.concurrent.ExecutionContext


object HttpRoute {

  def apply(checkout: Checkout)(implicit mat: ActorMaterializer, logger: LoggingAdapter, serialization: Serialization, formats: Formats, ec: ExecutionContext): Route =
    (pathPrefix("checkout") & post) { req =>
      req.complete(req.request.entity.dataBytes.runFold(ByteString.empty)(_ ++ _).map(bs => read[Vector[Item]](bs.utf8String)).map { items =>
        logger.info(s"Receieved event checkout items: [$items]")
        val price = checkout.calculatePrice(items)
        logger.info(s"price for items is $price")
        HttpEntity(ContentTypes.`application/json`, ByteString(price.setScale(2).toString()))
      })
    }
}
