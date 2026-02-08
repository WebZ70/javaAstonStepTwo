FROM openjdk:18.0.1
LABEL authors="Tim"
WORKDIR /app
RUN mkdir -p /app/logs
COPY target/javaAstonStepTwo-1.0.0.jar /app/app.jar
CMD ["java", "-jar", "app.jar"]