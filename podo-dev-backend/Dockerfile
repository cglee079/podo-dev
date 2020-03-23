FROM java:8
WORKDIR /

RUN mkdir -p /app
COPY ./ /app/

RUN echo "Asia/Seoul" > /etc/timezone
# RUN  [cp \ /usr/share/zoneinfo/Asia/Seoul \ /etc/localtime]

CMD java \
    -jar \
    -Dspring.profiles.active=deploy \
    -Dserver.ssl.key-store=file:./app/keystore.p12 \
    -Dspring.config.additional-location=file:./app/ \
    -Xms3072m \
    -Xmx3072m \
    -XX:NewSize=2048m \
    -XX:MaxNewSize=2048m \
    -XX:MetaspaceSize=200M \
    /app/$(ls /app | grep -E '.*\.jar')



