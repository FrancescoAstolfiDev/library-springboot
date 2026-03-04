# Phase 1: Build with Maeven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Phase 2: Execute
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copy the jar file generated from the build phase
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]