FROM maven:eclipse-temurin as build
LABEL authors="f0rkb0mbZ"

WORKDIR /app

COPY pom.xml .

RUN mvn -Dmaven.artifact.threads=8 -B dependency:go-offline

COPY src src

RUN mvn -B package

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/tldr-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-jar", "tldr-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080