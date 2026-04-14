# QMA Microservices

The original monolith has been split into **4 independent Spring Boot microservices**:

| Service | Port | Responsibility |
|---|---|---|
| `auth-service` | 8081 | User signup, login, Google OAuth, JWT issuance |
| `conversion-service` | 8082 | Unit conversion & comparison (no DB) |
| `arithmetic-service` | 8083 | Add, subtract, multiply, divide quantities (no DB) |
| `history-service` | 8084 | Persist & retrieve operation history |

---

## Architecture

```
Client
  ├── POST /api/auth/**           → auth-service:8081
  ├── POST /api/conversion/**     → conversion-service:8082
  ├── POST /api/arithmetic/**     → arithmetic-service:8083
  └── GET  /api/history/**        → history-service:8084  (JWT required)
```

All services share the same **JWT secret** so tokens issued by auth-service
are accepted by all other services.  
`auth-service` and `history-service` connect to **MySQL**.  
`conversion-service` and `arithmetic-service` are **stateless** (no DB needed).

---

## Prerequisites

- Java 17+
- Maven 3.8+
- Docker & Docker Compose (for the easy path)

---

## Option A — Run with Docker Compose (Recommended)

### Step 1 — Set your Google Client ID (optional)
```bash
export GOOGLE_CLIENT_ID=your-google-client-id
```
Skip this if you don't use Google login.

### Step 2 — Start everything
```bash
cd microservices/
docker-compose up --build
```

Wait ~2 minutes for Maven builds. All 4 services + MySQL will start.

### Step 3 — Verify services are up
```bash
curl http://localhost:8081/actuator/health   # auth-service
curl http://localhost:8082/actuator/health   # conversion-service
curl http://localhost:8083/actuator/health   # arithmetic-service
curl http://localhost:8084/actuator/health   # history-service
```

### Step 4 — Stop
```bash
docker-compose down
# To also delete the MySQL volume:
docker-compose down -v
```

---

## Option B — Run Locally with Maven (no Docker)

You need MySQL running locally first:

```sql
CREATE DATABASE quantitydb;
CREATE USER 'qmauser'@'localhost' IDENTIFIED BY 'qmapassword';
GRANT ALL ON quantitydb.* TO 'qmauser'@'localhost';
```

Then open **4 terminals**, one per service:

### Terminal 1 — auth-service (port 8081)
```bash
cd auth-service/
export SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/quantitydb
export SPRING_DATASOURCE_USERNAME=qmauser
export SPRING_DATASOURCE_PASSWORD=qmapassword
export JWT_SECRET="your-very-long-secret-key-at-least-32-chars-long!"
export GOOGLE_CLIENT_ID=your-google-client-id
export CORS_ALLOWED_ORIGIN=http://localhost:3000
mvn spring-boot:run
```

### Terminal 2 — conversion-service (port 8082)
```bash
cd conversion-service/
export JWT_SECRET="your-very-long-secret-key-at-least-32-chars-long!"
export CORS_ALLOWED_ORIGIN=http://localhost:3000
mvn spring-boot:run
```

### Terminal 3 — arithmetic-service (port 8083)
```bash
cd arithmetic-service/
export JWT_SECRET="your-very-long-secret-key-at-least-32-chars-long!"
export CORS_ALLOWED_ORIGIN=http://localhost:3000
mvn spring-boot:run
```

### Terminal 4 — history-service (port 8084)
```bash
cd history-service/
export SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/quantitydb
export SPRING_DATASOURCE_USERNAME=qmauser
export SPRING_DATASOURCE_PASSWORD=qmapassword
export JWT_SECRET="your-very-long-secret-key-at-least-32-chars-long!"
export CORS_ALLOWED_ORIGIN=http://localhost:3000
mvn spring-boot:run
```

---

## API Reference

### auth-service (port 8081)

```bash
# Signup
curl -X POST http://localhost:8081/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"secret123"}'

# Login — returns {"token":"..."}
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"secret123"}'
```

### conversion-service (port 8082)

```bash
# Convert 100 cm to meters
curl -X POST http://localhost:8082/api/conversion/convert \
  -H "Content-Type: application/json" \
  -d '{"sourceQuantity":{"value":100,"measurementType":"LENGTH","unitName":"CENTIMETER"},"targetUnit":"METER"}'

# Compare two quantities
curl -X POST http://localhost:8082/api/conversion/compare \
  -H "Content-Type: application/json" \
  -d '{"firstQuantity":{"value":100,"measurementType":"LENGTH","unitName":"CENTIMETER"},"secondQuantity":{"value":1,"measurementType":"LENGTH","unitName":"METER"}}'
```

### arithmetic-service (port 8083)

```bash
# Add 500g + 1kg  →  result in KG
curl -X POST http://localhost:8083/api/arithmetic/add \
  -H "Content-Type: application/json" \
  -d '{"firstQuantity":{"value":500,"measurementType":"WEIGHT","unitName":"GRAM"},"secondQuantity":{"value":1,"measurementType":"WEIGHT","unitName":"KILOGRAM"},"resultUnit":"KILOGRAM"}'

# Other operations: /subtract  /multiply  /divide
```

### history-service (port 8084)

```bash
# Save a record (called internally by other services or your frontend)
curl -X POST http://localhost:8084/api/history/save \
  -H "Content-Type: application/json" \
  -d '{"operationType":"CONVERT","firstOperandValue":100,"firstMeasurementType":"LENGTH","firstUnit":"CENTIMETER","resultOperandValue":1.0,"resultMeasurementType":"LENGTH","resultUnit":"METER","successful":true,"userEmail":"test@example.com"}'

# Get your history (requires JWT)
TOKEN="paste-your-token-here"
curl http://localhost:8084/api/history \
  -H "Authorization: Bearer $TOKEN"

# Filter by operation type
curl "http://localhost:8084/api/history/operation/CONVERT" \
  -H "Authorization: Bearer $TOKEN"
```

---

## Swagger UI

Each service exposes Swagger:
- http://localhost:8081/swagger-ui.html
- http://localhost:8082/swagger-ui.html
- http://localhost:8083/swagger-ui.html
- http://localhost:8084/swagger-ui.html

---

## Environment Variables Summary

| Variable | Used By | Description |
|---|---|---|
| `SPRING_DATASOURCE_URL` | auth, history | MySQL JDBC URL |
| `SPRING_DATASOURCE_USERNAME` | auth, history | MySQL username |
| `SPRING_DATASOURCE_PASSWORD` | auth, history | MySQL password |
| `JWT_SECRET` | all 4 | Must be identical across all services |
| `GOOGLE_CLIENT_ID` | auth | Google OAuth client ID |
| `CORS_ALLOWED_ORIGIN` | all 4 | Comma-separated allowed origins |
| `PORT` | all 4 | Server port (defaults: 8081-8084) |
