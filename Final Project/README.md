# 🧑‍💻 Crowdsourcing Marketplace (Freelancer Platform)

A full-stack web application where employers post projects and freelancers bid on them.  
Built using **React (Frontend)**, **Spring Boot (Backend)**, and **MySQL (Database)** — all containerized with **Docker**.

---

## ⚙️ Tech Stack

- **Frontend:** React.js
- **Backend:** Spring Boot (Java 17)
- **Database:** MySQL
- **Containerization:** Docker & Docker Compose

---

## 🚀 Features

- User registration & login
- Employers can post new projects
- Freelancers can view and bid on projects
- Automatic bid closing after deadline
- Auto-assigns best bid to freelancer
- REST API communication between frontend & backend

---

## 📋 Prerequisites

Before you run this manually, ensure you have the following installed:
* Java 17 (or newer)
* Maven
* Node.js & npm
* MySQL Server

---

## 🐳 Run with Docker (Recommended)

### 1️⃣ Clone this repository

```bash
git clone [https://github.com/](https://github.com/)<your-username>/<your-repo-name>.git
cd <your-repo-name>
```

### 2️⃣ Start all services

```bash
docker compose up --build
```
### 3️⃣ Access the app
Frontend → http://localhost:3000

Backend API → http://localhost:8080

MySQL → http://localhost:3306

#### To stop everything:
```bash
docker compose down
```
## 💻 Run Manually (Without Docker)
### 1. Database Setup
1. Make sure your MySQL server is running.

2. Create a new database for the project (e.g., CREATE DATABASE freelancer_db;).

3. Configure the backend by editing src/main/resources/application.properties. Update the following properties with your MySQL username, password, and database name:

```Properties
spring.datasource.url=jdbc:mysql://localhost:3306/freelancer_db
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
```
## 2. Backend
```bash
# Navigate to the backend directory
cd FreelancerUsingSpringBoot

# Build the project
mvn clean package -DskipTests

# Run the application
java -jar target/freelancer-springboot-1.0-SNAPSHOT.jar
```
## 3. Frontend
```bash
# In a new terminal, navigate to the frontend directory
cd Frontend

# Install dependencies
npm install

# Start the development server
npm start
```
### Access Points:

Backend → http://localhost:8080

Frontend → http://localhost:3000