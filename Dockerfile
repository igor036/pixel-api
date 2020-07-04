FROM asuprun/opencv-java
VOLUME /tmp
COPY target/pixel-api-0.1.0.jar app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
EXPOSE 8080
