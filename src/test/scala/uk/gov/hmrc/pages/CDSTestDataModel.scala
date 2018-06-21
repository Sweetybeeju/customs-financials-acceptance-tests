package uk.gov.hmrc.pages


import play.api.libs.json.Json

import scala.io.Source

case class CDSTestDataModel(pid: String, enrolments: List[Enrolment])
case class Enrolment(key: String, identifier: String, value: String, status: String)

object CDSTestDataModel{

  implicit val enrolmentFormats = Json.format[Enrolment]
  implicit val testDataModelFormats = Json.format[CDSTestDataModel]

  def testData(userType: String) : CDSTestDataModel = {
    val url = s"/authtestdata/$userType"
    val source = Source.fromURL(getClass.getResource(url)).mkString
    val userDetails = Json.parse(source)
    userDetails.as[CDSTestDataModel]
  }
}
