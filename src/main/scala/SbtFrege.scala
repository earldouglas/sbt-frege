package com.earldouglas.sbt.frege

import sbt._
import sbt.Keys._

object SbtFregec extends AutoPlugin {

  object autoImport {
    lazy val fregeOptions  = settingKey[Seq[String]]("Extra options for fregec")
    lazy val fregeSource   = settingKey[File]("Frege source directory")
    lazy val fregeTarget   = settingKey[File]("Frege target directory")
    lazy val fregeCompiler = settingKey[String]("Full name of the Frege compiler")
    lazy val fregeLibrary  = settingKey[ModuleID]("Frege library (fregec.jar)")
  }

  import autoImport._
  import java.io.File

  override def trigger = allRequirements
  override def requires = plugins.JvmPlugin

  def fregec(cp: Seq[File], fregeSource: File, fregeTarget: File,
             fregeCompiler: String, fregeOptions: Seq[String])
            (fregeSrcs: Set[File]): Set[File] = {

    val cps = cp.mkString(String.valueOf(File.pathSeparatorChar))

    fregeTarget.mkdirs()

    val fregeArgs = Seq(
      fregeCompiler,
      "-fp", cps,
      "-d", fregeTarget.getPath,
      "-sp", fregeSource.getPath,
      "-make"
    ) ++ fregeOptions ++ fregeSrcs.map(_.getPath)

    val forkOptions: ForkOptions = ForkOptions()
    val fork = new Fork("java", None)
    val result = fork(forkOptions, Seq("-cp", cps) ++ fregeArgs)
    if (result != 0) {
      throw new RuntimeException("Frege compilation error")
    } else {
      (PathFinder(fregeTarget) ** "*.java").get().toSet
    }
  }

  def scopedSettings(scope: Configuration, dirName: String) = Seq(
    Compat.fregeSource(scope) := (Compat.sourceDirectory(scope)).value / "frege",
    Compat.fregeTarget(scope) := baseDirectory.value / "target" / dirName,
    Compat.sourceGenerators(scope) += Def.task {
      val cacheDir = streams.value.cacheDirectory / dirName
      val cached = FileFunction.cached(
        cacheDir, FilesInfo.lastModified, FilesInfo.exists) {
          fregec((Compat.managedClasspath(scope)).value ++ (Compat.dependencyClasspath(scope)).value,
                 (Compat.fregeSource(scope)).value,
                 (Compat.fregeTarget(scope)).value,
                 (Compat.fregeCompiler(scope)).value,
                 (Compat.fregeOptions(scope)).value)
        }
      cached(((Compat.fregeSource(scope)).value ** "*.fr").get().toSet).toSeq
    }.taskValue,
    watchSources ++= ((Compat.fregeSource(scope)).value ** "*").get().map(x=>WatchSource(x))
  )

  override def projectSettings =
    scopedSettings(Compile, "frege") ++
    scopedSettings(Test,    "test-frege") ++
    Seq(
      fregeOptions := Seq("-j"),
      fregeCompiler := "frege.compiler.Main",
      fregeLibrary := "org.frege-lang" % "frege" % "3.24.100.1" classifier "jdk8",
      libraryDependencies += fregeLibrary.value
    )

}

/* Adds integration with frege-repl:
 *   * Launch the Frege REPL from sbt with the fregeRepl command
 *   * Access your project's classes and libraries from the Frege REPL
 * See: https://github.com/Frege/frege-repl
 */
object SbtFregeRepl extends AutoPlugin {

  object autoImport {
    // Use a special configuration so as not to pollute the Compile
    // configuration with frege-repl's jar and transitive dependencies.
    lazy val FregeReplConfig    = config("FregeReplConfig").hide
    lazy val fregeReplVersion   = settingKey[String]("Frege REPL version")
    lazy val fregeRepl          = inputKey[Unit]("Run the Frege REPL")
    lazy val fregeReplMainClass = settingKey[String]("Frege REPL main class")
  }

  import autoImport._

  override def trigger = allRequirements
  override def requires = plugins.JvmPlugin
  override val projectConfigurations = Seq(FregeReplConfig)

  override def projectSettings = Seq(
    fregeReplVersion := "1.3",
    libraryDependencies += "org.frege-lang" % "frege-repl-core" % fregeReplVersion.value % FregeReplConfig,
    fregeReplMainClass := "frege.repl.FregeRepl",
    fregeRepl := {
      val cp: String =
        Path.makeString(
          Compat.managedJars(FregeReplConfig).value ++
          (Compat.Compile_fullClasspath).value
        )
      val forkOptions = ForkOptions().withConnectInput(true).withOutputStrategy(Some(sbt.StdoutOutput))
      val mainClass: String = fregeReplMainClass.value
      new Fork("java", None).fork(forkOptions, Seq("-cp", cp, mainClass)).exitValue()
    }
  )

}
