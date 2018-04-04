package com.africastalking.core.commons

import com.africastalking.core.utils.DefaultJsonFormatter
import com.africastalking.sms.Message

trait JsonMarshalls extends DefaultJsonFormatter{

  implicit val messageFormat = jsonFormat5(Message)

}
