#!/bin/bash
mvn install:install-file -Dfile=$JSHOMEDIR/lib/optional/httpsession/gs-session-manager.jar -DgroupId=com.gigaspaces.httpsession -DartifactId=gs-session-manager -Dversion=1.0-SNAPSHOT -Dpackaging=jar
