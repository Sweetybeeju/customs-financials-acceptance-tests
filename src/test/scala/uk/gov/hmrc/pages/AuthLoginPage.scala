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
      textField("enrolment[$i].name").value = enrolment.name
      textField(s"enrolment[$i].taxIdentifier[$i].name").value = enrolment.identifier
      textField(s"enrolment[$i].taxIdentifier[$i].value").value = enrolment.value
      // TODO: Activated by default - cannot set select option, without value attribute
      // singleSel(s"enrolment[$i].state").value = enrolment.state
    }
  }
}
