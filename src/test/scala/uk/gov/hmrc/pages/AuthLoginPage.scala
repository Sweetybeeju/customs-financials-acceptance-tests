package uk.gov.hmrc.pages

import uk.gov.hmrc.utils.Configuration


object AuthLoginPage extends AuthLoginPage

trait AuthLoginPage extends WebPage {

  override val url: String = Configuration.settings.AUTH_LOGIN_STUB

  def loginAuth(userType:String, continueUrl: String) = {
    val userdata = CDSTestDataModel.testData(s"$userType.json")
    textField("authorityId").value = userdata.pid
    textField("redirectionUrl").value = continueUrl

    for ((enrolment, i) <- userdata.enrolments.getOrElse(List()).zipWithIndex) {
      val enrolmentId = s"enrolment[$i]"
      textField(s"$enrolmentId.name").value = enrolment.name
      // TODO Activated by default - may need to support de-activation, but the below doesn't work?
//      println(singleSel(s"$enrolmentId.state").value)
//      singleSel(s"$enrolmentId.state").value = enrolment.state
      val identifierId = s"taxIdentifier[$i]"
      textField(s"$enrolmentId.$identifierId.name").value = enrolment.identifierName
      textField(s"$enrolmentId.$identifierId.value").value = enrolment.identifierValue
    }
  }
}
