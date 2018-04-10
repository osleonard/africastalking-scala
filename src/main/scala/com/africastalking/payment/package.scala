package com.africastalking

import java.util.Calendar

import spray.json._
import com.africastalking.core.utils.{CurrencyCode, DefaultJsonFormatter}
import com.africastalking.payment.response.CheckoutResponse

package object payment {
  import DefaultJsonProtocol._
  implicit val paymentCardJsonFormat = jsonFormat6(PaymentCard.apply)

  final case class PaymentCard(number: String, cvvNumber: Int, expiryMonth: Int, expiryYear: Int, countryCode: String, authToken: String) {
    override def toString: String = this.toJson.compactPrint
  }

  object PaymentCard {
    def apply(number: String, cvvNumber: Int, expiryMonth: Int, expiryYear: Int, countryCode: String, authToken: String): PaymentCard = {
      require(isNumberValid(number), s"Invalid card number ${String.valueOf(number)}")
      require(isCVVNumberValid(cvvNumber), s"Invalid cvv number ${String.valueOf(cvvNumber)}")
      require(isExpiryMonthValid(expiryMonth), s"Invalid expiry month ${String.valueOf(expiryMonth)}. Should be between 1 and 12")
      require(isExpiryYearValid(expiryYear), s"Invalid expiry year ${String.valueOf(expiryYear)}. Should be greater or equal to current year")
      require(isCountryCodeValid(countryCode), s"Invalid country code $countryCode. Should be a two letter country code. See https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2ISO")
      require(isAuthTokenValid(authToken), "authToken is required")

      new PaymentCard(number, cvvNumber, expiryMonth, expiryYear, countryCode, authToken)
    }

    private def isNumberValid(number: String): Boolean = number != null && number.matches("^\\d{12,19}$")

    private def isCVVNumberValid(cvvNumber: Int): Boolean = String.valueOf(cvvNumber).matches("^\\d{3,4}$")

    private def isExpiryMonthValid(expiryMonth: Int): Boolean = expiryMonth >= 1 && expiryMonth <= 12

    private def isExpiryYearValid(expiryYear: Int): Boolean = expiryYear >= Calendar.getInstance.get(Calendar.YEAR)

    private def isCountryCodeValid(countryCode: String): Boolean = countryCode != null && countryCode.matches("^[A-Z]{2}$")

    private def isAuthTokenValid(authToken: String): Boolean = authToken != null
  }

  final case class MobileCheckoutRequest(productName: String, phoneNumber: String, currencyCode : CurrencyCode.Value, amount: Double)
  final case class CardCheckoutRequest(productName: String, currencyCode: CurrencyCode.Value, amount: Double, cardDetails: PaymentCard, narration: String)
  final case class MobileCheckoutPayload(username: String, productName: String, phoneNumber: String, currencyCode: String, amount: Double, metadata: Option[Map[String, String]] = None)
  final case class CardCheckoutPayload(username: String, productName: String, currencyCode: String, amount: Double, narration: String, metadata: Option[Map[String, String]] = None)

  object PaymentJsonProtocol extends DefaultJsonFormatter {
    implicit val mobileCheckoutPayloadFormat = jsonFormat6(MobileCheckoutPayload)
    implicit val cardCheckoutPayloadFormat   = jsonFormat6(CardCheckoutPayload)
    implicit val checkoutResponseFormat      = jsonFormat4(CheckoutResponse)
  }
}
