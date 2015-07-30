import sbt._
import Keys._

object SbtFrege extends Plugin {
  import SbtFregeKeys._
  import java.io.File

  object SbtFregeKeys {
    val fregeOptions = TaskKey[Seq[String]]("frege-options", "Extra options for fregec.")
    val fregeSource  = SettingKey[File]("frege-source", "Default Frege source directory.")
    val fregeTarget  = SettingKey[File]("frege-target", "Default Frege target directory.")
  }

  def fregec(classpath: Seq[sbt.Attributed[java.io.File]], fregeSource: File, fregeTarget: File) = {
    // TODO incremental compile -- don't recompile stuff unless needed
    // TODO mixed-mode compiling Java/Frege(/Scala?)
    val cp = classpath.map(_.data).mkString(String.valueOf(java.io.File.pathSeparatorChar))
    val fregeSrcs = (fregeSource ** "*.fr").getPaths
    fregeTarget.mkdirs()
    val fregeArgs = Seq(
        "-fp", cp
      , "-d", fregeTarget.getPath
    ) ++ fregeSrcs

    frege.compiler.Main.main(fregeArgs.toArray)
  }

  val fregeSettings = Seq(
      fregeOptions := Seq("-Xss1m")
    , fregeSource in Compile <<= (sourceDirectory in Compile)( _  / "frege" )
    , fregeTarget in Compile <<= baseDirectory(_ / "target" / "frege" / "classes")
    , (compile in Compile) <<= (compile in Compile, managedClasspath in Compile, fregeSource in Compile, fregeTarget in Compile) map {
        (x: sbt.inc.Analysis, managedClasspath, fregeSource, fregeTarget) =>
          fregec(managedClasspath, fregeSource, fregeTarget)
          x
        }
  )
}
