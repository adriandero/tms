# Build software.
FROM maven:3.8.4-openjdk-11 AS build
WORKDIR /build
COPY software .
RUN mvn package

# Run software
FROM openjdk:11.0.13-jre-slim AS run
WORKDIR /app
COPY --from=build /build/target/tms.jar .
ENTRYPOINT [ "java", "-jar", "tms.jar" ]