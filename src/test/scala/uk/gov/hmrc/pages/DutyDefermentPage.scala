package uk.gov.hmrc.pages


import org.scalatest.concurrent.ScalaFutures
import play.api.libs.ws.StandaloneWSResponse
import uk.gov.hmrc.utils.WSClient

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object DutyDefermentPage extends DutyDefermentPage

trait DutyDefermentPage extends WebPage with ScalaFutures {

  override val url: String = getUrl(port) + "/customs-financials/duty-deferment"

  def selectStatement(month: String): (DownloadedFile) = {
    val href = find(xpath(s"//ul[@class='list list-bullet duty-deferment-statements']/li/a[contains(text(), '$month-2018.pdf')]")).get.underlying.getAttribute("href")
    captureLinkContent(href)
  }

  def getFileName(link: String): String = {
    val fileName = find(linkText(link)).get.text
    fileName
  }

  private def captureLinkContent(url: String): DownloadedFile = {
    Await.result(
      WSClient.wsUrl(url).get().map { r =>
        DownloadedFile(r)
      }, 10.seconds)
  }

  def getFileSize(month: String) = {
    val element: String = find(xpath(s"//ul[@class='list list-bullet duty-deferment-statements']/li[@data-statement-name='$month-2018.pdf']/span")).get.text
    element
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
