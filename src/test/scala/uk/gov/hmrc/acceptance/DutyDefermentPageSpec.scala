package uk.gov.hmrc.acceptance

import uk.gov.hmrc.pages.{DownloadedFile, DutyDefermentListPage}

class DutyDefermentPageSpec extends AcceptanceSpec {

  val EXPECTED_TEST_FILE_SIZE_1_1MB = "1.1MB"

  feature("Duty deferment file listing") {

    val dutyDefermentPage = new DutyDefermentListPage()

    scenario("File can be downloaded") {
      Given("i am on the duty deferment page")
      dutyDefermentPage.navigateToPage
      When("i select a statement for a period")
      val statement: DownloadedFile = dutyDefermentPage.selectStatement(1)
      Then("i am able to access the pdf file for that period")
      (statement.data.length > 0) should be(true)
      statement.mimeType should be("application/pdf")
    }

    scenario("Link text should be file name") {
      Given("i am on the duty deferment page")
      dutyDefermentPage.navigateToPage
      When("i select a statement for a period")
      val statement: DownloadedFile = dutyDefermentPage.selectStatement(1)
      (statement.data.length > 0) should be(true)
      dutyDefermentPage.listOfStatements.nonEmpty should be(true)
      Then("the link text should be the file name")
      dutyDefermentPage.getFileName(1) should be(statement.name)
    }

    scenario("Understand the size of each PDF") {
      Given("i am on the duty deferment page")
      dutyDefermentPage.navigateToPage
      When("i select a statement for a period")
      val statement: DownloadedFile = dutyDefermentPage.selectStatement(1)
      (statement.data.length > 0) should be(true)
      Then("i am able to understand the size of each PDF")
      dutyDefermentPage.sizeOfStatement(1) should be(statement.sizeDescription)
    }
  }
}
