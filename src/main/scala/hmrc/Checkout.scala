package hmrc

object Checkout {
  def apply(offers: Vector[Vector[Item] => Vector[Item]]): Checkout = new Checkout(offers)
}

class Checkout(offers: Vector[Vector[Item] => Vector[Item]]) {
  def calculatePrice(items: Vector[Item]): BigDecimal = offers.foldLeft(items)((i, offer) => offer(i)).map(_.price).sum
}
