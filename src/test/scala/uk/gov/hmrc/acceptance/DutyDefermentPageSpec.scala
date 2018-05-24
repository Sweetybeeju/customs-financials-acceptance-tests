package uk.gov.hmrc.acceptance

import uk.gov.hmrc.pages.DutyDefermentListPage

class DutyDefermentPageSpec extends AcceptanceSpec {

  val EXPECTED_TEST_FILE_SIZE_1_1MB = "1.1MB"

  feature("Duty deferment file listing") {
    val dutyDefermentPage = new DutyDefermentListPage()
    ignore("File can be downloaded") {
      Given("i am on the duty deferment page")
      dutyDefermentPage.navigateToPage
      When("i select a statement for a period")
      val statement: (Array[Byte], String) = dutyDefermentPage.selectStatement(1)
      Then("i am able to access the pdf file for that period")
      (statement._1.length > 0) should be(true)
      statement._2 should be("application/pdf")
    }

    ignore("Link text should be file name") {
      Given("i am on the duty deferment page")
      dutyDefermentPage.navigateToPage
      When("i select a statement for a period")
      val statement: (Array[Byte], String) = dutyDefermentPage.selectStatement(1)
      (statement._1.length > 0) should be(true)
      Then("the link text should be the file name")
      dutyDefermentPage.getFileName should be("dummy-duty-deferment-statement")
    }

    ignore("Understand the size of each PDF") {
      Given("i am on the duty deferment page")
      dutyDefermentPage.navigateToPage
      When("i select a statement for a period")
      val statement: (Array[Byte], String) = dutyDefermentPage.selectStatement(1)
      (statement._1.length > 0) should be(true)
      Then("i am able to understand the size of each PDF")
      dutyDefermentPage.sizeOfStatement(1) should be(EXPECTED_TEST_FILE_SIZE_1_1MB)
    }
  }
}
