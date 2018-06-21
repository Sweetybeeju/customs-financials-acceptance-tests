package uk.gov.hmrc.pages

import uk.gov.hmrc.utils.Configuration


object AuthLoginPage extends AuthLoginPage

trait AuthLoginPage extends WebPage {

  override val url: String = Configuration.settings.AUTH_LOGIN_STUB

  def loginAuth(userType:String, continueUrl: String) = {
    val userdata = CDSTestDataModel.testData(s"$userType.json")
    textField("authorityId").value = userdata.pid
    textField("redirectionUrl").value = url
    textField("enrolment[0].name").value = userdata.enrolments.head.key
    textField("enrolment[0].taxIdentifier[0].name").value = userdata.enrolments.head.identifier
    textField("enrolment[0].taxIdentifier[0].value").value = userdata.enrolments.head.value
    // TODO: cannot set select option, without values set
    // singleSel("enrolment[0].state").value = userdata.enrolments.head.status
  }
}
