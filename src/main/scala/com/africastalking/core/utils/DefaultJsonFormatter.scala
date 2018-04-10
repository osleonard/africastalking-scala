package com.africastalking.core
package utils

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCode
import spray.json._

import scala.reflect.ClassTag


case class ApiResponse(responseStatus: StatusCode, payload: String)

trait DefaultJsonFormatter extends DefaultJsonProtocol with SprayJsonSupport {
  def jsonObjectFormat[E: ClassTag]: RootJsonFormat[E] = new RootJsonFormat[E] {
    val classTag = implicitly[ClassTag[E]]
    override def read(json: JsValue): E = classTag.runtimeClass.newInstance().asInstanceOf[E]
    override def write(obj: E): JsValue = JsObject("value" -> JsString(classTag.runtimeClass.getSimpleName))
  }
}



class EnumJsonConverter[T <: scala.Enumeration](enu: T) extends RootJsonFormat[T#Value] {
  override def write(obj: T#Value): JsValue = JsString(obj.toString)

  override def read(json: JsValue): T#Value = {
    json match {
      case JsString(txt) => enu.withName(txt)
      case somethingElse => throw DeserializationException(s"Expected a value from enum $enu instead of $somethingElse")
    }
  }
}