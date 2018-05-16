package uk.gov.hmrc.endtoend

import uk.gov.hmrc.pages.CDSLandingPage
import uk.gov.hmrc.specs.BaseSpec

class E2ETests extends BaseSpec{

  feature("Navigate to the CDS landing page") {
    scenario("Check content and url on the cds landing page") {
      Given("i navigate to the cds landing page")
      CDSLandingPage.navigateToCDSLandingPage()
      Then("i should see \"hello customs financials frontend\"")
      CDSLandingPage.checkCDSLandingPageHeader
    }
  }

}
