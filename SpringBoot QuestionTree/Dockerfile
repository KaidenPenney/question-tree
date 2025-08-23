FROM eclipse-temurin:17-jdk

WORKDIR /app

RUN apt-get update && apt-get install -y maven

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn package -DskipTests

CMD ["java", "-jar", "target/app.jar"]