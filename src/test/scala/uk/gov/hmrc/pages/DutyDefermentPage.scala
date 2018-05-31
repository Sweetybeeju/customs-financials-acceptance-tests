package uk.gov.hmrc.pages

import org.scalatest.concurrent.ScalaFutures
import play.api.libs.ws.StandaloneWSResponse

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._




object DutyDefermentPage extends WebPage with ScalaFutures{

  override val url: String = getUrl(port) + "/customs-financials/duty-deferment"

  def selectStatement(i: Int): DownloadedFile = {
    captureLinkContent(find(cssSelector(s".duty-deferment-statements li:nth-child(${i}) a")).get.underlying.getAttribute("href"))
  }

  private def captureLinkContent(url: String) : DownloadedFile = {
  Await.result(
    wsUrl(url).get().map{ r =>
      DownloadedFile(r)
    },10.seconds)

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
