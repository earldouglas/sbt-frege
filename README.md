## Getting started

Add the Frege sbt plugin to your project:

*project/plugins.sbt:*

```scala
resolvers += "earldouglas bintray" at "https://dl.bintray.com/earldouglas/sbt-plugins/"

addSbtPlugin("com.earldouglas" % "sbt-frege" % "0.1.0")
```

Enable the Frege sbt plugin, and include the Frege library:

*build.sbt:*

```scala
SbtFrege.fregeSettings
 
libraryDependencies += "frege" % "fregec" % "3.22.524" from
  "https://github.com/Frege/frege/releases/download/3.22.324/frege3.22.524-gcc99d7e.jar"
```

Write some Frege code:

*src/main/frege/example/HelloWorld.fr:*

```frege
package example.HelloWorld where

main :: [String] -> IO ()
main _ = print "Hello, world!"
```

Build it:

```
$ sbt
> compile
```

