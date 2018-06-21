package uk.gov.hmrc.stepdefs

import cucumber.api.DataTable
import cucumber.api.scala.{EN, ScalaDsl}
import org.scalatest.Matchers
import play.api.libs.json.{Format, Json}
import uk.gov.hmrc.pages._
import uk.gov.hmrc.utils.{StartUpTearDown, TestDataLoader}
import DutyDefermentAccount._

class DutyDefermentSteps extends WebPage with ScalaDsl with EN with Matchers with StartUpTearDown {

  var statement: DownloadedFile = _

  When("""^I select the month '(.*)' statement to download$""") { month: String =>
    statement = DutyDefermentPage.selectStatement(month)
  }

  Then("""^I am able to access the pdf file that was downloaded$""") { () =>
    statement.mimeType should be("application/pdf")
    (statement.data.length > 0) should be(true)
  }

  Then("""^the link text for period '(.*)' should be the same as the filename$""") { (linkText: String) =>
    val link = s"$linkText-2018.pdf"
    DutyDefermentPage.getFileName(link) should be(statement.name)
  }

  Then("""^I am able to understand the size of the PDF for '(.*)'$""") { (month: String) =>
    DutyDefermentPage.getFileSize(month) should be(statement.sizeDescription)
  }

  Then("""^I should see my following Duty Deferment Accounts$""") { data: DataTable =>

  }

  Given("""^I have a duty deferment account with DAN '(.*)'$""") { dan: String =>
    // TODO later, we may need this to setup DD account data in hods stub?
    // if we don't, this step can potentially be removed
  }

  When("""^I navigate to the duty deferment account details page for DAN '(.*)'$""") { dan: String =>
    DutyDefermentAccountDetailsPage(findDutyDefermentAccount(dan)).goToPage()
  }

  Then("""^I should see the account summary for DAN '(.*)'$""") { dan: String =>
    val expectedAccount = findDutyDefermentAccount(dan)
    DutyDefermentAccountDetailsPage(expectedAccount).getDisplayedAccount should be (expectedAccount)
  }

  private def findDutyDefermentAccount(dan: String): DutyDefermentAccount = {
    TestDataLoader.loadFromJson[DutyDefermentAccount](s"duty-deferment-accounts/${dan}")
  }

}

case class DutyDefermentAccount(dan: String,
                                `type`: String,
                                accountLimitRemaining: Float,
                                totalAccountLimit: Float,
                                guaranteeLimitRemaining: Float,
                                statements: Seq[DutyDefermentAccountStatement] = Seq.empty)

case class DutyDefermentAccountStatement(period: DutyDefermentAccountStatementPeriod)

case class DutyDefermentAccountStatementPeriod(year: Int, month: Int, number: Int)

object DutyDefermentAccount {
  implicit val dutyDefermentAccountStatementPeriodFormat: Format[DutyDefermentAccountStatementPeriod] = Json.format[DutyDefermentAccountStatementPeriod]
  implicit val dutyDefermentAccountStatementFormat: Format[DutyDefermentAccountStatement] = Json.format[DutyDefermentAccountStatement]
  implicit val dutyDefermentAccountFormat: Format[DutyDefermentAccount] = Json.format[DutyDefermentAccount]
}
