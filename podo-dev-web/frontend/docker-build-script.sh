#!/usr/bin/env bash
cd /volume1/docker-build/podo-dev-frontend
docker stop podo-dev-frontend
docker rm podo-dev-frontend
docker rmi podo-dev-frontend:1.0 --force
docker build --tag podo-dev-frontend:1.0 .
docker run --name podo-dev-frontend -p 20080:3000 -v /volume1/docker/podo-dev/podo-dev-frontend/static:/app/static podo-dev-frontend:1.0

