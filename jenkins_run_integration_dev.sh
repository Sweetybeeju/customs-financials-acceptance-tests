#!/usr/bin/env bash

sbt -Dwebdriver.chrome.driver=/usr/local/bin/chromedriver -Denv=dev clean test