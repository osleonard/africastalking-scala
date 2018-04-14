package com.africastalking

import com.africastalking.core.utils._
import com.africastalking.payment.recipient._

package object payment {
  final case class MobileCheckoutRequest(
    productName: String,
    phoneNumber: String,
    currencyCode : CurrencyCode.Value,
    amount: Double
  )

  final case class CardCheckoutRequest(
    productName: String,
    currencyCode: CurrencyCode.Value,
    amount: Double,
    cardDetails: PaymentCard,
    narration: String
  )

  final case class BankCheckoutRequest(
    productName: String,
    currencyCode: CurrencyCode.Value,
    amount: Double,
    bankAccount: BankAccount,
    narration: String
  )

  final case class B2BRequest(
    productName: String,
    provider: PaymentProvider.Value,
    transferType: TransferType.Value,
    currencyCode: CurrencyCode.Value,
    amount: Double,
    destinationChannel: String,
    destinationAccount: String
  )

  final case class MobileCheckoutPayload(
    username: String,
    productName: String,
    phoneNumber: String,
    currencyCode: String,
    amount: Double,
    metadata: Option[Map[String, String]] = None
  )

  final case class CardCheckoutPayload(
    username: String,
    productName: String,
    currencyCode: String,
    amount: Double,
    paymentCard: PaymentCard,
    narration: String,
    metadata: Option[Map[String, String]] = None
  )

  final case class BankCheckoutPayload(
    username: String,
    productName: String,
    currencyCode: String,
    amount: Double,
    bankAccount: BankAccount,
    narration: String,
    metadata: Option[Map[String, String]] = None
  )

  final case class CheckoutValidationPayload(
    username: String,
    transactionId: String,
    otp: String
  )

  final case class BankTransferPayload(
    username: String,
    productName: String,
    recipients: List[Recipient]
  )

  final case class WalletTransferPayload(
    username: String,
    productName: String,
    currencyCode: String,
    amount: Double,
    targetProductCode: String,
    metadata: Option[Metadata] = None
  )

  final case class TopUpStashPayload(
    username: String,
    productName: String,
    currencyCode: String,
    amount: Double,
    metadata: Option[Metadata] = None
  )

  final case class B2CPayload(
   username: String,
   productName: String,
   recipients: List[Consumer]
   )

  final case class B2BPayload(
    username: String,
    productName: String,
    provider: String,
    transferType: String,
    currencyCode: String,
    amount: Double,
    destinationChannel: String,
    destinationAccount: String,
    metadata: Option[Metadata] = None
  )
}