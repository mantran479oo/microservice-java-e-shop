FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy file .jar từ thư mục target cục bộ của service này vào container
COPY target/api-gateway-0.0.1-SNAPSHOT.jar ./

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "api-gateway-0.0.1-SNAPSHOT.jar"]