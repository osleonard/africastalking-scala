package com.africastalking.examples.application

import com.africastalking.application.ApplicationService
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.ExecutionContext.Implicits.global

import scala.util.{Success, Failure}

object ApplicationServiceExample extends App with LazyLogging {
  ApplicationService.fetchApplicationData onComplete {
    case Success(response) =>
      response match {
        case Right(payload) =>
          logger.info(payload.UserData.balance)
        case Left(errorMessage) =>
          logger.error(errorMessage)
      }
    case Failure(exception) =>
      logger.error(exception.getLocalizedMessage)
  }
}
