package uk.gov.hmrc.pages

import uk.gov.hmrc.pages.CDSLandingPage.{getUrl, port}

object DashboardPage extends WebPage {
  override val url : String = getUrl(port) + "/customs-financials/dashboard"

}
