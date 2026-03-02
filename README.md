# 🌍 Travel App

A modern, enterprise-grade **Spring Boot REST API** for travel route planning and transportation management. This case study project demonstrates best practices in building scalable microservices with advanced route optimization algorithms.

---

## 📋 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Getting Started](#-getting-started)
- [API Endpoints](#-api-endpoints)
- [Architecture & Design Patterns](#-architecture--design-patterns)
- [Caching Strategy](#-caching-strategy)
- [Configuration](#-configuration)
- [Docker Deployment](#-docker-deployment)
- [Testing](#-testing)
- [Contributing](#-contributing)

---

## ✨ Features

### 1. **Location Management**
- 📍 Create, retrieve, update, and delete travel locations
- Automatic location code normalization (uppercase conversion)
- Unique constraint validation on location codes
- Support for location details: name, country, city
- RESTful endpoints with proper HTTP status codes

### 2. **Transportation Management**
- 🚌 Manage multiple transportation types:
  - **FLIGHT** ✈️
  - **TRAIN** 🚂
  - **BUS** 🚌
  - **FERRY** ⛴️
  - **CAR** 🚗
- Define operating days (supports all 7 days of the week)
- Track origin and destination locations
- Real-time updates and deletions

### 3. **Intelligent Route Search**
- 🗺️ Multi-leg route planning with advanced algorithm
- Search routes between any two locations on a specific date
- Supports up to **3-segment journeys**:
  - Direct routes
  - Ground transportation → Flight
  - Flight → Ground transportation
  - Ground transportation → Flight → Ground transportation
- **Operating day validation** - only includes services operating on the requested date
- **Redis caching** for high-performance queries

### 4. **Enterprise Features**
- 🔐 Spring Security integration
- 📊 OpenAPI/Swagger documentation
- 📈 Spring Boot Actuator metrics
- 🔄 Transaction management with JPA
- 🚀 High-performance caching with Redis
- 📝 Comprehensive error handling

---

## 🛠 Tech Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| **Language** | Java | 17 LTS |
| **Framework** | Spring Boot | 3.3.3 |
| **Build Tool** | Maven | 3.9.8 |
| **Database** | PostgreSQL | 16 |
| **Cache** | Redis | 7 |
| **ORM** | Spring Data JPA | Included |
| **Security** | Spring Security | Included |
| **Documentation** | Swagger/OpenAPI | 2.6.0 |
| **Containerization** | Docker | Latest |

---

## 📁 Project Structure

```
travel-app/
├── src/
│   ├── main/
│   │   ├── java/com/seyhmus/travel/
│   │   │   ├── TravelApplication.java          # Main application entry point
│   │   │   │
│   │   │   ├── common/
│   │   │   │   ├── PingController.java         # Health check endpoint
│   │   │   │   └── exception/
│   │   │   │       ├── ApiError.java           # Unified error response
│   │   │   │       ├── ConflictException.java  # 409 Conflict
│   │   │   │       ├── ResourceNotFoundException.java  # 404 Not Found
│   │   │   │       └── GlobalExceptionHandler.java    # Global error handling
│   │   │   │
│   │   │   ├── config/
│   │   │   │   ├── OpenApiConfig.java          # Swagger/OpenAPI setup
│   │   │   │   ├── RedisCacheConfig.java       # Redis caching configuration
│   │   │   │   └── SecurityConfig.java         # Spring Security configuration
│   │   │   │
│   │   │   ├── location/
│   │   │   │   ├── LocationController.java     # REST endpoints
│   │   │   │   ├── LocationService.java        # Business logic
│   │   │   │   ├── LocationEntity.java         # JPA entity
│   │   │   │   ├── LocationRepository.java     # Data access layer
│   │   │   │   └── dto/
│   │   │   │       ├── LocationCreateRequest.java
│   │   │   │       ├── LocationResponse.java
│   │   │   │       └── LocationUpdateRequest.java
│   │   │   │
│   │   │   ├── transportation/
│   │   │   │   ├── TransportationController.java
│   │   │   │   ├── TransportationService.java
│   │   │   │   ├── TransportationEntity.java
│   │   │   │   ├── TransportationType.java     # Enum: FLIGHT, BUS, TRAIN, FERRY, CAR
│   │   │   │   ├── TransportationRepository.java
│   │   │   │   └── dto/
│   │   │   │       ├── TransportationCreateRequest.java
│   │   │   │       ├── TransportationResponse.java
│   │   │   │       └── TransportationUpdateRequest.java
│   │   │   │
│   │   │   └── route/
│   │   │       ├── RouteController.java        # Route search endpoint
│   │   │       ├── RouteService.java           # Route algorithm logic
│   │   │       └── dto/
│   │   │           ├── LocationShortResponse.java
│   │   │           ├── RouteResponse.java
│   │   │           └── RouteSegmentResponse.java
│   │   │
│   │   └── resources/
│   │       └── application.yml                 # Application configuration
│   │
│   └── test/
│       └── java/com/seyhmus/travel/
│           └── route/
│               └── RouteServiceTest.java       # Unit tests
│
├── docker-compose.yml                          # Multi-container setup
├── Dockerfile                                  # Application container
├── pom.xml                                     # Maven dependencies
└── README.md                                   # This file
```

---

## 🚀 Getting Started

### Prerequisites

- **Java 17+** installed
- **Maven 3.6+** installed
- **Docker & Docker Compose** (optional, for containerized deployment)
- **PostgreSQL 16** (if running locally without Docker)
- **Redis 7** (if running locally without Docker)

### Option 1: Docker Compose (Recommended)

The easiest way to run the entire stack:

```bash
# Clone the repository
git clone <repository-url>
cd travel-app

# Build and start all services
docker-compose up -d

# Check logs
docker-compose logs -f app

# Stop services
docker-compose down
```

**Services will be available at:**
- **API**: `http://localhost:8080`
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **API Docs**: `http://localhost:8080/api-docs`

### Option 2: Local Development

#### 1. Start Dependencies

```bash
# Start PostgreSQL
docker run -d \
  --name travel-postgres \
  -e POSTGRES_DB=travel \
  -e POSTGRES_USER=travel \
  -e POSTGRES_PASSWORD=travel \
  -p 5433:5432 \
  postgres:16

# Start Redis
docker run -d \
  --name travel-redis \
  -p 6379:6379 \
  redis:7
```

#### 2. Build and Run Application

```bash
# Build the project
mvn clean package -DskipTests

# Run the application
mvn spring-boot:run
```

#### 3. Access the Application

- **API Base URL**: `http://localhost:8080`
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **Health Check**: `http://localhost:8080/api/ping`

---

## 📡 API Endpoints

### Health Check
```http
GET /api/ping
```
Simple health check endpoint.

---

### 📍 **Locations API**

#### Create Location
```http
POST /api/locations
Content-Type: application/json

{
  "name": "Istanbul",
  "country": "Turkey",
  "city": "Istanbul",
  "locationCode": "IST"
}
```

#### Get All Locations
```http
GET /api/locations
```
Returns a list of all locations.

#### Get Single Location
```http
GET /api/locations/{id}
```

#### Update Location
```http
PUT /api/locations/{id}
Content-Type: application/json

{
  "name": "Istanbul",
  "country": "Turkey",
  "city": "Istanbul",
  "locationCode": "IST"
}
```

#### Delete Location
```http
DELETE /api/locations/{id}
```

---

### 🚌 **Transportations API**

#### Create Transportation
```http
POST /api/transportations
Content-Type: application/json

{
  "type": "FLIGHT",
  "originLocationId": 1,
  "destinationLocationId": 2,
  "operatingDays": [1, 2, 3, 4, 5]
}
```

**Transportation Types:**
- `FLIGHT` - Aircraft
- `TRAIN` - Railway
- `BUS` - Road bus
- `FERRY` - Water ferry
- `CAR` - Private vehicle

**Operating Days:** 1-7 (Monday-Sunday)

#### Get All Transportations
```http
GET /api/transportations
```

#### Get Single Transportation
```http
GET /api/transportations/{id}
```

#### Update Transportation
```http
PUT /api/transportations/{id}
Content-Type: application/json

{
  "type": "FLIGHT",
  "originLocationId": 1,
  "destinationLocationId": 2,
  "operatingDays": [1, 2, 3, 4, 5, 6]
}
```

#### Delete Transportation
```http
DELETE /api/transportations/{id}
```

---

### 🗺️ **Routes API** (Intelligent Route Search)

#### Search Routes
```http
GET /api/routes?originId=1&destinationId=2&date=2024-03-15
```

**Query Parameters:**
- `originId` (required): Starting location ID
- `destinationId` (required): Destination location ID
- `date` (required): Travel date (format: `YYYY-MM-DD`)

**Response Example:**
```json
[
  {
    "segments": [
      {
        "id": 1,
        "type": "FLIGHT",
        "origin": {
          "id": 1,
          "name": "Istanbul",
          "country": "Turkey",
          "city": "Istanbul",
          "locationCode": "IST"
        },
        "destination": {
          "id": 2,
          "name": "Ankara",
          "country": "Turkey",
          "city": "Ankara",
          "locationCode": "ANK"
        }
      }
    ]
  },
  {
    "segments": [
      {
        "id": 3,
        "type": "BUS",
        "origin": {
          "id": 1,
          "name": "Istanbul",
          "country": "Turkey",
          "city": "Istanbul",
          "locationCode": "IST"
        },
        "destination": {
          "id": 3,
          "name": "Bursa",
          "country": "Turkey",
          "city": "Bursa",
          "locationCode": "BUR"
        }
      },
      {
        "id": 2,
        "type": "FLIGHT",
        "origin": {
          "id": 3,
          "name": "Bursa",
          "country": "Turkey",
          "city": "Bursa",
          "locationCode": "BUR"
        },
        "destination": {
          "id": 2,
          "name": "Ankara",
          "country": "Turkey",
          "city": "Ankara",
          "locationCode": "ANK"
        }
      }
    ]
  }
]
```

---

## 🏗️ Architecture & Design Patterns

### Design Patterns Used

1. **Repository Pattern**
   - Abstracts data access logic
   - Enables easy testing with mocks
   - Provides clean separation of concerns

2. **Service Layer Pattern**
   - Contains all business logic
   - Handles validation and error management
   - Manages transactions

3. **DTO (Data Transfer Object) Pattern**
   - Separates API contracts from internal models
   - Prevents over-sharing of data
   - Decouples API from database schema

4. **Factory Pattern** (in DTOs)
   - Converts entities to responses
   - Handles data transformation

5. **Global Exception Handler**
   - Centralized error handling
   - Consistent error responses
   - Maintains clean controller code

### Layered Architecture

```
┌─────────────────────────────────┐
│      REST Controllers           │
├─────────────────────────────────┤
│      Services (Business Logic)  │
├─────────────────────────────────┤
│      Repositories (Data Access) │
├─────────────────────────────────┤
│      Database (PostgreSQL)      │
└─────────────────────────────────┘
```

---

## 🔐 Caching Strategy

The application uses **Redis** for intelligent caching:

### Route Caching
- **Cache Name**: `routes`
- **Cache Key**: `{originId}:{destinationId}:{date}`
- **TTL**: Spring default (configurable)
- **Invalidation**: Automatic when locations or transportations are modified

### Cache Invalidation
Both location and transportation modifications trigger `@CacheEvict` to ensure fresh route data:
- Location creation/update/deletion
- Transportation creation/update/deletion

This ensures the route search always returns accurate information.

---

## ⚙️ Configuration

### Application Properties (`application.yml`)

```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:postgresql://localhost:5433/travel
    username: travel
    password: travel

  jpa:
    hibernate:
      ddl-auto: create-drop  # Options: create-drop, update, validate
    open-in-view: false      # Prevent lazy loading issues

  data:
    redis:
      host: localhost
      port: 6379

  cache:
    type: none  # Set to 'redis' to enable caching

logging:
  level:
    org.springframework.boot.context.config: debug
    org.hibernate.tool.schema: debug

springdoc:
  api-docs:
    path: /api-docs
  swagger-ui:
    path: /swagger-ui
```

### Environment Variables (Docker)

```env
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/travel
SPRING_DATASOURCE_USERNAME=travel
SPRING_DATASOURCE_PASSWORD=travel
SPRING_DATA_REDIS_HOST=redis
SPRING_DATA_REDIS_PORT=6379
SPRING_JPA_HIBERNATE_DDL_AUTO=update
```

---

## 🐳 Docker Deployment

### Build Docker Image

```bash
docker build -t travel-app:latest .
```

### Run with Docker Compose

```bash
docker-compose up -d
```

### View Logs

```bash
docker-compose logs -f app
```

### Stop Services

```bash
docker-compose down
```

### Cleanup (Remove volumes)

```bash
docker-compose down -v
```

---

## 🧪 Testing

### Run All Tests

```bash
mvn test
```

### Run with Coverage

```bash
mvn test jacoco:report
```

### Run Specific Test Class

```bash
mvn test -Dtest=RouteServiceTest
```

### Current Test Coverage

- `RouteServiceTest.java` - Comprehensive unit tests for route algorithm
- Tests cover edge cases and various route combinations

---

## 🔍 API Documentation

Once the application is running, access interactive API documentation:

### Swagger UI
```
http://localhost:8080/swagger-ui.html
```

### OpenAPI JSON
```
http://localhost:8080/api-docs
```

### OpenAPI YAML
```
http://localhost:8080/api-docs.yaml
```

---

## 📊 Monitoring & Actuator

Spring Boot Actuator provides production-ready endpoints:

```bash
# Health check
curl http://localhost:8080/actuator/health

# Application metrics
curl http://localhost:8080/actuator/metrics

# Environment details
curl http://localhost:8080/actuator/env
```

---

## Performance Considerations

1. **Database Indexing**
   - Location codes are indexed for unique constraint
   - Foreign keys are indexed automatically

2. **Query Optimization**
   - `@Transactional(readOnly = true)` for read operations
   - JPA lazy loading disabled to prevent N+1 queries

3. **Caching**
   - Route queries are cached per `originId:destinationId:date`
   - Cache invalidated on data modifications

4. **Connection Pooling**
   - Spring Boot's HikariCP handles connection pooling

---

## 🔐 Security Features

- **Spring Security** integration
- **Input validation** using Jakarta Validation annotations
- **Exception handling** with sanitized error messages
- **CORS** configuration (can be enabled as needed)
- **Database credentials** configurable via environment

---

## 📝 Error Handling

### HTTP Status Codes

| Code | Scenario | Example |
|------|----------|---------|
| 200 | Successful GET | Fetch location details |
| 201 | Resource created | Create new location |
| 204 | No content | Delete successful |
| 400 | Bad request | Invalid input data |
| 404 | Not found | Location ID doesn't exist |
| 409 | Conflict | Duplicate location code |
| 500 | Server error | Unhandled exceptions |

### Error Response Format

```json
{
  "timestamp": "2024-03-15T10:30:00Z",
  "status": 409,
  "error": "Conflict",
  "message": "Location with locationCode 'IST' already exists",
  "path": "/api/locations"
}
```

---

## 🤝 Contributing

### Code Style
- Follow Java conventions
- Use meaningful variable names
- Add JavaDoc for public methods
- Keep methods focused (single responsibility)

### Testing Requirements
- Write tests for new features
- Maintain or improve code coverage
- Test edge cases

### Commit Messages
```
feat: Add new feature description
fix: Fix specific bug
docs: Update documentation
test: Add tests
refactor: Improve code structure
```

---

## 📄 License

This project is a case study for educational purposes.

---

## 👨‍💻 Author

**Şeyhmus Öztürk**

---

## 🚀 Future Enhancements

- [ ] Add authentication with JWT tokens
- [ ] Implement passenger booking system
- [ ] Add cost optimization algorithm
- [ ] Multi-language support
- [ ] Real-time availability updates
- [ ] Machine learning for demand prediction
- [ ] API rate limiting
- [ ] Advanced search filters (price, duration, etc.)
- [ ] Payment integration
- [ ] User review and rating system

---

## 📞 Support

For issues, questions, or contributions:
1. Check existing documentation
2. Review error messages carefully
3. Check application logs
4. Open an issue with detailed information

---

**Made with ❤️ for efficient travel planning**
