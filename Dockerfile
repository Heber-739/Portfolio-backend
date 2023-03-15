FROM amazoncorretto:11
MAINTAINER HeberDuarte
COPY target/portfolio-0.0.1-SNAPSHOT.jar  hmd-app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/hmd-app.jar"]
