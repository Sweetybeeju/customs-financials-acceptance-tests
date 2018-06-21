package uk.gov.hmrc.pages

import java.net.{URL, URLDecoder}
import java.nio.charset.StandardCharsets

import uk.gov.hmrc.utils.Configuration

object SignInPage extends SignInPage

trait SignInPage extends WebPage {

  override val url: String = Configuration.settings.SIGN_IN_PAGE

  def continueUrl: Option[String] = {
    val q = Option(new URL(webDriver.getCurrentUrl).getQuery)
    q.flatMap { str =>
      str.split("&").map(v => {
        val m =  v.split("=", 2).map(s => URLDecoder.decode(s, StandardCharsets.UTF_8.name()))
        m(0) -> m(1)
      }).toMap.get("continue")
    }
  }

  override def isCurrentPage: Boolean = getTitle == "Sign in - Government Gateway"
}
