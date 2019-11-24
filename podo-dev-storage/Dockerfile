FROM java:8
WORKDIR /
ADD podo-dev-storage.jar podo-dev-storage.jar
EXPOSE 8443

RUN echo "Asia/Seoul" > /etc/timezone
CMD ["java", "-jar", "-Dspring.profiles.active=deploy", "-Xms256m", "-Xmx256m", "-XX:NewSize=128m", "-XX:MaxNewSize=128m", "-XX:MetaspaceSize=50M", "podo-dev-storage.jar"]
