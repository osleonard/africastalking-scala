package com.africastalking.core.commons

import com.africastalking.core.ApiTestSpec
import com.africastalking.core.utils.TServiceConfig

class ServiceConfigSpec extends ApiTestSpec with TServiceConfig {

  "Api Production Host" should "be africastalking.com" in {
    assert(apiProductionHost === "https://api.africastalking.com/version1/")
  }

  "Api Sandbox Host" should "be sandbox.africastalking.com" in  {
    assert(apiSandboxHost === "https://api.sandbox.africastalking.com/version1/")
  }

  "Payment Production Host" should "be https://payments.africastalking.com/" in {
    assert(paymentProductionHost === "https://payments.africastalking.com/")
  }

  "Payment Sandbox Host" should "be https://payments.sandbox.africastalking.com/" in  {
    assert(paymentSandboxHost === "https://payments.sandbox.africastalking.com/")
  }

  "Voice Production Host" should "be https://voice.africastalking.com/" in {
    assert(voiceProductionHost === "https://voice.africastalking.com/")
  }

  "Voice Sandbox Host" should "be https://voice.sandbox.africastalking.com/" in  {
    assert(voiceSandboxHost === "https://voice.sandbox.africastalking.com/")
  }
}
