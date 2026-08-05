FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy file .jar từ thư mục target cục bộ của service này vào container
COPY target/eureka-server-0.0.1-SNAPSHOT.jar ./

EXPOSE 8761
ENTRYPOINT ["java", "-jar", "eureka-server-0.0.1-SNAPSHOT.jar"]