package uk.gov.hmrc.config

import java.io.InputStream
import java.util.Properties

class Environment {

  def getProperties: Properties = {
    val inJarOffClasspathRootPath = "env.properties"
    val inputStream: InputStream = classOf[Nothing].getClassLoader.getResourceAsStream(inJarOffClasspathRootPath)
    val props = new Properties()
    props.load(inputStream)
    props
  }

}
