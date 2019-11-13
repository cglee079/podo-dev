FROM java:8
WORKDIR /
ADD podo-dev-backend.jar podo-dev-backend.jar
EXPOSE 8443


RUN echo "Asia/Seoul" > /etc/timezone
# RUN  ["cp", "/usr/share/zoneinfo/Asia/Seoul", "/etc/localtime"]
CMD ["java", "-jar", "-Dspring.profiles.active=deploy", "-Xms2048m", "-Xmx2048m", "-XX:NewSize=1024m", "-XX:MaxNewSize=1024m", "-XX:MetaspaceSize=100M", "podo-dev-backend.jar"]
