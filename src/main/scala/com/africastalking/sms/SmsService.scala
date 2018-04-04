package com.africastalking.sms

import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{FormData, HttpMethods, HttpRequest, StatusCodes}
import com.africastalking.core.commons.TService
import com.africastalking.core.utils.{DefaultJsonFormatter, TServiceConfig}

import scala.concurrent.ExecutionContext.Implicits.global
import spray.json._

import scala.collection.mutable
import scala.concurrent.Future

case class Message(text: String, recipients: List[String], senderId: Option[String] = None)

case class Recipient(number: String, cost: String, status: String, messageId: String)

case class Subscription(id: Long, phoneNumber: String, date: String)


sealed trait MessageResponse

case object SendMessageResponse extends MessageResponse {

  case class SmsMessageData(recipients: List[Recipient])

}

case class SubscriptionResponse(success: String, description: String) extends MessageResponse

case object FetchMessageResponse extends MessageResponse {

  case class SmsMessageData(messages: List[Message])

}

object SmsJsonProtocol extends DefaultJsonFormatter {
  implicit val recipientFormat = jsonFormat4(Recipient)
  implicit val sendSmsResponseFormat = jsonFormat1(SendMessageResponse.SmsMessageData)
}

object SmsService extends TSmsService  {

  import SmsJsonProtocol._

 /* override def send(message: Message, enqueue: Boolean = false) :  {
    callEndpoint(message, enqueue, "messaging")
  }
*/

  override def send(message: Message, enqueue: Boolean) = ???

  override def sendPremium: Unit = ???


  private def callEndpoint(message: Message, enqueue: Boolean, endpoint: String): Future[Option[SendMessageResponse.SmsMessageData]] = {
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
          case  StatusCodes.OK => Some(response.payload.toJson.convertTo[SendMessageResponse.SmsMessageData])
          case _ => None
        }
      }
  }
}

trait TSmsService extends TService with TServiceConfig {

  def send(message: Message, enqueue : Boolean = false) : Future[SendMessageResponse.SmsMessageData]

  def sendPremium : Unit


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
