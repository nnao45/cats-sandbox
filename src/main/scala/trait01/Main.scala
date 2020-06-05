package trait01

import io.circe.{Decoder, Encoder, Json}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

trait ParentAdCandidate

case class NativeAdCandidate(adId: Long,
                                        adGroupId: Long,
                                        campaignId: Long,
                                        maybeFactor: Option[Double] = None,
                                        maybeTitle: Option[String] = None,
                                        maybeDescription: Option[String] = None,
                                        maybeTextOverflowSetting: Option[String] = None)
extends ParentAdCandidate

object NativeAdCandidate {
  implicit val decoder: Decoder[NativeAdCandidate] = deriveDecoder[NativeAdCandidate]
  implicit val encoder: Encoder[NativeAdCandidate] = deriveEncoder[NativeAdCandidate]
}

case class StaticBannerAdCandidate(adId: Long,
                                   adGroupId: Long,
                                   campaignId: Long,
                                   maybeFactor: Option[Double] = None,
                                   maybeTitle: Option[String] = None,
                                   maybeDescription: Option[String] = None)
extends ParentAdCandidate

object StaticBannerAdCandidate {
  implicit val decoder: Decoder[StaticBannerAdCandidate] = deriveDecoder[StaticBannerAdCandidate]
  implicit val encoder: Encoder[StaticBannerAdCandidate] = deriveEncoder[StaticBannerAdCandidate]
}

object ParentAdCandidate {
  import io.circe.syntax._
  implicit val encodeResponse: Encoder[ParentAdCandidate] =
    Encoder.instance {
      case native @ NativeAdCandidate(_, _, _, _, _, _, _) => native.asJson
      case banner @ StaticBannerAdCandidate(_, _, _, _, _, _) => banner.asJson
    }
  def derive(e: ParentAdCandidate): Json =
    encodeResponse(e)
}

object Main extends App {
  import ParentAdCandidate._
  val native = NativeAdCandidate(1, 1, 1, None, None, None, None)
  val banner = StaticBannerAdCandidate(1, 1, 1, None, None, None)
  val bannerJson = derive(banner)
  val nativeJson = derive(native)
  println(bannerJson.toString())
  println(nativeJson.toString())
}