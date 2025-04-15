# Tracking Number Generator Service

A Spring Boot-based REST API that generates unique tracking numbers using shipment and customer data.  
It supports Redis caching and persists generated data into a relational database.

---

## Features

- Generate unique tracking number (max 16 characters)
- Redis caching for performance
- Save tracking data into database
- MapStruct-based DTO to Entity mapping
- Swagger/OpenAPI documentation

---

## Tech Stack

- Java 17+
- Spring Boot 3+
- Redis
- MySQL
- Hibernate + Spring Data JPA
- MapStruct
- Lombok

---

## Prerequisites

- Java 17
- Maven
- Docker & Docker Compose
- Redis & MySQL
- Postman / curl for testing

---

## Setup Instructions

### 1. Clone the repository

```bash
git clone https://github.com/artdengun/generate-tracking-app.git
cd tracking-number-service
```

### 2. Start Redis and Database (via Docker)
for this requirement you need to have a docker in local computer. oke the next step you can Edit or use the provided `docker-compose.yml` for `mysql`:
```bash
services:
  mysql:
    image: mysql:latest
    container_name: mysql
    environment:
      MYSQL_DATABASE: shipping
      MYSQL_USER: admin          
      MYSQL_PASSWORD: admin        
    ports:
      - "3306:3306"
    volumes:
      - mysql-data:/var/lib/mysql 
    networks:
      - mysql-network

volumes:
  mysql-data:

networks:
  mysql-network:
    driver: bridge

```
and for `redis` : 

```bash
version: '3.8'

services:
  redis:
    image: redis:latest
    container_name: redis
    restart: always
    ports:
      - "6379:6379"
    volumes:
      - redis-data:/data
volumes:
  redis-data:
```
and then execute script `mysql` and `redis` with script at below: 
```bash
docker-compose up -d
```
if all docker container running you can go to the next step. 

### 3. Run the application

on the terminal execute : 
```bash
mvn spring-boot:run 
```
and then 
Application runs on: `http://localhost:8080`

---

## REST API

### `GET /next-tracking-number`

**Query Params:**

| Param               | Type      | Example           |
|--------------------|-----------|-------------------|
| origin_country_id  | String    | `MY`              |
| destination_country_id | String | `ID`              |
| weight             | BigDecimal| `1.25`            |
| created_at         | String    | `2025-04-15T12:00:00Z` |
| customer_id        | String    | `123`             |
| customer_name      | String    | `Denis`           |
| customer_slug      | String    | `denis-corp`      |

**Example:**

```bash
curl -X GET "http://localhost:8080/next-tracking-number?origin_country_id=ID&destination_country_id=SG&weight=1.25&created_at=2025-04-15T12:00:00Z&customer_id=123&customer_name=Denis&customer_slug=denis-corp"
```

**Response:**

```json
{
  "tracking_number": "IDSGABC123XYZ456",
  "created_at": "2025-04-15T13:20:49.911Z"
}
```

---

## Swagger UI

To view API documentation:

```
http://localhost:8080/swagger-ui.html
```

> Make sure `springdoc-openapi` is added in your dependencies.

---

## Development Notes

- Redis used for caching tracking number generation
- Uses DTO → Entity mapping via MapStruct
- Tracking history persisted via JPA repository

---
