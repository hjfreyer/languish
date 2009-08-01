#!/bin/bash

mvn install:install-file \
  -Dfile=hadrian-0.2.jar \
  -DgroupId=org.quenta \
  -DartifactId=hadrian \
  -Dversion=0.2 \
  -Dpackaging=jar \
  -DgeneratePom=true
