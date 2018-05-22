package uk.gov.hmrc.pages

import com.typesafe.scalalogging.LazyLogging
import org.openqa.selenium.By
import uk.gov.hmrc.drivers.Env

object CDSLandingPage extends WebPage with LazyLogging{

  override val url : String = s"${Env.host}/customs-financials/hello-world"
  lazy val headerText = "Hello from customs-financials-frontend !"
  lazy val pageTitle = "Hello from customs-financials-frontend"

  def helloCDSText = find(xpath("//*[@id='content']/article/h1")).get

  def checkCDSLandingPageHeader = {
    logger.debug("*********** checking for Hello from customs-financials-frontend !  **************")
    waitForElement(By.xpath("//*[@id='content']/article/h1"))
    helloCDSText.isDisplayed
    helloCDSText.text shouldBe headerText
  }

}
