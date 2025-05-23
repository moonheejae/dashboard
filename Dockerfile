# JDK 17 기반 이미지 사용
FROM openjdk:20-jdk

# 작업 디렉토리 설정
#WORKDIR /app

# 기존 .war 파일 삭제
RUN rm -rf /*.war

# 로컬에서 빌드된 .war 파일 도커로 파일명 변경하여 추가
ADD build/libs/ht_platform-0.1-SNAPSHOT.war /ht-platform.war

# JAR 파일 복사 (target 폴더의 JAR 파일명을 실제 파일명으로 변경해주세요)
#COPY target/*.jar app.jar

# 포트 설정 (애플리케이션의 실제 포트로 변경해주세요)
EXPOSE 8020

# 실행 명령어
ENTRYPOINT ["java", "-jar", "ht-platform.war"]