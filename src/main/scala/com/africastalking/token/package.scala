package com.africastalking

import com.africastalking.core.utils.DefaultJsonFormatter


package object token {
  final case class CheckoutTokenResponse(token: String, description: String)

  final case class GenerateAuthTokenResponse(token: String, lifetimeInSeconds: Long)

  final case class GenerateAuthTokenPayload(username: String)

  object TokenJsonProtocol extends DefaultJsonFormatter {
    implicit val checkoutTokenResponseFormat     = jsonFormat2(CheckoutTokenResponse)
    implicit val generateAuthTokenResponseFormat = jsonFormat2(GenerateAuthTokenResponse)
    implicit val generateAuthTokenPayloadFormat  = jsonFormat1(GenerateAuthTokenPayload)
  }
}
