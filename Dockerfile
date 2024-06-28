FROM maven:3-openjdk-17 AS build
COPY . .
RUN  mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
COPY --from=build /target/Travego-0.0.1-SNAPSHOT.jar Travego.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","Travego.jar"]