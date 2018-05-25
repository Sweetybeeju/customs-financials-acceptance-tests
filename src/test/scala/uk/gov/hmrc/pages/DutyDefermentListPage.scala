package uk.gov.hmrc.pages

import com.typesafe.scalalogging.LazyLogging
import org.openqa.selenium.{By, WebDriver}
import org.scalatest.concurrent.ScalaFutures
import play.api.libs.ws.StandaloneWSResponse
import play.libs.ws.StandaloneWSRequest

import scala.collection.JavaConversions
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class DutyDefermentListPage(implicit webdriver: WebDriver) extends WebPage with LazyLogging with ScalaFutures {

  override val url: String = s"${config.app.endpoint}/customs-financials/duty-deferment"

  def selectStatement(i: Int): DownloadedFile = {
    captureLinkContent(find(cssSelector(s".duty-deferment-statements li:nth-child(${i}) a")).get.underlying.getAttribute("href"))
  }

  def getFileName(i: Int): String = {
    waitForElement(By.cssSelector(s".duty-deferment-statements li:nth-child(${i}) a"))
    find(cssSelector(s".duty-deferment-statements li:nth-child(${i}) a")).get.text
  }

  def sizeOfStatement(i: Int): String = {
    val sizeInWords: String = find(cssSelector(s".duty-deferment-statements li:nth-child(${i}) .file-size")).get.text
    sizeInWords
  }

  private def captureLinkContent(url: String): DownloadedFile = {
    Await.result(
      ws.url(url).get().map { r =>
        DownloadedFile(r)
      }, 10.seconds)
  }

  def listOfStatements = {
    val statementList = JavaConversions.asScalaBuffer(webdriver.findElements(By.xpath("//*[@class='list list-bullet duty-deferment-statements']"))).toList
    statementList
  }

}

case class DownloadedFile(data: Array[Byte], mimeType: String, disposition: String, name: String) {

  def sizeDescription: String = data.length match {
    case kb if 1000 until 1000000 contains kb => f"${kb.toFloat / 1000}%1.1f kB"
    case mb if mb >= 1000000 => f"${mb.toFloat / 1000000}%1.1f MB"
    case _ => data.length + " bytes"
  }

}

object DownloadedFile {

  def apply(resp: StandaloneWSResponse): DownloadedFile = {
    val data = resp.bodyAsBytes.toArray
    val mimeType = resp.contentType
    val hdr = resp.header("Content-Disposition").getOrElse("").split(";").take(2).map(_.trim).toList
    val disposition = hdr(0)
    val name: String = hdr(1).split("=").take(2).map(_.trim).toList(1).replaceAll("\"", "")
    DownloadedFile(data, mimeType, disposition, name)
  }

}

