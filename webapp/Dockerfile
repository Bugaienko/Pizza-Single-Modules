#
# Build stage
#
FROM maven:3.8.7-openjdk-18-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip=true


#
# Package stage
#
FROM openjdk:17-jdk-alpine
COPY --from=build /home/app/target/webapp-0.0.1-SNAPSHOT.jar /usr/local/lib/webapp.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/usr/local/lib/webapp.jar"]
