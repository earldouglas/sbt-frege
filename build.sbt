// meta
organization := "com.earldouglas"
name := "sbt-frege"

// build
sbtPlugin := true
crossSbtVersions := Seq("1.0.1")
enablePlugins(SbtPlugin)
scalaVersion := "2.12.12"
scalacOptions ++= Seq( "-deprecation"
                     , "-encoding", "utf8"
                     , "-feature"
                     , "-language:existentials"
                     , "-language:experimental.macros"
                     , "-language:higherKinds"
                     , "-language:implicitConversions"
                     , "-unchecked"
                     , "-Xfatal-warnings"
                     , "-Xlint"
                     , "-Ypartial-unification"
                     , "-Yrangepos"
                     , "-Ywarn-unused"
                     , "-Ywarn-unused-import"
                     )

// publish
publishMavenStyle := false
licenses += ("BSD New", url("http://opensource.org/licenses/BSD-3-Clause"))
