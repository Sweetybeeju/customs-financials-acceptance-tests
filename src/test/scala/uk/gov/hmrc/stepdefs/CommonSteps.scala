package uk.gov.hmrc.stepdefs

import uk.gov.hmrc.pages.{CDSLandingPage, DutyDefermentPage}

class CommonSteps extends Steps {

  Given("""^i am on the (.*)$""") { (page: String) =>
    page match {
      case "Duty deferment page" => DutyDefermentPage.goToPage()
      case "Cds landing page" => CDSLandingPage.goToPage()
    }
  }

  Then("""^the page title should be "([^"]*)"$""") { pagetitle: String =>
    DutyDefermentPage.pageTitle should be(pagetitle)
  }

}
