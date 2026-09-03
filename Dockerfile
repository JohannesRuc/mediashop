FROM eclipse-temurin:17.0.7_7-jre-jammy

WORKDIR /app
COPY target/mediashop-1.4.2.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
