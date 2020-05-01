FROM java:8
WORKDIR /

RUN mkdir -p /app
COPY ./ /app/

RUN echo "Asia/Seoul" > /etc/timezone
# RUN  [cp \ /usr/share/zoneinfo/Asia/Seoul \ /etc/localtime]

CMD java \
    -jar \
    -Dspring.profiles.active=deploy \
    -Xms2048m \
    -Xmx2048m \
    -XX:NewSize=1024m \
    -XX:MaxNewSize=1024m \
    -XX:MetaspaceSize=200M \
    /app/$(ls /app | grep -E '.*\.jar')



