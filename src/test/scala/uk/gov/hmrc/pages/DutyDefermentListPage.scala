package uk.gov.hmrc.pages

import java.nio.charset.StandardCharsets

import com.typesafe.scalalogging.LazyLogging
import org.openqa.selenium.{By, WebDriver}
import org.scalatest.concurrent.ScalaFutures
import uk.gov.hmrc.drivers.Env

import scala.concurrent.Await
import scala.concurrent.duration._

object DutyDefermentListPage extends WebPage with LazyLogging with ScalaFutures {

  override val url: String = s"${Env.host}/customs-financials/duty-deferment"

  private val kb = "^([0-9]+)kB$".r
  private val mb = "^([0-9]+(\.[0-9]+)?)MB$".r

  def selectStatement(i: Int): Array[Byte] =
    captureLinkContent(find(By.cssSelector(s".duty-deferment-statements li:nth-child(${i}) a")).getAttribute("href"))

  def pdfHasBeenDownloaded: Boolean = ???

  def sizeOfStatement(i: Int): Int = {
    val sizeInWords: String = driver.findElement(By.cssSelector(s".duty-deferment-statements li:nth-child(${i}) .file-size")).getText.trim
    // TODO convert "1.1MB" or "512kB" etc into byte length as number
    sizeInWords match {
      case kb(kilobytes) => {
        // TODO
      }
      case mb(megabytes) => {
        // TODO
      }
    }
    0
  }

  private def captureLinkContent(url: String): Array[Byte] = {
    Await.result(
      ws.url(url).get().map { r =>
        r.body.getBytes(StandardCharsets.UTF_8)
      }, 10.seconds)
  }

}
