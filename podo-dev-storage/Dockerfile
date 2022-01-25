###########################
##### RUN BUILD #####
###########################
FROM java:8 AS builder

COPY . .

ARG SUB_MODULE

RUN chmod 777 gradlew
RUN ./gradlew :${SUB_MODULE}:clean
RUN ./gradlew -x test :${SUB_MODULE}:build


WORKDIR /
RUN mkdir /app
RUN cp -r ./${SUB_MODULE}/build/libs /app


###########################
##### RUN APPLICATION #####
###########################
FROM java:8

ARG HEAP_SIZE
ENV HEAP_SIZE=${HEAP_SIZE:-256M}
ARG NEW_SIZE
ENV NEW_SIZE=${NEW_SIZE:-256M}

COPY --from=builder /app /app

RUN echo "Asia/Seoul" > /etc/timezone

CMD java \
    -jar \
    -Dspring.profiles.active=deploy \
    -Xms${HEAP_SIZE} -Xmx${HEAP_SIZE} \
    -XX:NewSize=${NEW_SIZE} -XX:MaxNewSize=${NEW_SIZE} \
    -XX:MetaspaceSize=50M \
    /app/$(ls /app | grep -E '.*\.jar')



