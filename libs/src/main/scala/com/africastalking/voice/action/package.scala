package com.africastalking.voice

import java.net.URL


package object action {
  trait TAction

  final case class Say(
    text: String,
    playBeep: Boolean = false,
    voice: Voice.Value = Voice.Women
  ) extends TAction

  final case class Dequeue(
    phoneNumber: String,
    name: Option[String] = None
  ) extends TAction

  final case class Enqueue(
    holdMusic: Option[URL] = None,
    name: Option[String] = None
  ) extends TAction

  final case class Dial(
    phoneNumbers: List[String],
    record: Boolean = false,
    sequential: Boolean = false,
    callerId: Option[String] = None,
    ringBackTone: Option[URL] = None,
    maxDuration: Option[Int]
  ) extends TAction

  final case class GetDigits(
    child: TAction,
    numDigits: Option[Int] = None,
    timeout: Int = 30,
    finishOnKey: Option[String] = None,
    callbackUrl: Option[URL] = None
  ) extends TAction

  final case class Play(
    url: URL
  ) extends TAction

  final case class Record(
    action: Option[TAction] = None,
    finishOnKey: Option[String] = None,
    maxLength: Option[Int] = None,
    timeout: Int = 3600,
    trimSilence: Boolean = false,
    playBeep: Boolean = false,
    callbackUrl: Option[URL] = None
  ) extends TAction

  final case class Redirect(
    url: URL
  ) extends TAction

  case object Reject extends TAction

  case object Conference extends TAction
}
