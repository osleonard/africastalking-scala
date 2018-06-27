package com.africastalking.airtime

import akka.http.scaladsl.model._
import com.africastalking.core.commons.TService
import com.africastalking.core.utils.TServiceConfig
import scala.concurrent.Future
import spray.json._

object AirtimeService extends TAirtimeService {
  import AirtimeJsonProtocol._

  /**
    * This function sends airtime asynchronously , it takes recipient and amount to be sent
    * Minimum airtime that can be sent is 50 NGN in case of Nigeria users
    * A string response is returned in case of any exception and an airtime response is
    * returned when request is fulfilled
    * @param airtime
    * @return
    */
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
