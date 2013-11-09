#!/bin/sh

cd `dirname $0`
desktop/compile.sh
cd desktop/bin
jar cf ../../android/libs/sharedlib.jar edu/
[ -d ../../android/libs ] || mkdir android/libs/
