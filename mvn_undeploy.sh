#!/bin/bash

MODULE="." # module directory (default is parent)
CLEAN=""   # clean target (default is empty, i.e. don't clean)
TARGET="laptop" # default environment

while getopts "cm:t:" opt; do
	case $opt in
	"m")
		MODULE=$OPTARG
		;;
	"c")
		CLEAN="clean"
		;;
	"t")
		TARGET=$OPTARG
		;;
	"?")
		exit 1
		;;
	esac
done

if ([ ! -d $MODULE ]); then
	echo "ERROR: Directory $MODULE does not exist. Operation aborted."
	exit 1
fi

if ([ ! $TARGET = "laptop" ] && [ ! $TARGET = "desktop" ]); then
	echo "ERROR: Target should be laptop or desktop. Operation aborted."
	exit 1
fi

CMD="cd $MODULE"
echo $CMD
eval $CMD

CMD="mvn $CLEAN install -DskipTests -P $TARGET,undeploy"
echo $CMD
eval $CMD
