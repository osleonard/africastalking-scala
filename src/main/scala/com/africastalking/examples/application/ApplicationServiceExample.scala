package com.africastalking.examples.application

import com.africastalking.application.ApplicationService
import com.africastalking.examples.TApiExamples

import scala.concurrent.ExecutionContext.Implicits.global

object ApplicationServiceExample extends TApiExamples {
  def main(args: Array[String]): Unit = {
    fetchApplicationData()
  }

  private def fetchApplicationData() {
    ApplicationService.fetchApplicationData.onComplete(processResult)
  }
}
