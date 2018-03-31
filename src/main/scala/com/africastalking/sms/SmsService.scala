package com.africastalking.sms

import com.africastalking.core.utils.{DefaultJsonFormatter, Service}

case class Message(to : List[String], message : String, from : Option[String], bulkSMSMode : Option[Boolean], linkId : Option[String])

case class Recipient(number : String, cost : String, status : String, messageId : String)

case class Subscription(id: Long, phoneNumber : String, date : String)


sealed trait MessageResponse

case object SendMessageResponse extends MessageResponse {

  case class SmsMessageData(recipients : List[Recipient])
}

case class SubscriptionResponse(success : String, description : String) extends MessageResponse

case object FetchMessageResponse extends MessageResponse {

  case class  SmsMessageData(messages : List[Message])
}

class SmsService(username : String, apiKey : String) extends Service with  DefaultJsonFormatter {


}
