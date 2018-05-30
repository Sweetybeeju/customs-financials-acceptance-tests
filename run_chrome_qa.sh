#! /bin/sh

sbt -Denvironment=qa -Dbrowser=chrome 'test-only uk.gov.hmrc.runner.RunQa'