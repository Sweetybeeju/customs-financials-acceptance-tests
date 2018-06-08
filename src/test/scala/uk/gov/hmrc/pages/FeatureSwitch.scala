package uk.gov.hmrc.pages

case class FeatureSwitch(featureName: String) extends WebPage {
  override val url: String = getUrl(port) +  s"/customs-financials/test-only/feature/$featureName/enable"

  def enableFeature = {
    for (i <- 1 to 5) {
      go to url
    }
  }


}
