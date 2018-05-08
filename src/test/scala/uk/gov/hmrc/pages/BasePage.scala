package uk.gov.hmrc.pages

import org.openqa.selenium.By
import org.scalatest.selenium.WebBrowser
import org.scalatest.{BeforeAndAfterEach, FeatureSpec, GivenWhenThen, Matchers}
import uk.gov.hmrc.drivers.Driver

import scala.util.Try

abstract class BasePage extends FeatureSpec with GivenWhenThen with WebBrowser with Matchers with BeforeAndAfterEach{

  implicit val driver = Driver.createDriver

  def getText(locator:By): String = {
    driver.findElement(locator).getText
  }

  sys addShutdownHook {
    Try(driver.quit())
  }

}