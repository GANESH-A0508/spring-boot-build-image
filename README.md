Spring Boot Build Image with Cloud Native Buildpacks (spring-boot-build-image)
This repository demonstrates how to create a Spring Boot application, package it using Cloud Native Buildpacks with spring-boot-maven-plugin dependency, and run it using Docker.

🚀 Features
Simple Spring Boot application with a GET API.
Uses Spring Boot Maven Plugin to build a Docker image.
Docker image created with Paketo Buildpacks.
Configured Maven Profile for Docker support.
Supports both Windows (Docker Desktop) & Linux (Docker Daemon).
🛠 Prerequisites
Ensure you have the following installed:

Java 17 (Download)
Maven 3+ (Download)
Docker (Download)
Git (Download)
📂 Project Structure
.
├── src/main/java/com/maven/buildimage/
│   ├── BuildImageApplication.java   # Main Spring Boot Application
│   ├── controller/HelloController.java # Simple REST Controller
├── pom.xml                          # Maven Build Configuration
└── README.md                        # Project Documentation
🎯 How to Run the Application
1️⃣ Clone the Repository
git clone https://github.com/YOUR_USERNAME/spring-boot-build-image.git
cd spring-boot-build-image
2️ Build Docker Image Using Spring Boot Plugin with debug mode
mvn clean spring-boot:build-image -X
3️⃣ Verify the Docker Image
docker images
Expected output:

REPOSITORY                                 TAG       IMAGE ID       CREATED        SIZE
paketobuildpacks/run-jammy-tiny            latest    0c5ac79d549c   3 months ago   38.3MB
test/myapp-test                            latest    d48c03bc3a98   Just now      469MB
paketobuildpacks/builder-jammy-java-tiny   latest    13144ab1719e   45 years ago   998MB

4️⃣ Test the API
Open your browser or use cURL:

curl http://localhost:8080/start
Expected response:

Getting start with maven build image!!!!
🛠 Troubleshooting
❌ Issue: Illegal char <:> at index 5: npipe://...
✅ Fix:

Open Docker Desktop → Settings → General.
Enable "Expose daemon on tcp://localhost:2375 without TLS".
Restart Docker and try again.
✅ Fix:

Add below plugin to Fix the above issue.
org.springframework.boot spring-boot-maven-plugin  test/myapp-test:latest //./pipe/dockerDesktopLinuxEngine
And make sure below profile is setting correctly.
docker tcp://localhost:2375
❌ Issue: docker: command not found
✅ Fix: Ensure Docker CLI is installed and available in the system PATH.

docker --version
📌 Contribution Guide
Fork the repository.
Create a new branch (feature-branch).
Commit your changes.
Push to your fork and create a Pull Request.
💬 Need Help?
If you have any issues, feel free to open an issue on GitHub!

🔗 GitHub Repository: https://github.com/GANESH-A0508/spring-boot-build-image
