# Use lightweight Java 17 runtime (Amazon Corretto - stable)
FROM amazoncorretto:17-alpine

# Jar file path
ARG JAR_FILE=target/journalApp-0.0.1-SNAPSHOT.jar

# Copy jar
COPY ${JAR_FILE} app.jar

# Expose port
EXPOSE 8080

# Run application
ENTRYPOINT ["java","-jar","/app.jar"]

LABEL author="Abhinav Trivedi" \
      version="1.0" \
      description="Journal App with Redis, Mongo, JWT, Weather API" \
      org.opencontainers.image.title="journal-app" \
      org.opencontainers.image.version="1.0" \
      org.opencontainers.image.vendor="Abhinav"
