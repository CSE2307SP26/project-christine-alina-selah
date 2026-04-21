#!/bin/bash

#put commands here to compile and run your app from command line

set -e

mkdir -p bin

javac -d bin src/main/*.java

java -cp bin main.MainMenu