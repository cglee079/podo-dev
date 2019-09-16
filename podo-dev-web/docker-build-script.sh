cd /volume1/homes/cglee079/docker-build/podo-dev-backend
docker stop podo-dev-backend
docker rm podo-dev-backend
docker rmi podo-dev-backend:1.0
docker build --tag podo-dev-backend:1.0 .
docker run -it -p 28443:28443 -p 28080:28080 -v /volume1/docker/podo-dev/podo-dev-backend/logs:/logs -v /volume1/docker/podo-dev/podo-dev-backend/uploaded:/uploaded -v /volume1/docker/podo-dev/podo-dev-frontend/static:/static --name=podo-dev-backend podo-dev-backend:1.0
