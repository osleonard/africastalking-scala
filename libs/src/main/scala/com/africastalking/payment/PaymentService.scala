package com.africastalking.payment

import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._

import com.africastalking.core.commons.TService
import com.africastalking.core.utils.{CurrencyCode, Metadata, TServiceConfig}
import com.africastalking.payment.recipient.{Consumer, Recipient}
import com.africastalking.payment.response._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import spray.json._

object PaymentService extends TPaymentService {

  import PaymentJsonProtocol._

  /**
    *
    * @param checkoutRequest
    * @param metadata
    * @return
    */
  override def mobileCheckout(checkoutRequest: MobileCheckoutRequest, metadata: Option[Metadata] = None): Future[Either[String, CheckoutResponse]] = {
    if (!validatePhoneNumber(checkoutRequest.phoneNumber))
      Future.successful(Left(s"Invalid phone number: ${checkoutRequest.phoneNumber}; Expecting number in format +XXXxxxxxxxxx"))
    else {
      val checkoutPayload = MobileCheckoutPayload(
        username     = username,
        productName  = checkoutRequest.productName,
        phoneNumber  = checkoutRequest.phoneNumber,
        currencyCode = checkoutRequest.currencyCode.toString,
        amount       = checkoutRequest.amount,
        metadata     = metadata
      )

      Marshal(checkoutPayload)
        .to[RequestEntity]
        .flatMap(entity => callEndpoint[CheckoutResponse](entity, "mobile/checkout/request", stringToCheckoutResponse))
    }
  }

  /**
    *
    * @param checkoutRequest
    * @param metadata
    * @return
    */
  override def cardCheckout(checkoutRequest: CardCheckoutRequest, metadata: Option[Metadata] = None): Future[Either[String, CheckoutResponse]] = {
    val checkoutPayload = CardCheckoutPayload(
      username     = username,
      productName  = checkoutRequest.productName,
      currencyCode = checkoutRequest.currencyCode.toString,
      amount       = checkoutRequest.amount,
      paymentCard  = checkoutRequest.cardDetails,
      narration    = checkoutRequest.narration,
      metadata     = metadata
    )

    Marshal(checkoutPayload)
      .to[RequestEntity]
      .flatMap(entity => callEndpoint[CheckoutResponse](entity, "card/checkout/charge", stringToCheckoutResponse))
  }

  /**
    *
    * @param transactionId
    * @param otp
    * @return
    */
  override def validateCardCheckout(transactionId: String, otp: String): Future[Either[String, CheckoutValidateResponse]] = {
    val checkoutValidation = CheckoutValidationPayload(
      username      = username,
      transactionId = transactionId,
      otp           = otp
    )

    Marshal(checkoutValidation)
      .to[RequestEntity]
      .flatMap(entity => callEndpoint(entity, "card/checkout/validate", stringToCheckoutValidateResponse))
  }

  /**
    *
    * @param checkoutRequest
    * @param metadata
    * @return
    */
  override def bankCheckout(checkoutRequest: BankCheckoutRequest, metadata: Option[Metadata] = None): Future[Either[String, CheckoutResponse]] = {
    val checkoutPayload = BankCheckoutPayload(
      username     = username,
      productName  = checkoutRequest.productName,
      currencyCode = checkoutRequest.currencyCode.toString,
      amount       = checkoutRequest.amount,
      bankAccount  = checkoutRequest.bankAccount,
      narration    = checkoutRequest.narration,
      metadata     = metadata
    )

    Marshal(checkoutPayload)
      .to[RequestEntity]
      .flatMap(entity => callEndpoint(entity, "bank/checkout/charge", stringToCheckoutResponse))
  }

  /**
    *
    * @param transactionId
    * @param otp
    * @return
    */
  override def validateBankCheckout(transactionId: String, otp: String): Future[Either[String, CheckoutValidateResponse]] = {
    val checkoutValidation = CheckoutValidationPayload(
      username      = username,
      transactionId = transactionId,
      otp           = otp
    )

    Marshal(checkoutValidation)
      .to[RequestEntity]
      .flatMap(entity => callEndpoint(entity, "bank/checkout/validate", stringToCheckoutValidateResponse))
  }

  /**
    *
    * @param productName
    * @param recipients
    * @return
    */
  override def bankTransfer(productName: String, recipients: List[Recipient]): Future[Either[String, BankTransferResponse]] = {
    val bankTransferPayload = BankTransferPayload(
      username    = username,
      productName = productName,
      recipients  = recipients
    )

    Marshal(bankTransferPayload)
      .to[RequestEntity]
      .flatMap(entity => callEndpoint(entity, "bank/transfer", payload => payload.parseJson.convertTo[BankTransferResponse]))
  }

  /**
    *
    * @param productName
    * @param targetProductCode
    * @param currencyCode
    * @param amount
    * @param metadata
    * @return
    */
  override def walletTransfer(productName: String, targetProductCode: Long, currencyCode: CurrencyCode.Value, amount: Double, metadata: Option[Metadata]): Future[Either[String, WalletTransferResponse]] = {
    val walletTransferPayload = WalletTransferPayload(
      username          = username,
      productName       = productName,
      targetProductCode = targetProductCode.toString,
      currencyCode      = currencyCode.toString,
      amount            = amount,
      metadata          = metadata
    )

    Marshal(walletTransferPayload)
      .to[RequestEntity]
      .flatMap(entity => callEndpoint(entity, "transfer/wallet", payload => payload.parseJson.convertTo[WalletTransferResponse]))
  }

  /**
    *
    * @param productName
    * @param currencyCode
    * @param amount
    * @param metadata
    * @return
    */

  override def topUpStash(productName: String, currencyCode: CurrencyCode.Value, amount: Double, metadata: Option[Metadata]): Future[Either[String, TopUpStashResponse]] = {
    val topUpStashPayload = TopUpStashPayload(
      username     = username,
      productName  = productName,
      currencyCode = currencyCode.toString,
      amount       = amount,
      metadata     = metadata
    )

    Marshal(topUpStashPayload)
      .to[RequestEntity]
      .flatMap(entity => callEndpoint(entity, "topup/stash", payload => payload.parseJson.convertTo[TopUpStashResponse]))
  }

  /**
    *
    * @param b2bRequest
    * @param metadata
    * @return
    */

  override def mobileB2B(b2bRequest: B2BRequest, metadata: Option[Metadata] = None): Future[Either[String, B2BResponse]] = {
    val b2BPayload = B2BPayload(
      username           = username,
      productName        = b2bRequest.productName,
      provider           = b2bRequest.provider.toString,
      transferType       = b2bRequest.transferType.toString,
      currencyCode       = b2bRequest.currencyCode.toString,
      amount             = b2bRequest.amount,
      destinationChannel = b2bRequest.destinationChannel,
      destinationAccount = b2bRequest.destinationAccount,
      metadata           = metadata
    )

    Marshal(b2BPayload)
      .to[RequestEntity]
      .flatMap(entity => callEndpoint(entity, "mobile/b2b/request", payload => payload.parseJson.convertTo[B2BResponse]))
  }

  /**
    *
    * @param productName
    * @param recipients
    * @return
    */
  override def mobileB2C(productName: String, recipients: List[Consumer]): Future[Either[String, B2CResponse]] = {
    val b2cPayload = B2CPayload(
      username    = username,
      productName = productName,
      recipients  = recipients
    )

    Marshal(b2cPayload)
      .to[RequestEntity]
      .flatMap(entity => callEndpoint(entity, "mobile/b2c/request", payload => payload.parseJson.convertTo[B2CResponse]))
  }
  private def stringToCheckoutResponse(payload: String): CheckoutResponse = payload.parseJson.convertTo[CheckoutResponse]

  private def stringToCheckoutValidateResponse(payload: String): CheckoutValidateResponse = payload.parseJson.convertTo[CheckoutValidateResponse]
}



trait TPaymentService extends TService with TServiceConfig {
  def mobileCheckout(checkoutRequest: MobileCheckoutRequest, metadata: Option[Metadata]): Future[Either[String, CheckoutResponse]]

  def cardCheckout(checkoutRequest: CardCheckoutRequest, metadata: Option[Metadata]): Future[Either[String, CheckoutResponse]]

  def validateCardCheckout(transactionId: String, otp: String): Future[Either[String, CheckoutValidateResponse]]

  def bankCheckout(checkoutRequest: BankCheckoutRequest, metadata: Option[Metadata] = None): Future[Either[String, CheckoutResponse]]

  def validateBankCheckout(transactionId: String, otp: String): Future[Either[String, CheckoutValidateResponse]]

  def bankTransfer(productName: String, recipients: List[Recipient]): Future[Either[String, BankTransferResponse]]

  def walletTransfer(productName: String, targetProductCode: Long, currencyCode: CurrencyCode.Value, amount: Double, metadata: Option[Metadata]): Future[Either[String, WalletTransferResponse]]

  def topUpStash(productName: String, currencyCode: CurrencyCode.Value, amount: Double, metadata: Option[Metadata]): Future[Either[String, TopUpStashResponse]]

  def mobileB2B(b2bRequest: B2BRequest, metadata: Option[Metadata] = None): Future[Either[String, B2BResponse]]

  def mobileB2C(productName: String, recipients: List[Consumer]): Future[Either[String, B2CResponse]]

  override lazy val environmentHost: String = if (environ.toLowerCase.equals("production")) paymentProductionHost else paymentSandboxHost
}

