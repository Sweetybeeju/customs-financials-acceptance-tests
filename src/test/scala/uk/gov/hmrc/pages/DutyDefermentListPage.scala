package uk.gov.hmrc.pages

import com.typesafe.scalalogging.LazyLogging
import org.openqa.selenium.WebDriver
import uk.gov.hmrc.drivers.Env

class DutyDefermentListPage(driver: WebDriver) extends WebPage with LazyLogging {

  override val url: String = s"${Env.host}/customs-financials/duty-deferment"

  def selectStatement(i: Int): Unit = ???

  def pdfHasBeenDownloaded: Boolean = ???

  def downloadedPdfIsSameAsDisplayedSizeForStatement(i: Int): Boolean = ???

}
