# Stufe 1: baut das JAR im Container - keine lokale Java- oder Maven-Installation noetig
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /src
COPY pom.xml .
COPY src ./src
RUN mvn -B -q -DskipTests package \
 && mvn -B -q dependency:copy-dependencies -DincludeArtifactIds=h2 -DincludeScope=test -DoutputDirectory=/demo/lib

# Stufe 2: Laufzeit
FROM eclipse-temurin:17.0.7_7-jre-jammy

WORKDIR /app
COPY --from=build /src/target/mediashop-1.4.2.jar app.jar

# Demo-Betrieb fuer das Training: H2 im Speicher mit den Testdaten statt PostgreSQL.
# ABSICHTLICH VERWUNDBARE ANWENDUNG - nur lokal starten, nie ins Netz stellen.
COPY --from=build /demo/lib /app/lib
COPY src/test/resources/schema.sql src/test/resources/data.sql demo/jwt-public.pem /app/demo/
# Login ohne Keycloak: Tokens werden gegen einen festen Demo-Schluessel geprueft (nur oeffentlicher Teil im Repo).
ENV SPRING_DATASOURCE_URL="jdbc:h2:mem:mediashop;MODE=PostgreSQL;DB_CLOSE_DELAY=-1" \
    SPRING_SQL_INIT_MODE=always \
    SPRING_SQL_INIT_SCHEMA_LOCATIONS=file:/app/demo/schema.sql \
    SPRING_SQL_INIT_DATA_LOCATIONS=file:/app/demo/data.sql \
    SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI= \
    SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_PUBLIC_KEY_LOCATION=file:/app/demo/jwt-public.pem

EXPOSE 8080
ENTRYPOINT ["java", "-cp", "/app/app.jar", "-Dloader.path=/app/lib", "org.springframework.boot.loader.PropertiesLauncher"]
