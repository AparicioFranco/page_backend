# Use a base image with Java installed
FROM openjdk:17-jdk-slim AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle build files
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY gradlew .
COPY gradle gradle

# List the contents of the directory
RUN ls -l /app/

# Copy the application source code
COPY src src

# Download dependencies and build cache
RUN ./gradlew --no-daemon build || return 0

# Spring Boot stage
FROM openjdk:17-jdk-slim AS springboot_stage

# Set the working directory inside the container
RUN mkdir /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/build/libs/ /app/

# Expose the port that your Spring Boot application listens on
EXPOSE 8080

# Command to run your application
CMD ["java","-jar","/app/kotlinTutorial.jar"]

