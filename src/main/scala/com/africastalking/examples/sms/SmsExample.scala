
package com.africastalking.examples
package sms

import com.africastalking.sms.request._
import com.africastalking.sms.SmsService
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object SmsExample extends App with LazyLogging {

  def sendMessageExample(): Unit = {

    val messageObject = Message("Hello World From Lagos Scala", List("+2348063363424"), None)
    val responseFromApi = SmsService.send(messageObject)
    responseFromApi onComplete {
      case Success(message) => message match {
        case Right(smsResponse) => logger.info(smsResponse.toString)
        case Left(responseException) => logger.info(responseException.toString)
      }
      case Failure(ex) => logger.error(ex.getMessage)
    }

  }

  def fetchMessagesExample(): Unit = {
    val responseFromApi = SmsService.fetchMessages()
    responseFromApi onComplete {
      case Success(message) => message match {
        case Right(fetchMessageResponse) => logger.info(fetchMessageResponse.toString)
        case Left(responseException) => logger.info(responseException.toString)
      }
      case Failure(ex) => logger.error(ex.getMessage)
    }

  }

}