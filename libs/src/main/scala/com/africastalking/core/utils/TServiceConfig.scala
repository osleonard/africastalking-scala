package com.africastalking.core
package utils

import akka.util.Timeout
import com.typesafe.config.ConfigFactory

trait TServiceConfig {
  import scala.concurrent.duration._

  private lazy val config                          = ConfigFactory.load()
  protected lazy val username: String              = config.getString("api.username")
  protected lazy val apiKey: String                = config.getString("api.key")
  protected lazy val environ: String               = config.getString("api.environment")
  protected lazy val apiProductionHost: String     = config.getString("at.host.api.production")
  protected lazy val apiSandboxHost: String        = config.getString("at.host.api.sandbox")
  protected lazy val paymentProductionHost: String = config.getString("at.host.payment.production")
  protected lazy val paymentSandboxHost: String    = config.getString("at.host.payment.sandbox")
  protected lazy val voiceProductionHost: String   = config.getString("at.host.voice.production")
  protected lazy val voiceSandboxHost: String      = config.getString("at.host.voice.sandbox")
  protected lazy val apiEnvironmentHost: String    = if(environ.toLowerCase.equals("production")) apiProductionHost else apiSandboxHost

  def environmentHost: String = s"${apiEnvironmentHost}version1/"

  def requestTimeout: Timeout = {
    val t = config.getString("akka.http.server.request-timeout")
    val d = Duration(t)
    FiniteDuration(d.length, d.unit)
  }
}
