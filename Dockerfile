FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/{내 jar 파일 이름} sellerService.jar
ENTRYPOINT ["java", "-jar", "sellerService.jar"]
