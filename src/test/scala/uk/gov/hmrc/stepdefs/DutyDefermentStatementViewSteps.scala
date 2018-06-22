package uk.gov.hmrc.stepdefs

import cucumber.api.DataTable
import cucumber.api.scala.{EN, ScalaDsl}
import org.scalatest.Matchers
import uk.gov.hmrc.pages._
import uk.gov.hmrc.utils.StartUpTearDown

import scala.collection.JavaConverters._


class DutyDefermentStatementViewSteps extends WebPage with ScalaDsl with EN with Matchers with StartUpTearDown with TableHelpers {

  Given("""I have at least one Statement available to view""") {
    // TODO this may ultimately POST data to the HODS stub, but for now it does nothing
  }

  Then("""^I am able to see the period in which the Statement was issued$"""){ () =>
    // TODO include period year/month/number explicitly in scenario
    elementText(".period") should be ("Period 2018/02 - Statement Number 3")
  }

  Then("""^I am able to see the total liabilities for the (period|month) in which the Statement was issued$"""){ (periodType: String, liabilities: DataTable) =>
    val liabilitiesMap = liabilities.asMaps(classOf[String],classOf[String]).asScala.head.asScala
    for ((k, v) <- liabilitiesMap) {
      val cssClass = k.toLowerCase().replace(" ", "-")
      elementText(s".total-liabilities .for-$periodType .$cssClass") should be (v)
    }
  }

  Then("""^I can see the following transactions$"""){ transactions: DataTable =>
    val actualTable = htmlTableAsMaps(jsoupDocumentFor(".transactions"))
    val expectedTable = transactions.asMaps(classOf[String], classOf[String]).asScala.map(_.asScala.toMap)
    assertTablesAreEquivalent(expectedTable, actualTable)
  }

}

