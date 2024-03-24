FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/usermanagement-service.jar usermanagement-service.jar
EXPOSE 8080
CMD ["java","-jar","usermanagement-service.jar"]