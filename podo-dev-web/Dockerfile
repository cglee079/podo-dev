FROM java:8
WORKDIR /
ADD podo-dev-backend-1.0.jar podo-dev-backend-1.0.jar
EXPOSE 8443

RUN echo "Asia/Seoul" > /etc/timezone
# RUN  ["cp", "/usr/share/zoneinfo/Asia/Seoul", "/etc/localtime"]
CMD ["java", "-jar", "-Dspring.profiles.active=deploy", "podo-dev-backend-1.0.jar"]
