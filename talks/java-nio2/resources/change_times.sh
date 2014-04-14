#!/bin/sh

# script to set atime and mtime of the example file
touch -a -d "2014-04-17 11:22:33 +0000" lorem-ipsum.txt
touch -m -d "2014-04-17 10:20:30 +0000" lorem-ipsum.txt
