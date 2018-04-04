package com.africastalking.sms

import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{FormData, HttpMethods, HttpRequest, StatusCodes}
import com.africastalking.core.commons.TService
import com.africastalking.core.utils.TServiceConfig
import scala.concurrent.ExecutionContext.Implicits.global
import spray.json._
import scala.collection.mutable
import scala.concurrent.Future
import SendMessageResponse._

object SmsService extends TSmsService  {

  import SmsJsonProtocol._

  override def send(message: Message, enqueue: Boolean): Future[Either[String, SmsMessageData]] =
    callEndpoint(message, enqueue, "messaging")

  override def sendPremium(): Unit = ???


  private def callEndpoint(message: Message, enqueue: Boolean, endpoint: String): Future[Either[String, SmsMessageData]] = {
    val url = s"$environmentDomain$endpoint"
    val request: HttpRequest = HttpRequest(
      method = HttpMethods.POST,
      uri = url,
      headers = List(RawHeader("apiKey", apiKey)),
      entity = {
        val data = mutable.Map(
          "username" -> username,
          "to" -> message.recipients.mkString(","),
          "message" -> message.text,
          "enqueue" -> enqueue.toString
        )
        message.senderId match {
          case Some(sender) => data += ("from" -> sender)
          case None => None
        }

        FormData(data.toMap).toEntity
      }
    )

    makeRequest(request)
      .map { response =>
        response.responseStatus match {
          case  StatusCodes.OK => Right(response.payload.toJson.convertTo[SmsMessageData])
          case _ => Left("Sorry, something went wrong")
        }
      }
  }
}

trait TSmsService extends TService with TServiceConfig {
  def send(message: Message, enqueue : Boolean = false) : Future[Either[String, SmsMessageData]]

  def sendPremium() : Unit


/*
  def send(): Future[SendMessageResponse.SmsMessageData] = {
    val responseFromApi = makeRequest(apiKey, "messaging", "POST", false, Message)
    responseFromApi.flatMap {
      case HttpResponse(StatusCodes.OK, _, _, entity) => Unmarshal(entity).to[SendMessageResponse.SmsMessageData]
      case resp @ HttpResponse(code, _, _, _) => resp.discardEntityBytes()
        Future.failed{ErrorResponseException(code, Some("Error Occurred"))}
    }
  }
*/
}
