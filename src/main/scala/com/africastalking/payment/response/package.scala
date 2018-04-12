package com.africastalking.payment

package object response {
  final case class CheckoutResponse(
    transactionId: String,
    status: String,
    description: String,
    checkoutToken: Option[String] = None
  )

  final case class CheckoutValidateResponse(
    status: String,
    description: String,
    checkoutToken: String
  )

  final case class WalletTransferResponse(
    status: String,
    description: String,
    transactionId: String
  )

  final case class TopUpStashResponse(
    status: String,
    description: String,
    transactionId: String
  )

  final case class BankTransferResponse(
     bankEntries: List[BankEntry],
     errorMessage: Option[String] = None
  )

  final case class BankEntry(
    accountNumber: String,
    status: String,
    transactionId: String,
    transactionFee: String,
    errorMessage: String
  )

  final case class B2CResponse(
    numQueued: Int,
    totalValue: String,
    totalTransactionFee: String,
    entries: List[B2CEntry]
  )

  final case class B2BResponse(
    status: String,
    transactionId: String,
    transactionFee: String,
    providerChannel: String
  )

  final case class B2CEntry(
    phoneNumber: String,
    status: String,
    provider: String,
    providerChannel: String,
    value: String,
    transactionFee: String,
    errorMessage: Option[String] = None
  )
}