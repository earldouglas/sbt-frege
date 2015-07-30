Option(System.getProperty("plugin.version")) match {
  case None =>
    throw new RuntimeException(
      """|The system property 'plugin.version' is not defined.
         |Please specify this property using the SBT flag -D.""".stripMargin)
  case Some(pluginVersion) =>
    addSbtPlugin("com.earldouglas" % "sbt-frege" % pluginVersion)
}

libraryDependencies += "frege" % "fregec" % "3.22.524" from "https://github.com/Frege/frege/releases/download/3.22.324/frege3.22.524-gcc99d7e.jar"
