FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/astroturf-0.0.1-SNAPSHOT.jar app.jar

# Remove wait-for-it script as Railway manages service dependencies
EXPOSE 8080
# Increase JVM memory and add connection retry parameters
CMD ["java", "-Xmx512m", "-jar", "app.jar", "--spring.datasource.hikari.connection-timeout=120000", "--spring.datasource.hikari.maximum-pool-size=5"]