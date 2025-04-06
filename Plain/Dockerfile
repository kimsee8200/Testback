FROM gradle:7.6-jdk17-alpine AS builder
WORKDIR /app
COPY . .
RUN chmod +x gradlew
RUN ./gradlew clean build -x test
FROM bellsoft/liberica-openjdk-alpine:17
LABEL authors="user"
VOLUME /tmp
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
