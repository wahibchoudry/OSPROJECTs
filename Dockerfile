# Use official Maven image with Java 21
FROM maven:3.9.6-eclipse-temurin-21

# Set working directory inside container
WORKDIR /app

# Copy project files
COPY . .

# Build the project
RUN mvn clean package

# Run the application
CMD ["java", "-cp", "target/classes", "com.mycompany.osproject.OSPROJECT"]
