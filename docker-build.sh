#!/usr/bin/env bash

echo "CurseWrapper Docker for version ${1}"

ZIP_FILE=$(mktemp)
BUILD_DIR=$(mktemp -d)

echo "Downloading Image"
wget "-O" "${ZIP_FILE}" "https://noeppi-noeppi.github.io/MinecraftUtilities/maven/io/github/noeppi_noeppi/tools/CurseWrapperServer/${1}/CurseWrapperServer-${1}-docker.zip"

echo "Extracting Image"
unzip "${ZIP_FILE}" "-d" "${BUILD_DIR}"

if [[ -f "config.json" ]]; then
  cp "config.json" "${BUILD_DIR}/config.json"
fi

echo "Building Image cursewrapper:${1//-/_}"
cd "${BUILD_DIR}" || exit
docker "build" "-t" "cursewrapper:${1//-/_}" "."

if docker "service" "inspect" "cursewrapper" 2> /dev/null > /dev/null; then
  echo "Updating Image in Docker Service"
  docker "service" "update" "--image" "cursewrapper:${1//-/_}" "cursewrapper"
else
  echo "Creating Docker Service"
  if [[ $2 == "no-ssl" ]]; then
    docker "service" "create" "--name" "cursewrapper" "--secret" "curse_token" "cursewrapper:${1//-/_}" 
  else
    docker "service" "create" "--name" "cursewrapper" "--secret" "curse_token" "--secret" "ssl_keystore" "--secret" "ssl_keystore_password" "cursewrapper:${1//-/_}" 
  fi
fi
