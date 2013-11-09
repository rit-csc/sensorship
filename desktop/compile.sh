#!/bin/sh

cd `dirname $0`
find -name \*.java > sources.list
javac -d bin/ @sources.list
