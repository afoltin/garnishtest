#!/usr/bin/env bash

CURRENT_SCRIPT_DIR="$(dirname "$0")"

"${CURRENT_SCRIPT_DIR}/it-run.sh" -DpauseBeforeTests=true "$@"
