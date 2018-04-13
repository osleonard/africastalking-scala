package com.africastalking.payment

import com.africastalking.core.utils.{CurrencyCode, Metadata}

package object recipient {
  final case class Recipient(
    currencyCode: CurrencyCode.Value,
    amount: Double,
    bankAccount: BankAccount,
    narration: String,
    metadata: Option[Metadata] = None
  )

  final case class Business(
    currencyCode: CurrencyCode.Value,
    amount: Double,
    provider: PaymentProvider.Value,
    transferType: TransferType.Value,
    destinationChannel: String,
    destinationAccount: Option[String] = None,
    metadata: Option[Metadata] = None
   )

  object PaymentProvider extends Enumeration {
    val PROVIDER_MPESA  = Value("Mpesa")
    val PROVIDER_ATHENA = Value("Athena")
  }

  object TransferType extends Enumeration {
    val TRANSFER_TYPE_BUYGOODS = Value("BusinessBuyGoods")
    val TRANSFER_TYPE_PAYBILL  = Value("BusinessPayBill")
    val TRANSFER_TYPE_DISBURSE = Value("DisburseFundsToBusiness")
    val TRANSFER_TYPE_B2B      = Value("BusinessToBusinessTransfer")
  }

  final case class Consumer(
    name: String,
    phoneNumber: String,
    currencyCode: CurrencyCode.Value,
    amount: Double,
    reason: PaymentReasons.Value,
    providerChannel: Option[String] = None,
    metadata: Metadata              = Map.empty
  )

  object PaymentReasons extends Enumeration {
    val REASON_SALARY               = Value("SalaryPayment")
    val REASON_SALARY_WITH_CHARGE   = Value("SalaryPaymentWithWithdrawalChargePaid")
    val REASON_BUSINESS             = Value("BusinessPayment")
    val REASON_BUSINESS_WITH_CHARGE = Value("BusinessPaymentWithWithdrawalChargePaid")
    val REASON_PROMOTION            = Value("PromotionPayment")
  }
}
