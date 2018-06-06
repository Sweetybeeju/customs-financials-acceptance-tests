#! /bin/sh

sbt -Denvironment=local -Dbrowser=chrome 'test-only uk.gov.hmrc.runner.RunAcceptance'