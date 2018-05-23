package uk.gov.hmrc.acceptance

import uk.gov.hmrc.pages.DutyDefermentListPage

class DutyDefermentPageSpec extends AcceptanceSpec {

  val EXPECTED_TEST_FILE_SIZE_1_1MB = "1.1MB"

  feature("Duty deferment file listing") {
    val ddl = new DutyDefermentListPage()
    ignore("File can be downloaded") {
      Given("i am on the duty deferment page")
      ddl.navigateToPage
      When("i select a statement for a period")
      val statement: Array[Byte] = ddl.selectStatement(1)
      (statement.length > 0) should be(true)
      Then("i am able to access the pdf file for that period")
      ddl.sizeOfStatement(1) should be(EXPECTED_TEST_FILE_SIZE_1_1MB)
    }

    ignore("Link text should be file name") {
      Given("i am on the duty deferment page")
      ddl.navigateToPage
      When("i select a statement for a period")
      val statement: Array[Byte] = ddl.selectStatement(1)
      (statement.length > 0) should be(true)
      Then("the link text should be the file name")
      ddl.getFileName should be("dummy-duty-deferment-statement")
    }

    ignore("Understand the size of each PDF") {
      Given("i am on the duty deferment page")
      ddl.navigateToPage
      When("i select a statement for a period")
      val statement: Array[Byte] = ddl.selectStatement(1)
      (statement.length > 0) should be(true)
      Then("i am able to access the pdf file for that period")
      And("i am able to understand the size of each PDF")
      ddl.sizeOfStatement(1) should be(EXPECTED_TEST_FILE_SIZE_1_1MB)
    }
  }
}
