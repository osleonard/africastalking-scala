package com.africastalking.core.utils

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.settings.{ClientConnectionSettings, ConnectionPoolSettings}
import akka.stream.ActorMaterializer
import akka.util.ByteString
import com.africastalking.core.commons.Const

import scala.concurrent.duration._
import scala.language.postfixOps
import scala.concurrent.Future

abstract class Service extends DefaultJsonFormatter {
  /**
    *
    * @param apiKey
    * @param endpoint
    * @param requestVerb
    * @param isProductionDomain
    * @param payload
    * @return
    */
  def makeRequest(apiKey: String, endpoint: String, requestVerb: String, isProductionDomain: Boolean, payload: Any): Future[HttpResponse] = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher
    //setting idle timeout as 5 seconds or load it from configuration file
    val connectionSettings = ClientConnectionSettings(system).withIdleTimeout(5 seconds)
    val connectionPoolSettings = ConnectionPoolSettings(system).withConnectionSettings(connectionSettings)
    val environment = if (isProductionDomain) Const.PRODUCTION_DOMAIN else Const.SANDBOX_DOMAIN
    val url: String = s"https://api.$environment/version1/$endpoint"
    val fixPending = ByteString("abc")
    Marshal(fixPending).to[RequestEntity].flatMap {
      requestPayload =>
        val httpRequest = HttpRequest(extractRequestVerb(requestVerb), url, List(RawHeader("apiKey", apiKey)), requestPayload)
        val responseFuture: Future[HttpResponse] = Http().singleRequest(httpRequest, settings = connectionPoolSettings)
        responseFuture
    }
  }


  /**
    * We check the http verb supplied and match against akka http methods
    * if an unrecognized verb is supplied we then default the request to a get.
    *
    * @param verb
    * @return HttpMethod
    */

  protected def extractRequestVerb(verb: String): HttpMethod = verb match {

    case "GET" => HttpMethods.GET

    case "POST" => HttpMethods.POST

    case "PUT" => HttpMethods.PUT

    case "DELETE" => HttpMethods.DELETE

    case _ => HttpMethods.GET

  }

}
