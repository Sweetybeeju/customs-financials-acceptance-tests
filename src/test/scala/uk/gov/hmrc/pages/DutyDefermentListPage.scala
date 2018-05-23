package uk.gov.hmrc.pages

import com.typesafe.scalalogging.LazyLogging
import org.openqa.selenium.{By, WebDriver}
import org.scalatest.concurrent.ScalaFutures
import play.api.libs.ws.StandaloneWSRequest

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class DutyDefermentListPage(implicit webdriver:WebDriver) extends WebPage with LazyLogging with ScalaFutures {

//  override val url: String = s"${config.app.endpoint}/customs-financials/duty-deferment"
  override val url: String = "http://localhost:3000/duty-deferment"

  def selectStatement(i: Int): Array[Byte] = {
    captureLinkContent(find(cssSelector(s".duty-deferment-statements li:nth-child(${i}) a")).get.underlying.getAttribute("href"))
  }

  def getFileName: String = {
    waitForElement(By.linkText("dummy-duty-deferment-statement"))
    find(linkText("dummy-duty-deferment-statement")).get.text
  }


  def pdfHasBeenDownloaded: Boolean = ???

  def sizeOfStatement(i: Int): String = {
    val sizeInWords: String = find(cssSelector(s".duty-deferment-statements li:nth-child(${i}) .file-size")).get.text
    sizeInWords
  }

  private def captureLinkContent(url: String): Array[Byte] = {
    Await.result(
      ws.url(url).get().map { r =>
        var a: StandaloneWSRequest#Response = r
        r.bodyAsBytes.toArray
      }, 10.seconds)
  }

}



