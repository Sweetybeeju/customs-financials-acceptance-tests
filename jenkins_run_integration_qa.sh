#!/usr/bin/env bash

#! /bin/sh

driver_path=/usr/local/bin/chromedriver
sbt -Denvironment=qa -DturnOnProxy=yes 'test-only uk.gov.hmrc.runner.RunQa'