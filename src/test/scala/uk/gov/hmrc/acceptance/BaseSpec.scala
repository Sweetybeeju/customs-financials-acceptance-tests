package uk.gov.hmrc.acceptance

import com.typesafe.scalalogging.LazyLogging
import org.openqa.selenium.WebDriver
import org.scalatest._
import uk.gov.hmrc.drivers.Env

trait TestSpec extends FeatureSpec with BeforeAndAfterEach with BeforeAndAfterAll with Matchers{
  implicit val webDriver:WebDriver = Env.driver
}

trait BaseSpec extends TestSpec with GivenWhenThen with LazyLogging{

  var counter : Int = 0

  override def beforeEach(): Unit = {
    webDriver.manage().deleteAllCookies()
    counter = counter + 1
    logger.debug("******** Executing tests **********----> "+counter)
  }
}