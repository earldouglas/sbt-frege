scalaVersion := "2.11.7"

enablePlugins(JettyPlugin)

libraryDependencies +=
  "javax.servlet" % "javax.servlet-api" % "3.0.1" % "provided"

fregeLibrary := "org.frege-lang" % "frege" % "3.24.100.1" classifier "jdk8"
