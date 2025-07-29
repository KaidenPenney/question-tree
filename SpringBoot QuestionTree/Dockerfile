# Use Eclipse Temurin JDK as base image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src ./src

# Build the application
RUN ./mvnw package -DskipTests

# Run the application
ENTRYPOINT ["java", "-jar", "target/*.jar"] 
#kill me pls