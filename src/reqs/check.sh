#!/usr/bin/env bash

set -e

pushd `dirname $0`

function test() {
  for SCALA_VERSION in 2.4.0 2.13.4
  do
    _test
  done
}

function _test() {
  TMP_SRC_DIR=`mktemp -d`
  cp -rf . $TMP_SRC_DIR

  pushd $TMP_SRC_DIR

  echo "scalaVersion := \"$SCALA_VERSION\"" > scalaVersion.sbt
  echo "sbt.version=$SBT_VERSION" > project/build.properties

  cat project/build.properties
  cat scalaVersion.sbt

  TMP_HOME_DIR=`mktemp -d`

  curl -s -L $SBT_LAUNCH -o $TMP_HOME_DIR/sbt-launch-$SBT_VERSION.jar

  HOME=$TMP_HOME_DIR java -jar $TMP_HOME_DIR/sbt-launch-$SBT_VERSION.jar clean run

  popd
}

for SBT_VERSION in 1.3.0 1.3.13 1.4.1 1.4.9 1.5.0 1.5.4
do
  SBT_LAUNCH=https://repo1.maven.org/maven2/org/scala-sbt/sbt-launch/$SBT_VERSION/sbt-launch-$SBT_VERSION.jar
  test
done
