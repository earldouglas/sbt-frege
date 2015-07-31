## Requirements

* sbt 0.13.6+

## Getting started

Add the Frege sbt plugin to your project:

*project/plugins.sbt:*

```scala
addSbtPlugin("com.earldouglas" % "sbt-frege" % "0.3.0")
```

Enable the Frege sbt plugin, and include the Frege library:

both *project/project/build.sbt* and *build.sbt:*

```scala
libraryDependencies += "frege" % "fregec" % "3.22.524" from
  "https://github.com/Frege/frege/releases/download/3.22.324/frege3.22.524-gcc99d7e.jar"
```

Write some Frege code:

*src/main/frege/example/HelloWorld.fr:*

```frege
package example.HelloWorld where

main :: [String] -> IO ()
main _ = println "Hello, world!"
```

Build and run it:

```
$ sbt
> compile
> run
Hello, world!
```

## Configuration

* `fregeOptions`: Extra options for fregec (`Seq[String]`)
* `fregeSource`: Frege source directory (default *src/frege/*) (`File`)
* `fregeTarget`: Frege target directory (default *target/frege/*) (`File`)
* `fregeCompiler`: Full name of the Frege compiler (default *frege.compiler.Main*) (`String`)

## Features

* Compile Frege code in *src/frege/*
* Call Frege code from Java/Scala/etc. code
