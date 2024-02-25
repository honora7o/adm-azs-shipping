FROM amazoncorretto:21-alpine-jdk

WORKDIR /app

COPY build/libs/azship-app-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]