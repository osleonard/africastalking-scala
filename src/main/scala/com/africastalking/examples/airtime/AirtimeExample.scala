package com.africastalking.examples
package airtime

import com.africastalking.airtime
import com.africastalking.airtime.AirtimeService
import scala.concurrent.ExecutionContext.Implicits.global

import scala.util.{Failure, Success}

object AirtimeExample extends App {

  val payload = airtime.Airtime(List("+2348063363424"),"NGN 10")

  val response = AirtimeService.send(payload)

  response onComplete{
    case Success(message) => println(message)
    case Failure(ex) => println(ex)
  }

}
