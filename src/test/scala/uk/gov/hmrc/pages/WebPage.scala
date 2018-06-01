package uk.gov.hmrc.pages

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import uk.gov.hmrc.stepdefs.Steps
import uk.gov.hmrc.utils.{Configuration, Driver}
import org.openqa.selenium.{By, WebDriver, WebElement}
import org.scalatest.selenium.Page
import org.openqa.selenium.support.ui.{ExpectedCondition, ExpectedConditions, WebDriverWait}
import org.openqa.selenium.support.ui.ExpectedConditions._
import org.scalatest.selenium.WebBrowser
import org.scalatest.time.{Seconds, Span}
import org.scalatest.{Assertions, Matchers}
import play.api.libs.ws.StandaloneWSRequest
import play.api.libs.ws.ahc.{AhcWSClientConfigFactory, StandaloneAhcWSClient}


import scala.concurrent.ExecutionContext.Implicits.global
import java.util.concurrent.TimeUnit

import scala.concurrent.duration.Duration


trait WebPage extends Page with WebBrowser with Assertions with Matchers with Steps{

  val relativeUrl = ""
  val port = 9876

  lazy val envUrl : String = Configuration.settings.url

  implicit val duration: Duration = Span(2, Seconds)


  def waitFor[T](condition: ExpectedCondition[T])(implicit wait: WebDriverWait): T = wait.until(condition)

  def getUrl(port: Int) = if (envUrl.startsWith("http://local")) s"$envUrl:$port" else envUrl

  def goToPage(): Unit = go to url

  def getCurrentUrl()(implicit driver: WebDriver): String = driver.getCurrentUrl

  def getPageContent()(implicit driver: WebDriver): String = driver.getPageSource

  protected def pageHeader: Query = cssSelector("h1")

  def pageHeaderText() = {
    pageHeader.element.text
  }

  def waitForPageToLoad: WebElement = {
    waitFor(visibilityOfElementLocated(pageHeader.by))
  }

}
