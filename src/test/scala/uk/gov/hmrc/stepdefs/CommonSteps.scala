package uk.gov.hmrc.stepdefs

import cucumber.api.scala.{EN, ScalaDsl}
import org.scalatest.Matchers
import uk.gov.hmrc.pages._
import uk.gov.hmrc.utils.StartUpTearDown


class CommonSteps extends WebPage with ScalaDsl with EN with Matchers with StartUpTearDown {

  val someDAN = "1234567"
  val someStatementKey = StatementKey(someDAN, 2018, 2, 3)

  Given("""^the (.*) feature is (.*)""") { (feature: String, featureState: String) =>
    FeatureSwitch(feature.toLowerCase().replace(" ", "-"), featureState.dropRight(1)).featureToggle
  }

  Given("""^I (am on|try to navigate to|navigate to) the (.*) page$""") { (_: String, page: String) =>
    page match {
      case "Financials Landing" => FinancialsLandingPage.goToPage()
      case "Duty deferment" => DutyDefermentPage.goToPage()
      case "Cds landing" => CDSLandingPage.goToPage()
      case "Dashboard" => DashboardPage.goToPage()
      case "Auth login stub" => AuthLoginPage.goToPage()
      case "Duty Deferment Statement View" => DutyDefermentStatementViewPage(someStatementKey).goToPage()
      case "Finance" => ""
    }
  }

  Then("""^the page title should be "([^"]*)"$""") { pagetitle: String =>
    getTitle should be(pagetitle)
  }

  When("""^click submit$"""){ () =>
    click on className("button")
  }

  Then("""^page not found is returned$""") { () =>
    pageTitle shouldBe "Page not found - 404"
  }
}
