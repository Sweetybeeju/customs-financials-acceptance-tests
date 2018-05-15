package uk.gov.hmrc.specs

import uk.gov.hmrc.pages.CDSLandingPage

class CDSLandingTestsSpec extends BaseSpec {

  feature("Navigate to the CDS landing page") {
    scenario("Check content and url on the cds landing page") {
      Given("i navigate to the cds landing page")
      CDSLandingPage.navigateToCDSLandingPage()
      Then("i should see \"hello customs financials frontend\"")
      CDSLandingPage.checkCDSLandingPageHeader
    }

    scenario("Check title") {
      Given("i navigate to the cds landing page")
      CDSLandingPage.navigateToCDSLandingPage()
      Then("the page tile should be \"Hello from customs-financials-frontend\"")
      CDSLandingPage.assertPageTitle(CDSLandingPage.pageTitle)
    }
  }
}
