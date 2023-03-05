# For local development, so we don't need to recache everything for Maven
FROM openjdk:8
ADD ./target/pet-hospital-backend-0.0.1-SNAPSHOT.jar /usr/src/pet-hospital-backend-0.0.1-SNAPSHOT.jar
WORKDIR usr/src
ENTRYPOINT ["java","-jar", "pet-hospital-backend-0.0.1-SNAPSHOT.jar"]
