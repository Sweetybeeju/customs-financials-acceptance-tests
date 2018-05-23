//package uk.gov.hmrc.drivers
//
//import java.net.InetSocketAddress
//import java.util.concurrent.TimeUnit
//
//import com.typesafe.scalalogging.LazyLogging
//import net.lightbody.bmp.BrowserMobProxyServer
//import net.lightbody.bmp.proxy.auth.AuthType
//import org.openqa.selenium.WebDriver
//import org.openqa.selenium.chrome.{ChromeDriver, ChromeDriverService, ChromeOptions}
//import org.openqa.selenium.remote.{BrowserType, CapabilityType, DesiredCapabilities}
//import org.scalatest.Matchers
//import org.scalatest.selenium.WebBrowser
//
//import scala.util.Try
//
//trait Host extends LazyLogging{
//
//  val hostIs = Option(System.getProperty("env")).getOrElse("local")
//  val turnOnProxy = Option(System.getProperty("turnOnProxy")).getOrElse("No")
//  val proxyPort = Option(System.getProperty("proxyPort")).getOrElse("16688").toInt
//
//  val proxy = new BrowserMobProxyServer
//  private val isJsEnabled: Boolean = true
//  val options = new ChromeOptions()
//
//  val host = hostIs match {
//      case "local" => {
//        logger.info("********* executing tests on local environment *********")
//        "http://localhost:9876"
//      }
//      case "dev" => {
//        logger.info("********* executing tests on dev environment ******")
//        "http://localhost:9876"
//      }
//      case "staging" => {
//        logger.info("********* executing tests on staging environment *********")
//        "http://localhost:9876"
//      }
//      case "qa" => {
//        logger.info("********* executing tests on qa environment *********")
//        "https://www.qa.tax.service.gov.uk"
//      }
//      case _ => throw new IllegalArgumentException("environment not recognised")
//    }
//}
//
//trait Env extends Matchers with WebBrowser with Host{
//
//  private def getOs: String = System.getProperty("os.name")
//
//  lazy val isMac: Boolean = getOs.startsWith("Mac")
//  lazy val isLinux: Boolean = getOs.startsWith("Linux")
//  lazy val waitTime = 30
//  lazy val pollingWaitTime = 3000
//
//  if (isMac) {
//    System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, "./src/test/drivers/chrome/chromedriverMac")
//    System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/src/test/resources/geckodriver")
//  } else if (isLinux) {
//    System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver")
//  }
//
//  val driver = createDriver
//
//  private def createDriver: WebDriver = {
//    val browser = System.getProperty("browser", "chrome")
//    browser match {
//      case "chrome" => {
//        logger.info("******* executing tests on chrome browser *********")
//        createChromeDriver
//      }
//      case _ => throw new IllegalArgumentException(s"browser $browser not recognised")
//    }
//  }
//
//  private def createChromeDriver: WebDriver = {
//
//    if (Env.turnOnProxy.equalsIgnoreCase("yes")) {
//      if (Env.proxy.isStarted) Env.proxy.stop()
//      Env.proxy.setConnectTimeout(15, TimeUnit.SECONDS)
//      val upstream_proxy = new InetSocketAddress("outbound-proxy-vip", 3128)
//      Env.proxy.setChainedProxy(upstream_proxy)
//      Env.proxy.chainedProxyAuthorization("jenkins", "$S4sJkIUkx&V", AuthType.BASIC)
//      Env.proxy.setTrustAllServers(true)
//      Env.proxy.start(Env.proxyPort)
//      Env.options.addArguments(s"--proxy-server=localhost:${Env.proxyPort}")
//    } else {
//      Env.options.addArguments("--start-maximized")
//    }
//
//    val capabilities = DesiredCapabilities.chrome()
//    capabilities.setJavascriptEnabled(true)
//    capabilities.setBrowserName(BrowserType.CHROME)
//    capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true)
//
//    options.merge(capabilities)
//    new ChromeDriver(options)
//  }
//
////  sys addShutdownHook {
////    Try(driver.quit())
////  }
//
//}
//
//object Env extends Env