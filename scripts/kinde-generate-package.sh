#!/usr/bin/env bash

set -e

SCRIPT="$0"
echo "# START SCRIPT: $SCRIPT"

declare cwd="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
declare root="$(cd "$cwd" && cd ../ && pwd)"

LANGUAGE="php"
SUPPORTS=("php")
while getopts c:l: flag
do
    case "${flag}" in
        c) CONFIG=${OPTARG};;
        l) LANGUAGE=${OPTARG};;
    esac
done

if [[ ! " ${SUPPORTS[*]} " =~ " ${LANGUAGE} " ]]; then
    echo "$LANGUAGE does not support. Support LANGUAGEs:"
    for i in "${SUPPORTS[@]}"; do echo "$i"; done
    exit;
fi

if [ ! "$CONFIG" ]; then
    CONFIG="${root}/bin/configs/kinde-${LANGUAGE}.yaml"
fi

echo "Building package... $CONFIG"
(cd "$root" && ./scripts/meta-codegen.sh -g "kinde-$LANGUAGE" -c $CONFIG)