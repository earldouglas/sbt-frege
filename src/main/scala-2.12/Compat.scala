package com.earldouglas.sbt.frege

import sbt.Def.Initialize
import sbt.Keys._
import sbt._

object Compat {

  val Compile_fullClasspath: Initialize[Task[Seq[File]]] =
    Def.task {
      (Compile / fullClasspath).value
        .map(_.data)
    }

  def dependencyClasspath(scope: Configuration): Initialize[Task[Seq[File]]] =
    Def.task {
      (scope / sbt.Keys.dependencyClasspath).value
        .map(_.data)
    }

  def managedClasspath(scope: Configuration): Initialize[Task[Seq[File]]]  =
    Def.task {
      (scope / sbt.Keys.managedClasspath).value
        .map(_.data)
    }

  def fregeCompiler(scope: Configuration) = scope / SbtFregec.autoImport.fregeCompiler
  def fregeOptions(scope: Configuration) = scope / SbtFregec.autoImport.fregeOptions
  def fregeSource(scope: Configuration) = scope / SbtFregec.autoImport.fregeSource
  def fregeTarget(scope: Configuration) = scope / SbtFregec.autoImport.fregeTarget
  def sourceDirectory(scope: Configuration) = scope / sbt.Keys.sourceDirectory
  def sourceGenerators(scope: Configuration) = scope / sbt.Keys.sourceGenerators

  def managedJars(config: Configuration): Initialize[Task[Seq[File]]] =
    Def.task {
      Classpaths.managedJars(config, classpathTypes.value, update.value)
        .map(_.data)
    }
}
