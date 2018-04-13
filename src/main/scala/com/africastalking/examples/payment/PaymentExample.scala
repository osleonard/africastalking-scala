package com.africastalking.examples
package payment


import com.africastalking.core.utils.CurrencyCode
import com.africastalking.payment.recipient.{Consumer, PaymentReasons}
import com.africastalking.payment.{MobileCheckoutRequest, PaymentService}

import scala.concurrent.ExecutionContext.Implicits.global

object PaymentExample extends TApiExamples {

  def main(args: Array[String]): Unit = {
    // mobileCheckout()
    mobileB2C()
  }

  def mobileCheckout() {
    val checkoutRequest = MobileCheckoutRequest(
      productName  = "pace mate",
      phoneNumber  = "+254701435178",
      currencyCode = CurrencyCode.KES,
      amount       = 100.00
    )

    PaymentService
      .mobileCheckout(checkoutRequest)
      .onComplete(processResult)
  }

  def mobileB2C() {
    val recipients = List(
      Consumer(
        name         = "Babatunde Ekemode",
        phoneNumber  = "+254701435178",
        currencyCode = CurrencyCode.KES,
        amount       = 100.00,
        reason       = PaymentReasons.REASON_BUSINESS
      )
    )

    PaymentService
      .mobileB2C("redElastic", recipients)
      .onComplete(processResult)
  }
}
