#!/usr/bin/env bash
cd /volume1/homes/cglee079/docker-build/podo-dev-nuxt
docker stop podo-dev-nuxt
docker rm podo-dev-nuxt
docker rmi podo-dev-nuxt:1.0 --force
docker build --tag podo-dev-nuxt:1.0 .
docker run --name podo-dev-nuxt -p 20080:3000 -v /volume1/docker/podo-dev/podo-dev-nuxt/static:/app/static podo-dev-nuxt:1.0

