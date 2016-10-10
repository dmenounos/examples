#!/bin/sh

if [ ! -d ./target/classes ] || [ ! -d ./target/dependency ]; then
	mvn compile dependency:copy-dependencies -DskipTests
fi

java -cp ./target/classes:./target/dependency/* test.svhc.Application
