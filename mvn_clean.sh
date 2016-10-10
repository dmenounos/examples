#!/bin/bash

MODULE="." # module directory (default is parent)

while getopts "m:" opt; do
	case $opt in
	"m")
		MODULE=$OPTARG
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

CMD="cd $MODULE"
echo $CMD
eval $CMD

CMD="mvn clean"
echo $CMD
eval $CMD
