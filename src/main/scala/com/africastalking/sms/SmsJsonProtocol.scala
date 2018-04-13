package com.africastalking.sms
import com.africastalking.core.utils.DefaultJsonFormatter
import com.africastalking.sms.request._
import com.africastalking.sms.response.{FetchMessageResponse, FetchSMSData, SMSMessageData, SendMessageResponse}
object SmsJsonProtocol extends DefaultJsonFormatter {
  implicit val messageFormat = jsonFormat3(Message)
  implicit val reciepientsFormat = jsonFormat4(Recipient)
  implicit val smsMessageDataFormat = jsonFormat2(SMSMessageData)
  implicit val sendSmsMessageFormat = jsonFormat1(SendMessageResponse)
  implicit val fatchMessageFormat = jsonFormat6(FetchMessage)
  implicit val fetchSmsDataFormat = jsonFormat1(FetchSMSData)
  implicit val fetchMessageResponseFormat = jsonFormat1(FetchMessageResponse)
}
