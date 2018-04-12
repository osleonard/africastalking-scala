package com.africastalking.examples

import com.typesafe.scalalogging.LazyLogging

import scala.util.{Failure, Success, Try}

trait TApiExamples extends LazyLogging {
  def processResult[T](result: Try[T]): Unit = result match {
    case Success(response) =>
      response match {
        case Right(payload) =>
          logger.info(payload.toString)
        case Left(errorMessage: String) =>
          logger.error(errorMessage)
      }
    case Failure(exception) =>
      logger.error(exception.getLocalizedMessage)
  }
}
