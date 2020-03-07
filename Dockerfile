FROM openjdk:8-jre-alpine
ENV TZ=Europe/Moscow
WORKDIR /opt
COPY target/*.jar .
ENTRYPOINT ["/bin/sh", "-c", "java -Dspring.profiles.active=$PROFILE -jar *.jar"]
