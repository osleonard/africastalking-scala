package com.africastalking.core
package utils

import akka.util.Timeout
import com.typesafe.config.ConfigFactory

trait TServiceConfig {
  private val config = ConfigFactory.load()
  val username: String = config.getString("api.username")
  val apiKey: String = config.getString("api.key")
  val environ: String = config.getString("api.environ")
  val productionDomain: String = config.getString("api.domain.production")
  val sandboxDomain: String = config.getString("api.domain.sandbox")
  val environmentDomain: String = if(environ.toLowerCase.equals("production")) productionDomain else sandboxDomain

  import scala.concurrent.duration._

  def requestTimeout: Timeout = {
    val t = config.getString("akka.http.server.request-timeout")
    val d = Duration(t)
    FiniteDuration(d.length, d.unit)
  }
}
