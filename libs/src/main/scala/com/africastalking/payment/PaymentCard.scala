package com.africastalking.payment

import java.util.Calendar

final case class PaymentCard(number: String, cvvNumber: Int, expiryMonth: Int, expiryYear: Int, countryCode: String, authToken: String) {
  override def toString: String =
    s"""
       {"number": ${this.number}, "cvvNumber": ${this.cvvNumber}, "expiryMonth": ${this.expiryMonth}, "countryCode": ${this.countryCode}, "authToken": ${this.authToken} }
     """
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
