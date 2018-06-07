package uk.gov.hmrc.pages

import uk.gov.hmrc.utils.Configuration


object AuthLoginPage extends AuthLoginPage

trait AuthLoginPage extends WebPage {

  override val url: String = Configuration.settings.AUTH_LOGIN_STUB


  def loginAuth(userType:String, continueUrl: String) = {
    val userdata = CDSTestDataModel.testData(s"$userType.json")
    textField("authorityId").value = userdata.pid
    textField("redirectionUrl").value = continueUrl
  }
}
