#  Finance Dashboard System

##  Project Overview

The Finance Dashboard System is a backend application built using Spring Boot. It allows users to manage financial records such as income and expenses, perform CRUD operations, and view summarized dashboard insights.

---

## Features

* ✔ User & Role Management
* ✔ Financial Records CRUD (Create, Read, Update, Delete)
* ✔ Filtering by Type, Category, and Date
* ✔ Pagination Support
* ✔ Dashboard APIs (Total Income, Total Expense, Net Balance)
* ✔ Category-wise Summary
* ✔ Role-Based Access Control (RBAC)
* ✔ Input Validation
* ✔ Global Exception Handling
* ✔ Clean Architecture (Controller, Service, Repository layers)

---

## 🛠️ Tech Stack

* Java
* Spring Boot
* Spring Data JPA
* MySQL
* Maven

---

## ⚙ Setup Instructions

### 1. Clone the repository

```bash
git clone https://github.com/RUTU-J/Finance-Dasboard-System.git
```

### 2. Create Database

```sql
CREATE DATABASE finance_dashboard;
```

### 3. Configure application.properties

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/finance_dashboard
spring.datasource.username=root
spring.datasource.password=root
```

### 4. Run the application

Run the main class:

```bash
FinanceDashboardSystemApplication.java
```

---

##  Security (Role-Based Access)

This project uses **Role-Based Access Control (RBAC)**.

Roles:

* **VIEWER** → Can only view data
* **ANALYST** → Can create and update
* **ADMIN** → Full access (including delete)

Role is passed via request (header or parameter) to simulate authentication.

---

##  API Endpoints

### 🔹 Financial Records

| Method | Endpoint             | Description           |
| ------ | -------------------- | --------------------- |
| POST   | `/records`           | Create record         |
| GET    | `/records`           | Get all records       |
| GET    | `/records/paginated` | Get paginated records |
| PUT    | `/records/{id}`      | Update record         |
| DELETE | `/records/{id}`      | Delete record         |

---

###  Filtering APIs

| Endpoint                 | Description        |
| ------------------------ | ------------------ |
| `/records?type=INCOME`   | Filter by type     |
| `/records?category=Food` | Filter by category |

---

###  Dashboard APIs

| Endpoint              | Description      |
| --------------------- | ---------------- |
| `/dashboard/income`   | Total Income     |
| `/dashboard/expense`  | Total Expense    |
| `/dashboard/balance`  | Net Balance      |
| `/dashboard/category` | Category Summary |

---

##  Assumptions

* Authentication is simulated using role input
* No external authentication system (JWT/Spring Security) used
* Designed for backend assessment purpose

---

##  Future Enhancements

* Add Spring Security / JWT Authentication
* Add Swagger API Documentation
* Add Unit Testing
* Add Frontend UI

---

##  Author

**Rutuja Khade**

---
