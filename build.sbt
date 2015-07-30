sbtPlugin := true

version := "0.1-SNAPSHOT"

name := "sbt-frege"

organization := "com.earldouglas"

ScriptedPlugin.scriptedSettings

resolvers += "jamesearldouglas" at "http://jamesearldouglas.github.com/maven-repo"

libraryDependencies += "frege" % "fregec" % "3.19.157"

publishTo := Some(Resolver.file("file",  new File("target/maven-repo")))

