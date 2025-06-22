FROM maven AS builder

WORKDIR /app

COPY pom.xml .
COPY src/main src/main
RUN mvn package

FROM openjdk:23

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
