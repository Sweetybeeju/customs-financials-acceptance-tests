

name := "customs-financials-acceptance-tests"

version := "0.1"

scalaVersion := "2.11.11"

val scalatestVersion = "3.0.4"

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
  "net.lightbody.bmp" % "browsermob-core" % "2.1.5"
)



