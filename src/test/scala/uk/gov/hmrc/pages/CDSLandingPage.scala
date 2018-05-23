package uk.gov.hmrc.pages

import com.typesafe.scalalogging.LazyLogging
import org.openqa.selenium.{By, WebDriver}

class CDSLandingPage(implicit webdriver:WebDriver) extends WebPage with LazyLogging{

  override val url : String = s"${config.app.endpoint}/customs-financials/hello-world"

  def helloCDSText = find(xpath("//*[@id='content']/article/h1")).get

  def cdsLandingPageHeader = {
    logger.debug("*********** checking for Hello from customs-financials-frontend !  **************")
    waitForElement(By.xpath("//*[@id='content']/article/h1"))
    find(xpath("//*[@id='content']/article/h1")).get.text
  }

}

