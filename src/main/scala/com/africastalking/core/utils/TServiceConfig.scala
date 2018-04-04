package com.africastalking.core
package utils

import akka.util.Timeout
import com.typesafe.config.ConfigFactory

trait TServiceConfig {
  private val config = ConfigFactory.load()
  lazy val username: String = config.getString("api.username")
  lazy val apiKey: String = config.getString("api.key")
  lazy val environ: String = config.getString("api.environ")
  lazy val productionDomain: String = config.getString("api.domain.production")
  lazy val sandboxDomain: String = config.getString("api.domain.sandbox")
  lazy val environmentDomain: String = if(environ.toLowerCase.equals("production")) productionDomain else sandboxDomain

  import scala.concurrent.duration._

  def requestTimeout: Timeout = {
    val t = config.getString("akka.http.server.request-timeout")
    val d = Duration(t)
    FiniteDuration(d.length, d.unit)
  }
}
