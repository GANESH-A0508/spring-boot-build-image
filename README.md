# Spring Boot Build Image with Cloud Native Buildpacks

This repository demonstrates how to create a Spring Boot application, package it using Cloud Native Buildpacks with the `spring-boot-maven-plugin`, and run it using Docker.

## 🚀 Features

- Simple Spring Boot application with a GET API.
- Uses Spring Boot Maven Plugin to build a Docker image.
- Docker image created with Paketo Buildpacks.
- Configured Maven profile for Docker support.
- Supports both Windows (Docker Desktop) & Linux (Docker Daemon).

## 🛠 Prerequisites

Ensure you have the following installed:

- Java 17 [(Download)](https://adoptium.net/) ☕
- Maven 3+ [(Download)](https://maven.apache.org/download.cgi) 📦
- Docker [(Download)](https://www.docker.com/get-started) 🐳
- Git [(Download)](https://git-scm.com/downloads) 🔗

## 📂 Project Structure

```
.
├── src/main/java/com/maven/buildimage/
│   ├── BuildImageApplication.java   # Main Spring Boot Application
│   ├── controller/HelloController.java # Simple REST Controller
├── pom.xml                          # Maven Build Configuration
└── README.md                        # Project Documentation
```

## 🎯 How to Run the Application

### 1️⃣ Clone the Repository

```sh
git clone https://github.com/YOUR_USERNAME/spring-boot-build-image.git
cd spring-boot-build-image
```

### 2️⃣ Build Docker Image Using Spring Boot Plugin (with Debug Mode)

```sh
mvn clean spring-boot:build-image -X
```

## 🔍 How `mvn spring-boot:build-image` Works

1️⃣ **Detects the Application Type** 🧐

- Determines if it's a Java application.

2️⃣ **Chooses a Base Image (Builder)** 🏗️

- By default, it selects `paketobuildpacks/builder-jammy-java-tiny`.
- This can be changed using the builder configuration in `pom.xml`.

3️⃣ **Builds the Image Using Buildpacks** 🔨

- Uses Paketo Buildpacks to create an optimized Docker image.

4️⃣ **Tags the Image** 🏷️

- The image is tagged as `test/myapp-test:latest`.

## 📌 Customizing the Builder Image

Modify `pom.xml` to specify a different builder:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <image>
                    <name>test/myapp-test:latest</name>
                </image>
                <builder>paketobuildpacks/builder:base</builder>
            </configuration>
        </plugin>
    </plugins>
</build>
```

## 🎯 Verifying and Pushing the Docker Image

### 1️⃣ Verify the Docker Image 🔍

```sh
docker images
```

Expected output:

```
REPOSITORY                                 TAG       IMAGE ID       CREATED        SIZE
paketobuildpacks/run-jammy-tiny            latest    0c5ac79d549c   3 months ago   38.3MB
test/myapp-test                            latest    d48c03bc3a98   Just now      469MB
paketobuildpacks/builder-jammy-java-tiny   latest    13144ab1719e   45 years ago   998MB
```

### 2️⃣ Test the API 🔥

Open your browser or use cURL:

```sh
curl http://localhost:8080/start
```

Expected response:

```
Getting started with Maven build image!!!!
```

### 3️⃣ Push the Docker Image to Docker Hub 🚀

Ensure you're logged into Docker Hub:

```sh
docker login
```

Tag the image:

```sh
docker tag test/myapp-test:latest your-dockerhub-username/myapp-test:latest
```

Push the image:

```sh
docker push your-dockerhub-username/myapp-test:latest
```

✅ **Verify in Docker Hub:**

- 🔗 Go to [Docker Hub](https://hub.docker.com/)
- 🔍 Check if your image appears under **Repositories**.

## 📌 Automating Image Build and Push in Maven

To build and push the image in a single `mvn install` run, update `pom.xml`:

```xml
<build>
    <plugins>
        <!-- Spring Boot Build Image Plugin -->
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <image>
                    <name>docker-username/spring-boot-build-image</name>
                </image>
            </configuration>
            <executions>
                <execution>
                    <id>build-image</id>
                    <goals>
                        <goal>build-image</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>

        <!-- Docker Push Plugin -->
        <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>exec-maven-plugin</artifactId>
            <version>3.0.0</version>
            <executions>
                <execution>
                    <id>docker-push</id>
                    <phase>install</phase>
                    <goals>
                        <goal>exec</goal>
                    </goals>
                    <configuration>
                        <executable>docker</executable>
                        <arguments>
                            <argument>push</argument>
                            <argument>docker-username/spring-boot-build-image</argument>
                        </arguments>
                    </configuration>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

Now, follow below steps:

✅ Step 1: Authenticate with Docker Hub
Run the following command before building the image:

```sh
docker login -u username
```
Enter your Docker Hub password when prompted. Note if authentication fails, create a PAT(personal access token) from docker hub and enter this token in place of password.

✅ Step 2: Run mvn clean install command

```sh
mvn clean install
```

It will:
1️⃣ Compile and package the Java application. 📦
2️⃣ Build the Docker image. 🏗️
3️⃣ Push the image to Docker Hub automatically. 🚀

## 🛠 Troubleshooting

### ❌ Issue: `docker: command not found`

✅ Fix: Ensure Docker CLI is installed and available in the system `PATH`.

```sh
docker --version
```

### ❌ Issue: Illegal char `:` at index 5: `npipe://...`

✅ Fix:

1. Open Docker Desktop → Settings → General.
2. Enable **"Expose daemon on tcp\://localhost:2375 without TLS"**.
3. Restart Docker and try again.

Alternatively, add the following plugin to `pom.xml`:

```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <configuration>
        <image>
            <name>test/myapp-test:latest</name>
        </image>
        <docker>
            <host>//./pipe/dockerDesktopLinuxEngine</host>
        </docker>
    </configuration>
</plugin>
```

Ensure the following profile is set correctly:

```xml
<profile>
    <id>docker</id>
    <properties>
        <docker.host>tcp://localhost:2375</docker.host>
    </properties>
</profile>
```

## 📌 Contribution Guide

1. Fork the repository. 🍴
2. Create a new branch (`feature-branch`). 🌱
3. Commit your changes. ✅
4. Push to your fork and create a Pull Request. 🔃

## 💬 Need Help?

If you have any issues, feel free to open an issue on GitHub!

🔗 **GitHub Repository:** [spring-boot-build-image](https://github.com/GANESH-A0508/spring-boot-build-image)

