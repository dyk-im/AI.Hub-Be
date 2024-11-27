#스프링 프로젝트 구동을 위한 베이스 이미지(jdk)
From openjdk:17-jdk-slim
#작업 디렉터리 설정a
WORKDIR /app
#빌드 결과물인 jar 파일 위치 설정 /
ARG JAR_FILE=build/libs/aihubbe-0.0.1-SNAPSHOT.jar
# 빌드된 jar 파일 복제
COPY ${JAR_FILE} app.jar
# 8080번 포트 연결
EXPOSE 8080
# 앱 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]
