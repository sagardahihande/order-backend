# Order Backend Service

This project implements a scalable backend for food delivery orders.

## Tech Stack
- Java 17
- Spring Boot 3
- MySQL
- In-memory Queue (simulating AWS SQS)

## Setup

1. clone - 

2. Configure DB credentials in `src/main/resources/application.properties`.

3. Build & run:
  mvn spring-boot:run
  

## APIs

- POST /api/orders
- GET /api/orders?page=0&size=10
- GET /api/orders/{id}
- GET /api/orders/{id}/status
- PUT /api/orders/{id}/status


1. Place a new order

POST http://localhost:8080/api/orders
Headers: Content-Type: application/json
Body:

{
  "customerName": "Sagar Dahihande",
  "items": ["Pizza", "Coke"],
  "totalAmount": 450.50
}


2. Get all orders (with pagination)

GET http://localhost:8080/api/orders?page=0&size=10


3. Get a specific order by ID

GET http://localhost:8080/api/orders/1

4. Update order status manually

PUT http://localhost:8080/api/orders/1/status
Headers: Content-Type: application/json
Body:

{
  "status": "PROCESSED"
}
