# Microservices Architecture Overview

## 1. apptraqer-gateway-service
- **Description**: The entry point for all incoming requests from the frontend. Routes requests to appropriate microservices and handles authentication and authorization.
- **Responsibilities**:
  - Routes requests to the correct microservice (e.g., `/jobs`, `/users`).
  - Integrates with **Spring Cloud Gateway** for routing and **Spring Security** for managing authentication and authorization.
  - Validates authentication tokens (e.g., JWT, OAuth2).
  - Interacts with the **apptraqer-auth-service** to authenticate requests and ensure that users have the correct roles.
- **Connected Services**: 
  - **apptraqer-job-service**
  - **apptraqer-user-service**
  - **apptraqer-company-service**
  - **apptraqer-personnel-service**
  - **apptraqer-agency-service**
  - **apptraqer-search-service**
  - **apptraqer-auth-service**

---

## 2. Microservices

### 2.1 Directly Accessible by API Gateway

#### 2.1.1 apptraqer-job-service
- **API Endpoints**: `/jobs`, `/jobs/{id}`, `/jobs/{id}/applications`
- **Data**: Stores job-related data in a **PostgreSQL** database and integrates with **Elasticsearch**.

#### 2.1.2 apptraqer-user-service
- **API Endpoints**: `/users`, `/users/{id}`
- **Data**: Stores user data in a **NoSQL** database (e.g., **Firebase**) and integrates with **Elasticsearch**.

#### 2.1.3 apptraqer-company-service
- **API Endpoints**: `/companies`, `/companies/{id}`
- **Data**: Stores company-related data in a **PostgreSQL** database and integrates with **Elasticsearch**.

#### 2.1.4 apptraqer-personnel-service
- **API Endpoints**: `/personnel`, `/personnel/{id}`
- **Data**: Stores personnel data in a **NoSQL** database (e.g., **MongoDB**) and integrates with **Elasticsearch**.

#### 2.1.5 apptraqer-agency-service
- **API Endpoints**: `/agencies`, `/agencies/{id}`
- **Data**: Stores agency-related data in a **PostgreSQL** database and integrates with **Elasticsearch**.

#### 2.1.6 apptraqer-search-service
- **Description**: Handles search queries from the frontend or other services.
- **Data**: Queries **Elasticsearch** for data related to jobs, users, companies, and personnel.

### 2.2 Only Talked to by Other Services

#### 2.2.1 apptraqer-auth-service
- **Description**: Manages authentication and authorization for the entire system.
- **Authentication Methods**: Supports **email/password** and **OAuth2-based SSO** (e.g., Google, Facebook).
- **Security**: Integrates with **Spring Security** for authentication and authorization.
- **Role-Based Access Control (RBAC)**: Ensures users have access to the appropriate resources based on roles and permissions.

---

## 3. Data Flow and Event-Driven Architecture

### **Event Stream** (Using Kafka)
- **Kafka** handles event streams between services, such as new job postings or user registrations. These events are published to Kafka topics and consumed by other services (e.g., **apptraqer-search-service**).

### **Logstash**
- Collects logs from microservices and sends them to **Elasticsearch** for indexing. This enables centralized logging.

### **Elasticsearch**
- Stores **log data** and **application data** for efficient querying. Microservices can query **Elasticsearch** to retrieve data (e.g., job listings or user info).

---

## 4. Microservice Communication

- **Synchronous** Communication: 
  - Microservices communicate synchronously via **HTTP** (REST APIs) for data access.
- **Asynchronous** Communication: 
  - Microservices communicate asynchronously via **Kafka** for events (e.g., job posting, user registration).

---

## 5. Docker & Network

- Each microservice, **Elasticsearch**, **Kafka**, and **Logstash** runs in separate **Docker containers**.
- These services communicate over a private Docker network. The **apptraqer-gateway-service** is exposed to the outside world, while the other services are internal.

---

## 6. Logging and Monitoring

- **Logstash** or **Filebeat** collects logs from microservices and sends them to **Elasticsearch**.
- **Kibana** is used to visualize logs and monitor system health.
