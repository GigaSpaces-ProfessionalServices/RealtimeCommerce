#!/bin/bash
mvn install:install-file -Dfile=$JSHOMEDIR/lib/optional/httpsession/gs-session-manager-10.1.0-12593-M11.jar -DgroupId=com.gigaspaces.httpsession -DartifactId=gs-session-manager -Dversion=1.0-SNAPSHOT -Dpackaging=jar
