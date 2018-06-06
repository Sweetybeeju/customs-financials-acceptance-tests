package uk.gov.hmrc.pages


object AuthLoginPage extends AuthLoginPage

trait AuthLoginPage extends WebPage {

  override val url = "http://localhost:9949/auth-login-stub/gg-sign-in"

  def loginAuth(userType:String) = {
    val userdata = CDSTestDataModel.testData(s"$userType.json")
    textField("authorityId").value = userdata.pid
    textField("redirectionUrl").value = "http://localhost:9876/customs-financials/"
  }
}
