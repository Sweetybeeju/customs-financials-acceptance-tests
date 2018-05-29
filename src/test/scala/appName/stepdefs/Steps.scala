package appName.stepdefs

import cats.syntax.either._
import cucumber.api.scala.{EN, ScalaDsl}
import appName.utils.Driver
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.scalatest.Matchers

trait Steps extends ScalaDsl with EN with Matchers {

  import Steps._

  /** Tries to get the value of [[_driver]] and [[_wait]] - will throw an exception if they don't exist */
  implicit def getDriverUnsafe: WebDriver = _driver.getOrElse(sys.error("Driver does not exist"))
  implicit def getWaitUnsafe: WebDriverWait = _wait.getOrElse(sys.error("WebDriverWait object does not exist"))

  // create a new driver for each scenario
  Before { _ ⇒
    if (_driver.isEmpty) {
      val d = Driver.newWebDriver()
        // map the left to Nothing
        .leftMap(e ⇒ sys.error(s"Could not find driver: $e"))
        // merge will merge Nothing and WebDriver to WebDriver since Nothing is a subtype of everything
        .merge
      _driver = Some(d)
    }

    if (_wait.isEmpty) {
      val wdw = new WebDriverWait(_driver.get, 15l, 100l)
      _wait = Some(wdw)
      }
  }

  After { _ ⇒
    _driver.foreach(_.quit())
    _driver = None
  }

}

object Steps {

  /**
    * Each step definition file extends the `Steps` trait , but they will all reference this single driver
    * in the companion object. Having this variable in the trait would cause multiple drivers to be
    * created
    */
  private var _driver: Option[WebDriver] = None
  private var _wait: Option[WebDriverWait] = None

}
