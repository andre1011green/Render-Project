FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN ./mvnw clean install -DskipTests



# Use the official PostgreSQL image from the Docker Hub
FROM dejwcake/postgres18

# Set environment variables for the database
ENV POSTGRES_DB=ers_database
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=mayday

# Copy initialization script
COPY /init.sql /docker-entrypoint-initdb.d/

# Expose the default PostgreSQL port EXPOSE
EXPOSE 5432

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY /target/Practice-Project-2-0.0.1-SNAPSHOT.jar   moon.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "moon.jar"]


