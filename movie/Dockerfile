FROM amazoncorretto:21
WORKDIR /movie-app
CMD ["./gradlew", "clean", "bootJar"]
COPY build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
