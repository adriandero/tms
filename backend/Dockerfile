FROM maven:3.8.4-openjdk-11 AS build
WORKDIR /build
COPY . .
RUN mvn clean package spring-boot:repackage

FROM openjdk:11.0.13-jre-slim AS run
WORKDIR /app
COPY --from=build /build/target/tms-1.0-SNAPSHOT.jar ./tms.jar
ENTRYPOINT [ "java", "-Dspring.profiles.active=prod", "-jar", "tms.jar" ]