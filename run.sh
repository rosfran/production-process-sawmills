#!/bin/sh

mvn -DskipTests=true clean compile package install exec:java < problem_set.txt
