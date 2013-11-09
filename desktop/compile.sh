#!/bin/sh

cd `dirname $0`
find . -name \*.java > sources.list
[ -d bin ] || mkdir bin
javac -d bin/ @sources.list
