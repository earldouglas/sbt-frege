// meta
organization := "com.earldouglas"
name := "sbt-frege"

// build
sbtPlugin := true
enablePlugins(SbtPlugin)
scalaVersion := "2.12.20"
crossScalaVersions += "3.3.5"
pluginCrossBuild / sbtVersion := {
  scalaBinaryVersion.value match {
    case "2.12" => (pluginCrossBuild / sbtVersion).value
    case _      => "2.0.0-M2"
  }
}
scalacOptions ++= {
  scalaBinaryVersion.value match {
    case "2.12" =>
      Seq(
        "-Ywarn-unused-import",
        "-Xlint",
        "-Ypartial-unification",
        "-Yrangepos"
      )
    case _ =>
      Seq.empty // ("-Wunused:all") this is blocked by sbt/sbt#7726
  }
}
scalacOptions ++= Seq( "-deprecation"
                     , "-encoding", "utf8"
                     , "-feature"
                     , "-language:existentials"
                     , "-language:experimental.macros"
                     , "-language:higherKinds"
                     , "-language:implicitConversions"
                     , "-unchecked"
                     , "-Xfatal-warnings"
                     )

// publish to Sonatype, https://www.scala-sbt.org/release/docs/Using-Sonatype.html
credentials := List(Credentials(Path.userHome / ".sbt" / "sonatype_credentials"))
description := "Frege support for sbt"
developers := List(Developer(id = "earldouglas", name = "James Earl Douglas", email = "james@earldouglas.com", url = url("https://earldouglas.com/")))
homepage := Some(url("https://github.com/earldouglas/sbt-frege"))
licenses := List("BSD New" -> url("https://opensource.org/licenses/BSD-3-Clause"))
organizationHomepage := Some(url("https://earldouglas.com/"))
organizationName := "James Earl Douglas"
pomIncludeRepository := { _ => false }
publishMavenStyle := true
publishTo := sonatypePublishToBundle.value
scmInfo := Some(ScmInfo(url("https://github.com/earldouglas/sbt-frege"), "scm:git@github.com:earldouglas/sbt-frege.git"))
versionScheme := Some("semver-spec")
