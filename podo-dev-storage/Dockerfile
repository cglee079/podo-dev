FROM java:8
WORKDIR /
ADD podo-dev-storage.jar podo-dev-storage.jar
EXPOSE 8443

RUN echo "Asia/Seoul" > /etc/timezone
CMD ["java", "-jar", "-Dspring.profiles.active=deploy", "-Xms512m", "-Xmx512m", "-XX:NewSize=256m", "-XX:MaxNewSize=256m", "-XX:MetaspaceSize=100M", "podo-dev-storage.jar"]
