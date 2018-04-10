package com.africastalking.payment

import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.{Accept, RawHeader}
import com.africastalking.core.commons.TService
import com.africastalking.core.utils.{Metadata, TServiceConfig}
import com.africastalking.payment.response._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import spray.json._

object PaymentService extends TPaymentService {
  import PaymentJsonProtocol._

  override def mobileCheckout(checkoutRequest: MobileCheckoutRequest, metadata: Option[Metadata] = None): Future[Either[String, CheckoutResponse]] = {

    if(!validatePhoneNumber(checkoutRequest.phoneNumber))
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

  override def bankTransfer: Unit = ???

  override def walletTransfer: Unit = ???

  override def topupStash: Unit = ???

  override def mobileB2B: Unit = ???

  override def mobileB2C: Unit = ???

  private def callEndpoint[T](entity: RequestEntity, endpoint: String, f: String => T): Future[Either[String, T]] = {
    val url = s"$environmentHost$endpoint"
    val request: HttpRequest = HttpRequest(
      method  = HttpMethods.POST,
      uri     = url,
      headers = List(RawHeader("apiKey", apiKey), Accept(MediaTypes.`application/json`)),
      entity  = entity
    )
    makeRequest(request).map { response =>
      response.responseStatus match {
        case StatusCodes.OK | StatusCodes.Created => Right(f(response.payload))
        case _                                    => Left(s"Sorry, ${response.payload}")
      }
    }
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
  def bankTransfer: Unit
  def walletTransfer: Unit
  def topupStash: Unit
  def mobileB2B: Unit
  def mobileB2C: Unit

  override lazy val environmentHost: String = if(environ.toLowerCase.equals("production")) paymentProductionHost else paymentSandboxHost
}

