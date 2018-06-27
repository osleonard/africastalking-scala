package com.africastalking.sms

import com.africastalking.core.ApiTestSpec
import com.africastalking.sms.request.{Message, Recipient}
import com.africastalking.sms.response.SMSMessageData
import com.africastalking.sms.response.SendMessageResponse
import org.scalamock.scalatest.{AsyncMockFactory, MockFactory}

class SmsServiceSpec extends ApiTestSpec with AsyncMockFactory {
  //shared objects
  val smsPayload = Message("Hello World From Scala",List("+2348063363424"))
  val smsResponsePayload = SMSMessageData("Sent to 1/1 Total Cost: NGN 2.2000",
    List(Recipient("+2348063363424","Success","NGN 2.2000","ATXid_41a7f1f2566a89c868ec2bdebe92a38d")))
  val smsMessageResponse = SendMessageResponse(smsResponsePayload)
  val smsTrait = mock[TSmsService]

  "sms payload" should "be correctly set" in {
     smsPayload must have (
       'text ("Hello World From Scala"),
       'recipients (List("+2348063363424"))
       )  
  }

  

//  "Send sms method" should "eventually return a future response" in {

//    (smsTrait.send _).expects(smsPayload,0)
//  }

//  "fetch message" should "eventually return a list of sent messages" in {
//    (smsTrait.fetchMessages _).expects("0").once()
//    (smsTrait.fetchMessages _).verify("0")
//  }

}
