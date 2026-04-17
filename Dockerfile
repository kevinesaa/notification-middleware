# Etapa 1: compilación
FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app
COPY . .
RUN sed -i 's/\r//' gradlew && chmod +x gradlew
RUN ./gradlew bootJar --no-daemon

# Etapa 2: imagen de ejecución
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
