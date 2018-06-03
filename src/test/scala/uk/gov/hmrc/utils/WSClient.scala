package uk.gov.hmrc.utils

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import play.api.libs.ws.{DefaultWSProxyServer, StandaloneWSRequest, WSProxyServer}
import play.api.libs.ws.ahc.{AhcWSClientConfigFactory, StandaloneAhcWSClient}
import uk.gov.hmrc.utils.Driver.turnOnProxy

object WSClient {

  implicit val system: ActorSystem = ActorSystem()
  implicit val mat: ActorMaterializer = ActorMaterializer()
  private val ws = StandaloneAhcWSClient(
    config = AhcWSClientConfigFactory.forConfig(ConfigFactory.load())
  )

  val wsProxy: Option[WSProxyServer] = if (turnOnProxy.contains("yes")) {
    Some(DefaultWSProxyServer(host = "localhost", port = 16633, principal = Some("jenkins"), password = Some("$S4sJkIUkx")))
  } else {
    None
  }

  def wsUrl(url: String): StandaloneWSRequest = {
    val u = ws.url(url)
    if (Driver.turnOnProxy.contains("yes")) {
      u.withProxyServer(wsProxy.get)
    } else {
      u
    }
  }
}
