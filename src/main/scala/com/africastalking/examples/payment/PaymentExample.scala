package com.africastalking.examples.payment

import com.africastalking.core.utils.CurrencyCode
import com.africastalking.payment.{CheckoutRequest, PaymentService}
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}


object PaymentExample extends App with LazyLogging {

  val checkoutRequest = CheckoutRequest(
    productName  = "pace mate",
    phoneNumber  = "+254701435178",
    currencyCode = CurrencyCode.KES,
    amount       = 100.00
  )

  val response = PaymentService.mobileCheckout(checkoutRequest)

  response onComplete {
    case Success(checkResponseEither) =>
      checkResponseEither match {
        case Right(checkoutResponse) =>
          logger.info(checkoutResponse.toString)

        case Left(errorMessage) =>
          logger.error(errorMessage)
      }
    case Failure(exception) => logger.error(exception.getMessage)
  }
}
