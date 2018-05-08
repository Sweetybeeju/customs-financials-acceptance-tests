name := "customs-financials-acceptance-tests"

version := "0.1"

scalaVersion := "2.12.6"

val scalatestVersion = "3.0.4"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % scalatestVersion % "test",
  "org.seleniumhq.selenium" % "selenium-java" % "3.4.0"
)
