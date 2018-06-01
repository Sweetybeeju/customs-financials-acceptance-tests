package uk.gov.hmrc.pages

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import org.scalatest.concurrent.ScalaFutures
import play.api.libs.ws.ahc.{AhcWSClientConfigFactory, StandaloneAhcWSClient}
import play.api.libs.ws.{StandaloneWSRequest, StandaloneWSResponse}
import uk.gov.hmrc.utils.Driver

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object DutyDefermentPage extends WebPage with ScalaFutures{

  override val url: String = getUrl(port) + "/customs-financials/duty-deferment"

  var cap : DownloadedFile = _

  implicit val system: ActorSystem = ActorSystem()
  implicit val mat: ActorMaterializer = ActorMaterializer()
  private val ws = StandaloneAhcWSClient(
    config = AhcWSClientConfigFactory.forConfig(ConfigFactory.load())
  )

  def wsUrl(url: String): StandaloneWSRequest = {
    val u = ws.url(url)
    if (Driver.turnOnProxy.contains("yes")) {
      u.withProxyServer(Driver.wsProxy.get)
    } else {
      u
    }
  }

  def selectStatement(month: String): DownloadedFile = {
    val listOfElements: List[DutyDefermentPage.Element] =findAll(xpath("//ul[@class='list list-bullet duty-deferment-statements']/li/a")).toList
    for (el <- listOfElements){
     val href: String =  el.attribute("href").get
      if(href.contains(month)){
        cap = captureLinkContent(href)
      }
    }
    cap
  }

  def getFileName(i: Int): String = {
    find(cssSelector(s".duty-deferment-statements li:nth-child(${i}) a")).get.text
  }

  private def captureLinkContent(url: String) : DownloadedFile = {
  Await.result(
    wsUrl(url).get().map{ r =>
      DownloadedFile(r)
    },10.seconds)
  }

  def sizeOfStatement(i: Int): String = {
    val sizeInWords: String = find(cssSelector(s".duty-deferment-statements li:nth-child(${i}) .file-size")).get.text
    sizeInWords
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
    println("hdr "+hdr(1))
    DownloadedFile(data, mimeType, disposition, name)
  }

}
