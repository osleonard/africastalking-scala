package com.africastalking.token

import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.{FormData, RequestEntity}
import com.africastalking.core.commons.TService
import com.africastalking.core.utils.TServiceConfig

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import spray.json._

object TokenService extends TTokenService {

  import TokenJsonProtocol._

  override def createCheckoutToken(phoneNumber: String): Future[Either[String, CheckoutTokenResponse]] = {
    val entity = FormData(Map("phoneNumber" -> phoneNumber)).toEntity

    callEndpoint(entity, "checkout/token/create", payload => payload.parseJson.convertTo[CheckoutTokenResponse])
  }

  override def generateAuthToken: Future[Either[String, GenerateAuthTokenResponse]] = {
    val authTokenPayload = GenerateAuthTokenPayload(
      username = username
    )

    Marshal(authTokenPayload)
      .to[RequestEntity]
      .flatMap(entity => callEndpoint(entity, "auth-token/generate", payload => payload.parseJson.convertTo[GenerateAuthTokenResponse]))
  }
}

trait TTokenService extends TService with TServiceConfig {
  def createCheckoutToken(phoneNumber: String): Future[Either[String, CheckoutTokenResponse]]

  def generateAuthToken: Future[Either[String, GenerateAuthTokenResponse]]

  override lazy val environmentHost: String = apiEnvironmentHost
}