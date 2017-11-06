package hmrc

object Offers {

  def buyOneGetOneFreeApples: Vector[Item] => Vector[Item] = items => {
    val count = items.count(_.getClass == classOf[Apple])
    val rem = count % 2
    Vector.fill((count - rem) / 2 + rem)(Apple()) ++ items.filterNot(_.getClass == classOf[Apple])
  }

  def threeForTwoOranges: Vector[Item] => Vector[Item] = items => {
    val count = items.count(_.getClass == classOf[Orange])
    val rem = count % 3
    Vector.fill((count - rem) / 3 * 2 + rem)(Orange()) ++ items.filterNot(_.getClass == classOf[Orange])
  }

}
