#!/usr/bin/env bash

CURRENT_SCRIPT_DIR="$(dirname "$0")"

MAVEN_DEBUG_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8000"
env MAVEN_OPTS="$MAVEN_OPTS" MAVEN_DEBUG_OPTS="$MAVEN_DEBUG_OPTS" "${CURRENT_SCRIPT_DIR}/it-run.sh" -DpauseBeforeTests=true -Dhttp.proxyHost=127.0.0.1 -Dhttp.proxyPort=8888 -Dhttps.proxyHost=127.0.0.1 -Dhttps.proxyPort=8888 -Dhttp.nonProxyHosts= "$@"
