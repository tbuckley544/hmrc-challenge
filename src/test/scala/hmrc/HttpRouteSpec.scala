package hmrc

import akka.event.LoggingAdapter
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.json4s.{DefaultFormats, Formats, Serialization, native}
import org.scalatest.{Matchers, WordSpec}
import Offers._

class HttpRouteSpec extends WordSpec with Matchers with ScalatestRouteTest {

  implicit val log: LoggingAdapter = system.log
  implicit val serialization: Serialization = native.Serialization
  implicit val formats: Formats = DefaultFormats + Items

  "A Http Route" should {
    "calculate the total of a given list of apples and oranges" in {
      val checkout: Checkout = Checkout(Vector())
      Post("/checkout", """["apple", "orange", "apple", "orange"]""") ~> HttpRoute(checkout) ~> check {
        responseAs[String] shouldBe "1.70"
      }

      Post("/checkout", """["apple", "apple", "apple", "apple"]""") ~> HttpRoute(checkout) ~> check {
        responseAs[String] shouldBe "2.40"
      }

      Post("/checkout", """["orange", "orange", "orange", "orange"]""") ~> HttpRoute(checkout) ~> check {
        responseAs[String] shouldBe "1.00"
      }
    }

    "calculate the total of a given list of apples and oranges when offers are active" in {
      val checkout: Checkout = Checkout(Vector(buyOneGetOneFreeApples, threeForTwoOranges))
      Post("/checkout", """["apple", "orange", "apple", "orange"]""") ~> HttpRoute(checkout) ~> check {
        responseAs[String] shouldBe "1.10"
      }

      Post("/checkout", """["apple", "apple", "apple", "apple"]""") ~> HttpRoute(checkout) ~> check {
        responseAs[String] shouldBe "1.20"
      }

      Post("/checkout", """["orange", "orange", "orange", "orange"]""") ~> HttpRoute(checkout) ~> check {
        responseAs[String] shouldBe "0.75"
      }
    }
  }
}
