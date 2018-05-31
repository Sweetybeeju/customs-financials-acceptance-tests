package uk.gov.hmrc.utils

import java.net.InetSocketAddress
import java.util.concurrent.TimeUnit

import cats.syntax.either._
import net.lightbody.bmp.proxy.auth.AuthType
import net.lightbody.bmp.{BrowserMobProxy, BrowserMobProxyServer}
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.{ChromeDriver, ChromeDriverService, ChromeOptions}
import org.openqa.selenium.remote.{BrowserType, CapabilityType, DesiredCapabilities}
import play.api.libs.ws.{DefaultWSProxyServer, WSProxyServer}

object Driver {

  private val systemProperties = System.getProperties

  def getOs: String = System.getProperty("os.name")

  lazy val isMac: Boolean = getOs.startsWith("Mac")
  lazy val isLinux: Boolean = getOs.startsWith("Linux")

  val proxy: BrowserMobProxy = new BrowserMobProxyServer()
  val proxyPort: Int = Option(System.getProperty("proxyPort")).getOrElse("11000").toInt
  val turnOnProxy: String = Option(System.getProperty("turnOnProxy")).getOrElse("No")
  private val isJsEnabled: Boolean = true

  if (isMac) {
    System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, "./drivers/chrome/chromedriverMac")
  } else {
    System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, "/usr/local/bin/chromedriver")
  }

  def newWebDriver(): Either[String, WebDriver] = {
    val selectedDriver: Either[String, WebDriver] = Option(systemProperties.getProperty("browser", "chrome")).map(_.toLowerCase) match {
      case Some("chrome") ⇒ Right(createChromeDriver(false))
      case Some("headless") ⇒ Right(createChromeDriver(true))
      case Some(other) ⇒ Left(s"Unrecognised browser: $other")
      case None ⇒ Left("No browser set")
    }

    selectedDriver.map { driver ⇒
      sys.addShutdownHook(driver.quit())
      driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS)
    }
    selectedDriver
  }

  private def createChromeDriver(headless: Boolean): WebDriver = {

    val capabilities = DesiredCapabilities.chrome()
    capabilities.setJavascriptEnabled(isJsEnabled)
    capabilities.setBrowserName(BrowserType.CHROME)
    capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true)
    val options = new ChromeOptions()
    options.addArguments("test-type")
    options.addArguments("--disable-gpu")
    if (headless) options.addArguments("--headless")
    if (turnOnProxy.equalsIgnoreCase("yes")) {
      if (proxy.isStarted) proxy.stop()
      proxy.setConnectTimeout(15, TimeUnit.SECONDS)
      val upstream_proxy = new InetSocketAddress("outbound-proxy-vip", 3128)
      proxy.setChainedProxy(upstream_proxy)
      proxy.chainedProxyAuthorization("jenkins", "$S4sJkIUkx&V", AuthType.BASIC)
      proxy.setTrustAllServers(true)
      proxy.start(proxyPort)
      options.addArguments(s"--proxy-server=localhost:${proxyPort}")
    }
    options.merge(capabilities)
    new ChromeDriver(options)
  }

  val wsProxy: Option[WSProxyServer] = if(turnOnProxy.contains("yes")){
    Some(DefaultWSProxyServer(host = "localhost",port=3128,principal = Some("jenkins"),password = Some("$S4sJkIUkx")))
  } else {
    None
  }
}