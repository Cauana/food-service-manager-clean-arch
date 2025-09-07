FROM maven:3.9.11-amazoncorretto-21-alpine

WORKDIR /app

COPY src /app/src

COPY pom.xml /app

RUN mvn clean package -DskipTests

CMD ["java", "-jar", "target/food-service-manager.jar"]