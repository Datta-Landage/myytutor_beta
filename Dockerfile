# Use an official Maven image to build the application
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy Maven wrapper files first for better caching
COPY .mvn/ .mvn/
COPY mvnw .
COPY mvnw.cmd .

# Copy pom.xml and download dependencies (cached layer)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests -B

# Use lightweight JRE runtime image for production
FROM eclipse-temurin:17-jre-alpine

# Install curl for health checks
RUN apk add --no-cache curl

# Create non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Set the working directory
WORKDIR /app

# Copy the built JAR from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# JVM optimization for containerized environment
ENV JAVA_OPTS="-Xmx512m -Xms256m -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

# Run the application with optimized JVM settings
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
