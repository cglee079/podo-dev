FROM java:8
WORKDIR /

RUN mkdir -p /app
COPY ./ /app/

RUN echo "Asia/Seoul" > /etc/timezone

CMD java \
    -jar \
    -Dspring.profiles.active=deploy \
    -Xms1024m \
    -Xmx1024m \
    -XX:NewSize=512m \
    -XX:MaxNewSize=512m \
    -XX:MetaspaceSize=100M \
    podo-dev-storage.jar
