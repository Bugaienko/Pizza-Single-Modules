#
# Build stage
#
FROM maven:3.8.7-openjdk-18-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package
#-Dmaven.test.skip=true


#
# Package stage
#
FROM openjdk:17-jdk-alpine
COPY --from=build /home/app/target/pizzarest-0.0.5.jar /usr/local/lib/rest.jar
EXPOSE 8099
ENTRYPOINT ["java","-jar","/usr/local/lib/rest.jar"]
