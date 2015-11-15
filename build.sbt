// general
organization  :=  "com.earldouglas"
name          :=  "sbt-frege"
sbtPlugin     :=  true
scalaVersion  :=  "2.10.6"
scalacOptions ++= Seq("-feature", "-deprecation")

// scripted-plugin
ScriptedPlugin.scriptedSettings
scriptedBufferLog  := false
scriptedLaunchOpts <+= version { "-Dplugin.version=" + _ }
watchSources       <++= sourceDirectory map { path => (path ** "*").get }

// bintray-sbt
publishMavenStyle := false
licenses          += ("BSD New", url("http://opensource.org/licenses/BSD-3-Clause"))
