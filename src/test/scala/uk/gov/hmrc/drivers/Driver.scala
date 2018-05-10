package uk.gov.hmrc.drivers

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.{ChromeDriver, ChromeDriverService}

object Driver {

  def getOs: String = System.getProperty("os.name")

  lazy val isMac: Boolean = getOs.startsWith("Mac")
  lazy val isLinux: Boolean = getOs.startsWith("Linux")

  if(isMac) {
    System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, "./src/test/drivers/chrome/chromedriverMac")
    System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/src/test/resources/geckodriver")
  }else if(isLinux) {
    System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, "./src/test/drivers/chrome/chromedriverLinux")
    System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/src/test/resources/geckodriver")
  }

  def createDriver: WebDriver = {
    val driver = new ChromeDriver()
    driver
  }

}