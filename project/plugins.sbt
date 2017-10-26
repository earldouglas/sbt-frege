// bintray for publishing
addSbtPlugin("org.foundweekends" % "sbt-bintray" % "0.5.1")

// scripted for plugin testing
libraryDependencies += "org.scala-sbt" %% "scripted-plugin" % sbtVersion.value
