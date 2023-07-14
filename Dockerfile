#
# Build stage
#
FROM maven:3.8.1-openjdk-19 AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests

#
# Package stage
#
FROM openjdk:19-jdk
COPY --from=build /target/url-shortener-1.0-SNAPSHOT.jar demo.jar

# ENV PORT=7000
EXPOSE 7000
ENTRYPOINT ["java", "-jar", "demo.jar"]
