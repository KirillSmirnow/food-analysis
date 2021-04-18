FROM openjdk:8-jre-alpine
ENV TZ=Europe/Moscow
WORKDIR /opt
COPY target/*.jar .
ENTRYPOINT exec java -Xms100M -Xmx300M -Dspring.profiles.active=$PROFILE -jar *.jar
