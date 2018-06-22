package uk.gov.hmrc.pages


import play.api.libs.json.Json

import scala.io.Source

case class Enrolment(name: String, identifierName: String, identifierValue: String, state: String)

object Enrolment {
  implicit val enrolmentFormats = Json.format[Enrolment]
}

case class CDSTestDataModel(pid:String, enrolments: Option[Seq[Enrolment]])

object CDSTestDataModel{

  implicit val testDataModelFormats = Json.format[CDSTestDataModel]

  def testData(userType:String) : CDSTestDataModel = {
    val url = s"/authtestdata/$userType"
    val source = Source.fromURL(getClass.getResource(url)).mkString
    val userDetails = Json.parse(source)
    userDetails.as[CDSTestDataModel]
  }
}
