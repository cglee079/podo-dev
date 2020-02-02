#!/usr/bin/env bash
cd /volume1/docker-build/podo-dev/podo-dev-storage
docker stop podo-dev-storage
docker rm podo-dev-storage
docker rmi podo-dev-storage:1.0
docker build --tag podo-dev-storage:1.0 .
docker run -it -p 38443:38443 -p 38080:38080 -v /volume1/docker/podo-dev/podo-dev-storage/logs:/data/logs -v /volume1/docker/podo-dev/podo-dev-storage/uploaded:/uploaded --name=podo-dev-storage podo-dev-storage:1.0
