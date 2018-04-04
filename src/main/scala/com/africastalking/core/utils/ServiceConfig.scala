package com.africastalking.core.utils

import akka.util.Timeout
import com.typesafe.config.ConfigFactory

trait ServiceConfig {
  private val config = ConfigFactory.load()
  lazy val username: String = config.getString("api.username")
  lazy val apiKey: String = config.getString("api.key")
  lazy val environ: String = config.getString("api.environ")

  import scala.concurrent.duration._

  def requestTimeout: Timeout = {
    val t = config.getString("akka.http.server.request-timeout")
    val d = Duration(t)
    FiniteDuration(d.length, d.unit)
  }
}
