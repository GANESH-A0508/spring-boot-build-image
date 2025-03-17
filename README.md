# Spring Boot Build Image with Cloud Native Buildpacks (spring-boot-build-image)

This repository demonstrates how to create a **Spring Boot application**, package it using **Cloud Native Buildpacks**  with spring-boot-maven-plugin dependency, and run it using **Docker**.

## 🚀 Features
- Simple **Spring Boot application** with a **GET API**.
- Uses **Spring Boot Maven Plugin** to **build a Docker image**.
- Docker image created with **Paketo Buildpacks**.
- Configured **Maven Profile** for **Docker support**.
- Supports both **Windows (Docker Desktop) & Linux (Docker Daemon)**.

## 🛠 Prerequisites
Ensure you have the following installed:

- **Java 17** ([Download](https://adoptopenjdk.net/))
- **Maven 3+** ([Download](https://maven.apache.org/download.cgi))
- **Docker** ([Download](https://www.docker.com/get-started))
- **Git** ([Download](https://git-scm.com/downloads))

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

### 2️ Build Docker Image Using Spring Boot Plugin with debug mode
```sh
mvn clean spring-boot:build-image -X
```

#### 🔍 How `mvn spring-boot:build-image` Works

1. **Detects the Application Type**
   - Determines if it's a Java application.
2. **Chooses a Base Image (Builder)**
   - By default, it selects `paketobuildpacks/builder-jammy-java-tiny`.
   - This can be changed using the `builder` configuration in `pom.xml`.
3. **Builds the Image Using Buildpacks**
   - Uses Paketo Buildpacks to create an optimized Docker image.
4. **Tags the Image**
   - The image is tagged as `test/myapp-test:latest`.

#### 📌 Customizing the Builder Image

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

Other available builder images:

| Builder | Base Image | Description |
|---------|-----------|-------------|
| `paketobuildpacks/builder:base` | Ubuntu (Jammy) | Default for most Java apps |
| `paketobuildpacks/builder:full` | Ubuntu (Jammy) | Includes all dependencies |
| `paketobuildpacks/builder:tiny` | Minimal Ubuntu | Smallest possible image |
| `gcr.io/paketo-buildpacks/builder` | Google Container Registry | Alternative option |

To force a specific builder:

```sh
mvn spring-boot:build-image -Dspring-boot.build-image.builder=paketobuildpacks/builder:full
```

### 3️⃣ Verify the Docker Image
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

### 4️⃣ Test the API
Open your browser or use **cURL**:
```sh
curl http://localhost:8080/start
```
Expected response:
```json
Getting start with maven build image!!!!
```

## 🛠 Troubleshooting
### ❌ Issue: `Illegal char <:> at index 5: npipe://...`
✅ **Fix:**
1. Open **Docker Desktop** → **Settings** → **General**.
2. Enable **"Expose daemon on tcp://localhost:2375 without TLS"**.
3. Restart Docker and try again.

✅ **Fix:**
1. Add below plugin to Fix the above issue.
2.   <plugin>
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
3. And make sure below profile is setting correctly.
4. <profiles>
		<profile>
			<id>docker</id>
			<properties>
				<docker.host>tcp://localhost:2375</docker.host>
			</properties>
		</profile>
	</profiles>

### ❌ Issue: `docker: command not found`
✅ **Fix:**
Ensure **Docker CLI** is installed and available in the system **PATH**.
```sh
docker --version
```

## 📌 Contribution Guide
1. **Fork** the repository.
2. **Create** a new branch (`feature-branch`).
3. **Commit** your changes.
4. **Push** to your fork and create a **Pull Request**.

## 💬 Need Help?
If you have any issues, feel free to **open an issue** on GitHub!

---
🔗 **GitHub Repository:** [https://github.com/GANESH-A0508/spring-boot-build-image](https://github.com/GANESH-A0508/spring-boot-build-image)
