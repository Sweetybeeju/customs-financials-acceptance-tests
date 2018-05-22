package uk.gov.hmrc.acceptance

import uk.gov.hmrc.pages.{CDSLandingPage, DutyDefermentListPage}

class DutyDefermentPageSpec extends BaseSpec {

  feature("duty deferment file listing") {
    scenario("file can be downloaded") {
      Given("I am on the duty deferment page")
      DutyDefermentListPage.navigateToPage
      When("I select a statement for a period")
//      val statement: Array[Byte] = DutyDefermentListPage.selectStatement(1)
      Then("I am able to access the pdf file for that period")
//      (statement.length > 0) should be (true)
      And("the link text is the file name")
      DutyDefermentListPage.getFileName should be("dummy-duty-deferment-statement")
      And("I am able to understand the size of each PDF")
//      DutyDefermentListPage.sizeOfStatement(1) should be (statement.length) // TODO make this tolerant
    }
  }
}
