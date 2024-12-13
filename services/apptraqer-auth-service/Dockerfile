FROM openjdk:17-jdk-slim as build

WORKDIR /app

COPY build.gradle settings.gradle ./

COPY gradlew ./
COPY gradle/wrapper/ ./gradle/wrapper/

ENV SPRING_PROFILES_ACTIVE=test

RUN ./gradlew build --no-daemon

COPY src ./src

RUN ./gradlew build

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/build/libs/apptraqer-auth-service.jar apptraqer-auth-service.jar

EXPOSE 8081

CMD ["java", "-jar", "apptraqer-auth-service.jar"]
