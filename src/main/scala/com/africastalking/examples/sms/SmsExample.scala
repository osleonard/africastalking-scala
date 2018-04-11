
package com.africastalking.examples
package sms
import com.africastalking.sms
import com.africastalking.sms.SmsService
import com.typesafe.scalalogging.LazyLogging
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object SmsExample extends App with LazyLogging{
  var messageObject = sms.Message("Hello World From Lagos Scala",List("+2348063363424"),None)
  val responseFromApi = SmsService.send(messageObject)
  responseFromApi onComplete {
    case Success(message) => message match {
      case Right(smsResponse) => logger.info(smsResponse.recipients.toString)
      case Left(responseException) => logger.info(responseException.toString)
    }
    case Failure(ex) => logger.error(ex.getMessage)
  }
}