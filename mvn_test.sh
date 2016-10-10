#!/bin/bash

MODULE="." # module directory (default is parent)
CLEAN=""   # clean target (default is empty, i.e. don't clean)
DTEST=""   # test class (default is empty, i.e. run all tests)
ATYPE=""                             # default arquillian adapter
# ATYPE="-P arquillian-jboss-remote" # default arquillian adapter

while getopts "cm:a:t:" opt; do
	case $opt in
	"m")
		MODULE=$OPTARG
		;;
	"c")
		CLEAN="clean"
		;;
	"a")
		ATYPE="-P arquillian-jboss-$OPTARG"
		;;
	"t")
		DTEST="-Dtest=$OPTARG"
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

CMD="mvn $CLEAN test $ATYPE $DTEST"
echo $CMD
eval $CMD
