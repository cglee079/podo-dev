FROM java:8
WORKDIR /
ADD podo-dev-backend.jar podo-dev-backend.jar
EXPOSE 8443


RUN echo "Asia/Seoul" > /etc/timezone
# RUN  ["cp", "/usr/share/zoneinfo/Asia/Seoul", "/etc/localtime"]
CMD ["java", "-jar", "-Dspring.profiles.active=deploy", "-Xms3072m", "-Xmx3072m", "-XX:NewSize=2048m", "-XX:MaxNewSize=2048m", "-XX:MetaspaceSize=200M", "podo-dev-backend.jar"]
