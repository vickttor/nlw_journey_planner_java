FROM maven:3.9.3-eclipse-temurin-17

LABEL authors="Victor H. Silva"
LABEL description="NLW Journey - Planner - RESTful API built with Spring Boot"
EXPOSE 8080

WORKDIR /usr/api

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package

# Command to run the Spring Boot application with DevTools for auto-refresh
ENTRYPOINT ["mvn", "spring-boot:run"]