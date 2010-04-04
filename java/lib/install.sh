#!/bin/bash

mvn install:install-file \
  -Dfile=hadrian-0.2.jar \
  -DgroupId=org.quenta \
  -DartifactId=hadrian \
  -Dversion=0.2 \
  -Dpackaging=jar \
  -DgeneratePom=true

mvn install:install-file \
  -Dfile=jparsec-2.0.jar \
  -DgroupId=jparsec2 \
  -DartifactId=jparsec \
  -Dversion=2.0 \
  -Dpackaging=jar \
  -DgeneratePom=true
  