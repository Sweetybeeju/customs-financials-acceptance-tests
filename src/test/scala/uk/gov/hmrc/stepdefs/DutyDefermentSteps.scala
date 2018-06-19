package uk.gov.hmrc.stepdefs

import cucumber.api.DataTable
import cucumber.api.scala.{EN, ScalaDsl}
import org.scalatest.Matchers
import uk.gov.hmrc.pages._
import uk.gov.hmrc.utils.StartUpTearDown

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

  Given("""^I have a duty deferment account with DAN '(.*)'$""") { dan: Int =>
    // TODO later, we may need this to setup DD account data in hods stub?
    // if we don't, this step can potentially be removed
  }

  When("""^I navigate to the duty deferment account details page for DAN '(.*)'$""") { dan: Int =>
    DutyDefermentAccountDetailsPage(findDutyDefermentAccount(dan)).goToPage()
  }

  Then("""^I should see the account summary for DAN '(.*)'$""") { dan: Int =>
    val acc = findDutyDefermentAccount(dan)
    DutyDefermentAccountDetailsPage(acc).getDisplayedAccount should be (acc)
  }

  private def findDutyDefermentAccount(dan: Int): DutyDefermentAccount = {
    // TODO load accounts from JSON and return  the requested one as DD account. Hard coded for now.
    DutyDefermentAccount(
      "1234567", "foo", 34632.00f, 60000.00f, 0.00f
    )
  }

}

case class DutyDefermentAccount(dan: String,
                                accountType: String,
                                accountLimitRemaining: Float,
                                totalAccountLimit: Float,
                                guaranteeLimitRemaining: Float)

