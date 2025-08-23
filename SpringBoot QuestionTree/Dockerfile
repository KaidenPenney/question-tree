# Start from a JDK image
FROM eclipse-temurin:17-jdk

# Install Maven inside the container
RUN apt-get update && apt-get install -y maven

# Set working directory
WORKDIR /app

# Copy pom.xml first to download dependencies
COPY pom.xml .

# Go offline with dependencies
RUN mvn dependency:go-offline

# Copy the rest of the project
COPY src ./src

# Package the app
RUN mvn package

# Run the Spring Boot app
CMD ["java", "-jar", "target/question-tree-0.0.1-SNAPSHOT.jar"]

#used ai for this part as unfamiliar with dockerfiles