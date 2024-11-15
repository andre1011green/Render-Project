FROM dejwcake/postgres16 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN ./mvnw clean install -DskipTests



# Use the official PostgreSQL image from the Docker Hub
FROM dejwcake/postgres16

# Set environment variables for the database
ENV POSTGRES_DB=sunnyday2
ENV POSTGRES_USER=root
ENV POSTGRES_PASSWORD=Mayday

# Copy initialization script
COPY /init.sql /docker-entrypoint-initdb.d/

# Expose the default PostgreSQL port EXPOSE
EXPOSE 5432

FROM openjdk:21-jdk
WORKDIR /app
COPY Practice-Project.jar   meToo.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "meToo.jar"]


