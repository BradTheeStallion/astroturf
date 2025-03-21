FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Add wait-for-it script to check database availability
ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

EXPOSE 8080
CMD ["/bin/bash", "-c", "/wait-for-it.sh ${DATABASE_HOST:-mysql}:${DATABASE_PORT:-3306} -t 60 && java -jar app.jar"]