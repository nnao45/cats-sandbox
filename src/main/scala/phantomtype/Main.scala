package phantomtype

import enumeratum._

sealed abstract class NetworkType(val index: Int, val name: String) extends EnumEntry

object NetworkType extends Enum[NetworkType] {
  @inline
  private[this] final case object EmptyReservation extends NetworkType(0, "unused")

  /** モバイルアプリ */
  case object MobileApp extends NetworkType(1, "mobile_app")

  /** docomo 向け */
  case object Docomo extends NetworkType(2, "web_docomo")

  val values = findValues
}

object Main extends App {
  val a1 = Some(1)
  val b1 = NetworkType.MobileApp
  (b1: NetworkType) match {
    case NetworkType.Docomo => println("docomo")
    case NetworkType.MobileApp => println("app")
  }
}
