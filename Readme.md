# 📐 Quantity Measurement Application

A **production-ready Spring Boot REST API** for unit conversion, comparison, and arithmetic across multiple measurement categories — with JWT authentication, Google OAuth2, MySQL persistence, and Railway deployment support.

---

## 📚 Table of Contents

- [Project Overview](#-project-overview)
- [Evolution Summary (UC1–UC18)](#-evolution-summary-uc1uc18)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Features](#-features)
- [API Endpoints](#-api-endpoints)
- [Authentication](#-authentication)
- [DTOs & Request Format](#-dtos--request-format)
- [Supported Units](#-supported-units)
- [Environment Variables](#-environment-variables)
- [Local Setup](#-local-setup)
- [Running with Docker / Railway](#-running-with-docker--railway)
- [Swagger / API Docs](#-swagger--api-docs)
- [Database Schema](#-database-schema)
- [Design Principles](#-design-principles)
- [Concepts Covered](#-concepts-covered)

---

## 🧭 Project Overview

This project demonstrates the **incremental evolution** of a Quantity Measurement system across 18 use cases — from a simple equality check to a fully secured, cloud-deployed Spring Boot application with database persistence, JWT-based auth, and Google OAuth2 login.

---

## 🔄 Evolution Summary (UC1–UC18)

| UC | Title | Key Addition |
|----|-------|--------------|
| UC1 | Feet Equality | Value-based equality for Feet |
| UC2 | Feet & Inches Equality | Inch support, same-unit comparison |
| UC3 | Generic QuantityLength | `LengthUnit` enum, cross-unit equality (DRY) |
| UC4 | Extended Unit Support | Yard, Centimeter; Open/Closed Principle |
| UC5 | Conversion API | `convert(value, source, target)` with precision handling |
| UC6 | Quantity Addition | Same-unit & cross-unit addition |
| UC7 | Target Unit Addition | Result in any specified unit |
| UC8 | Standalone Unit Enum | Unit enum owns its conversion logic |
| UC9 | Weight Measurement | Kilogram, Gram, Pound |
| UC10 | Generic Quantity Class | `Quantity<U extends Measurable>` — compile-time type safety |
| UC11 | Volume Measurement | Litre, Millilitre, Gallon |
| UC12 | Subtraction & Division | Cross-unit subtraction; division ratio |
| UC13 | Centralized Arithmetic | `ArithmeticOperation` enum; DRY enforcement |
| UC14 | Temperature Measurement | Celsius, Fahrenheit, Kelvin; special formulas |
| UC15 | N-Tier Architecture | Controller → Service → Repository layering |
| UC16 | JDBC Persistence | MySQL integration; operation history saved to DB |
| UC17 | Spring Boot Backend | REST API with Spring Boot, Swagger, Actuator |
| UC18 | Google Auth & JWT | Signup/Login/Google OAuth2; JWT-secured endpoints |

---

## 🛠 Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 17 |
| Framework | Spring Boot 3.2.0 |
| Security | Spring Security + JWT (jjwt 0.11.5) |
| OAuth2 | Google ID Token verification (`google-api-client 2.2.0`) |
| Persistence | Spring Data JPA + Hibernate |
| Database | MySQL 8 |
| Validation | Jakarta Bean Validation |
| Docs | SpringDoc OpenAPI / Swagger UI 2.3.0 |
| Monitoring | Spring Boot Actuator |
| Build | Maven (with Maven Wrapper) |
| Deployment | Railway (Nixpacks builder) |
| Utilities | Lombok |

---

## 🗂 Project Structure

```
quantitymeasurement/
│
├── src/main/java/com/app/quantitymeasurement/
│   ├── config/
│   │   ├── JwtAuthenticationFilter.java    # JWT filter (per-request token validation)
│   │   ├── SecurityConfig.java             # CORS, stateless session, route permissions
│   │   └── WebConfig.java                  # Web MVC config
│   │
│   ├── controller/
│   │   ├── AuthController.java             # /api/auth/** (signup, login, google)
│   │   └── QuantityMeasurementController.java  # /api/quantity/**
│   │
│   ├── dto/
│   │   ├── QuantityDTO.java                # value + measurementType + unitName
│   │   ├── ConversionRequestDTO.java       # sourceQuantity + targetUnit
│   │   ├── BinaryOperationRequestDTO.java  # firstQuantity + secondQuantity + resultUnit
│   │   ├── OperationRequestDTO.java        # generic operate wrapper
│   │   └── QuantityOperationResultDTO.java # unified response object
│   │
│   ├── entity/
│   │   ├── QuantityMeasurementEntity.java  # DB entity for operation history
│   │   └── User.java                       # User entity (LOCAL / GOOGLE provider)
│   │
│   ├── enums/
│   │   └── OperationType.java              # CONVERT, COMPARE, ADD, SUBTRACT, MULTIPLY, DIVIDE
│   │
│   ├── exception/
│   │   ├── QuantityMeasurementException.java
│   │   ├── ErrorResponse.java
│   │   └── GlobalExceptionHandler.java     # @ControllerAdvice handler
│   │
│   ├── model/
│   │   └── QuantityModel.java
│   │
│   ├── repository/
│   │   ├── IQuantityMeasurementRepository.java
│   │   └── UserRepository.java
│   │
│   ├── service/
│   │   ├── IQuantityMeasurementService.java
│   │   └── QuantityMeasurementServiceImpl.java
│   │
│   ├── util/
│   │   ├── JwtUtil.java                    # Token generation & validation
│   │   └── QuantityMathHelper.java         # Core conversion & arithmetic engine
│   │
│   └── QuantityMeasurementApplication.java
│
├── src/main/resources/
│   ├── application.properties              # Main config (uses env vars)
│   └── application-prod.properties         # Production overrides
│
├── src/test/java/com/app/quantitymeasurement/
│   ├── controller/
│   │   └── QuantityMeasurementControllerTest.java
│   ├── integration/
│   │   └── QuantityMeasurementIntegrationTest.java
│   └── QuantityMeasurementApplicationTests.java
│
├── railway.json                            # Railway deployment config
├── pom.xml
└── README.md
```

---

## ✨ Features

- ✅ Unit **conversion** across Length, Weight, Volume, and Temperature
- ✅ **Cross-unit equality** comparison
- ✅ Arithmetic: **add, subtract, multiply, divide** with result in any target unit
- ✅ **Operation history** persisted to MySQL with user email tracking
- ✅ **JWT authentication** (24-hour token, HS256)
- ✅ **Google OAuth2** sign-in (ID token verified server-side)
- ✅ Stateless, sessionless security (Spring Security)
- ✅ Global exception handling with structured error responses
- ✅ Swagger UI for interactive API exploration
- ✅ Actuator endpoints for health/metrics monitoring
- ✅ CORS configurable per environment
- ✅ Railway-ready deployment

---

## 🌐 API Endpoints

### Auth — `/api/auth`

| Method | Endpoint | Auth Required | Description |
|--------|----------|---------------|-------------|
| POST | `/api/auth/signup` | ❌ | Register with email & password |
| POST | `/api/auth/login` | ❌ | Login, receive JWT |
| POST | `/api/auth/google` | ❌ | Google OAuth2 login, receive JWT |

### Quantity — `/api/quantity`

| Method | Endpoint | Auth Required | Description |
|--------|----------|---------------|-------------|
| POST | `/api/quantity/convert` | ❌ | Convert a quantity to another unit |
| POST | `/api/quantity/compare` | ❌ | Compare two quantities for equality |
| POST | `/api/quantity/add` | ❌ | Add two quantities |
| POST | `/api/quantity/subtract` | ❌ | Subtract two quantities |
| POST | `/api/quantity/multiply` | ❌ | Multiply two quantities |
| POST | `/api/quantity/divide` | ❌ | Divide two quantities |
| POST | `/api/quantity/operate` | ❌ | Generic operation dispatcher |
| GET | `/api/quantity/history` | ✅ | Get all operation history (JWT required) |
| GET | `/api/quantity/history/operation/{type}` | ✅ | Filter history by operation type |

### Monitoring

| Endpoint | Description |
|----------|-------------|
| `GET /actuator/health` | App health status |
| `GET /actuator/info` | App info |
| `GET /actuator/metrics` | Metrics |
| `GET /swagger-ui.html` | Interactive API docs |
| `GET /v3/api-docs` | OpenAPI JSON spec |

---

## 🔐 Authentication

### Signup
```http
POST /api/auth/signup
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "yourpassword"
}
```

### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "yourpassword"
}
```
**Response:**
```json
{ "token": "<JWT_TOKEN>" }
```

### Google Login
```http
POST /api/auth/google
Content-Type: application/json

{
  "token": "<GOOGLE_ID_TOKEN>"
}
```
**Response:**
```json
{ "token": "<JWT_TOKEN>" }
```

### Using JWT on Protected Routes
```http
GET /api/quantity/history
Authorization: Bearer <JWT_TOKEN>
```

> ⚠️ JWT tokens expire after **24 hours** (`jwt.expiration-ms=86400000`).

---

## 📦 DTOs & Request Format

### `QuantityDTO`
```json
{
  "value": 1.0,
  "measurementType": "LENGTH",
  "unitName": "FOOT"
}
```

### Convert Request
```json
{
  "sourceQuantity": {
    "value": 1.0,
    "measurementType": "LENGTH",
    "unitName": "FOOT"
  },
  "targetUnit": "INCH"
}
```

### Binary Operation Request (add / subtract / multiply / divide / compare)
```json
{
  "firstQuantity": {
    "value": 1.0,
    "measurementType": "LENGTH",
    "unitName": "FOOT"
  },
  "secondQuantity": {
    "value": 12.0,
    "measurementType": "LENGTH",
    "unitName": "INCH"
  },
  "resultUnit": "FOOT"
}
```

### `QuantityOperationResultDTO` (Response)
```json
{
  "resultValue": 2.0,
  "resultUnit": "FOOT",
  "measurementType": "LENGTH",
  "comparisonResult": null,
  "operationType": "ADD",
  "successful": true
}
```

---

## 📏 Supported Units

### Length
| Unit | Key |
|------|-----|
| Foot | `FOOT` |
| Inch | `INCH` |
| Yard | `YARD` |
| Centimeter | `CENTIMETER` |

### Weight
| Unit | Key |
|------|-----|
| Kilogram | `KILOGRAM` |
| Gram | `GRAM` |
| Pound | `POUND` |

### Volume
| Unit | Key |
|------|-----|
| Litre | `LITRE` |
| Millilitre | `MILLILITRE` |
| Gallon | `GALLON` |

### Temperature *(conversion & comparison only — arithmetic not supported)*
| Unit | Key |
|------|-----|
| Celsius | `CELSIUS` |
| Fahrenheit | `FAHRENHEIT` |
| Kelvin | `KELVIN` |

**Conversion formulas:**
```
°F = (°C × 9/5) + 32
°C = (°F − 32) × 5/9
K  = °C + 273.15
```

---

## ⚙️ Environment Variables

Set these as environment variables (locally via `.env`, on Railway via the Variables panel):

| Variable | Description | Example |
|----------|-------------|---------|
| `SPRING_DATASOURCE_URL` | JDBC connection string | `jdbc:mysql://host:3306/db?useSSL=true&serverTimezone=UTC` |
| `SPRING_DATASOURCE_USERNAME` | MySQL username | `root` |
| `SPRING_DATASOURCE_PASSWORD` | MySQL password | `yourpassword` |
| `JWT_SECRET` | HS256 signing key (min 32 chars) | `super_secret_key_min_32_characters!!` |
| `GOOGLE_CLIENT_ID` | Google OAuth2 client ID | `xxxx.apps.googleusercontent.com` |
| `CORS_ALLOWED_ORIGIN` | Allowed frontend origin(s), comma-separated | `http://localhost:3000,https://yourapp.vercel.app` |
| `PORT` | Server port (defaults to 8080) | `8080` |

---

## 🚀 Local Setup

### Prerequisites
- Java 17+
- Maven 3.8+ (or use the included `./mvnw`)
- MySQL 8 running locally

### 1. Clone the repository
```bash
git clone <your-repo-url>
cd quantitymeasurement
```

### 2. Configure environment variables

Create a `.env` file in the project root (already in `.gitignore`):
```env
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/QuantityMeasurementDb?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=your_mysql_password
JWT_SECRET=your_secret_key_minimum_32_characters_long
GOOGLE_CLIENT_ID=your_google_client_id.apps.googleusercontent.com
CORS_ALLOWED_ORIGIN=http://localhost:3000
```

### 3. Run the application
```bash
./mvnw spring-boot:run
```

Or build and run the JAR:
```bash
./mvnw clean package -DskipTests
java -jar target/quantitymeasurement-0.0.1-SNAPSHOT.jar
```

### 4. Run tests
```bash
./mvnw test
```

The app will be available at `http://localhost:8080`.

---

## ☁️ Running with Docker / Railway

### Railway Deployment

This project includes a `railway.json` configured for Nixpacks auto-detection:

```json
{
  "$schema": "https://railway.app/railway.schema.json",
  "build": {
    "builder": "NIXPACKS"
  }
}
```

**Steps:**
1. Push your code to GitHub
2. Create a new project on [Railway](https://railway.app)
3. Add a **MySQL** plugin/service
4. Set all required environment variables in the Railway Variables panel
5. Deploy — Railway will auto-build with Maven and start the app

> Railway automatically injects `PORT`. The app reads it via `server.port=${PORT:8080}`.

---

## 📖 Swagger / API Docs

Once running, visit:

- **Swagger UI:** `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON:** `http://localhost:8080/v3/api-docs`

All endpoints are documented and testable directly from the browser.

---

## 🗄 Database Schema

Two tables are auto-created by Hibernate (`spring.jpa.hibernate.ddl-auto=update`):

### `users`
| Column | Type | Notes |
|--------|------|-------|
| `id` | BIGINT (PK) | Auto-increment |
| `email` | VARCHAR (unique) | Normalized to lowercase |
| `password` | VARCHAR | BCrypt hashed; NULL for Google users |
| `provider` | VARCHAR | `LOCAL` or `GOOGLE` |
| `name` | VARCHAR | Populated from Google profile |

### `quantity_measurement_entity`
| Column | Type | Notes |
|--------|------|-------|
| `id` | BIGINT (PK) | Auto-increment |
| `operation_type` | VARCHAR(32) | CONVERT, COMPARE, ADD, etc. |
| `first_operand_value` | DOUBLE | |
| `first_measurement_type` | VARCHAR(32) | LENGTH, WEIGHT, etc. |
| `first_unit` | VARCHAR(32) | FOOT, KILOGRAM, etc. |
| `second_operand_value` | DOUBLE | NULL for unary ops |
| `second_measurement_type` | VARCHAR(32) | |
| `second_unit` | VARCHAR(32) | |
| `result_operand_value` | DOUBLE | |
| `result_measurement_type` | VARCHAR(32) | |
| `result_unit` | VARCHAR(32) | |
| `comparison_result` | BOOLEAN | For COMPARE operations |
| `error_message` | VARCHAR(1000) | Populated on failure |
| `successful` | BOOLEAN | |
| `user_email` | VARCHAR | NULL if unauthenticated |
| `created_at` | DATETIME | Auto-set on insert |
| `updated_at` | DATETIME | Auto-set on update |

---

## 🏗 Design Principles

| Principle | Implementation |
|-----------|---------------|
| **DRY** | `QuantityMathHelper` centralizes all conversion & arithmetic logic |
| **Open/Closed** | Add new units by extending enums — no existing logic changes |
| **Single Responsibility** | Controller → Service → Repository; each layer has one job |
| **Interface Segregation** | `IQuantityMeasurementService` contract separate from implementation |
| **Generics & Type Safety** | `Quantity<U extends Measurable>` prevents cross-category misuse |
| **Defensive Programming** | Null checks, input validation, exception handling at all layers |
| **Stateless Security** | JWT-based; no server-side session state |

---

## 🧠 Concepts Covered

- Object Equality Contract
- DRY & Refactoring
- Enum Usage with Behaviour
- Generics and Type Safety
- Defensive Programming
- Floating-point Precision Handling (`EPSILON` comparisons)
- N-Tier Architecture (Controller / Service / Repository)
- JDBC & JPA Persistence
- Spring Security (stateless, JWT)
- Google OAuth2 (server-side ID token verification)
- REST API Design
- Global Exception Handling
- Swagger / OpenAPI Documentation
- Spring Boot Actuator
- Cloud Deployment (Railway)

---