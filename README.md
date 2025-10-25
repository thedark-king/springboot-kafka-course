# Wikimedia Kafka Streamer 🚀

[![Java](https://img.shields.io/badge/Java-23-blue?logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-green?logo=spring)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.8+-red?logo=apache-maven)](https://maven.apache.org/)
[![Kafka](https://img.shields.io/badge/Kafka-3.5+-orange?logo=apachekafka)](https://kafka.apache.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8+-blue?logo=mysql)](https://www.mysql.com/)

A Spring Boot application that streams **real-time Wikimedia events** and publishes them to **Kafka**, with optional **MySQL persistence**.

---

## Features ✨

- Connects to Wikimedia’s SSE stream for recent changes.
- Publishes events to a Kafka topic in real-time.
- Handles SSE reconnections automatically.
- Optional persistence to MySQL using Spring Data JPA.
- Fully configurable via `application.properties`.

---

## Tech Stack 🛠️

- **Java 23**
- **Spring Boot 3.5.6**
- **Spring Kafka**
- **Spring Data JPA**
- **MySQL 8**
- **LaunchDarkly EventSource** for SSE

---

## Quick Start ⚡

### Prerequisites

- Java 23+
- Apache Kafka running
- MySQL 8+ (optional)
- Maven 3.8+

### Setup

1. Clone the repository:

```
bash
git clone https://github.com/yourusername/wikimedia-kafka-streamer.git
cd wikimedia-kafka-streamer
````
### Configure
`application.properties`:

```
properties
# Kafka
spring.kafka.bootstrap-servers=localhost:9092
topic.name=wikimedia.recentchange

# MySQL (optional)
spring.datasource.url=jdbc:mysql://localhost:3306/wikimedia?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### Build and run the application: 

```
bash
mvn clean install
mvn spring-boot:run
```

### Kafka Topic
•	Default: wikimedia.recentchange  
•	Ensure it exists:

```
bash
kafka-topics.sh --create --topic wikimedia.recentchange --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
```

### Logging
•	Logs SSE events, reconnect attempts, and Kafka send status.  
•	Uses SLF4J + Logback.

⸻

### Contributing 🤝
1.	Fork & clone the repo.
2.	Create a branch: git checkout -b feature/your-feature
3.	Commit: git commit -m "Add feature"
4.	Push: git push origin feature/your-feature
5.	Open a Pull Request.
