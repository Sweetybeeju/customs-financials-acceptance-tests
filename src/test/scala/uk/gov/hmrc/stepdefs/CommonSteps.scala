package uk.gov.hmrc.stepdefs

import cucumber.api.scala.{EN, ScalaDsl}
import org.scalatest.Matchers
import uk.gov.hmrc.pages._
import uk.gov.hmrc.utils.StartUpTearDown


class CommonSteps extends WebPage with ScalaDsl with EN with Matchers with StartUpTearDown{

  Given("""^I am on the (.*) page$""") { page: String =>
    page match {
      case "Duty deferment" => DutyDefermentPage.goToPage()
      case "Cds landing" => CDSLandingPage.goToPage()
      case "Dashboard" => DashboardPage.goToPage()
      case "Auth login stub" => AuthLoginPage.goToPage()
    }
  }

  Then("""^the page title should be "([^"]*)"$""") { pagetitle: String =>
    getTitle should be(pagetitle)
  }

  When("""^click submit$"""){ () =>
    click on className("button")
  }
}
