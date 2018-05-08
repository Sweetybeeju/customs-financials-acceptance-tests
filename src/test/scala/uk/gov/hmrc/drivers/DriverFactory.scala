package uk.gov.hmrc.drivers

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.{ChromeDriver, ChromeDriverService}

object Driver {

  def getOs = System.getProperty("os.name")

  lazy val isMac = getOs.startsWith("Mac")

  if(isMac) {
    System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, "./src/test/drivers/chrome/chromedriverMac")
    System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/src/test/resources/geckodriver")
  }else {

  }

  def createDriver: WebDriver = {
    val driver = new ChromeDriver()
    driver
  }

}