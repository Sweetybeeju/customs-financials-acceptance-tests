package uk.gov.hmrc.stepdefs

import cucumber.api.scala.{EN, ScalaDsl}
import org.scalatest.Matchers
import uk.gov.hmrc.pages.{FinancialsLandingPage, WebPage}
import uk.gov.hmrc.utils.StartUpTearDown

class FinancialsLandingSteps extends WebPage with ScalaDsl with EN with Matchers with StartUpTearDown {

  Then("""^I can see the Financials Landing page$"""){ () =>
    FinancialsLandingPage.isCurrentPage should be (true)
  }

  Then("""I should see the duty deferment account with DAN '(.*)' listed in the accounts""") { dan: Int =>
  }

}
