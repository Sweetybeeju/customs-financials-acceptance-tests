package appName.pages

object DutyDefermentPage extends WebPage {

  override val expectedPageTitle: Option[String] = Some("Duty Deferment Statements")

  override val port = 9876

  override val url : String = getUrl(port) + "/customs-financials/duty-deferment"

}
