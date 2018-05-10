package uk.gov.hmrc.pages

import org.openqa.selenium.{By, WebDriver}
import org.scalatest._
import org.scalatest.selenium.WebBrowser
import uk.gov.hmrc.drivers.Driver

import scala.util.Try

abstract class BasePage extends FeatureSpec with GivenWhenThen with WebBrowser with Matchers with BeforeAndAfter {

  implicit val driver: WebDriver = Driver.createDriver

  def getText(locator: By): String = {
    driver.findElement(locator).getText
  }

  before {
    driver.manage().deleteAllCookies()
  }

  after {
  }

  sys addShutdownHook {
    Try(driver.quit())
  }

}