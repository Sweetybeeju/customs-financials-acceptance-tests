package uk.gov.hmrc.stepdefs

import cucumber.api.scala.{EN, ScalaDsl}
import org.scalatest.Matchers
import uk.gov.hmrc.pages.{DashboardPage, WebPage}
import uk.gov.hmrc.utils.StartUpTearDown

class DashBoardSteps extends WebPage with ScalaDsl with EN with Matchers with StartUpTearDown{

  Given("""^I attempt to navigate to the Dashboard page directly without Auth$"""){ () =>
    DashboardPage.goToPage()
  }


  Then("""^I should not be on the (.*) page$"""){ (page : String) =>
    DashboardPage.getCurrentUrl() should not be DashboardPage.url
  }

  Then("""^I should be on the Dashboard page$"""){ () =>
    DashboardPage.goToPage()
    DashboardPage.getH1Text should be("user@test.com you are logged in")
  }

}
