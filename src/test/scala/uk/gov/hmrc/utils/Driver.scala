package uk.gov.hmrc.utils

import java.net.InetSocketAddress
import java.util.concurrent.TimeUnit

import cats.syntax.either._
import org.openqa.selenium.{Proxy, WebDriver}
import org.openqa.selenium.chrome.{ChromeDriver, ChromeDriverService, ChromeOptions}
import org.openqa.selenium.firefox.{FirefoxDriver, FirefoxOptions}
import org.openqa.selenium.remote.{BrowserType, CapabilityType, DesiredCapabilities}
import net.lightbody.bmp.client.ClientUtil
import net.lightbody.bmp.proxy.auth.AuthType
import net.lightbody.bmp.{BrowserMobProxy, BrowserMobProxyServer}

object Driver {

  private val systemProperties = System.getProperties

  def getOs = System.getProperty("os.name")

  lazy val isMac: Boolean = getOs.startsWith("Mac")
  lazy val isLinux: Boolean = getOs.startsWith("Linux")
  lazy val linuxArch = systemProperties.getProperty("os.arch")
  val turnOnProxy = Option(System.getProperty("turnOnProxy")).getOrElse("No")


  if (isMac) {
    System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, "./drivers/chrome/chromedriverMac")
    System.setProperty("webdriver.gecko.driver", "./drivers/firefox/geckodriverMac")
  } else {
    System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, "/usr/local/bin/chromedriver")
    System.setProperty("webdriver.gecko.driver", "./drivers/firefox/geckodriver")
  }

  def newWebDriver(): Either[String, WebDriver] = {
    val selectedDriver: Either[String, WebDriver] = Option(systemProperties.getProperty("browser", "chrome")).map(_.toLowerCase) match {
      case Some("firefox") ⇒ Right(createFirefoxDriver())
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


  private val isJsEnabled: Boolean = true

  private def createFirefoxDriver(): WebDriver = {

    val capabilities = DesiredCapabilities.firefox()
    capabilities.setJavascriptEnabled(isJsEnabled)
    capabilities.setBrowserName(BrowserType.FIREFOX)

    val proxy: BrowserMobProxy = new BrowserMobProxyServer()
    addQaProxy(proxy, getProxyConfig, capabilities)

    val options = new FirefoxOptions()
    options.merge(capabilities)

    new FirefoxDriver(options)
  }

  private def createChromeDriver(headless: Boolean): WebDriver = {

    val capabilities = DesiredCapabilities.chrome()
    capabilities.setJavascriptEnabled(isJsEnabled)
    capabilities.setBrowserName(BrowserType.CHROME)
    capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true)

    val proxy: BrowserMobProxy = new BrowserMobProxyServer()
    addQaProxy(proxy, getProxyConfig, capabilities)

    val options = new ChromeOptions()
    options.addArguments("test-type")
    options.addArguments("--disable-gpu")
    if (headless) options.addArguments("--headless")
    Option(System.getProperty("qa.proxy")).foreach { proxyConfig => options.addArguments(s"--proxy-server=http://$proxyConfig") }
    options.merge(capabilities)

    new ChromeDriver(options)
  }

  case class ProxyConfig(username: String, password: String, host: String, portNumber: Int)

  private def getProxyConfig: Option[ProxyConfig] = {
    val proxySettingPattern = """(.+):(.+)@(.+):(\d+)""".r
    Option(System.getProperty("qa.proxy")) map {
      case proxySettingPattern(user, password, host, port) => ProxyConfig(user, password, host, port.toInt)
      case _ => throw new RuntimeException("QA Proxy settings must be provided as username:password@proxyHost:proxyPortNumber")
    }
  }

  private def addQaProxy(proxy: BrowserMobProxy, proxyConfigOption: Option[ProxyConfig], capabilities: DesiredCapabilities): Unit = {
    proxyConfigOption foreach { proxyConfig =>
      proxy.chainedProxyAuthorization(proxyConfig.username, proxyConfig.password, AuthType.BASIC)
      proxy.setChainedProxy(new InetSocketAddress(proxyConfig.host, proxyConfig.portNumber))
      proxy.setTrustAllServers(true)
      proxy.start()
      val seleniumProxy: Proxy = ClientUtil.createSeleniumProxy(proxy)
      capabilities.setCapability(CapabilityType.PROXY, seleniumProxy)
    }
  }

}