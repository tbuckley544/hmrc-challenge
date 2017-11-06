import org.json4s.CustomSerializer
import org.json4s.JsonAST.{JNull, JString}

package object hmrc {

  sealed trait Item {
    val price: BigDecimal
  }

  case class Apple(price: BigDecimal = 0.6) extends Item

  case class Orange(price: BigDecimal = 0.25) extends Item

  case object Items extends CustomSerializer[Item](format => ( {
    case JString(s) if s.toLowerCase.equals("apple") => Apple()
    case JString(s) if s.toLowerCase.equals("orange") => Orange()
    case JNull => null
  }, {
    case Apple(_) => JString("apple")
    case Orange(_) => JString("orange")
  })
  )

}
