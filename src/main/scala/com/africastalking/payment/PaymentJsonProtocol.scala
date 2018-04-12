package com.africastalking.payment

import com.africastalking.core.utils._
import com.africastalking.payment.recipient._
import com.africastalking.payment.response._

object PaymentJsonProtocol extends DefaultJsonFormatter {
  implicit val currencyCodeFormat              = new EnumJsonConverter(CurrencyCode)
  implicit val bankCodeFormat                  = new EnumJsonConverter(BankCode)
  implicit val paymentProviderFormat           = new EnumJsonConverter(PaymentProvider)
  implicit val paymentReasonsFormat            = new EnumJsonConverter(PaymentReasons)
  implicit val transferTypeFormat              = new EnumJsonConverter(TransferType)
  implicit val bankAccountFormat               = jsonFormat4(BankAccount)
  implicit val bankFormat                      = jsonFormat5(Recipient)
  implicit val consumerFormat                  = jsonFormat7(Consumer)
  implicit val paymentCardFormat               = jsonFormat6(PaymentCard.apply)
  implicit val mobileCheckoutPayloadFormat     = jsonFormat6(MobileCheckoutPayload)
  implicit val cardCheckoutPayloadFormat       = jsonFormat7(CardCheckoutPayload)
  implicit val bankCheckoutPayloadFormat       = jsonFormat7(BankCheckoutPayload)
  implicit val checkoutValidationPayloadFormat = jsonFormat3(CheckoutValidationPayload)
  implicit val checkoutResponseFormat          = jsonFormat4(CheckoutResponse)
  implicit val checkoutValidateResponseFormat  = jsonFormat3(CheckoutValidateResponse)
  implicit val walletTransferResponseFormat    = jsonFormat3(WalletTransferResponse)
  implicit val topUpStashResponseFormat        = jsonFormat3(TopUpStashResponse)
  implicit val bankTransferPayloadFormat       = jsonFormat3(BankTransferPayload)
  implicit val walletTransferPayloadFormat     = jsonFormat6(WalletTransferPayload)
  implicit val bankEntryFormat                 = jsonFormat5(BankEntry)
  implicit val bankTransferResponseFormat      = jsonFormat2(BankTransferResponse)
  implicit val topUpStashPayloadFormat         = jsonFormat5(TopUpStashPayload)
  implicit val b2CPayloadFormat                = jsonFormat3(B2CPayload)
  implicit val b2BPayloadFormat                = jsonFormat9(B2BPayload)
  implicit val b2CEntryFormat                  = jsonFormat7(B2CEntry)
  implicit val b2BResponseFormat               = jsonFormat4(B2BResponse)
  implicit val b2CResponseFormat               = jsonFormat4(B2CResponse)
}