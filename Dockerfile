FROM openjdk:8-jdk-alpine
RUN mkdir /app
COPY target/user-service-0.1.0.jar /app
WORKDIR /app
EXPOSE 5000
CMD ["java", "-jar", "user-service-0.1.0.jar"]
