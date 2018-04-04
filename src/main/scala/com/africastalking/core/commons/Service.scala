package com.africastalking.core.commons

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.settings.{ClientConnectionSettings, ConnectionPoolSettings}
import akka.stream.ActorMaterializer
import com.africastalking.core.utils.{Const, ServiceConfig}

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.language.postfixOps

trait Service extends JsonMarshalls {
  this: ServiceConfig =>


  import Const._

  def makeRequest(endpoint: String, requestVerb: String,  payload: AnyRef): Future[HttpResponse] = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher
    //setting idle timeout as 5 seconds or load it from configuration file
    val connectionSettings = ClientConnectionSettings(system).withIdleTimeout(5 seconds)
    val connectionPoolSettings = ConnectionPoolSettings(system).withConnectionSettings(connectionSettings)
    val environment = environs.getOrElse(environ, SANDBOX_DOMAIN)
    val url: String = s"https://api.$environment/version1/$endpoint"
    Marshal(payload).to[RequestEntity].flatMap {
      requestPayload =>
        val httpRequest = HttpRequest(extractRequestVerb(requestVerb), url, List(RawHeader("apiKey", apiKey)), requestPayload)
        val responseFuture: Future[HttpResponse] = Http(system).singleRequest(httpRequest, settings = connectionPoolSettings)
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


  /**
    *
    */

  private val environs = Map(
    "sandbox"       -> SANDBOX_DOMAIN,
    "production"    -> PRODUCTION_DOMAIN
  )

}
