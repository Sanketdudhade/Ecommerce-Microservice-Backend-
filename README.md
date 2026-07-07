# 🛒 E-Commerce Backend Microservices

A scalable and production-oriented **E-Commerce Backend** built using **Spring Boot Microservices**. The project follows a distributed architecture with independent services for authentication, product management, cart management, order processing, payment integration, and future notification support.

---

# 🚀 Features

* User Registration & Login using JWT Authentication
* Role-Based Authorization (Admin / Customer)
* Product & Category Management
* Shopping Cart Management
* Order Management
* Razorpay Payment Integration
* Service Discovery using Eureka Server
* API Gateway
* MySQL Database
* Centralized Exception Handling
* RESTful APIs
* DTO Mapping using MapStruct
* Validation using Jakarta Validation
* Lombok for Boilerplate Reduction

---

# 🏗️ Microservices Architecture

```text
                    React Frontend
                           │
                           ▼
                     API Gateway
                           │
        ┌───────────┬────────────┬─────────────┬─────────────┐
        ▼           ▼            ▼             ▼             ▼
  Auth Service  Product Service Cart Service Order Service Payment Service
        │           │            │             │             │
        └───────────┴────────────┴─────────────┴─────────────┘
                           │
                           ▼
                      Eureka Server
```

---

# 📦 Services

## 1. Auth Service

Responsible for:

* User Registration
* User Login
* JWT Token Generation
* JWT Validation
* Role Management
* Password Encryption (BCrypt)

### Technologies

* Spring Security
* JWT
* MySQL

---

## 2. Product Service

Responsible for:

* Product CRUD
* Category CRUD
* Search Products
* Product Availability
* Stock Management

---

## 3. Cart Service

Responsible for:

* Add Product to Cart
* Update Quantity
* Remove Product
* View Cart
* Calculate Total Price

---

## 4. Order Service

Responsible for:

* Create Order
* Order History
* Order Status
* Order Details
* Order Validation

---

## 5. Payment Service

Responsible for:

* Create Razorpay Order
* Verify Payment
* Refund Payment
* Cancel Payment
* Payment History

---

# 🛠 Technology Stack

## Backend

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* Spring Validation
* Spring Cloud
* Spring Cloud Gateway
* Eureka Discovery Server
* MapStruct
* Lombok

---

## Database

* MySQL

---

## Payment Gateway

* Razorpay

---

## Build Tool

* Maven

---

## API Testing

* Postman

---

## Version Control

* Git
* GitHub

---

# 📁 Project Structure

```text
Ecommerce-Backend
│
├── Eureka-Server
├── API-Gateway
├── Auth-Service
├── Product-Service
├── Cart-Service
├── Order-Service
├── Payment-Service
├── Notification-Service (Upcoming)
└── README.md
```

---

# ⚙️ Prerequisites

* Java 21
* Maven 3.9+
* MySQL 8+
* Git
* IntelliJ IDEA / VS Code

---

# 🔧 Installation

## Clone Repository

```bash
git clone https://github.com/your-username/ecommerce-backend.git
```

---



## Configure application.properties

Update:

* Database URL
* Username
* Password
* JWT Secret
* Razorpay Keys
* Eureka Server URL

---

## Start Services

Run in the following order:

1. Eureka Server
2. API Gateway
3. Auth Service
4. Product Service
5. Cart Service
6. Order Service
7. Payment Service
8. Notification Service (Future)

---

# 🔐 Authentication

The project uses **JWT Authentication**.

### Login Flow

```text
Login

↓

JWT Generated

↓

Frontend Stores Token

↓

Authorization: Bearer <token>

↓

API Gateway

↓

Requested Microservice
```

---

# 💳 Payment Flow

```text
Create Payment

↓

Razorpay Order

↓

React Checkout

↓

Payment Success

↓

Verify Payment

↓

Database Updated
```

---

# 📚 REST APIs

## Authentication

* POST `/api/auth/register`
* POST `/api/auth/login`

---

## Products

* GET `/api/products`
* GET `/api/products/{id}`
* POST `/api/products`
* PUT `/api/products/{id}`
* DELETE `/api/products/{id}`

---

## Categories

* GET `/api/categories`
* POST `/api/categories`
* PUT `/api/categories/{id}`
* DELETE `/api/categories/{id}`

---

## Cart

* POST `/api/cart/add`
* PUT `/api/cart/update`
* DELETE `/api/cart/remove/{productId}`
* GET `/api/cart`

---

## Orders

* POST `/api/orders`
* GET `/api/orders`
* GET `/api/orders/{id}`

---

## Payments

* POST `/api/payments/create`
* POST `/api/payments/verify`
* POST `/api/payments/refund`
* PUT `/api/payments/cancel/{paymentId}`
* GET `/api/payments/{paymentId}`
* GET `/api/payments/history`

---

# 🔒 Security

* JWT Authentication
* BCrypt Password Encryption
* Stateless Authentication
* Role-Based Authorization
* Request Validation
* Global Exception Handling

---

# 📈 Future Enhancements

* Notification Service
* Docker
* Docker Compose
* Redis Caching
* Kafka/RabbitMQ
* Elasticsearch
* Prometheus & Grafana Monitoring
* CI/CD using Jenkins or GitHub Actions
* Kubernetes Deployment
* AWS Deployment
* Product Reviews & Ratings
* Wishlist
* Coupons & Discounts
* Inventory Alerts

---

# 👨‍💻 Author

**Sanket Dudhade**

Java Full Stack Developer

---

# 📄 License

This project is developed for educational and portfolio purposes.
