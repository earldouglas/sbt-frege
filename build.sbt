// meta
organization := "com.earldouglas"
name := "sbt-frege"

// build
sbtPlugin := true
scalaVersion := "2.12.8"
scalacOptions ++=
  Seq( "-Yrangepos"
     , "-Ywarn-unused"
     , "-Ywarn-unused-import"
     , "-deprecation"
     , "-feature"
     , "-unchecked"
     )

// publish
publishMavenStyle := false
licenses += ("BSD New", url("http://opensource.org/licenses/BSD-3-Clause"))
