package hmrc

import org.scalatest.{Matchers, WordSpec}
import Offers._

class CheckoutSpec extends WordSpec with Matchers {

  "A Checkout" should {
    "calculate the value of a given list of items" in {
      val subjectUnderTest: Checkout = Checkout(Vector())
      subjectUnderTest.calculatePrice(Vector.fill(5)(Apple())) shouldBe 3.0
      subjectUnderTest.calculatePrice(Vector.fill(5)(Orange())) shouldBe 1.25
      subjectUnderTest.calculatePrice(Vector.fill(5)(Apple()) ++ Vector.fill(5)(Orange())) shouldBe 4.25
    }

    "calculate value of apples when buy one get one free is active" in {
      val subjectUnderTest: Checkout = Checkout(Vector(buyOneGetOneFreeApples))
      subjectUnderTest.calculatePrice(Vector.fill(5)(Apple())) shouldBe 1.8
      subjectUnderTest.calculatePrice(Vector.fill(2)(Apple())) shouldBe 0.6
    }

    "calculate value of oranges when buy 3 for price of 2 is active" in {
      val subjectUnderTest: Checkout = Checkout(Vector(threeForTwoOranges))
      subjectUnderTest.calculatePrice(Vector.fill(5)(Orange())) shouldBe 1.0
      subjectUnderTest.calculatePrice(Vector.fill(3)(Orange())) shouldBe 0.5
    }
  }

}
