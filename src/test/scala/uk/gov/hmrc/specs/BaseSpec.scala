package uk.gov.hmrc.specs

import org.openqa.selenium.WebDriver
import org.scalatest._
import uk.gov.hmrc.drivers.Driver

trait TestSpec extends FeatureSpec with BeforeAndAfterEach with BeforeAndAfterAll with Matchers{
  implicit val webDriver:WebDriver = Driver.driver
}

trait BaseSpec extends TestSpec with GivenWhenThen{

  var counter : Int = 0

  override def beforeEach(): Unit = {
    webDriver.manage().deleteAllCookies()
    counter = counter + 1
    println("*********************** Executing tests ********************----> "+counter)
  }

}