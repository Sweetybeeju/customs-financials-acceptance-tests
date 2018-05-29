#! /bin/sh

driver_path=/usr/local/bin/chromedriver
sbt -Denvironment=local -Dbrowser=chrome test