package com.africastalking.sms

import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.unmarshalling.Unmarshal
import com.africastalking.core.commons.Service
import com.africastalking.core.utils.{DefaultJsonFormatter, ErrorResponseException, ServiceConfig}
import com.sun.xml.internal.ws.resources.SenderMessages

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

case class Message( message: String, recipients: List[String], senderId: Option[String] = None)

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

object SmsService extends SmsService {


  override def send(message: Message, enqueue: Boolean) = ???

  override def sendPremium: Unit = ???
}

trait SmsService extends Service with ServiceConfig{

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
