# Use Eclipse Temurin JDK as base image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Install Maven inside container
RUN apt-get update && apt-get install -y maven

# Copy pom.xml first (caching dependencies step)
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Run the application
CMD ["java", "-jar", "target/app.jar"]