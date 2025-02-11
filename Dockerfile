FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/Monolithic-0.0.1-SNAPSHOT.jar app.jar

EXPOSE ${SERVER_PORT}

CMD ["java", "-jar", "app.jar"]
