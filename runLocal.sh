#!/usr/bin/env bash

sbt -Dbrowser=chrome  -Denv=local acceptance:test