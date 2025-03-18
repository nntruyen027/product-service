FROM openjdk:23-slim AS base

WORKDIR /app

COPY target/server-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
