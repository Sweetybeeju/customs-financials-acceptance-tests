package uk.gov.hmrc.pages

object FinancialsLandingPage extends FinancialsLandingPage

case class Account(accountNumber: String,
                   accountType: String,
                   currentBalance: BigDecimal,
                   accountStatus: String,
                   AccountCurrentHeadroomForEachDutyDefermentAccount: BigDecimal)

trait FinancialsLandingPage extends WebPage {

  val ACCOUNT_NUMBER_INDEX = 1
  val ACCOUNT_TYPE_INDEX = 2
  val CURRENT_BALANCE_INDEX = 3
  val ACCOUNT_STATUS_INDEX = 4
  val ACCOUNT_CURRENT_HEADROOM_FOR_EACH_DUTY_DEFERMENT_ACCOUNT_INDEX = 5

  implicit class StringToBigDecimal(s: String) {
    def toBigdecimal = BigDecimal.apply(s)
  }

  override val url: String = getUrl(port) + "/customs-financials/accounts"
  override def isCurrentPage: Boolean = getTitle == "Accounts"

//  val numberOfAccounts: Int = findAll(xpath("//*[@id='content']/table/tbody/tr")).size
//  import scala.collection.immutable.IndexedSeq
//  val accounts: IndexedSeq[Account] = {
//    for (accountIndex <- 0 to numberOfAccounts)
//      yield Account(
//        find(xpath(s"//*[@id='content']/table/tbody/tr[$accountIndex]/td[$ACCOUNT_NUMBER_INDEX]")).get.text,
//        find(xpath(s"//*[@id='content']/table/tbody/tr[$accountIndex]/td[$ACCOUNT_TYPE_INDEX]")).get.text,
//        find(xpath(s"//*[@id='content']/table/tbody/tr[$accountIndex]/td[$CURRENT_BALANCE_INDEX]")).get.text.toBigdecimal,
//        find(xpath(s"//*[@id='content']/table/tbody/tr[$accountIndex]/td[$ACCOUNT_STATUS_INDEX]")).get.text,
//        find(xpath(s"//*[@id='content']/table/tbody/tr[$accountIndex]/td[$ACCOUNT_CURRENT_HEADROOM_FOR_EACH_DUTY_DEFERMENT_ACCOUNT_INDEX]")).get.text.toBigdecimal
//      )
//  }
}
