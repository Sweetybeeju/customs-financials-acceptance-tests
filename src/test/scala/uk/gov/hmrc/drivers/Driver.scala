package uk.gov.hmrc.drivers

import com.typesafe.scalalogging.LazyLogging
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.{ChromeDriver, ChromeDriverService}
import org.scalatest.Matchers
import org.scalatest.selenium.WebBrowser

import scala.util.Try

trait Host extends LazyLogging{
  val hostIs = System.getProperty("env" , "local").toLowerCase()

  val host = hostIs match {
    case "local" => {
      logger.info("********* executing tests on local environment *********")
      "http://localhost:9876"
    }
    case "dev" => {
      logger.info("********* executing tests on dev environment ******")
      "http://localhost:9876"
    }
    case "staging" => {
      logger.info("********* executing tests on staging environment *********")
      "http://localhost:9876"
    }
    case _ => throw new IllegalArgumentException("environment not recognised")
  }
}

trait Driver extends Matchers with WebBrowser with Host{

  private def getOs: String = System.getProperty("os.name")

  lazy val isMac: Boolean = getOs.startsWith("Mac")
  lazy val isLinux: Boolean = getOs.startsWith("Linux")
  lazy val waitTime = 30

  if (isMac) {
    System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, "./src/test/drivers/chrome/chromedriverMac")
    System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/src/test/resources/geckodriver")
  } else if (isLinux) {
    System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, "./src/test/drivers/chrome/chromedriverLinux")
    System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/src/test/resources/geckodriver")
  }

  val driver = createDriver

  private def createDriver: WebDriver = {
    val browser = System.getProperty("browser", "chrome")
    browser match {
      case "chrome" => {
        logger.info("******* executing tests on chrome browser *********")
        createChromeDriver
      }
      case _ => throw new IllegalArgumentException(s"browser $browser not recognised")
    }
  }

  private def createChromeDriver: WebDriver = {
    val driver = new ChromeDriver()
    driver
  }

  sys addShutdownHook {
    Try(driver.quit())
  }

}

object Driver extends Driver