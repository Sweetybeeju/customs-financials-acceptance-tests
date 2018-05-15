package uk.gov.hmrc.config

import java.io.{FileInputStream, IOException}
import java.util.{Properties, PropertyResourceBundle}

object MessageReader {

  val messageResource = {
    lazy val message = new FileInputStream("./src/test/resources/cds/page_element_identifiers")
    try {
      new PropertyResourceBundle(message)
    } finally {
      message.close()
    }
  }

  def getElement(key:String) = {
    val modifiedKey = key.toLowerCase.replaceFirst("'","").replaceAll(" +","_")
    messageResource.getString(modifiedKey)
  }
}

