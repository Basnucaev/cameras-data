FROM openjdk:11
EXPOSE 8090
WORKDIR /app
COPY . /app
RUN ./mvnw clean package -DskipTests
COPY /target/cameras-data-0.0.1-SNAPSHOT.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]
