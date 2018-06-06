package uk.gov.hmrc.pages

object DashboardPage extends WebPage {
  override val url : String = getUrl(port) + "/customs-financials/dashboard"

  def getH1Text: String = {
    find(xpath("//*[@id='content']/article/h1")).get.text
  }

}
