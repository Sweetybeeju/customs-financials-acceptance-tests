package uk.gov.hmrc.config

import java.io.IOException
import java.util.Properties

object Environment {

  private def getProperties(): Properties = {
    val prop = new Properties()
    prop
  }

  def getProperty(key:String) : String = {
    var value : String = ""
    try{
      value = getProperties().getProperty(key)
    } catch {
        case ex : IOException => {
          println("Oops something went wrong" )
        }
    }
    value
  }

}

