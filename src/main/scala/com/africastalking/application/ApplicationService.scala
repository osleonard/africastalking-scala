package com.africastalking.application

import akka.http.scaladsl.model.headers.{Accept, RawHeader}
import akka.http.scaladsl.model._
import com.africastalking.core.commons.TService
import com.africastalking.core.utils.TServiceConfig

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import spray.json._

object ApplicationService extends TApplicationService {

  import ApplicationJsonProtocol._

  override def fetchApplicationData: Future[Either[String, ApplicationResponse]] = {
    val httpRequest = HttpRequest(
      method  = HttpMethods.GET,
      uri     = Uri(s"${environmentHost}user?username=$username"),
      headers = List(RawHeader("apiKey", apiKey), Accept(MediaTypes.`application/json`))
    )

    makeRequest(httpRequest).map { response =>
      response.responseStatus match {
        case StatusCodes.OK | StatusCodes.Created => Right(response.payload.parseJson.convertTo[ApplicationResponse])
        case _                                    => Left(s"Sorry, ${response.payload}")
      }
    }
  }
}

trait TApplicationService extends TService with TServiceConfig {
  def fetchApplicationData: Future[Either[String, ApplicationResponse]]
}