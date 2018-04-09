package com.africastalking.examples.sms
import com.africastalking.sms
import com.africastalking.sms.SmsService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object SmsExample extends App{
  var messageObject = sms.Message("Hello World From Lagos Scala",List("+2348063363424"),None)
  val responseFromApi = SmsService.send(messageObject)
  responseFromApi onComplete{
    case Success(message) => println(message)
    case Failure(ex) => println(ex)
  }
}
