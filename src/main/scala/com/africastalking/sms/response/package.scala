package com.africastalking.sms

import request._

package object response {

  case class SendMessageResponse(
    SMSMessageData: SMSMessageData
  )

  case class SMSMessageData(
    Message: String,
    Recipients: List[Recipient]
  )

  case class FetchMessageResponse(
    SMSMessageData: FetchSMSData
  )

  case class FetchSMSData(
    Messages: List[FetchMessage]
  )
}
