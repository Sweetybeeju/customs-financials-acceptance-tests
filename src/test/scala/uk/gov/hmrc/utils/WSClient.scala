package uk.gov.hmrc.utils

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import play.api.libs.ws.StandaloneWSRequest
import play.api.libs.ws.ahc.{AhcWSClientConfigFactory, StandaloneAhcWSClient}

object WSClient {

  implicit val system: ActorSystem = ActorSystem()
  implicit val mat: ActorMaterializer = ActorMaterializer()
  private val ws = StandaloneAhcWSClient(
    config = AhcWSClientConfigFactory.forConfig(ConfigFactory.load())
  )

  def wsUrl(url: String): StandaloneWSRequest = {
    val u = ws.url(url)
    if (Driver.turnOnProxy.contains("yes")) {
      u.withProxyServer(Driver.wsProxy.get)
    } else {
      u
    }
  }

}
