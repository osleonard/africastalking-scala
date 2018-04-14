
package com.africastalking.examples
package sms

import com.africastalking.sms.request._
import com.africastalking.sms.SmsService

import scala.concurrent.ExecutionContext.Implicits.global

object SmsExample extends TApiExamples {

  def main(args: Array[String]): Unit = {
    sendMessage()
    fetchMessages()
  }

  private def sendMessage() {
    val message = Message(
      text       = "Hello World From Lagos Scala",
      recipients = List("+2348063363424")
    )

    SmsService
      .send(message)
      .onComplete(processResult)
  }

  private def fetchMessages() {
    SmsService
      .fetchMessages()
      .onComplete(processResult)
  }
}