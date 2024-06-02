###########################
##### RUN BUILD #####
###########################
FROM cglee079/podo-dev-backend:base  AS builder

COPY --chown=gradle:gradle . /home/gradle/src

ARG SUB_MODULE

WORKDIR /home/gradle/src

RUN gradle --no-daemon --debug :${SUB_MODULE}:clean
RUN gradle --no-daemon --debug -x test :${SUB_MODULE}:build


WORKDIR /
RUN mkdir /app
RUN mkdir ls
RUN cp -r /home/gradle/src/${SUB_MODULE}/build/libs/* /app


###########################
##### RUN APPLICATION #####
###########################
FROM openjdk:11.0.16-jdk

COPY --from=builder /app /app

RUN echo "Asia/Seoul" > /etc/timezone

CMD java \
    -jar \
    -Dspring.profiles.active=prod \
    /app/$(ls /app | grep -E '.*\.jar')



