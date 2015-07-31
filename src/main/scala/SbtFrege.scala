import sbt._
import Keys._

object SbtFrege extends AutoPlugin {

  object autoImport {
    lazy val fregeOptions  = taskKey[Seq[String]]("Extra options for fregec.")
    lazy val fregeSource   = settingKey[File]("Default Frege source directory.")
    lazy val fregeTarget   = settingKey[File]("Default Frege target directory.")
    lazy val fregeCompiler = settingKey[String]("Full name of the Frege compiler.")
  }

  import autoImport._
  import java.io.File

  override def trigger = allRequirements
  override def requires = plugins.JvmPlugin

  def fregec(cp: Seq[sbt.Attributed[java.io.File]],
             fregeSource: File,
             fregeTarget: File,
             fregeCompiler: String): Unit = {

    val cps = cp.map(_.data).mkString(String.valueOf(java.io.File.pathSeparatorChar))
    val fregeSrcs = (fregeSource ** "*.fr").getPaths

    fregeTarget.mkdirs()

    val fregeArgs = Seq(
      fregeCompiler,
      "-j",
      "-fp", cps,
      "-d", fregeTarget.getPath
    ) ++ fregeSrcs

    val forkOptions: ForkOptions = new ForkOptions
    val fork = new Fork("java", None)
    val result = fork(forkOptions, Seq("-cp", cps) ++ fregeArgs)
    if (result != 0) {
      throw new Exception("Frege compilation error")
    }
  }

  override def projectSettings = Seq(
    fregeOptions := Seq("-Xss1m"),
    fregeSource := (sourceDirectory in Compile).value / "frege",
    fregeTarget := baseDirectory.value / "target" / "frege",
    sourceGenerators in Compile += Def.task {
      fregec((managedClasspath in Compile).value,
             (fregeSource in Compile).value,
             (fregeTarget in Compile).value,
             (fregeCompiler in Compile).value)
      (fregeTarget.value ** "*.java").get
    }.taskValue,
    fregeCompiler := "frege.compiler.Main",
    watchSources := {
      watchSources.value ++
      ((sourceDirectory in Compile).value / "frege" ** "*").get
    }
  )

}
