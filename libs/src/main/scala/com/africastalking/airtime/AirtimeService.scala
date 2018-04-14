package com.africastalking.airtime

import akka.http.scaladsl.model._
import com.africastalking.core.utils.TServiceConfig
import com.africastalking.core.commons.TService
import spray.json._
import scala.concurrent.Future

object AirtimeService extends TAirtimeService {
  import AirtimeJsonProtocol._

  override def send(airtime: AirtimeRequest) : Future[Either[String, AirtimeResponse]] = {
    val airtimePayload = Map(
      "username"  -> username,
      "recipients" -> airtime.toString
    )

    val entity = FormData(airtimePayload).toEntity

    callEndpoint(entity, "airtime/send", payload => payload.parseJson.convertTo[AirtimeResponse])
  }
}

trait TAirtimeService extends TService with TServiceConfig {
  def send(airtime: AirtimeRequest) : Future[Either[String, AirtimeResponse]]
}
