package com.africastalking.examples.voice

import com.africastalking.voice.ActionBuilder
import com.typesafe.scalalogging.LazyLogging

object VoiceServiceExample extends App with LazyLogging {
  val response =
    ActionBuilder
      .say
      .play
      .getDigits
      .build
}
