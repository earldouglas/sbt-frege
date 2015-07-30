// general
organization  :=  "com.earldouglas"
name          :=  "sbt-frege"
sbtPlugin     :=  true
scalaVersion  :=  "2.10.5"
scalacOptions ++= Seq("-feature", "-deprecation")

libraryDependencies += "frege" % "fregec" % "3.22.524" from
  "https://github.com/Frege/frege/releases/download/3.22.324/frege3.22.524-gcc99d7e.jar"

// scripted-plugin
ScriptedPlugin.scriptedSettings
scriptedBufferLog  := false
scriptedLaunchOpts <+= version { "-Dplugin.version=" + _ }
watchSources       <++= sourceDirectory map { path => (path ** "*").get }

// bintray-sbt
publishMavenStyle := false
licenses          += ("BSD New", url("http://opensource.org/licenses/BSD-3-Clause"))
