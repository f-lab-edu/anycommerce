# 1. OpenJDK 기반 이미지 사용
FROM openjdk:17-jdk-slim AS build

WORKDIR /app

COPY . /app
RUN chmod +x gradlew && ./gradlew build --no-daemon

# 2. 실제 애플리케이션 실행용 이미지 생성
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

CMD ["java", "-jar", "app.jar"]
