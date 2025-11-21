FROM openjdk:8u102-jre
EXPOSE 8080
COPY target/devops-integration.jar devops-integration.jar
ENTRYPOINT ["java","-jar","/devops-integration.jar"]
