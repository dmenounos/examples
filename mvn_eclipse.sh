#!/bin/bash

MODULE="." # module directory (default is parent)
CLEAN=""   # clean target (default is empty, i.e. don't clean)
ATYPE=""   # default arquillian adapter

while getopts "cm:a:" opt; do
	case $opt in
	"m")
		MODULE=$OPTARG
		;;
	"c")
		CLEAN="eclipse:clean"
		;;
	"a")
		ATYPE="-P arquillian-jboss-$OPTARG"
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

CMD="mvn $CLEAN eclipse:eclipse $ATYPE"
echo $CMD
eval $CMD
