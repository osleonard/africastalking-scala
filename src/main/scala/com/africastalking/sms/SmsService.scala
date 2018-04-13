package com.africastalking.sms

import akka.http.scaladsl.model._
import com.africastalking.core.commons.TService
import com.africastalking.core.utils.TServiceConfig
import spray.json._
import scala.collection.mutable
import scala.concurrent.Future
import akka.http.scaladsl.model.headers.{Accept, RawHeader}
import com.africastalking.sms.request.Message
import com.africastalking.sms.response.{FetchMessageResponse, SMSMessageData, SendMessageResponse}

import scala.concurrent.ExecutionContext.Implicits.global

object SmsService extends TSmsService {

  import SmsJsonProtocol._

  override def send(message: Message, enqueue: Int = 0): Future[Either[String, SendMessageResponse]] = {
    val entity = {
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
    callEndpoint(entity, "messaging", payload => payload.parseJson.convertTo[SendMessageResponse])
  }

  override def sendPremium(message: Message, keyword: String, linkId: String, retryDurationInHours: Long) = ???

  override def fetchMessages(lastReceivedId: String = "0") : Future[Either[String, FetchMessageResponse]] = {
    val httpRequest = HttpRequest(
      method  = HttpMethods.GET,
      uri     = Uri(s"${environmentHost}messaging?username=$username&lastReceivedId=$lastReceivedId"),
      headers = List(RawHeader("apiKey", apiKey), Accept(MediaTypes.`application/json`))
    )
    makeRequest(httpRequest).map { response =>
      response.responseStatus match {
        case StatusCodes.OK | StatusCodes.Created => Right(response.payload.parseJson.convertTo[FetchMessageResponse])
        case _                                    => Left(s"Sorry, ${response.payload}")
      }
    }
  }
}

trait TSmsService extends TService with TServiceConfig {

  def send(message: Message, enqueue: Int): Future[Either[String, SendMessageResponse]]

  def sendPremium(message: Message, keyword: String, linkId: String, retryDurationInHours: Long): Future[Either[String, SMSMessageData]]

  def fetchMessages(lastReceivedId : String) : Future[Either[String, FetchMessageResponse]]
}
