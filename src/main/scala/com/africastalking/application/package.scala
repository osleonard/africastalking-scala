package com.africastalking

import com.africastalking.core.utils.DefaultJsonFormatter

package object application {

  case class ApplicationResponse(UserData: UserData)
  case class UserData(balance: String)

  object ApplicationJsonProtocol extends DefaultJsonFormatter {
    implicit val userDataFormat            = jsonFormat1(UserData)
    implicit val applicationResponseFormat = jsonFormat1(ApplicationResponse)
  }
}
