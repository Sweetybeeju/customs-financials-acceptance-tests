package uk.gov.hmrc.stepdefs

import cucumber.api.scala.{EN, ScalaDsl}
import org.scalatest.Matchers
import uk.gov.hmrc.pages._
import uk.gov.hmrc.utils.StartUpTearDown


class CommonSteps extends WebPage with ScalaDsl with EN with Matchers with StartUpTearDown{

  var statement: DownloadedFile = _

  Given("""^I am on the (.*) page$""") { page: String =>
    page match {
      case "Duty deferment" => DutyDefermentPage.goToPage()
      case "Cds landing" => CDSLandingPage.goToPage()
      case "Dashboard" => DashboardPage.goToPage()
      case "Auth login stub" => AuthLoginPage.goToPage()
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
    DutyDefermentPage.getFileSize(month) should be(statement.sizeDescription)
  }

  When("""^I enter the (.*) user details on the auth page$"""){ userType:String =>
    AuthLoginPage.loginAuth(userType)
  }

  When("""^click submit$"""){ () =>

  }

  Then("""^I should be on the Dashboard page$"""){ () =>

  }

}
