package uk.gov.hmrc.specs

import com.typesafe.scalalogging.LazyLogging
import org.openqa.selenium.WebDriver
import org.scalatest._
import uk.gov.hmrc.drivers.Env
import uk.gov.hmrc.pages.CDSLandingPage

trait TestSpec extends FeatureSpec with BeforeAndAfterEach with BeforeAndAfterAll with Matchers{
  implicit val webDriver:WebDriver = Env.driver
}

trait BaseSpec extends TestSpec with GivenWhenThen with LazyLogging{

  var counter : Int = 0

  override def beforeEach(): Unit = {
    webDriver.manage().deleteAllCookies()
    counter = counter + 1
    logger.info("******** Executing tests **********----> "+counter)
  }
}