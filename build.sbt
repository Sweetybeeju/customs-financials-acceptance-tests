import sbt.Resolver
import sbt.Tests.{Group, SubProcess}

name := "customs-financials-acceptance-tests"

version := "0.1"

scalaVersion := "2.12.6"

val scalatestVersion = "3.0.4"

testOptions in Test ++= Seq(
  Tests.Argument(TestFrameworks.ScalaTest, "-h", "target/test-reports"),
  Tests.Argument(TestFrameworks.ScalaTest, "-o")
)
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % scalatestVersion % "test",
  "org.seleniumhq.selenium" % "selenium-java" % "3.4.0",
  "org.pegdown" % "pegdown" % "1.6.0" % Test,
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0"
)



