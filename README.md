[![Build status](https://github.com/earldouglas/sbt-frege/workflows/build/badge.svg)](https://github.com/earldouglas/sbt-frege/actions)
[![Latest version](https://img.shields.io/github/tag/earldouglas/sbt-frege.svg)](https://index.scala-lang.org/earldouglas/sbt-frege)

## Features

* Compile Frege code from your project's *src/main/frege/* directory
* Call Frege code from your project's Java/Scala/etc. code
* Launch the [Frege REPL][1] with your project's classes and libraries

## Requirements

* sbt 1.0.1+
* Scala 2.4.0+

*For sbt 0.13.6+ projects, use sbt-frege version 1.1.3*

## Getting started from a template

```
$ sbt new earldouglas/sbt-frege.g8
A project built with sbt-frege

name [My Frege Project]: hello frege

Template applied in ./hello-frege

$ cd hello-frege/
$ sbt
> test
example.HelloWorldSuite:
  + multiply 0.008s
  + showWork 0.016s
[info] Passed: Total 2, Failed 0, Errors 0, Passed 2
> run
[info] 6 * 7 = 42
```

## Getting started from scratch

Add the Frege sbt plugin to your project:

*project/plugins.sbt:*

```scala
addSbtPlugin("com.earldouglas" % "sbt-frege" % "3.0.3")
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

Try it from the Frege REPL:

```
$ sbt
> fregeRepl

frege> import example.HelloWorld (main)

frege> main []
Hello, world!
()
```

## Configuration

### Frege compiler

* `fregeOptions: Seq[String]` - Extra options for fregec
* `fregeSource: File` - Frege source directory (default
  *src/main/frege/*)
* `fregeTarget: File` - Frege target directory (default *target/frege/*)
* `fregeCompiler: String` - Full name of the Frege compiler (default
  *frege.compiler.Main*)
* `fregeLibrary: ModuleID` - Frege library (fregec.jar) to use (default
  *Frege 3.23.288*)

### Frege REPL

* `fregeReplVersion: String` - The version of [frege-repl][1] to use
  (default 1.3)
* `fregeReplMainClass: String` - The Frege REPL main class (default
  `frege.repl.FregeRepl`)

Though sbt-frege uses 3.24.100.1 by default, Frege REPL 1.3 depends on
Frege 3.23.288, so it takes priority when launching `fregeRepl`.

[1]: https://github.com/Frege/frege-repl
