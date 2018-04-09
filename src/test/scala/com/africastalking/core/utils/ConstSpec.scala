package com.africastalking.core
package utils
import com.africastalking.core.commons.ATCoreTestServiceT
class ConstSpec extends ATCoreTestServiceT{

  "Production domain" should "be africastalking.com" in {
    assert(productionDomain === "https://api.africastalking.com/version1/")
  }

  "Test domain" should "be sandbox.africastalking.com" in  {
    assert(sandboxDomain === "https://api.sandbox.africastalking.com/version1/")
  }

  "International number format" should "match defined regex format irrespective of country" in {
    assert("+2348063333333" matches Const.INTL_PHONE_FORMAT)
    assert("+254722222222" matches Const.INTL_PHONE_FORMAT)
  }

}
