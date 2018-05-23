package uk.gov.hmrc.acceptance

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.{ChromeDriver, ChromeOptions}
import org.openqa.selenium.remote.{BrowserType, CapabilityType, DesiredCapabilities}
import org.scalatest._
import uk.gov.hmrc.drivers.Profile

trait AcceptanceSpec extends FeatureSpec with BeforeAndAfterAll with BeforeAndAfterEach with Profile with GivenWhenThen with Matchers{

  implicit val webDriver: WebDriver = createWebDriver

  def createWebDriver: WebDriver = config.browser.name match {
    case "chrome" => createChromeDriver
    case _ => throw new IllegalArgumentException(s"Unrecognised browser type: ${config.browser.name}")
  }

  override protected def beforeAll(): Unit = {
    super.beforeAll()
  }

  override protected def afterAll(): Unit = {
    super.afterAll()
    try {
      webDriver.quit()
    } catch {
      case e: Exception => log(s"Could not quit webdriver: ${e.getMessage}")
    }
  }

  override protected def beforeEach(): Unit = webDriver.manage().deleteAllCookies()

  private def createChromeDriver: WebDriver = {
    val caps = DesiredCapabilities.chrome()
    caps.setBrowserName(BrowserType.CHROME)
    caps.setJavascriptEnabled(config.browser.javascriptEnabled)
    caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, config.browser.acceptSslCertificates)
    val opts = new ChromeOptions()
    if (Profile.proxy.isDefined) {
      opts.addArguments(s"--proxy-server=localhost:${config.proxy.localPort.get}")
    }
    new ChromeDriver(opts.merge(caps))
  }

}
