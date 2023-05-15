#Specify a base image
FROM openjdk:11
# From adoptopenjdk/openjdk11
VOLUME /app
# copy jar file 
COPY target/raktkosh-api-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT [ "java","-jar","/app/app.jar"]

