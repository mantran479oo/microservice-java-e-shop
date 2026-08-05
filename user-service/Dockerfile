FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy file .jar từ thư mục target cục bộ của service này vào container
COPY target/user-service-0.0.1-SNAPSHOT.jar ./

EXPOSE 8082
ENTRYPOINT ["java", "-jar", "user-service-0.0.1-SNAPSHOT.jar"]