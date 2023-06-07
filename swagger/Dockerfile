###
# Build to Jar File
###
FROM maven:3.6.0-jdk-11-slim AS build
RUN mkdir /home/app
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package
#RUN --mount=type=cache,target=/root/.m2 mvn -f /home/app/pom.xml clean package

###
# Run Application
###
FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/*.jar spring-boot-application.jar
EXPOSE 8090
#CMD java -jar  /app/spring-boot-application.jar
ENTRYPOINT ["java","-jar","/spring-boot-application.jar"]

