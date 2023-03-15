FROM amazoncorretto:11-alpine-jdk
MAINTAINER Heber
COPY target/portfolio-0.0.1-SNAPSHOT  heber-portfolio.jar
ENTRYPOINT ["java","-jar","/heber-portfolio.jar"]