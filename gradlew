#!/bin/sh
set -e
GRADLE_VERSION=8.10.2
BASE="$HOME/.gradle/wrapper/dists/gradle-$GRADLE_VERSION-bin"
DIST="$BASE/gradle-$GRADLE_VERSION"
if [ ! -x "$DIST/bin/gradle" ]; then
  mkdir -p "$BASE"
  TMP="$BASE/gradle.zip"
  if [ ! -f "$TMP" ]; then
    curl -fsSL "https://services.gradle.org/distributions/gradle-$GRADLE_VERSION-bin.zip" -o "$TMP"
  fi
  rm -rf "$DIST"
  unzip -q "$TMP" -d "$BASE"
fi
exec "$DIST/bin/gradle" "$@"
