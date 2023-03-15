FROM amazoncorretto:11-alpine-jdk
MAINTAINER HeberDuarte
COPY target/portfolio-0.0.1-SNAPSHOT.jar  hmd-app.jar
ENTRYPOINT ["java","-jar","/hmd-app.jar"]
