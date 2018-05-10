package uk.gov.hmrc.pages

import org.openqa.selenium.By
import org.scalatest.Matchers
import org.scalatest.concurrent.Eventually
import org.scalatest.selenium.{Page, WebBrowser}
import uk.gov.hmrc.drivers.Driver

trait WebPage extends Page with WebBrowser with Matchers with Eventually {
  implicit val webdriver = Driver.driver

  def getText(locator: By): String = {
    webdriver.findElement(locator).getText
  }

  def assertPageTitle(title:String) = {
    pageTitle shouldBe title
  }
}
