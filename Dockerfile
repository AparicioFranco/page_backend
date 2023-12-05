FROM adoptopenjdk/openjdk17:latest

# Set the working directory to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Install any needed packages specified in requirements.txt
RUN apk add --no-cache postgresql-client

# Make port 5432 available to the world outside this container
EXPOSE 5432

# Run the command to start the Kotlin application
CMD ["java", "-jar", "app.jar"]