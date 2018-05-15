package uk.gov.hmrc.pages

import uk.gov.hmrc.drivers.Driver

object CDSLandingPage extends WebPage {

  override val url : String = s"${Driver.host}/customs-financials/hello-world"
  lazy val pageTitle = "Hello from customs-financials-frontend"

  def navigateToCDSLandingPage() = {
    go to url
  }

  def checkCDSLandingPageHeader = {
    getText("//*[@id='content']/article/h1") shouldBe "Hello from customs-financials-frontend !"
  }

}
