# Use a base image with Java installed
FROM openjdk:17-jdk-slim AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle build files
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY gradlew .
COPY gradle gradle

RUN apt-get update && apt-get install -y bash curl

# Install dos2unix to convert Windows line endings to Unix
RUN apt-get update && apt-get install -y dos2unix

# Convert gradlew to Unix line endings
RUN dos2unix /app/gradlew

# Set executable permissions for gradlew
RUN chmod +x /app/gradlew

# List the contents of the directory
RUN ls -l /app/gradlew

# Copy the application source code
COPY src src

# Download dependencies and build cache
RUN /bin/bash ./gradlew --no-daemon build

# Spring Boot stage
FROM openjdk:17-jdk-slim AS springboot_stage

# Set the working directory inside the container
RUN mkdir /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/build/libs/ /app/

# Expose the port that your Spring Boot application listens on
EXPOSE 8080

# Command to run your application
CMD ["java","-jar","/app/listadelimperio_backend.jar"]
