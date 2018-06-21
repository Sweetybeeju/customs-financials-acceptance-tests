package uk.gov.hmrc.pages

import org.scalatest.concurrent.ScalaFutures
import uk.gov.hmrc.stepdefs.DutyDefermentAccount

case class DutyDefermentAccountDetailsPage(dutyDefermentAccount: DutyDefermentAccount) extends WebPage with ScalaFutures {
  override val url: String = getUrl(port) + s"/customs-financials/duty-deferment/${dutyDefermentAccount.dan}"

  def getDisplayedAccount: DutyDefermentAccount = {
    DutyDefermentAccount(
      find(cssSelector(".account .dan")).get.text.trim.mkString,
      find(cssSelector(".account .account-type")).get.text.trim,
      find(cssSelector(".account .account-limit-remaining")).get.text.trim.toFloat,
      find(cssSelector(".account .total-account-limit")).get.text.trim.toFloat,
      find(cssSelector(".account .guarantee-limit-remaining")).get.text.trim.toFloat
    )
  }
}