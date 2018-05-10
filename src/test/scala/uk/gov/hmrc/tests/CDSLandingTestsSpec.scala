package uk.gov.hmrc.tests

import org.openqa.selenium.By
import uk.gov.hmrc.pages.CDSLandingPage

class CDSLandingTestsSpec extends BaseSpec {

  feature("Navigate to the CDS landing page") {
    scenario("Check content and url on the cds landing page") {
      Given("i navigate to the cds landing page")
      CDSLandingPage.navigateToCDSLandingPage()
      Then("i should the hello customs declarations")
      CDSLandingPage.checkCDSLandingPageHeader
    }

    scenario("Check title") {
      Given("i navigate to the cds landing page")
      CDSLandingPage.navigateToCDSLandingPage()
      Then("the page tile should be Hello from customs-financials-frontend")
      CDSLandingPage.assertPageTitle(CDSLandingPage.pageHeaderText)
    }
  }
}
