FROM gradle:jdk17

WORKDIR /app

COPY . /app

RUN gradle build

CMD ["java", "-jar", "-Dspring.profiles.active=dev", "/app/build/libs/main-service-0.0.1-SNAPSHOT.jar"]