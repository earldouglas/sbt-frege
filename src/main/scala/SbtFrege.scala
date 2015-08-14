import sbt._
import Keys._

object SbtFrege extends AutoPlugin {

  object autoImport {
    lazy val fregeOptions  = taskKey[Seq[String]]("Extra options for fregec")
    lazy val fregeSource   = settingKey[File]("Frege source directory")
    lazy val fregeTarget   = settingKey[File]("Frege target directory")
    lazy val fregeCompiler = settingKey[String]("Full name of the Frege compiler")
    lazy val fregeLibrary  = settingKey[ModuleID]("Frege library (fregec.jar)")
  }

  import autoImport._
  import java.io.File

  override def trigger = allRequirements
  override def requires = plugins.JvmPlugin

  def fregec(cp: Seq[sbt.Attributed[File]], fregeSource: File, fregeTarget: File,
             fregeCompiler: String)(fregeSrcs: Set[File]): Set[File] = {

    val cps = cp.map(_.data).mkString(String.valueOf(File.pathSeparatorChar))

    fregeTarget.mkdirs()

    val fregeArgs = Seq(
      fregeCompiler,
      "-j",
      "-fp", cps,
      "-d", fregeTarget.getPath,
      "-sp", fregeSource.getPath,
      "-make"
    ) ++ fregeSrcs.map(_.getPath)

    val forkOptions: ForkOptions = new ForkOptions
    val fork = new Fork("java", None)
    val result = fork(forkOptions, Seq("-cp", cps) ++ fregeArgs)
    if (result != 0) {
      throw new RuntimeException("Frege compilation error")
    } else {
      (PathFinder(fregeTarget) ** "*.java").get.toSet
    }
  }

  override def projectSettings = Seq(
    fregeOptions := Seq("-Xss1m"),
    fregeSource := (sourceDirectory in Compile).value / "frege",
    fregeTarget := baseDirectory.value / "target" / "frege",
    sourceGenerators in Compile += Def.task {
      val cacheDir = streams.value.cacheDirectory / "frege"
      val cached = FileFunction.cached(
        cacheDir, FilesInfo.lastModified, FilesInfo.exists) {
          fregec((managedClasspath in Compile).value,
                 (fregeSource in Compile).value,
                 (fregeTarget in Compile).value,
                 (fregeCompiler in Compile).value)
        }
      cached((fregeSource.value ** "*.fr").get.toSet).toSeq
    }.taskValue,
    fregeCompiler := "frege.compiler.Main",
    watchSources := {
      watchSources.value ++
      ((sourceDirectory in Compile).value / "frege" ** "*").get
    },
    fregeLibrary := "frege" % "fregec" % "3.22.524" from
      "https://github.com/Frege/frege/releases/download/3.22.324/" +
      "frege3.22.524-gcc99d7e.jar",
    libraryDependencies += fregeLibrary.value
  )

}
