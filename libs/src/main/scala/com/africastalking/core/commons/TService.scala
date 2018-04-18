package com.africastalking.core
package commons

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.{Accept, RawHeader}
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

  /**
    * This function takes request entity as payload, endpoint per request and a return entity type
    * @param entity
    * @param endpoint
    * @param f
    * @tparam T
    * @return
    */
  protected def callEndpoint[T](entity: RequestEntity, endpoint: String, f: String => T): Future[Either[String, T]] = {
    val url = s"$environmentHost$endpoint"
    val request: HttpRequest = HttpRequest(
      method  = HttpMethods.POST,
      uri     = url,
      headers = List(RawHeader("apiKey", apiKey), Accept(MediaTypes.`application/json`)),
      entity  = entity
    )
    makeRequest(request).map { response =>
      response.responseStatus match {
        case StatusCodes.OK | StatusCodes.Created => Right(f(response.payload))
        case _                                    => Left(s"Sorry, ${response.payload}")
      }
    }
  }

  /**
    * This function is only used when @HttpRequest is composed from a service class without using the call endpoint
    * function
    * @param httpRequest
    * @return
    */

  protected def makeRequest(httpRequest: => HttpRequest): Future[ApiResponse] = {
    val connectionSettings     = ClientConnectionSettings(system).withIdleTimeout(requestTimeout.duration)
    val connectionPoolSettings = ConnectionPoolSettings(system).withConnectionSettings(connectionSettings)

    Http().singleRequest(httpRequest, settings = connectionPoolSettings)
    .flatMap(response =>
      Unmarshal(response.entity).to[String]
        .map(payload => ApiResponse(response.status, payload))
    )
  }

  /**
    * Function can be used to validate phone numbers so as to avoid runtime errors as the api requires all
    * phone numbers must be in international format.
    * @param phone
    * @return
    */

  protected def validatePhoneNumber(phone: String): Boolean = if(phone.matches(Const.INTL_PHONE_FORMAT)) true else false
}
