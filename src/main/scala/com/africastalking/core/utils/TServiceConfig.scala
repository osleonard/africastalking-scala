package com.africastalking.core
package utils

import akka.util.Timeout
import com.typesafe.config.ConfigFactory

trait TServiceConfig {
  import scala.concurrent.duration._

  private lazy val config                = ConfigFactory.load()
  lazy val username: String              = config.getString("at.username")
  lazy val apiKey: String                = config.getString("at.key")
  lazy val environ: String               = config.getString("at.environ")
  lazy val apiProductionHost: String     = config.getString("at.host.api.production")
  lazy val apiSandboxHost: String        = config.getString("at.host.api.sandbox")
  lazy val paymentProductionHost: String = config.getString("at.host.payment.production")
  lazy val paymentSandboxHost: String    = config.getString("at.host.payment.sandbox")
  lazy val voiceProductionHost: String   = config.getString("at.host.voice.production")
  lazy val voiceSandboxHost: String      = config.getString("at.host.voice.sandbox")
  lazy val environmentHost: String       = if(environ.toLowerCase.equals("production")) apiProductionHost else apiSandboxHost

  def requestTimeout: Timeout = {
    val t = config.getString("akka.http.server.request-timeout")
    val d = Duration(t)
    FiniteDuration(d.length, d.unit)
  }
}
