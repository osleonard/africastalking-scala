package com.africastalking.examples
package airtime

import com.africastalking.airtime.{AirtimeRequest, AirtimeService}

import scala.concurrent.ExecutionContext.Implicits.global

object AirtimeExample extends TApiExamples {

  def main(args: Array[String]): Unit = {
    sendAirtime()
  }

  private def sendAirtime() {
    val payload = AirtimeRequest(
      recipients = "+2348063363424",
      amount     = "NGN 50"
    )

    AirtimeService
      .send(payload)
      .onComplete(processResult)
  }
}
