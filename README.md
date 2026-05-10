# Smart AML Backend

**Modular Monolith Architecture | Java 21 | Spring Boot 3.3.2**

A production-ready Anti-Money Laundering (AML) compliance platform with enforced module boundaries, ready to scale to microservices.

## 🏗️ Architecture

- **Modular Monolith**: One Git repo, one deployable JAR
- **11 Maven Modules**: Each with clear boundaries and APIs
- **Tenant-Isolated**: Multi-tenant at database level
- **Hybrid Storage**: Local-first with async S3 sync
- **OCR Integration**: Tesseract 4 for document scanning
- **UN Sanctions**: Real-time sanctions screening

## 📦 Modules

```
smart-aml-shared          ← Pure Java, no Spring (enums, responses, events)
smart-aml-infrastructure  ← Technical plumbing (security, storage, email)
smart-aml-auth            ← JWT authentication & sessions
smart-aml-user            ← User management & RBAC
smart-aml-customer        ← Customer onboarding & KYC
smart-aml-screening       ← Sanctions screening & risk assessment
smart-aml-analytics       ← Dashboard & analytics
smart-aml-reporting       ← GoAML & custom reports
smart-aml-audit           ← Audit trail & compliance logging
smart-aml-support         ← Support tickets
smart-aml-app             ← Spring Boot assembler (main application)
```

## 🚀 Quick Start

### Prerequisites
- Java 21+
- Maven 3.8+
- PostgreSQL 16+
- Redis 7+
- Docker & Docker Compose (optional)

### Local Setup

```bash
# Clone
git clone https://github.com/abutaleb6/smart-aml-backend.git
cd smart-aml-backend

# Copy environment
cp smart-aml-app/.env.example smart-aml-app/.env

# Start services (Docker)
cd smart-aml-app
docker-compose up -d

# Build & run
cd ..
mvn clean install
mvn -pl smart-aml-app spring-boot:run
```

Application runs on `http://localhost:8080`

## 🔑 Default Credentials

**Super Admin** (auto-created at first migration):
- Email: `superadmin@smartaml.com`
- Password: `Admin@123!` (Change immediately!)

## 📚 API Documentation

Swagger UI: `http://localhost:8080/swagger-ui.html`
OpenAPI Spec: `http://localhost:8080/v3/api-docs`

## 🔄 Module Dependencies

```
smart-aml-app
├── smart-aml-auth
├── smart-aml-user
├── smart-aml-customer
├── smart-aml-screening
├── smart-aml-analytics
├── smart-aml-reporting
├── smart-aml-audit
├── smart-aml-support
└── smart-aml-infrastructure
    └── smart-aml-shared
```

**Rule**: Modules only call other modules via **public API interfaces** in `api/` package.

## 🧪 Testing

```bash
# Run all tests
mvn test

# Run specific module tests
mvn test -pl smart-aml-auth
```

## 🐳 Docker Deployment

```bash
cd smart-aml-app
docker-compose up -d
```

## ✅ Key Features

✅ **Authentication** - JWT (HS512) with refresh tokens, device tracking, rate limiting
✅ **User Management** - RBAC with 27 fine-grained permissions, approval workflows
✅ **Customer Onboarding** - 9-step wizards, OCR for documents, batch import
✅ **Screening** - UN Sanctions List, Jaro-Winkler matching, risk scoring
✅ **Storage** - Hybrid (local + async S3), automatic retry
✅ **Email** - Thymeleaf templates, 5 templates with purple branding
✅ **Analytics** - Dashboard, risk distribution, screening stats (Redis cached)
✅ **Audit Trail** - All actions logged, searchable, exportable
✅ **Reports** - GoAML format, custom documents, expiry tracking

## 📋 Database

Flyway automatically manages migrations from all modules.

```bash
# View migrations
ls smart-aml-*/src/main/resources/db/migration/
```

## 🔮 Future Microservice Extraction

When ready to extract `smart-aml-screening` to a separate service:
1. Copy module to new Spring Boot project
2. Replace `ScreeningApi` with Feign client
3. Replace `ApplicationEvent` with Kafka topics
4. Everything else remains identical

## 📝 License

Proprietary - Smart AML

---

**Built with ❤️ using modular monolith architecture principles**