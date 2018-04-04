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

trait TService extends JsonMarshalls {
  this: TServiceConfig =>

  implicit private val system = ActorSystem()
  implicit private val materializer = ActorMaterializer()
  implicit private val executionContext = system.dispatcher

  private val environments = Map(
    "sandbox"       -> sandboxDomain,
    "production"    -> productionDomain
  )

  /*def makeRequest(endpoint: String, requestVerb: String,  payload: JsValue): Future[ApiResponse] = {
    val connectionSettings = ClientConnectionSettings(system).withIdleTimeout(requestTimeout.duration)
    val connectionPoolSettings = ConnectionPoolSettings(system).withConnectionSettings(connectionSettings)
    val environmentDomain = environments.getOrElse(environ, sandboxDomain)
    val endPointUrl: String = s"$environmentDomain$endpoint"

    callEndpoint(endPointUrl, httpMethods.getOrElse(requestVerb, GET), payload, connectionPoolSettings)
  }*/

  def makeRequest(httpRequest: => HttpRequest): Future[ApiResponse] = {
    val connectionSettings = ClientConnectionSettings(system).withIdleTimeout(requestTimeout.duration)
    val connectionPoolSettings = ConnectionPoolSettings(system).withConnectionSettings(connectionSettings)

    Http().singleRequest(httpRequest, settings = connectionPoolSettings)
    .flatMap { response =>
      Unmarshal(response.entity).to[String]
        .map(data => ApiResponse(response.status, data))
    }
  }
}
