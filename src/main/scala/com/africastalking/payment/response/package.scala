package com.africastalking.payment

package object response {
  final case class CheckoutResponse(transactionId: String, status: String, description: String, checkoutToken: Option[String] = None)
}
