package uk.gov.hmrc.utils

import java.net.InetSocketAddress
import java.util.concurrent.TimeUnit

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

  val instance: WebDriver = newWebDriver()

  def newWebDriver(): WebDriver = {
    val browserProperty = systemProperties.getProperty("browser", "chrome")
    val driver = createBrowser(browserProperty)
    driver
  }

  private def createBrowser(browserProperty: String): WebDriver = {
    browserProperty match {
      case "chrome" => createChromeDriver()
      case _ => throw new IllegalArgumentException(s"browser type $browserProperty not recognised ")
    }
  }

  private def createChromeDriver(): WebDriver = {

    val options = new ChromeOptions()
    options.addArguments("test-type")
    options.addArguments("--disable-gpu")
    println(s"### turnOnProxy: ${turnOnProxy}")
    if (turnOnProxy.equalsIgnoreCase("yes")) {
      println("### Proxy is turned ON")
      if (proxy.isStarted) proxy.stop()
      proxy.setConnectTimeout(15, TimeUnit.SECONDS)
      val upstream_proxy = new InetSocketAddress("outbound-proxy-vip", 3128)
      println("This is the upstream proxy "+upstream_proxy)
      proxy.setChainedProxy(upstream_proxy)
      proxy.chainedProxyAuthorization("jenkins", "$S4sJkIUkx&V", AuthType.BASIC)
      proxy.setTrustAllServers(true)
      proxy.start(16633)
      options.addArguments(s"--proxy-server=localhost:${16633}")
    }

    val capabilities = DesiredCapabilities.chrome()
    capabilities.setJavascriptEnabled(isJsEnabled)
    capabilities.setBrowserName(BrowserType.CHROME)
    capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true)

    options.addArguments("--incognito")
    options.merge(capabilities)
    new ChromeDriver(options)
  }

  val wsProxy: Option[WSProxyServer] = if (turnOnProxy.contains("yes")) {
    Some(DefaultWSProxyServer(host = "localhost", port = 16633, principal = Some("jenkins"), password = Some("$S4sJkIUkx")))
  } else {
    None
  }

  sys.addShutdownHook(instance.quit())
}
