#!/bin/bash

# safety settings
set -u
set -e
set -o pipefail

"$(which sbt activator | head -n1)" dist

# TODO: Fix getting version number from SBT and remove hardcoded versions
#version=$(
#    "$(which sbt activator | head -n1)" version | grep -F '[info] ' | tail -n1 | sed 's/^.* //' | grep -oE '[0-9.A-Z-]+' | head -n2 | tail -n1
#)
version=0.1.0-SNAPSHOT

#scalaVersion=$(
#    "$(which sbt activator | head -n1)" scalaVersion | grep -F '[info] ' | tail -n1 | sed 's/^.* //' | grep -oE '[0-9.A-Z-]+' | head -n2 | tail -n1 | sed 's/^\([^.]*.[^.]*\).*/\1/'
#)
scalaVersion=2.12

if [ -e target/assets ]; then
    rm -r target/assets
fi
mkdir -p target
mkdir target/assets
unzip -d target/assets "./webapp/target/scala-$scalaVersion/server_$scalaVersion-$version-web-assets.jar"


if [ -e pack.zip ]; then
	rm pack.zip
fi

(cd target/assets/public && zip -r ../../../pack.zip .)
