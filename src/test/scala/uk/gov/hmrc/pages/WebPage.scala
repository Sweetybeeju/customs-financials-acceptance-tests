package uk.gov.hmrc.pages

import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.{ExpectedCondition, WebDriverWait}
import org.scalatest.selenium.WebBrowser
import org.scalatest.time.{Seconds, Span}
import org.scalatest.{Assertions, Matchers}
import uk.gov.hmrc.utils.{Configuration, StartUpTearDown}

import scala.concurrent.duration.Duration


trait WebPage extends WebBrowser with Assertions with Matchers with StartUpTearDown{

  val relativeUrl = ""
  val port = 9876

  val url = ""
  lazy val envUrl : String = Configuration.settings.url

  implicit val duration: Duration = Span(2, Seconds)

  def waitFor[T](condition: ExpectedCondition[T])(implicit wait: WebDriverWait): T = wait.until(condition)

  def getUrl(port: Int) = if (envUrl.startsWith("http://local")) s"$envUrl:$port" else envUrl

  def goToPage(): Unit = {
    go to url
  }

  def getCurrentUrl()(implicit driver: WebDriver): String = driver.getCurrentUrl

  def getPageContent()(implicit driver: WebDriver): String = driver.getPageSource

  protected def pageHeader: Query = cssSelector("h1")

  def pageHeaderText() = {
    pageHeader.element.text
  }

  def getTitle = pageTitle

  def isCurrentPage: Boolean = false

}
