package uk.gov.hmrc.stepdefs

import org.jsoup.nodes.Document
import org.scalatest.Matchers

import scala.collection.JavaConverters._

trait TableHelpers extends Matchers {

  def assertTablesAreEquivalent(expectedTable: Seq[Map[String, String]],
                                        actualTable: Seq[Map[String, String]]) = {
    // TODO find a nicer matcher - actualTable should be (expectedTable)
    for (i <- expectedTable.indices) {
      expectedTable(i).map {
        case (k, v) => {
          actualTable(i).keys should contain(k)
          actualTable(i).get(k) should be(Option(v))
        }
      }
    }
  }

  def htmlTableAsMaps(doc: Document) = {
    val headings = headingsFromDocument(doc)
    val rows = rowsFromDocument(doc)

    val actualTable: Seq[Map[String, String]] = rows.map { row =>
      val rowData = row.getElementsByTag("td").asScala.map(_.text)
      val rowMap = headings.zipAll(rowData, "<Column?>", "<Data?>").toMap
      rowMap
    }
    actualTable
  }

  def rowsFromDocument(doc: Document) = {
    doc.body().getElementsByTag("tbody").first.getElementsByTag("tr").asScala
  }

  def headingsFromDocument(doc: Document) = {
    doc.body().getElementsByTag("thead").first.getElementsByTag("th").asScala.map(_.text)
  }

}
