# 1. OpenJDK 17 사용
FROM openjdk:17-jdk-slim

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. JAR 파일을 컨테이너로 복사
COPY build/libs/*.jar app.jar

# 4. 실행 권한 부여
RUN chmod +x app.jar

# 5. 컨테이너가 실행될 때 실행할 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]