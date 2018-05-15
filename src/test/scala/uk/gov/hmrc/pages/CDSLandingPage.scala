package uk.gov.hmrc.pages

import com.typesafe.scalalogging.LazyLogging
import uk.gov.hmrc.drivers.Driver
import uk.gov.hmrc.config.MessageReader._

object CDSLandingPage extends WebPage with LazyLogging{

  override val url : String = s"${Driver.host}/customs-financials/hello-world"
  lazy val headerText = "Hello from customs-financials-frontend !"
  lazy val pageTitle = "Hello from customs-financials-frontend"
  lazy val headerXpath = getElement("page_header_text_xpath")

  def navigateToCDSLandingPage() = {
    logger.info("*********** user is on CDS landing page **************")
    go to url
  }

  def checkCDSLandingPageHeader = {
    xpathElement(headerXpath).getText shouldBe headerText
  }

}
