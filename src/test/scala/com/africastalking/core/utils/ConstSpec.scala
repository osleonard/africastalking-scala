package com.africastalking.core
package utils

class ConstSpec extends ApiTestSpec {
  "International number format" should "match defined regex format irrespective of country" in {
    assert("+2348063333333" matches Const.INTL_PHONE_FORMAT)
    assert("+254722222222" matches Const.INTL_PHONE_FORMAT)
  }

}
