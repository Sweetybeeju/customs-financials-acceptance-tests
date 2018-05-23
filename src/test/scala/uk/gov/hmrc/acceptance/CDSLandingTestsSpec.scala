package uk.gov.hmrc.acceptance

import uk.gov.hmrc.pages.CDSLandingPage

class CDSLandingTestsSpec extends AcceptanceSpec  {

  feature("Navigate to the CDS landing page") {
    val cds = new CDSLandingPage()
    scenario("Check content and url on the cds landing page") {
      Given("i navigate to the cds landing page")
      cds.navigateToPage
      Then("i should see \"hello customs financials frontend\"")
      cds.cdsLandingPageHeader should be("Hello from customs-financials-frontend !")
    }

    scenario("Check title") {
      Given("i navigate to the cds landing page")
      cds.navigateToPage
      Then("the page tile should be \"Hello from customs-financials-frontend\"")
      cds.pageTitle should be("Hello from customs-financials-frontend")
    }
  }
}
