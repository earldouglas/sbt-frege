// general
organization := "com.earldouglas"
name         := "sbt-frege"

sbtPlugin := true

scalaVersion := "2.12.4"
scalacOptions ++=
  Seq( "-Yrangepos"
     , "-Ywarn-unused"
     , "-Ywarn-unused-import"
     , "-deprecation"
     , "-feature"
     , "-unchecked"
     )

// bintray-sbt
publishMavenStyle := false
licenses += ("BSD New", url("http://opensource.org/licenses/BSD-3-Clause"))

// scripted-plugin
scriptedLaunchOpts := { scriptedLaunchOpts.value ++
  Seq("-Xmx1024M", "-Dplugin.version=" + version.value)
}
scriptedBufferLog := false
