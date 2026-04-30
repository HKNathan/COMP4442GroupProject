# COMP4442 Spring Boot Service Computing on AWS

## Description
This project demonstrates a Spring Boot service computing application deployed on AWS cloud infrastructure such as EC2 and EBS.  

The project is designed to show:
- practical use of Spring Boot for service APIs,
- deployment and operation on AWS,

## Installation
### 1) Clone the repository
```bash
git clone <your-repository-url>
cd comp4442-service-app-main
```

### 2) Check Java version (Java 17+ recommended)
```bash
java -version
```

### 3) Build the project with Maven
```bash
./mvnw clean install
```
On Windows PowerShell:
```powershell
.\mvnw.cmd clean install
```

### 4) Start the Spring Boot application
```bash
./mvnw spring-boot:run
```
On Windows PowerShell:
```powershell
.\mvnw.cmd spring-boot:run
```

## Usage
Once running, the service is typically available at:
- `http://localhost:8080`

## AWS Deployment (EC2/EBS)
Basic demonstration workflow:
1. Build a deployable JAR:
   ```bash
   ./mvnw clean package
   ```
2. Launch an EC2 instance and attach/configure EBS if needed.
3. Copy the JAR to EC2 (for example using `scp`).
4. Run the service on EC2:
   ```bash
   java -jar target/*.jar
   ```
5. Verify via EC2 public IP and open security group ports (e.g., 8080).

## License
This project is released under the **MIT License**.  
