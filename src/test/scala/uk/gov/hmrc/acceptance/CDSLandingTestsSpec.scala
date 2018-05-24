package uk.gov.hmrc.acceptance

import uk.gov.hmrc.pages.CDSLandingPage

class CDSLandingTestsSpec extends AcceptanceSpec  {

  feature("Navigate to the CDS landing page") {
    val cdsPage = new CDSLandingPage()
    scenario("Check content and url on the cdsPage landing page") {
      Given("i navigate to the cdsPage landing page")
      cdsPage.navigateToPage
      Then("i should see \"hello customs financials frontend\"")
      cdsPage.cdsLandingPageHeader should be("Hello from customs-financials-frontend !")
    }

    scenario("Check title") {
      Given("i navigate to the cdsPage landing page")
      cdsPage.navigateToPage
      Then("the page tile should be \"Hello from customs-financials-frontend\"")
      cdsPage.pageTitle should be("Hello from customs-financials-frontend")
    }
  }
}
