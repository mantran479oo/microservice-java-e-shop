FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy file .jar từ thư mục target cục bộ của service này vào container
COPY target/config-server-1.0-SNAPSHOT.jar ./

EXPOSE 8888
ENTRYPOINT ["java", "-jar", "config-server-1.0-SNAPSHOT.jar"]