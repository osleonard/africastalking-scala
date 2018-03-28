package com.africastalking.core.utils

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.{Accept, RawHeader}
import akka.http.scaladsl.settings.{ClientConnectionSettings, ConnectionPoolSettings}
import akka.stream.ActorMaterializer
import com.typesafe.config.{Config, ConfigFactory}

import scala.concurrent.duration._
import scala.language.postfixOps
import scala.concurrent.Future

abstract class Service {
  /**
    *
    * @param apiKey
    * @param endpoint
    * @param requestVerb
    * @param isProductionDomain
    * @param payload
    * @return
    */
  def makeRequest(apiKey : String, endpoint : String, requestVerb : String, isProductionDomain : Boolean, payload : HttpEntity): Future[HttpResponse] ={
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher
    //setting idle timeout as 5 seconds or load it from configuration file
    val connectionSettings = ClientConnectionSettings(system).withIdleTimeout(5 seconds)
    val connectionPoolSettings = ConnectionPoolSettings(system).withConnectionSettings(connectionSettings)

    val environment = if(isProductionDomain) "africastalking.com" else "sandbox.africastalking.com"
    val url : String = s"https://api.$environment/version1/$endpoint"
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = url , method = extractRequestVerb(requestVerb),
      headers = List(RawHeader("apiKey",apiKey),Accept(MediaTypes.`application/json`)),
      entity = HttpEntity(MediaTypes.`multipart/form-data`, data = payload.dataBytes)),settings = connectionPoolSettings)
    responseFuture
  }


  /**
    * We check the http verb supplied and match against akka http methods
    * if an unrecognized verb is supplied we then default the request to a get.
    * @param verb
    * @return HttpMethod
    */

  protected def extractRequestVerb(verb : String) : HttpMethod =  verb match  {

    case "GET" => HttpMethods.GET

    case "POST" => HttpMethods.POST

    case "PUT" => HttpMethods.PUT

    case "DELETE" => HttpMethods.DELETE

    case _ => HttpMethods.GET

  }

}
