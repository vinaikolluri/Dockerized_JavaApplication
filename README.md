Step-by-Step Guide: Deploying a Spring Boot Monolithic Application with Docker Compose


Step 1: Update application.properties
Modify your src/main/resources/application.properties to use environment variables:
spring.application.name=Monolithic
# Database Configuration (Use environment variables)
spring.datasource.url=jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}?createDatabaseIfNotExist=true
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
# Server Port
server.port=${SERVER_PORT}



Step 2: Create .env File
Store the database credentials securely.
📍 Create .env file in the root directory
DB_HOST=mysql
DB_PORT=3306
DB_NAME=monolithic
DB_USER=vinay
DB_PASSWORD=Vinay@7782
SERVER_PORT=8080

Step 3: Create Dockerfile
This file will containerize your Java Spring Boot application.
📍 Create a Dockerfile in the project root
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/Monolithic-0.0.1-SNAPSHOT.jar app.jar

EXPOSE ${SERVER_PORT}

CMD ["java", "-jar", "app.jar"]





Step 4: Create docker-compose.yml
Defines how to run both MySQL and the Spring Boot app.
📍 Create a docker-compose.yml in the project root
version: "3.8"

services:
  mysql:
    image: mysql:8.0
    container_name: mysql-db
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: Vinay@7782
      MYSQL_DATABASE: monolithic
      MYSQL_USER: vinay
      MYSQL_PASSWORD: Vinay@7782
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql

  app:
    build: .
    container_name: java-app
    restart: always
    depends_on:
      - mysql
    environment:
      DB_HOST: mysql
      DB_PORT: 3306
      DB_NAME: monolithic
      DB_USER: vinay
      DB_PASSWORD: Vinay@7782
      SERVER_PORT: 8080
    ports:
      - "8080:8080"
    env_file:
      - .env

volumes:
  mysql_data:

Step 5: Build & Run
1. Build JAR File
mvn clean package -DskipTests

2. Build and Start Containers
docker-compose up --build -d

✅ This will:
Build the Spring Boot app.
Pull and start MySQL.
Run the application using environment variables.


Step 6: Check Application
Verify Running Containers
docker ps

Check Logs
docker logs -f java-app

Access the App
Open a browser and go to:
http://localhost:8080

Step 7: Stop Containers
To stop and remove everything:
docker-compose down -v

✅ Summary of the Process




1
Modify application.properties to use environment variables
2
Store credentials in .env file
3
Create Dockerfile for the Java app
4
Create docker-compose.yml to manage MySQL & app
5
Run mvn clean package to build the JAR
6
Run docker-compose up --build -d to start the app
7
Open http://localhost:8080 in a browser

This setup ensures secure, containerized, and scalable deployment of your Spring Boot application using Docker Compose. 🚀

