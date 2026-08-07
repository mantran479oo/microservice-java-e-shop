# syntax=docker/dockerfile:1.7
FROM maven:3.9-eclipse-temurin-17-alpine AS build

ARG SERVICE
WORKDIR /workspace
COPY . .
RUN --mount=type=cache,target=/root/.m2 \
    test -n "${SERVICE}" && \
    mvn -B -ntp -DskipTests -pl "${SERVICE}" -am package

FROM eclipse-temurin:17-jre-alpine

ARG SERVICE
RUN addgroup -S spring && adduser -S spring -G spring
WORKDIR /app
COPY --from=build /workspace/${SERVICE}/target/${SERVICE}-*.jar /app/app.jar

ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75.0"
USER spring:spring
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
