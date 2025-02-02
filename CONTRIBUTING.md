[![Build status](https://github.com/earldouglas/sbt-frege/workflows/build/badge.svg)](https://github.com/earldouglas/sbt-frege/actions)
[![Latest version](https://img.shields.io/github/tag/earldouglas/sbt-frege.svg)](https://index.scala-lang.org/earldouglas/sbt-frege)

# Contributing

## Publishing

This project uses [sbt-sonatype](https://github.com/xerial/sbt-sonatype)
to automate most of the publishing process:

```
$ export VERSION=3.0.3
$ nix-shell
$ sbt
> set ThisBuild / version := sys.env("VERSION")
> +publishSigned
> sonatypeBundleRelease
$ git tag $VERSION
$ git push --tags
```
