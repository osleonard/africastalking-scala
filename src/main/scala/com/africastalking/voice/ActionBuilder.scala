package com.africastalking.voice

import com.africastalking.voice.action._

import scala.xml.{Elem, NodeSeq}

object ActionBuilder {

  private var xml: ResponseXml = EmptyXml

  def build: String = {
    xml match {
      case EmptyXml => ""
      case NonEmptyXml(node) =>
        val element: Elem =
          <Response>
            node
          </Response>

        element.toString()
    }
  }

  def say = this

  def play = this

  def dial = this

  def getDigits = this

  lazy val conference = <Conference />

  lazy val reject = <Reject />

  def say(say: Say) = {
    val sayElement = <Say voice={say.voice.toString} playBeep={say.playBeep.toString} >{say.text}</Say>
    xml = NonEmptyXml(sayElement)
    this
  }

  def play(url: String) = <Play url={url}/>


  def getDigits(node: NodeSeq, finishOnKey: String, timeout: Int = 30) = {
    <GetDigits timeout={timeout.toString} finishOnKey={finishOnKey}>
      node
    </GetDigits>
  }

  def dial(phoneNumbers: String, record: String = "false") = {
      <Dial
        phoneNumbers={phoneNumbers}
        ringbackTone="http://mymediafile.com/playme.mp3"
        record={record}
        sequential="true"
      />
  }

  def record(node: NodeSeq,  finishOnKey: String, maxLength: String, timeout: Int = 3600, playBeep: Boolean = false, trimSilence: Boolean = false) = {
    <Record
      timeout={timeout.toString}
      finishOnKey={finishOnKey}
      playBeep={playBeep.toString}
      trimSilence={trimSilence.toString} >
      node
    </Record>
  }

  def enqueue(holdMusic: String, queueName: String) = <Enqueue holdMusic={holdMusic} name={queueName} />

  def dequeue(phoneNumber: String) = <Dequeue phoneNumber={phoneNumber} />

  def redirect(url: String) =   <Redirect>{url}</Redirect>
}

object Voice extends Enumeration {
  val Man   = Value("man")
  val Women = Value("women")
}

trait ResponseXml

case object EmptyXml extends ResponseXml

case class NonEmptyXml(element: Elem) extends ResponseXml