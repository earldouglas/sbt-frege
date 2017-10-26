// general
organization := "com.earldouglas"
name := "sbt-frege"
sbtPlugin := true
scalaVersion := "2.12.4"
scalacOptions ++= Seq("-feature", "-deprecation")

// bintray-sbt
publishMavenStyle := false
licenses += ("BSD New", url("http://opensource.org/licenses/BSD-3-Clause"))
