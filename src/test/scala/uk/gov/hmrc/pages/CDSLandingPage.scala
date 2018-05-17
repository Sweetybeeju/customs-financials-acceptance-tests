package uk.gov.hmrc.pages

import com.typesafe.scalalogging.LazyLogging
import uk.gov.hmrc.drivers.Env

object CDSLandingPage extends WebPage with LazyLogging{

  override val url : String = s"${Env.host}/customs-financials/hello-world"
  lazy val headerText = "Hello from customs-financials-frontend !"
  lazy val pageTitle = "Hello from customs-financials-frontend"

  def helloCDSText = find(xpath("//*[@id='content']/article/h1")).get

  def navigateToCDSLandingPage() = {
    logger.info("*********** user is on CDS landing page **************")
    go to url
    pageTitle should be(pageTitle)
  }

  def checkCDSLandingPageHeader = {
    logger.info("*********** checking for Hello from customs-financials-frontend !  **************")
    waitForElementVisibility(helloCDSText.underlying)
    helloCDSText.isDisplayed
    helloCDSText.text shouldBe headerText
  }

}
