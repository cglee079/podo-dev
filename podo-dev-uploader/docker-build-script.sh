cd /volume1/docker-build/podo-dev-uploader
docker stop podo-dev-uploader
docker rm podo-dev-uploader
docker rmi podo-dev-uploader:1.0
docker build --tag podo-dev-uploader:1.0 .
docker run -it -p 38443:38443 -p 38080:38080 -v /volume1/docker/podo-dev/podo-dev-uploader/logs:/logs -v /volume1/docker/podo-dev/podo-dev-uploader/uploaded:/uploaded --name=podo-dev-uploader podo-dev-uploader:1.0
