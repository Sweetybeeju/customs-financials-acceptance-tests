#!/usr/bin/env bash

#! /bin/sh

driver_path=/usr/local/bin/chromedriver
sbt -Denvironment=qa -Dbrowser=chrome -Dwebdriver.chrome.driver=${driver_path} clean test