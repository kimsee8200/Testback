FROM bellsoft/liberica-openjdk-alpine:17
FROM openjdk:17-jdk

LABEL authors="user"
VOLUME /tmp

ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8082


CMD ["./gradlew", "clean", "build", "-x", "test"]
ENTRYPOINT ["java", "-jar", "/app.jar"]