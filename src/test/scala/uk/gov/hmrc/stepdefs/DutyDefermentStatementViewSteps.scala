package uk.gov.hmrc.stepdefs

import java.util.NoSuchElementException
import scala.collection.JavaConverters._

import cucumber.api.{DataTable, PendingException}
import cucumber.api.scala.{EN, ScalaDsl}
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.scalatest.Matchers
import uk.gov.hmrc.pages._
import uk.gov.hmrc.utils.StartUpTearDown


class DutyDefermentStatementViewSteps extends WebPage with ScalaDsl with EN with Matchers with StartUpTearDown {

  Given("""I have at least one Statement available to view""") {
    // TODO this may ultimately POST data to the HODS stub, but for now it does nothing
  }

  Then("""^I am able to see the period in which the Statement was issued$"""){ () =>
    // TODO include period year/month/number explicitly in scenario
    pageElement(".period .year").toInt should be (2018)
    pageElement(".period .month").toInt should be (2)
    pageElement(".period .number").toInt should be (3)
  }

  Then("""^I am able to see the total liabilities for the (period|month) in which the Statement was issued$"""){ periodType: String =>
    // TODO explicitly state amounts in scenario
    pageElement(s".total-liabilities .$periodType .vat-due").toFloat should be (9001.00)
    pageElement(s".total-liabilities .$periodType .duty-due").toFloat should be (6000.00)
    pageElement(s".total-liabilities .$periodType .excise-due").toFloat should be (3333.00)
  }

  Then("""^I can see the following transactions$"""){ transactions: DataTable =>
    // TODO match scenario transactions to those in the webpage

    // TODO may be a cleaner way to do this...? maybe just jsoup.parse the whole page in the page object?
    val transactionsHTML = find(cssSelector(".transactions")).get.underlying.getAttribute("outerHTML")
    val doc: Document = Jsoup.parseBodyFragment(transactionsHTML)

    val headings = doc.body().getElementsByTag("thead").first.getElementsByTag("th").asScala.map(_.text)
    val rows = doc.body().getElementsByTag("tbody").first.getElementsByTag("tr").asScala

    val actualTable: Seq[Map[String, String]] = rows.map { row =>
      val rowData = row.getElementsByTag("td").asScala.map(_.text)
      val rowMap = headings.zipAll(rowData, "<Column?>", "<Data?>").toMap
      rowMap
    }

    val tbl = transactions.asMaps(classOf[String],classOf[String])
    val expectedTable = tbl.asScala.map(_.asScala)

    // TODO find a nicer matcher - actualTable should be (expectedTable)
    for (i <- expectedTable.indices) {
      expectedTable(i).map{
        case (k,v) => {
          actualTable(i).keys should contain(k)
          actualTable(i).get(k) should be (Option(v))
        }
      }
    }


  }


  private def pageElement(selector: String) = {
    try {
      find(cssSelector(selector)).get.text.trim
    } catch {
      case _: NoSuchElementException => fail(s"Selector $selector not found in page")
    }
  }
}

