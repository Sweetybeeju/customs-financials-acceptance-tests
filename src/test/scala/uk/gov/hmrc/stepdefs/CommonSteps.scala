package uk.gov.hmrc.stepdefs

import cucumber.api.DataTable
import cucumber.api.scala.{EN, ScalaDsl}
import org.scalatest.Matchers
import uk.gov.hmrc.pages.{CDSLandingPage, DownloadedFile, DutyDefermentPage, WebPage}

import scala.collection.JavaConversions


class CommonSteps extends WebPage with ScalaDsl with EN with Matchers {

  var statement: DownloadedFile = _

  Given("""^i am on the (.*)$""") { page: String =>
    page match {
      case "Duty deferment page" => DutyDefermentPage.goToPage()
      case "Cds landing page" => CDSLandingPage.goToPage()
    }
  }

  Then("""^the page title should be "([^"]*)"$""") { pagetitle: String =>
    DutyDefermentPage.getTitle should be(pagetitle)
  }

  When("""^i select the following statement to download$""") { months: DataTable =>
    val listOfMonths: List[String] = JavaConversions.asScalaBuffer(months.asList(classOf[String])).toList
    for (month <- listOfMonths) {
      month match {
        case "May" => statement = DutyDefermentPage.selectStatement(1)
      }
    }
  }

  Then("""^i am able to access the pdf file that was downlaoded$""") { () =>
    statement.mimeType should be("application/pdf")
    (statement.data.length > 0) should be(true)
  }
}
