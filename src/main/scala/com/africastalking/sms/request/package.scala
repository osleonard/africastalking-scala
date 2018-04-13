package com.africastalking.sms

package object request {

  case class Message(
    text: String,
    recipients: List[String],
    senderId: Option[String] = None
  )

  case class Recipient(
    number: String,
    status: String,
    cost: String,
    messageId: String
  )


  case class Subscription(
    id: Long,
    phoneNumber: String,
    date: String
 )
  case class FetchMessage(
     from : String,
     to : String,
     text : String,
     linkId : String,
     date : String,
     id : Long
  )

}
