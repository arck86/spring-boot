FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/spring-boot-jwt-0.0.1-SNAPSHOT.war
ADD ${JAR_FILE} app.war
ENTRYPOINT ["java","-jar","/app.war"]