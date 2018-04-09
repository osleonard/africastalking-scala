package com.africastalking.sms

import akka.http.scaladsl.model.headers.{Accept, RawHeader}
import akka.http.scaladsl.model._
import com.africastalking.core.commons.TService
import com.africastalking.core.utils.TServiceConfig
import scala.concurrent.ExecutionContext.Implicits.global
import spray.json._
import scala.collection.mutable
import scala.concurrent.Future
import SendMessageResponse._
object SmsService extends TSmsService {

  import SmsJsonProtocol._

  override def send(message: Message, enqueue: Int=0): Future[Either[String, SmsMessageData]] = {
    val response = callEndpoint(message, enqueue, "messaging")
    response

  }

  override def sendPremium(message: Message, keyword: String, linkId: String, retryDurationInHours: Long) = ???

  private def callEndpoint(message: Message, enqueue: Int, endpoint: String): Future[Either[String, SmsMessageData]] = {
    val url = s"$environmentDomain$endpoint"
    val request: HttpRequest = HttpRequest(
      method = HttpMethods.POST,
      uri = url,
      headers = List(RawHeader("apiKey", apiKey),Accept(MediaTypes.`application/json`)),
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
          case StatusCodes.OK => Right(response.payload.toJson.convertTo[SmsMessageData])
          case _ => Left(s"Sorry, ${response.payload}")
        }
      }
  }
}

trait TSmsService extends TService with TServiceConfig {

  def send(message: Message, enqueue: Int): Future[Either[String, SmsMessageData]]

  def sendPremium(message: Message, keyword: String, linkId: String, retryDurationInHours: Long): Future[Either[String, SmsMessageData]]

}
