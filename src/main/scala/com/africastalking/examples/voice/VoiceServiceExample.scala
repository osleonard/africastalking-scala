package com.africastalking.examples.voice

import com.africastalking.examples.TApiExamples
import com.africastalking.voice.ActionBuilder

object VoiceServiceExample extends TApiExamples {

  def main(args: Array[String]): Unit = {
      ActionBuilder
        .say
        .play
        .getDigits
        .build
  }
}
