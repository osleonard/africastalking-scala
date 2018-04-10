package com.africastalking.core
package commons

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.settings.{ClientConnectionSettings, ConnectionPoolSettings}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import com.africastalking.core.utils.{ApiResponse, TServiceConfig}
import scala.concurrent.Future
import scala.language.postfixOps

import com.africastalking.core.utils.Const

trait TService {
  this: TServiceConfig =>

  implicit private val system           = ActorSystem()
  implicit private val materializer     = ActorMaterializer()
  implicit private val executionContext = system.dispatcher

  def makeRequest(httpRequest: => HttpRequest): Future[ApiResponse] = {
    val connectionSettings     = ClientConnectionSettings(system).withIdleTimeout(requestTimeout.duration)
    val connectionPoolSettings = ConnectionPoolSettings(system).withConnectionSettings(connectionSettings)

    Http().singleRequest(httpRequest, settings = connectionPoolSettings)
    .flatMap(response =>
      Unmarshal(response.entity).to[String]
        .map(payload => ApiResponse(response.status, payload))
    )
  }

  protected def validatePhoneNumber(phone: String): Boolean = if (phone.matches(Const.INTL_PHONE_FORMAT)) true else false
}
