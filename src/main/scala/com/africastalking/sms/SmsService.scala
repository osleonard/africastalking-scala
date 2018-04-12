package com.africastalking.sms

import akka.http.scaladsl.model._
import com.africastalking.core.commons.TService
import com.africastalking.core.utils.TServiceConfig
import spray.json._
import scala.collection.mutable
import scala.concurrent.Future
import SendMessageResponse._

object SmsService extends TSmsService {

  import SmsJsonProtocol._

  override def send(message: Message, enqueue: Int = 0): Future[Either[String, SmsMessageData]] = {
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
    callEndpoint(entity, "messaging", payload => payload.parseJson.convertTo[SmsMessageData])
  }

  override def sendPremium(message: Message, keyword: String, linkId: String, retryDurationInHours: Long) = ???
}

trait TSmsService extends TService with TServiceConfig {

  def send(message: Message, enqueue: Int): Future[Either[String, SmsMessageData]]

  def sendPremium(message: Message, keyword: String, linkId: String, retryDurationInHours: Long): Future[Either[String, SmsMessageData]]

  override lazy val environmentHost: String = if(environ.toLowerCase.equals("production")) apiProductionHost else apiSandboxHost
}
