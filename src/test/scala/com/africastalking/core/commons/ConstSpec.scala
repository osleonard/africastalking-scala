package com.africastalking.core.commons

import org.scalatest.{FlatSpec, Matchers}

class ConstSpec extends FlatSpec with Matchers{

  "Production domain" should "be africastalking.com" in {
    assert(Const.PRODUCTION_DOMAIN === "africastalking.com")
  }

  "Test domain" should "be sandbox.africastalking.com" in  {
    assert(Const.SANDBOX_DOMAIN === "sandbox.africastalking.com")
  }

  "International number format" should "match defined regex format irrespective of country" in {
    assert("+2348063333333" matches Const.INTL_PHONE_FORMAT)
    assert("+254722222222" matches Const.INTL_PHONE_FORMAT)
  }

}
