package appName.data

import appName.utils.Environment
import appName.utils.Configuration.environment

case class TestClient(nino: String)

object ClientTestData {

  lazy val ClientWithData: TestClient = environment  match {
    case Environment.Local => TestClient("AE123456C")
    case Environment.Dev => TestClient("AA001813D")
    case Environment.Qa => TestClient("AA001813D")
  }

}

