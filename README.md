## Features

* Compile Frege code from your project's *src/main/frege/* directory
* Call Frege code from your project's Java/Scala/etc. code
* Launch the [Frege REPL][1] with your project's classes and libraries

## Requirements

* sbt 1.0.0+

*For sbt 0.13.6+ projects, use sbt-frege version 1.1.3*

## Getting started

Add the Frege sbt plugin to your project:

*project/plugins.sbt:*

```scala
addSbtPlugin("com.earldouglas" % "sbt-frege" % "3.0.0")
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

* `fregeOptions` - Extra options for fregec: `Seq[String]`
* `fregeSource` - Frege source directory (default *src/main/frege/*):
  `File`
* `fregeTarget` - Frege target directory (default *target/frege/*):
  `File`
* `fregeCompiler` - Full name of the Frege compiler (default
  *frege.compiler.Main*): `String`
* `fregeLibrary` - Frege library (fregec.jar) to use (default *Frege
  3.23.288*), `ModuleID`

### Frege REPL

* `fregeReplVersion` - The version of [frege-repl][1] to use (default
  1.3): `String`
* `fregeReplMainClass` - The Frege REPL main class (default
  `frege.repl.FregeRepl`): `String`

Though sbt-frege uses 3.24-7.30 by default, Frege REPL 1.3 depends on
Frege 3.23.288, so it takes priority when launching `fregeRepl`.

[1]: https://github.com/Frege/frege-repl
