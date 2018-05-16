package uk.gov.hmrc.pages

import com.typesafe.scalalogging.LazyLogging
import uk.gov.hmrc.drivers.Driver

object CDSLandingPage extends WebPage with LazyLogging{

  override val url : String = s"${Driver.host}/customs-financials/hello-world"
  lazy val headerText = "Hello from customs-financials-frontend !"
  lazy val pageTitle = "Hello from customs-financials-frontend"

  def helloCDSText = find(xpath("//*[@id='content']/article/h1")).get

  def navigateToCDSLandingPage() = {
    logger.info("*********** user is on CDS landing page **************")
    go to url
  }

  def checkCDSLandingPageHeader = {
    helloCDSText.text shouldBe headerText
  }

}
