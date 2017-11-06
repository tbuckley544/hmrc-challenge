package hmrc

object Checkout {
  def apply(): Checkout = new Checkout()
}

class Checkout {
  def calculatePrice(items: Vector[Item]): BigDecimal = items.map(_.price).sum
}
