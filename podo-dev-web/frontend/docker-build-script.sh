#!/usr/bin/env bash
cd /volume1/docker-build/podo-dev/podo-dev-frontend
docker stop podo-dev-frontend
docker rm podo-dev-frontend
docker rmi podo-dev-frontend:1.1 --force
docker build --tag podo-dev-frontend:1.1 .
docker run --name podo-dev-frontend -p 20080:8000 -v /volume1/docker/podo-dev/podo-dev-frontend/static:/app/static podo-dev-frontend:1.1

