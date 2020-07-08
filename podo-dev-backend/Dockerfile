FROM java:8
WORKDIR /

RUN mkdir -p /app
COPY ./ /app/

RUN echo "Asia/Seoul" > /etc/timezone
# RUN  [cp \ /usr/share/zoneinfo/Asia/Seoul \ /etc/localtime]

CMD java \
    -jar \
    -Dspring.profiles.active=deploy \
    -Xms1024m \
    -Xmx1024m \
    -XX:NewSize=512m \
    -XX:MaxNewSize=512m \
    -XX:MetaspaceSize=50M \
    /app/$(ls /app | grep -E '.*\.jar')



