package uk.gov.hmrc.stepdefs

import cucumber.api.Scenario
import cucumber.api.scala.{EN, ScalaDsl}
import org.scalatest.Matchers
import uk.gov.hmrc.utils.StartUpTearDown
import cucumber.api.java.{After, Before}
import org.openqa.selenium.{OutputType, TakesScreenshot, WebDriverException}


class Hooks extends ScalaDsl with EN with Matchers with StartUpTearDown{

  @Before
  def initialize(): Unit = {
    webDriver.manage().deleteAllCookies()
  }

  @After
  def tearDown(result: Scenario) {
    if (result.isFailed) {
      webDriver match {
        case screenshot1: TakesScreenshot =>
          try {
            val screenshot = screenshot1.getScreenshotAs(OutputType.BYTES)
            result.embed(screenshot, "image/png")
          } catch {
            case somePlatformsDontSupportScreenshots: WebDriverException => System.err.println(somePlatformsDontSupportScreenshots.getMessage)
          }
        case _ =>
      }
    }
  }
}
