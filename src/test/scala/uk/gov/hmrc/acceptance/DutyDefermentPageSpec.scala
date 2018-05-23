package uk.gov.hmrc.acceptance

import uk.gov.hmrc.pages.DutyDefermentListPage

class DutyDefermentPageSpec extends BaseSpec {

  val EXPECTED_TEST_FILE_SIZE_1_1MB = "1.1MB"

  feature("Duty deferment file listing") {
    ignore("File can be downloaded") {
      Given("i am on the duty deferment page")
      DutyDefermentListPage.navigateToPage
      When("i select a statement for a period")
      val statement: Array[Byte] = DutyDefermentListPage.selectStatement(1)
      (statement.length > 0) should be(true)
      Then("i am able to access the pdf file for that period")
      DutyDefermentListPage.sizeOfStatement(1) should be(EXPECTED_TEST_FILE_SIZE_1_1MB)
    }

    ignore("Link text should be file name") {
      Given("i am on the duty deferment page")
      DutyDefermentListPage.navigateToPage
      When("i select a statement for a period")
      val statement: Array[Byte] = DutyDefermentListPage.selectStatement(1)
      (statement.length > 0) should be(true)
      Then("the link text should be the file name")
      DutyDefermentListPage.getFileName should be("dummy-duty-deferment-statement")
    }

    ignore("Understand the size of each PDF") {
      Given("i am on the duty deferment page")
      DutyDefermentListPage.navigateToPage
      When("i select a statement for a period")
      val statement: Array[Byte] = DutyDefermentListPage.selectStatement(1)
      (statement.length > 0) should be(true)
      Then("i am able to access the pdf file for that period")
      And("i am able to understand the size of each PDF")
      DutyDefermentListPage.sizeOfStatement(1) should be(EXPECTED_TEST_FILE_SIZE_1_1MB)
    }
  }
}
