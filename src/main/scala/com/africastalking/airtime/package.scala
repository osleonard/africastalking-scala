package com.africastalking

import com.africastalking.core.utils.DefaultJsonFormatter
package object airtime {
  case class Airtime(recipients: String, amount: String){
    override def toString: String =  s"""
       [{"amount":"${this.amount}", "phoneNumber":"${this.recipients}"}]
     """
  }

  case class AirtimeResponse(numSent: Int, totalAmount: String, totalDiscount: String, errorMessage: String, responses: List[AirtimeEntry])

  case class AirtimeEntry(errorMessage: String, phoneNumber: String, amount: String, discount: String, status: String, requestId: String)


  object AirtimeJsonProtocol extends DefaultJsonFormatter {
    implicit val airtimeMessageFormat = jsonFormat2(Airtime)
    implicit val airtimeEntityFormat = jsonFormat6(AirtimeEntry)
    implicit val airtimeResponseFormat = jsonFormat5(AirtimeResponse)

  }

}
