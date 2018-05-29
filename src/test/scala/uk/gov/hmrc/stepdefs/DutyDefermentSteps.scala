package uk.gov.hmrc.stepdefs

import uk.gov.hmrc.pages.DutyDefermentPage

class DutyDefermentSteps extends Steps{

  Given("""^i am on the duty deferment page$"""){ () =>
    DutyDefermentPage.goToPage()
  }

  Then("""^the page title should be "([^"]*)"$"""){ pagetitle:String =>
    DutyDefermentPage.pageTitle should be(pagetitle)
  }

}
