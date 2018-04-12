package com.africastalking

import com.africastalking.core.utils.DefaultJsonFormatter


package object token {
  final case class CheckoutTokenResponse(token: String, description: String)

  final case class AuthTokenResponse(token: String, lifetimeInSeconds: Long)

  final case class AuthTokenPayload(body: String)

  object TokenJsonProtocol extends DefaultJsonFormatter {
    implicit val checkoutTokenResponseFormat = jsonFormat2(CheckoutTokenResponse)
    implicit val authTokenResponseFormat     = jsonFormat2(AuthTokenResponse)
    implicit val authTokenPayloadFormat      = jsonFormat1(AuthTokenPayload)
  }
}
