# Stage 1: Build using Maven
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build

WORKDIR /app

# Copy pom.xml and download dependencies first (for Docker caching)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source and build the jar
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Minimal runtime image
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the fat jar from the build stage
COPY --from=build /app/target/jdbc-api-demo-1.0-SNAPSHOT.jar app.jar

# Expose the port your Spark API listens on
EXPOSE 7080

# Run the app
ENTRYPOINT ["java","-Dconfig=config.properties", "-jar", "app.jar"]

