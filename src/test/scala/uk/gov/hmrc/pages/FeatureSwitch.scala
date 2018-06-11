package uk.gov.hmrc.pages

case class FeatureSwitch(featureName: String) extends WebPage {
   val enableUrl: String = getUrl(port) +  s"/customs-financials/test-only/feature/$featureName/enable"
   val disableUrl: String = getUrl(port) +  s"/customs-financials/test-only/feature/$featureName/disable"

  def enableFeature = {
    for (i <- 1 to 5) {
      go to enableUrl
    }
  }

  def disableFeature = {
    for (i <- 1 to 5) {
      go to disableUrl
    }
  }
}
