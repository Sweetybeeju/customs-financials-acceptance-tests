package uk.gov.hmrc.endtoend

import uk.gov.hmrc.acceptance.AcceptanceSpec
import uk.gov.hmrc.pages.CDSLandingPage

class EndToEndSpec extends AcceptanceSpec {

  feature("Navigate to the CDS landing page") {
    val cds = new CDSLandingPage()
    scenario("Check content and url on the cds landing page") {
      Given("i navigate to the cds landing page")
      cds.navigateToPage
      Then("i should see \"hello customs financials frontend\"")
      cds.cdsLandingPageHeader
    }
  }
}
