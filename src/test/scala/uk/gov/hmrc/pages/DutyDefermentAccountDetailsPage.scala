package uk.gov.hmrc.pages

import org.openqa.selenium.By
import org.scalatest.concurrent.ScalaFutures
import uk.gov.hmrc.stepdefs.{DutyDefermentAccount, DutyDefermentAccountStatement, DutyDefermentAccountStatementPeriod}

case class DutyDefermentAccountDetailsPage(dutyDefermentAccount: DutyDefermentAccount) extends WebPage with ScalaFutures {
  override val url: String = getUrl(port) + s"/customs-financials/duty-deferment/${dutyDefermentAccount.dan}"

  def getDisplayedAccount: DutyDefermentAccount = {
    val statements: Seq[DutyDefermentAccountStatement] = findAll(cssSelector(".account .statements .statement")).map { el: Element =>
      val year = el.underlying.findElement(By.cssSelector(".period .year")).getText.trim.toInt
      val month = el.underlying.findElement(By.cssSelector(".period .month")).getText.trim.toInt
      val num = el.underlying.findElement(By.cssSelector(".period .number")).getText.trim.toInt
      DutyDefermentAccountStatement(DutyDefermentAccountStatementPeriod(year, month, num))
    }.toSeq
    DutyDefermentAccount(
      find(cssSelector(".account .dan")).get.text.trim.mkString,
      find(cssSelector(".account .account-type")).get.text.trim,
      find(cssSelector(".account .account-limit-remaining")).get.text.trim.toFloat,
      find(cssSelector(".account .total-account-limit")).get.text.trim.toFloat,
      find(cssSelector(".account .guarantee-limit-remaining")).get.text.trim.toFloat,
      statements
    )
  }
}