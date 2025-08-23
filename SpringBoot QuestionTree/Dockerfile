# Use Eclipse Temurin JDK as base image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy pom.xml and wrapper files
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Fix line endings + make mvnw executable
RUN sed -i 's/\r$//' mvnw && chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src ./src

# Build the application
RUN ./mvnw package -DskipTests

# Run the application
CMD ["java", "-jar", "target/questiontree-api-0.0.1-SNAPSHOT.jar"]