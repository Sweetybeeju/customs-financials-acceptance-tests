package uk.gov.hmrc.pages

import java.util.concurrent.TimeUnit

import akka.stream.ActorMaterializer
import org.openqa.selenium.support.ui.{ExpectedConditions, FluentWait, WebDriverWait}
import org.openqa.selenium.{By, StaleElementReferenceException, WebDriver, WebElement}
import org.scalatest.Matchers
import org.scalatest.concurrent.Eventually
import org.scalatest.selenium.{Page, WebBrowser}
import play.api.libs.ws.ahc.StandaloneAhcWSClient
import uk.gov.hmrc.drivers.Env

trait WebPage extends Page with WebBrowser with Matchers with Eventually {
  implicit val webdriver = Env.driver

//  implicit val mat = ActorMaterializer()
//
//  val ws = StandaloneAhcWSClient()

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

  def waitForElement(by: By): WebElement = {
    new WebDriverWait(webdriver,20).until(ExpectedConditions.visibilityOfElementLocated(by))
  }

  def navigateToPage: Unit = go to (url)

}
