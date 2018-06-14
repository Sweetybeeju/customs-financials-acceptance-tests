package uk.gov.hmrc.pages

object CDSLandingPage extends CDSLandingPage

trait CDSLandingPage extends WebPage{
  override val url : String = getUrl(port) + "/customs-financials/hello-world"
}
