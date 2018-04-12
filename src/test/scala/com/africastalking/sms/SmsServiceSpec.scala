package com.africastalking.sms

import com.africastalking.core.ApiTestSpec
import com.africastalking.sms
import org.scalamock.scalatest.{AsyncMockFactory, MockFactory}

class SmsServiceSpec extends ApiTestSpec with AsyncMockFactory {
  val messageObject = sms.Message("Hello World From Lagos Scala", List("+2348063363424"), None)
//  val responseObject = SendMessageResponse.SmsMessageData(List(Recipient()))

  it should "always be true" in {
    assert(true === true)
  }


}
