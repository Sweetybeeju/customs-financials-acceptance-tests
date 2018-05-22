import uk.gov.hmrc.DefaultBuildSettings.addTestReportOption

name := "customs-financials-acceptance-tests"

version := "0.1"

scalaVersion := "2.11.11"

val scalatestVersion = "3.0.4"

lazy val EndToEndTest = config("endtoend") extend Test
lazy val AcceptanceTest = config("acceptance") extend Test

val testConfig = Seq(AcceptanceTest,EndToEndTest,Test)

lazy val customsAcceptanceTests = (project in file("."))
    .configs(testConfig: _*)
    .settings(
      allResolvers,
      acceptanceTestSettings,
      endtoendTestSettings
    )

lazy val allResolvers = resolvers ++= Seq(
  Resolver.bintrayRepo("hmrc", "releases"),
  Resolver.jcenterRepo
)

testOptions in Test ++= Seq(
  Tests.Argument(TestFrameworks.ScalaTest, "-o"),
  Tests.Argument(TestFrameworks.ScalaTest, "-h", "target/test-reports")
)


libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % scalatestVersion % "test",
  "org.seleniumhq.selenium" % "selenium-java" % "3.7.1" % "test",
  "org.pegdown" % "pegdown" % "1.6.0" % Test,
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
  "net.lightbody.bmp" % "browsermob-core" % "2.1.5",
  "com.typesafe.play" %% "play-ahc-ws-standalone" % "2.0.0-M1"
)

def filterTestsOnPackageName(rootPackage: String): (String => Boolean) = {
  testName => testName startsWith("uk.gov.hmrc." + rootPackage)
}

lazy val acceptanceTestSettings =
  inConfig(AcceptanceTest)(Defaults.testTasks) ++
    Seq(
      testOptions in AcceptanceTest := Seq(Tests.Filter(filterTestsOnPackageName("acceptance"))),
      testOptions in AcceptanceTest ++= Seq(
        Tests.Argument(TestFrameworks.ScalaTest, "-o"),
        Tests.Argument(TestFrameworks.ScalaTest, "-h", "target/test-reports")
      ),
      fork in AcceptanceTest := false,
      parallelExecution in AcceptanceTest := false
    )

lazy val endtoendTestSettings =
  inConfig(EndToEndTest)(Defaults.testTasks) ++
    Seq(
      testOptions in EndToEndTest := Seq(Tests.Filter(filterTestsOnPackageName("endtoend"))),
      testOptions in EndToEndTest ++= Seq(
        Tests.Argument(TestFrameworks.ScalaTest, "-o"),
        Tests.Argument(TestFrameworks.ScalaTest, "-h", "target/test-reports")
      ),
      fork in EndToEndTest := false,
      parallelExecution in EndToEndTest := false
    )

