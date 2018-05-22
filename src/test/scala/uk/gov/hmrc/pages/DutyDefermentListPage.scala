package uk.gov.hmrc.pages

import com.typesafe.scalalogging.LazyLogging
import org.openqa.selenium.By
import org.scalatest.concurrent.ScalaFutures
import play.api.libs.ws.StandaloneWSRequest
import uk.gov.hmrc.drivers.Env

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object DutyDefermentListPage extends WebPage with LazyLogging with ScalaFutures {

  override val url: String = s"${Env.host}/customs-financials/duty-deferment"

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


