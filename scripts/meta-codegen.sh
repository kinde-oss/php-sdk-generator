#!/usr/bin/env bash

set -e

SCRIPT="$0"
echo "# START SCRIPT: $SCRIPT"

declare cwd="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
declare root="$(cd "$cwd" && cd ../ && pwd)"
EXECUTABLE="${root}/modules/openapi-generator-cli/target/openapi-generator-cli.jar"

GENERATOR_NAME="php"
OUT_GENERATOR="php"
CONFIG="${root}/bin/configs/php.yaml"
OUTPUT="$root/out/kinde-$OUT_GENERATOR-sdk"
TEMAPLTE="$root/generators/$GENERATOR_NAME/src/main/resources/$OUT_GENERATOR"

while getopts c:g:o: flag
do
    case "${flag}" in
        c) CONFIG=${OPTARG};;
        g) GENERATOR_NAME=${OPTARG};;
        o) OUT_GENERATOR=${OPTARG};;
    esac
done

if [ ! -f "$EXECUTABLE" ]; then
  echo "Rebuilding…"
  (cd "$root" && ./mvnw -B clean package)
fi

if [ ! -d "$OUTPUT" ]; then
  echo "Creating output folder…"
  mkdir "$root/out" && mkdir "$OUTPUT"
fi

export JAVA_OPTS="${JAVA_OPTS} -Xmx1024M -DloggerPath=conf/log4j.properties"

(cd "$root" && ./mvnw -B package -Djacoco.skip=true -DskipTests=true -f "$root"/generators/${OUT_GENERATOR}/pom.xml)

ags2="generate -o $OUTPUT -t $TEMAPLTE $@"

java $JAVA_OPTS -cp ${root}/generators/${OUT_GENERATOR}/target/${GENERATOR_NAME}-openapi-generator-1.0.0.jar:$EXECUTABLE org.openapitools.codegen.OpenAPIGenerator $ags2

echo "Package generated at : $OUTPUT"