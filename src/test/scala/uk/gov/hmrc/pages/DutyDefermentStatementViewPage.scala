package uk.gov.hmrc.pages

import org.scalatest.concurrent.ScalaFutures

case class DutyDefermentStatementViewPage(statementKey: StatementKey) extends WebPage with ScalaFutures {

  override val url: String = getUrl(port) + s"/customs-financials/duty-deferment/${statementKey.dan}/statement/${statementKey.year}/${statementKey.month}/${statementKey.number}"

}

case class StatementKey(
                         dan: String,
                         year: Int,
                         month: Int,
                         number: Int
                       )