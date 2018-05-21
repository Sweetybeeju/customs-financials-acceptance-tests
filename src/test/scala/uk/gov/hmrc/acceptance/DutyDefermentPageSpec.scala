package uk.gov.hmrc.acceptance

import uk.gov.hmrc.pages.{CDSLandingPage, DutyDefermentListPage}

class DutyDefermentPageSpec extends BaseSpec {

  feature("duty deferment file listing") {
    scenario("file can be downloaded") {
      Given("I am on the duty deferment page")
      val page = new DutyDefermentListPage(webDriver)
      page.navigateToPage
      When("I select a statement for a period")
      page.selectStatement(0)
      Then("I am able to access the pdf file for that period")
      page.pdfHasBeenDownloaded should be (true)
      And("I am able to understand the size of each PDF")
      page.downloadedPdfIsSameAsDisplayedSizeForStatement(0)
    }
  }
}
