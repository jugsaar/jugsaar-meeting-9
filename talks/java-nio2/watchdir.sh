#!/bin/sh

# executes the WatchDir example to monitor filesystem actions in the subdirectory ./tmp

CLASS_PATH=target/classes/

java -cp $CLASS_PATH de.jugsaar.meeting9.nio2.watchservice.WatchDir -r ./tmp