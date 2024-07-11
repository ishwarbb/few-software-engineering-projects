#!/bin/bash

export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64/ ; \
cd books-parent/ ; \
mvn clean -DskipTests install ; \
cd ../books-web/ && \
mvn jetty:run ; \
cd .. ; \
echo ":)"
