package com.africastalking.core.commons

import com.africastalking.core.ApiTestSpec
import com.africastalking.core.utils.TServiceConfig

class ServiceConfigSpec extends ApiTestSpec with TServiceConfig {

  "Api Production Host" should "be africastalking.com" in {
    apiProductionHost must be ("https://api.africastalking.com/")
  }

  "Api Sandbox Host" should "be sandbox.africastalking.com" in  {
    apiSandboxHost must be ("https://api.sandbox.africastalking.com/")
  }

  "Payment Production Host" should "be https://payments.africastalking.com/" in { 
    paymentProductionHost must be ("https://payments.africastalking.com/")
  }

  "Payment Sandbox Host" should "be https://payments.sandbox.africastalking.com/" in  {
    paymentSandboxHost must be ("https://payments.sandbox.africastalking.com/")
  }
  
  "Voice Production Host" should "be https://voice.africastalking.com/" in {
    voiceProductionHost must be ("https://voice.africastalking.com/")
  }

  "Voice Sandbox Host" should "be https://voice.sandbox.africastalking.com/" in  {
    voiceSandboxHost must be ("https://voice.sandbox.africastalking.com/")
  }
}
