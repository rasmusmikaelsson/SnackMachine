#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"
mkdir -p out
find src -name '*.java' -print0 | xargs -0 javac -encoding UTF-8 -d out
jar cfe MySnackMachine.jar se.umu.cs.snackmachine.MySnackMachine -C out .
echo "Klart: java -jar MySnackMachine.jar"
