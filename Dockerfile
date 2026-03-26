# ---------- STAGE 1: Build the React frontend ----------
# Use Node.js to install dependencies and build the frontend
FROM node:20-alpine AS frontend-build

# Set working directory inside the container
WORKDIR /app/frontend

# Copy package files first (for better Docker layer caching)
COPY frontend/package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the frontend source code
COPY frontend/ ./

# Build the production-ready frontend (Vite outputs to /dist)
RUN npm run build


# ---------- STAGE 2: Build the Spring Boot backend ----------
# Use Maven with JDK 17 to compile the backend
FROM maven:3.9.6-eclipse-temurin-17-alpine AS backend-build

# Set working directory
WORKDIR /app

# Copy Maven configuration and source code
COPY pom.xml .
COPY src ./src

# Copy the built frontend (dist folder) into Spring Boot static resources
COPY --from=frontend-build /app/frontend/dist ./src/main/resources/static

# Build the Spring Boot application (skip tests for faster build)
RUN mvn clean package -DskipTests


# ---------- STAGE 3: Final runtime image ----------
# Use a lightweight JRE image to run the application
FROM eclipse-temurin:17-jre-alpine

# Set working directory
WORKDIR /app

# Copy the generated JAR file from the previous stage
COPY --from=backend-build /app/target/*.jar app.jar

# Define the command to run the application
ENTRYPOINT ["java", "-Xmx512m", "-jar", "app.jar"]