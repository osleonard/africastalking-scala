package com.africastalking.examples
package airtime

import com.africastalking.airtime
import com.africastalking.airtime.AirtimeService
import com.typesafe.scalalogging.LazyLogging
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object AirtimeExample extends App with LazyLogging {

  val payload = airtime.Airtime("+2348063363424", "NGN 50")
  val response = AirtimeService.send(payload)
  response onComplete {
    case Success(message) => message match {
      case Right(airtimeResponse) => logger.info(airtimeResponse.toString)
      case Left(apiResponseException) => logger.error(apiResponseException.toString)
    }
    case Failure(ex) => logger.error(ex.getMessage)
  }

}
