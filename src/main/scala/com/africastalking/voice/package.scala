package com.africastalking

import com.africastalking.core.utils.DefaultJsonFormatter

package object voice {
  final case class CallPayload(
    username: String,
    from: String,
    to: String
  )

  final case class QueuedCallsPayload(
    username: String,
    phoneNumbers: String
  )

  final case class MediaUploadPayload(
    username: String,
    url: String,
    phoneNumber: String
  )

  final case class CallResponse(
    status: String,
    phoneNumber: String,
    errorMessage: String
  )

  final case class QueuedCallsResponse(
    phoneNumber: String,
    queueName: String,
    numCalls: Int
  )

  object VoiceJsonProtocol extends DefaultJsonFormatter {
    implicit val callPayloadFormat         = jsonFormat3(CallPayload)
    implicit val queuedCallsPayloadFormat  = jsonFormat2(QueuedCallsPayload)
    implicit val mediaUploadPayloadFormat  = jsonFormat3(MediaUploadPayload)
    implicit val callResponseFormat        = jsonFormat3(CallResponse)
    implicit val queuedCallsResponseFormat = jsonFormat3(QueuedCallsResponse)
  }
}
