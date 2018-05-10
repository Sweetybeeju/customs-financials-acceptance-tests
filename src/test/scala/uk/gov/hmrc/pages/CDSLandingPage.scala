package uk.gov.hmrc.pages

import org.openqa.selenium.By
import uk.gov.hmrc.drivers.Driver

object CDSLandingPage extends WebPage {

  override val url : String = s"${Driver.host}/customs-financials-frontend/hello-world"
  lazy val pageHeaderText = "Hello from customs-financials-frontend"

  def navigateToCDSLandingPage()={
    go to url
  }

  def checkCDSLandingPageHeader = {
    getText(By.xpath("//*[@id='content']/article/h1")) shouldBe "Hello from customs-financials-frontend !"
  }


}
