#!/usr/bin/env bash
cd /volume1/docker-build/podo-dev/podo-dev-backend
docker stop podo-dev-backend
docker rm podo-dev-backend
docker rmi podo-dev-backend
docker build --tag podo-dev-backend .
docker run -it -p 28443:8443 -p 28080:8080 -v /volume1/docker/podo-dev/podo-dev-backend/logs:/data/logs -v /volume1/docker/podo-dev/podo-dev-backend/uploaded:/uploaded -v /volume1/docker/podo-dev/podo-dev-frontend/static:/static --name=podo-dev-backend podo-dev-backend
