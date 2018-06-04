package uk.gov.hmrc.stepdefs

import cucumber.api.DataTable
import cucumber.api.scala.{EN, ScalaDsl}
import org.openqa.selenium.By
import org.scalatest.Matchers
import uk.gov.hmrc.pages.{CDSLandingPage, DownloadedFile, DutyDefermentPage, WebPage}
import uk.gov.hmrc.utils.StartUpTearDown

import scala.collection.JavaConversions


class CommonSteps extends WebPage with ScalaDsl with EN with Matchers with StartUpTearDown{

  var statement: DownloadedFile = _

  Given("""^I am on the (.*)$""") { page: String =>
    page match {
      case "Duty deferment page" => DutyDefermentPage.goToPage()
      case "Cds landing page" => CDSLandingPage.goToPage()
    }
  }

  Then("""^the page title should be "([^"]*)"$""") { pagetitle: String =>
    DutyDefermentPage.getTitle should be(pagetitle)
  }

  When("""^I select the month '(.*)' statement to download$""") { month: String  =>
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

  Then("""^I am able to understand the size of the PDF for '(.*)'$""") { (month:String) =>
    "9.6 kB" should be(statement.sizeDescription)
  }
}
