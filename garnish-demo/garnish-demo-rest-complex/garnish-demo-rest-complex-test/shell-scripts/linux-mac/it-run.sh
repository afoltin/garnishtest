#!/usr/bin/env bash

CURRENT_SCRIPT_DIR="$(dirname "$0")"
DEMO_REST_ROOT_DIR="${CURRENT_SCRIPT_DIR}/../../.."

# improve build speed
MVN_OPTS="${MVN_OPTS} -T 2C"
MVN_OPTS="${MVN_OPTS} -Dmaven.source.skip=true"
MVN_OPTS="${MVN_OPTS} -Dmaven.javadoc.skip=true"
MVN_OPTS="${MVN_OPTS} -Denforcer.skip=true"
MVN_OPTS="${MVN_OPTS} -Dpmd.skip=true"
MVN_OPTS="${MVN_OPTS} -Dfindbugs.skip=true"
MVN_OPTS="${MVN_OPTS} -Dcheckstyle.skip=true"

cd "${DEMO_REST_ROOT_DIR}"

echo deleting previous report...
rm -rf "garnish-demo-rest-complex-test/target/cucumber/" 2> /dev/null
echo ...done deleting previous report

echo ""

# TIP: if building the war starts taking too long, you can make a separate shell script for this, and only run it when needed
mvn verify -am -pl garnish-demo-rest-complex-web/,garnish-demo-rest-complex-test/ -DrunIntegrationTests "$@"
