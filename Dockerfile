FROM openjdk:23-slim AS base

WORKDIR /app

COPY target/product-service-0.0.1-SNAPSHOT.jar product-service-0.0.1-SNAPSHOT.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "product-service-0.0.1-SNAPSHOT.jar"]
