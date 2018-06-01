package uk.gov.hmrc.stepdefs

import cucumber.api.DataTable
import uk.gov.hmrc.pages.{CDSLandingPage, DownloadedFile, DutyDefermentPage}

import scala.collection.JavaConversions


class CommonSteps extends Steps {

  var statement: DownloadedFile = _

  Given("""^I am on the (.*)$""") { page: String =>
    page match {
      case "Duty deferment page" => DutyDefermentPage.goToPage()
      case "Cds landing page" => CDSLandingPage.goToPage()
    }
  }

  Then("""^the page title should be "([^"]*)"$""") { pagetitle: String =>
    DutyDefermentPage.pageTitle should be(pagetitle)
  }

  When("""^I select the following statement to download$""") { months: DataTable =>
    val listOfMonths: List[String] = JavaConversions.asScalaBuffer(months.asList(classOf[String])).toList
    for (month <- listOfMonths) {
        statement = DutyDefermentPage.selectStatement(month)
    }
  }

  Then("""^I am able to access the pdf file that was downloaded$""") { () =>
    statement.mimeType should be("application/pdf")
    (statement.data.length > 0) should be(true)
  }

  Then("""^the link text should be the same as the filename$""") { () =>
    DutyDefermentPage.getFileName(1) should be(statement.name)
  }

  Then("""^I am able to understand the size of each PDF$""") { () =>
    println("****    "+statement.sizeDescription)
    DutyDefermentPage.sizeOfStatement(1) should be(statement.sizeDescription)
  }
}
