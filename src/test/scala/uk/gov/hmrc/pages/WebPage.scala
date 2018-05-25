package uk.gov.hmrc.pages

import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import org.openqa.selenium.support.ui.{ExpectedConditions, FluentWait, WebDriverWait}
import org.openqa.selenium.{By, StaleElementReferenceException, WebDriver, WebElement}
import org.scalatest.Matchers
import org.scalatest.concurrent.Eventually
import org.scalatest.selenium.{Page, WebBrowser}
import play.api.libs.ws.StandaloneWSRequest
import play.api.libs.ws.ahc.{AhcWSClientConfigFactory, StandaloneAhcWSClient}
import uk.gov.hmrc.drivers.Profile

abstract class WebPage(implicit webdriver : WebDriver) extends Page with WebBrowser with Matchers with Eventually with Profile{

  implicit val system: ActorSystem = ActorSystem()
  implicit val mat: ActorMaterializer = ActorMaterializer()
  private val ws = StandaloneAhcWSClient(
    config = AhcWSClientConfigFactory.forConfig(ConfigFactory.load())
  )

  def wsUrl(url: String): StandaloneWSRequest = {
    val u = ws.url(url)
    if (Profile.wsProxy.isDefined) {
      u.withProxyServer(Profile.wsProxy.get)
    } else {
      u
    }
  }

  var fluentWait: FluentWait[WebDriver] = new FluentWait[WebDriver](webdriver)
    .withTimeout(3000, TimeUnit.SECONDS)
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
    new WebDriverWait(webdriver, 20).until(ExpectedConditions.visibilityOfElementLocated(by))
  }

  def navigateToPage: Unit = go to (url)

}
