package uk.gov.hmrc.pages

import org.scalatest.Matchers
import org.scalatest.concurrent.Eventually
import org.scalatest.selenium.{Page, WebBrowser}
import uk.gov.hmrc.drivers.Driver

trait WebPage extends Page with WebBrowser with Matchers with Eventually {
  implicit val webdriver = Driver.driver

  def getText(locator: String): String = {
    find(xpath(locator)).get.text
  }

  def assertPageTitle(title:String) = {
    pageTitle shouldBe title
  }
}
