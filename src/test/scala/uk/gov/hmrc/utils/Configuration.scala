package uk.gov.hmrc.utils


case class Configuration(url: String, AUTH_LOGIN_STUB:String, SIGN_IN_PAGE: String)

object Configuration {

  lazy val environment: Environment.Name = {
    val environmentProperty = Option(System.getProperty("environment")).getOrElse("local").toLowerCase
    environmentProperty match {
      case "local" => Environment.Local
      case "qa" => Environment.Qa
      case "dev" => Environment.Dev
      case "staging" => Environment.Staging
      case _ => throw new IllegalArgumentException(s"Environment '$environmentProperty' not known")
    }
  }

  lazy val settings: Configuration = create()

  private def create(): Configuration = {
    environment match {
      case Environment.Local =>
        new Configuration(
          url = "http://localhost",
          AUTH_LOGIN_STUB = "http://localhost:9949/auth-login-stub/gg-sign-in",
          SIGN_IN_PAGE = "http://localhost:9025/gg/sign-in"
        )
      case Environment.Dev =>
        new Configuration(
          url = "http://localhost",
          AUTH_LOGIN_STUB = "http://localhost:9949/auth-login-stub/gg-sign-in",
          SIGN_IN_PAGE = "http://localhost:9025/gg/sign-in"

        )
      case Environment.Qa =>
        new Configuration(
          url = "https://www.qa.tax.service.gov.uk",
          AUTH_LOGIN_STUB = "https://www.qa.tax.service.gov.uk/auth-login-stub/gg-sign-in",
          SIGN_IN_PAGE = "https://www.qa.tax.service.gov.uk/gg/sign-in"

        )
      case Environment.Staging =>
        new Configuration(
          url = "https://www.staging.tax.service.gov.uk",
          AUTH_LOGIN_STUB = "https://www.staging.tax.service.gov.uk/auth-login-stub/gg-sign-in",
          SIGN_IN_PAGE = "https://www.staging.tax.service.gov.uk/gg/sign-in"

        )
      case _ => throw new IllegalArgumentException(s"Environment '$environment' not known")
    }
  }
}


object Environment extends Enumeration {
  type Name = Value
  val Local, Dev, Qa, Staging = Value
}

