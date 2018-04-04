package com.africastalking.core
package utils


import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.settings.{ClientConnectionSettings, ConnectionPoolSettings}
import akka.stream.ActorMaterializer
import akka.util.ByteString
import com.africastalking.core.commons._

import scala.concurrent.Future
import scala.language.postfixOps

trait Service extends JsonMarshalls {
  this: ServiceConfig =>


  import Const._

  def makeRequest(endpoint: String, requestVerb: String,  payload: AnyRef): Future[HttpResponse] = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    val connectionSettings = ClientConnectionSettings(system).withIdleTimeout(requestTimeout.duration)
    val connectionPoolSettings = ConnectionPoolSettings(system).withConnectionSettings(connectionSettings)
    val environmentDomain = environments.getOrElse(environ, sandboxDomain)
    val endPointUrl: String = s"$environmentDomain$endpoint"
    val p = ByteString("abc")

    Marshal(p).to[RequestEntity].flatMap {
      requestPayload =>
        val httpRequest = HttpRequest(extractHttpMethod(requestVerb), endPointUrl, List(RawHeader("apiKey", apiKey)), requestPayload)
        val responseFuture: Future[HttpResponse] = Http().singleRequest(httpRequest, settings = connectionPoolSettings)
        responseFuture
    }
  }

  protected def extractHttpMethod(verb: String): HttpMethod = verb match {

    case "GET" => HttpMethods.GET

    case "POST" => HttpMethods.POST

    case "PUT" => HttpMethods.PUT

    case "DELETE" => HttpMethods.DELETE

    case _ => HttpMethods.GET

  }

  private val environments = Map(
    "sandbox"       -> sandboxDomain,
    "production"    -> productionDomain
  )

}
