scalaVersion := "2.11.7"

enablePlugins(JettyPlugin)

javacOptions ++= Seq("-encoding", "UTF-8")

libraryDependencies +=
  "javax.servlet" % "javax.servlet-api" % "3.0.1" % "provided"
