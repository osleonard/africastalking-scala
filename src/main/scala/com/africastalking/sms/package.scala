package com.africastalking

import com.africastalking.core.utils.DefaultJsonFormatter

package object sms {

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

}
