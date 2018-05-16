package uk.gov.hmrc.pages

import java.util.concurrent.TimeUnit

import org.openqa.selenium.support.ui.{ExpectedConditions, FluentWait}
import org.openqa.selenium.{By, StaleElementReferenceException, WebDriver, WebElement}
import org.scalatest.Matchers
import org.scalatest.concurrent.Eventually
import org.scalatest.selenium.{Page, WebBrowser}
import uk.gov.hmrc.drivers.Env

trait WebPage extends Page with WebBrowser with Matchers with Eventually {
  implicit val webdriver = Env.driver

  var fluentWait: FluentWait[WebDriver] = new FluentWait[WebDriver](webdriver)
    .withTimeout(Env.waitTime, TimeUnit.SECONDS)
    .pollingEvery(1, TimeUnit.SECONDS)
    .ignoring(classOf[NoSuchElementException])
    .ignoring(classOf[StaleElementReferenceException])

  def assertPageTitle(title: String) = {
    pageTitle shouldBe title
  }

  def waitForElementVisibility(webelement: WebElement): WebElement = {
    fluentWait.until(ExpectedConditions.visibilityOf(webelement))
  }
}
