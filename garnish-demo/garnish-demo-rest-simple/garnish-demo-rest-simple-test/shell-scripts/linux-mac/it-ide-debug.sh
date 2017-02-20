#!/usr/bin/env bash

CURRENT_SCRIPT_DIR="$(dirname "$0")"

MAVEN_DEBUG_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8000"
env MAVEN_OPTS="$MAVEN_OPTS" MAVEN_DEBUG_OPTS="$MAVEN_DEBUG_OPTS" "${CURRENT_SCRIPT_DIR}/it-run.sh" -DpauseBeforeTests=true "$@"
