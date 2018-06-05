#!/usr/bin/env bash

driver_path=/usr/local/bin/chromedriver
sbt -Denvironment=dev -Dbrowser=chrome -Dwebdriver.chrome.driver=${driver_path} 'test-only uk.gov.hmrc.runner.RunAcceptance'