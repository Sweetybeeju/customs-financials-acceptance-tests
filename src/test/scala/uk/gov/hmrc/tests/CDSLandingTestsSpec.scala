package uk.gov.hmrc.tests

import org.openqa.selenium.By
import uk.gov.hmrc.pages.BasePage

class CDSLandingTestsSpec extends BasePage {

  feature("Navigate to the CDS landing page") {
    scenario("Check content and url on the cds landing page") {
      Given("i navigate to the cds landing page")
      go to "http:localhost:9000/customs-financials-frontend/hello-world"
      Then("i should the hello customs declarations")
      getText(By.xpath("//*[@id='content']/article/h1")) shouldBe "Hello from customs-financials-frontend !"
    }

    scenario("Check title"){
      Given("i navigate to the cds landing page")
      go to "http:localhost:9000/customs-financials-frontend/hello-world"
      Then("the page tile should be Hello from customs-financials-frontend")
      pageTitle should be("Hello from customs-financials-frontend")
    }
  }
}
