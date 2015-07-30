import sbt._
import Keys._

object SbtFrege extends Plugin {

  import SbtFregeKeys._
  import java.io.File

  object SbtFregeKeys {
    val fregeOptions  = taskKey[Seq[String]]("Extra options for fregec.")
    val fregeSource   = settingKey[File]("Default Frege source directory.")
    val fregeTarget   = settingKey[File]("Default Frege target directory.")
    val fregeCompiler = settingKey[String]("Full name of the Frege compiler.")
  }

  def fregec(cp: Seq[sbt.Attributed[java.io.File]],
             fregeSource: File,
             fregeTarget: File,
             fregeCompiler: String): Unit = {

    val cps = cp.map(_.data).mkString(String.valueOf(java.io.File.pathSeparatorChar))
    val fregeSrcs = (fregeSource ** "*.fr").getPaths

    fregeTarget.mkdirs()

    val fregeArgs = Seq(
      fregeCompiler,
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

  val fregeSettings = Seq(
    fregeOptions := Seq("-Xss1m"),
    fregeSource := (sourceDirectory in Compile).value / "frege",
    fregeTarget := baseDirectory.value / "target" / "frege" / "classes",
    (compile in Compile) := {
      fregec((managedClasspath in Compile).value,
             (fregeSource in Compile).value,
             (fregeTarget in Compile).value,
             (fregeCompiler in Compile).value)
      (compile in Compile).value
    },
    fregeCompiler := "frege.compiler.Main",
    watchSources := {
      (watchSources in Compile).value ++
      ((sourceDirectory in Compile).value / "frege" ** "*").get
    }

  )
}
