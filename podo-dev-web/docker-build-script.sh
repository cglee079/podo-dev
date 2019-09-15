cd /volume1/homes/cglee079/docker-build/podo-dev-web
docker stop podo-dev-web
docker rm podo-dev-web
docker rmi podo-dev-web:1.0
docker build --tag podo-dev-web:1.0 .
docker run -it -p 28443:28443 -p 28080:28080 -v /volume1/docker/podo-dev/podo-dev-web/logs:/logs -v /volume1/docker/podo-dev/podo-dev-web/uploaded:/uploaded -v /volume1/docker/podo-dev/podo-dev-nuxt/static:/static --name=podo-dev-web podo-dev-web:1.0