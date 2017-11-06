package hmrc

import org.scalatest.{Matchers, WordSpec}

class CheckoutSpec extends WordSpec with Matchers {

  val subjectUnderTest: Checkout = Checkout()

  "A Checkout" should {
    "calculate the value of a given list of items" in {
      subjectUnderTest.calculatePrice(Vector.fill(5)(Apple())) shouldBe 3.0
      subjectUnderTest.calculatePrice(Vector.fill(5)(Orange())) shouldBe 1.25
      subjectUnderTest.calculatePrice(Vector.fill(5)(Apple()) ++ Vector.fill(5)(Orange())) shouldBe 4.25
    }
  }

}
