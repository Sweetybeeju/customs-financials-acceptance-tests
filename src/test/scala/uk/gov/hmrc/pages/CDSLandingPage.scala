package uk.gov.hmrc.pages

object CDSLandingPage extends WebPage{

  override val url : String = getUrl(port) + "/customs-financials/hello-world"
}
