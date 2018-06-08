package uk.gov.hmrc.pages

object FeatureSwitch extends WebPage {
  override val url = "https://www.qa.tax.service.gov.uk/customs-financials/test-only/feature/duty-deferment/enable"

  def enableDutyDeferment() = {
    for (i <- 1 to 5) {
      go to url
    }
  }


}
