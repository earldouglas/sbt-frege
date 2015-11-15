## Requirements

* sbt 0.13.6+

## Getting started

Add the Frege sbt plugin to your project:

*project/plugins.sbt:*

```scala
addSbtPlugin("com.earldouglas" % "sbt-frege" % "1.0.0")
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
* `fregeSource`: Frege source directory (default *src/main/frege/*) (`File`)
* `fregeTarget`: Frege target directory (default *target/frege/*) (`File`)
* `fregeCompiler`: Full name of the Frege compiler (default *frege.compiler.Main*) (`String`)
* `fregeLibrary`: Frege library (fregec.jar) to use (default *Frege 3.22.524*) (`ModuleID`)

## Features

* Compile Frege code in *src/main/frege/*
* Call Frege code from Java/Scala/etc. code
