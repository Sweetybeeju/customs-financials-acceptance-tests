package uk.gov.hmrc.drivers

import java.net.InetSocketAddress
import java.util.concurrent.TimeUnit

import com.typesafe.scalalogging.LazyLogging
import net.lightbody.bmp.BrowserMobProxyServer
import net.lightbody.bmp.proxy.auth.AuthType
import org.openqa.selenium.chrome.ChromeDriverService

case class AppConfig(endpoint: String = "http://localhost:9876")

case class ProxyConfig(enabled: Boolean = false,
                       upstreamHost: Option[String] = None,
                       upstreamPort: Option[Int] = None,
                       localPort: Option[Int] = None,
                       username: Option[String] = None,
                       password: Option[String] = None,
                       timeoutInSeconds: Option[Int] = None)

case class BrowserConfig(name: String = "chrome",
                         driver: String = "/usr/local/bin/chromedriver",
                         driverSystemPropertyName: String = ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,
                         waitTime: Int = 30,
                         pollingWaitTime: Int = 3000,
                         javascriptEnabled: Boolean = true,
                         acceptSslCertificates: Boolean = true)

case class Config(app: AppConfig = AppConfig(),
                  proxy: ProxyConfig = ProxyConfig(),
                  browser: BrowserConfig = BrowserConfig())

trait Logging extends LazyLogging {

  def log(msg: String): Unit = logger.debug(s"********* ${msg} *********") // if you really must have stars in every log message, encapsulate them ... and the level at which we log durings tests/build

}

trait Profile extends Logging {

  val defaultProfile: String = "local-mac"

  val activeProfile: String = Option(System.getProperty("test.profile")).getOrElse(defaultProfile)

  lazy val config: Config = pureconfig.loadConfigOrThrow[Config](activeProfile)
  println("******  "+config)

}

object Profile extends Profile {

  System.setProperty(config.browser.driverSystemPropertyName, config.browser.driver)

  val proxy: Option[BrowserMobProxyServer] = config.proxy.enabled match {
    case true => {
      val p = new BrowserMobProxyServer
      p.setConnectTimeout(config.proxy.timeoutInSeconds.getOrElse(15), TimeUnit.SECONDS)
      val upstream = new InetSocketAddress(config.proxy.upstreamHost.get, config.proxy.upstreamPort.get) // proxy is enabled: upstream host and port MUST be defined
      p.setChainedProxy(upstream)
      if (config.proxy.username.isDefined && config.proxy.password.isDefined) {
        p.chainedProxyAuthorization(config.proxy.username.get, config.proxy.username.get, AuthType.BASIC) // assume only BASIC auth supported for the time being
      }
      p.setTrustAllServers(true)
      p.start(config.proxy.localPort.get) // proxy is enabled: local port MUST be defined
      Some(p)
    }
    case _ => None
  }

//  sys addShutdownHook {
//    proxy.filter(_.isStarted).foreach(_.stop())
//  }

}
