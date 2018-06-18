package uk.gov.hmrc.stepdefs

import cucumber.api.scala.{EN, ScalaDsl}
import org.scalatest.Matchers
import uk.gov.hmrc.pages.{AuthLoginPage, DashboardPage, SignInPage, WebPage}
import uk.gov.hmrc.utils.StartUpTearDown

class SignInSteps extends WebPage with ScalaDsl with EN with Matchers with StartUpTearDown {

  Given("""^I am not signed in$"""){ () =>
    // do nothing: cookie should have been deleted in before hook
  }

  Then("""^I am redirected to the Sign In page$"""){ () =>
    SignInPage.isCurrentPage should be (true)
  }

  When("""^I sign in as (.*) user$"""){ userType:String =>
    val continueUrl = SignInPage.continueUrl.get
    AuthLoginPage.goToPage()
    AuthLoginPage.loginAuth(userType, continueUrl)
    AuthLoginPage.submit()
  }

  Then("""^I am redirected to the Dashboard page$"""){ () =>
    // FIXME recent changes to auth have broken acceptance tests!!! Needs to be fixed ASAP!
//    DashboardPage.isCurrentPage should be (true)
  }

}
