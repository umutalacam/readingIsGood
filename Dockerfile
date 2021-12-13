FROM openjdk:11
MAINTAINER umutalacam.org
COPY ReadingApp/target/ReadingApp-0.0.1-SNAPSHOT.jar ReadingApp-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/ReadingApp-0.0.1-SNAPSHOT.jar"]