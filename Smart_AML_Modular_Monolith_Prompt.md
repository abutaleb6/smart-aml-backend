# Smart AML — Modular Monolith Backend Prompt
# VERSION 3.0 — Modular Monolith Architecture (97% Coverage)
# Paste this entire file at the start of your Claude Code session
#
# ARCHITECTURE: Modular Monolith
#   - One Git repository, one deployable JAR
#   - Internally split into Maven sub-modules with enforced boundaries
#   - Modules communicate ONLY through public API interfaces (no direct cross-module repo calls)
#   - Future-ready: each module can be extracted into a microservice with minimal effort
#
# DECISIONS LOCKED:
# ✅ OCR Provider     → Tess4J (Tesseract 4) — open source, offline, no API key
# ✅ Sanctions Data   → UN Consolidated Sanctions List (public XML, nightly sync at 02:00)
# ✅ Email Templates  → HTML via Thymeleaf (5 templates, purple brand design)
# ✅ Environment      → .env.example with all sample values provided
# ✅ Storage          → Hybrid: local-first, async S3 sync, local fallback on download

---

## 1. WHAT IS MODULAR MONOLITH — RULES FOR CLAUDE

```
RULE 1 — MODULE BOUNDARIES (most important rule):
  A module's internal classes (entities, repositories, @Service implementations)
  are PACKAGE-PRIVATE or in an 'internal' sub-package.
  Only classes in the module's 'api' sub-package are public and usable by other modules.

  WRONG (simple monolith):
    // In ScreeningService.java
    @Autowired CustomerRepository customerRepository; // ❌ crosses module boundary

  CORRECT (modular monolith):
    // In ScreeningService.java
    @Autowired CustomerApi customerApi; // ✅ uses public API interface from smart-aml-shared

RULE 2 — DEPENDENCY DIRECTION (one-way only):
  smart-aml-app → ALL modules
  ALL modules → smart-aml-shared
  ALL modules → smart-aml-infrastructure
  NO module → another module's internal classes
  ALLOWED inter-module calls: only via interfaces defined in smart-aml-shared

RULE 3 — DATABASE:
  All modules share ONE PostgreSQL database and ONE DataSource.
  Each module owns its own tables — no other module's service queries another
  module's tables directly. Cross-module data needs go through the API interface.

RULE 4 — EVENTS (for decoupled communication):
  When Module A needs to notify Module B of something without a direct dependency,
  use Spring ApplicationEvent (in-process event bus, no Kafka needed).
  Example: CustomerSubmittedEvent published by customer module,
           consumed by audit module and screening module independently.

RULE 5 — EACH MODULE STRUCTURE:
  aml-{module}/
  ├── src/main/java/com/smartaml/{module}/
  │   ├── api/               ← PUBLIC: interfaces + DTOs other modules can use
  │   │   ├── {Module}Api.java        (interface)
  │   │   └── dto/                   (request/response records — no JPA annotations)
  │   ├── internal/          ← PRIVATE: implementation, entities, repos
  │   │   ├── entity/
  │   │   ├── repository/
  │   │   ├── service/
  │   │   ├── mapper/
  │   │   └── event/
  │   └── web/               ← REST controllers (depends on api/ DTOs only)
  │       └── {Module}Controller.java
  └── src/main/resources/
      └── db/migration/      ← This module's Flyway migrations only
```

---

## 2. MAVEN PARENT + MODULE STRUCTURE

```xml
<!-- ROOT: pom.xml (parent, no src/) -->
<groupId>com.smartaml</groupId>
<artifactId>smart-aml</artifactId>
<version>1.0.0-SNAPSHOT</version>
<packaging>pom</packaging>

<modules>
  <module>smart-aml-shared</module>
  <module>smart-aml-infrastructure</module>
  <module>smart-aml-auth</module>
  <module>smart-aml-user</module>
  <module>smart-aml-customer</module>
  <module>smart-aml-screening</module>
  <module>smart-aml-analytics</module>
  <module>smart-aml-reporting</module>
  <module>smart-aml-audit</module>
  <module>smart-aml-support</module>
  <module>smart-aml-app</module>
</modules>

<properties>
  <java.version>21</java.version>
  <spring-boot.version>3.3.2</spring-boot.version>
  <mapstruct.version>1.5.5.Final</mapstruct.version>
  <lombok.version>1.18.32</lombok.version>
  <jjwt.version>0.12.5</jjwt.version>
</properties>

<!-- dependencyManagement: declare ALL versions here, child modules inherit -->
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-dependencies</artifactId>
      <version>${spring-boot.version}</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
    <!-- Internal modules -->
    <dependency><groupId>com.smartaml</groupId>
      <artifactId>smart-smart-aml-shared</artifactId><version>${project.version}</version></dependency>
    <dependency><groupId>com.smartaml</groupId>
      <artifactId>smart-smart-aml-infrastructure</artifactId><version>${project.version}</version></dependency>
    <dependency><groupId>com.smartaml</groupId>
      <artifactId>smart-smart-aml-auth</artifactId><version>${project.version}</version></dependency>
    <dependency><groupId>com.smartaml</groupId>
      <artifactId>smart-smart-aml-user</artifactId><version>${project.version}</version></dependency>
    <dependency><groupId>com.smartaml</groupId>
      <artifactId>smart-smart-aml-customer</artifactId><version>${project.version}</version></dependency>
    <dependency><groupId>com.smartaml</groupId>
      <artifactId>smart-smart-aml-screening</artifactId><version>${project.version}</version></dependency>
    <dependency><groupId>com.smartaml</groupId>
      <artifactId>smart-smart-aml-analytics</artifactId><version>${project.version}</version></dependency>
    <dependency><groupId>com.smartaml</groupId>
      <artifactId>smart-smart-aml-reporting</artifactId><version>${project.version}</version></dependency>
    <dependency><groupId>com.smartaml</groupId>
      <artifactId>smart-smart-aml-audit</artifactId><version>${project.version}</version></dependency>
    <dependency><groupId>com.smartaml</groupId>
      <artifactId>smart-smart-aml-support</artifactId><version>${project.version}</version></dependency>
    <!-- Third party -->
    <dependency><groupId>io.jsonwebtoken</groupId>
      <artifactId>jjwt-api</artifactId><version>${jjwt.version}</version></dependency>
    <dependency><groupId>io.jsonwebtoken</groupId>
      <artifactId>jjwt-impl</artifactId><version>${jjwt.version}</version></dependency>
    <dependency><groupId>io.jsonwebtoken</groupId>
      <artifactId>jjwt-jackson</artifactId><version>${jjwt.version}</version></dependency>
    <dependency><groupId>org.mapstruct</groupId>
      <artifactId>mapstruct</artifactId><version>${mapstruct.version}</version></dependency>
    <dependency><groupId>org.mapstruct</groupId>
      <artifactId>mapstruct-processor</artifactId><version>${mapstruct.version}</version></dependency>
    <dependency><groupId>net.sourceforge.tess4j</groupId>
      <artifactId>tess4j</artifactId><version>5.10.0</version></dependency>
    <dependency><groupId>org.apache.pdfbox</groupId>
      <artifactId>pdfbox</artifactId><version>3.0.1</version></dependency>
    <dependency><groupId>org.apache.poi</groupId>
      <artifactId>poi-ooxml</artifactId><version>5.2.5</version></dependency>
    <dependency><groupId>com.github.librepdf</groupId>
      <artifactId>openpdf</artifactId><version>1.3.30</version></dependency>
    <dependency><groupId>org.jsoup</groupId>
      <artifactId>jsoup</artifactId><version>1.17.2</version></dependency>
    <dependency><groupId>org.apache.commons</groupId>
      <artifactId>commons-text</artifactId><version>1.11.0</version></dependency>
    <dependency><groupId>com.blueconic</groupId>
      <artifactId>browscap-java</artifactId><version>1.4.1</version></dependency>
    <dependency><groupId>software.amazon.awssdk</groupId>
      <artifactId>s3</artifactId><version>2.25.60</version></dependency>
    <dependency><groupId>com.fasterxml.jackson.dataformat</groupId>
      <artifactId>jackson-dataformat-xml</artifactId></dependency>
  </dependencies>
</dependencyManagement>

<!-- Common plugins for all child modules -->
<build>
  <pluginManagement>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.12.1</version>
        <configuration>
          <source>21</source><target>21</target>
          <annotationProcessorPaths>
            <path><groupId>org.projectlombok</groupId>
              <artifactId>lombok</artifactId><version>${lombok.version}</version></path>
            <path><groupId>org.mapstruct</groupId>
              <artifactId>mapstruct-processor</artifactId><version>${mapstruct.version}</version></path>
          </annotationProcessorPaths>
        </configuration>
      </plugin>
    </plugins>
  </pluginManagement>
</build>
```

---

## 3. MODULE DEPENDENCY MAP

```
smart-aml-shared          → (no internal dependencies — pure Java, no Spring)
smart-aml-infrastructure  → smart-aml-shared
smart-aml-auth            → smart-aml-shared, smart-aml-infrastructure, smart-aml-user(api only)
smart-aml-user            → smart-aml-shared, smart-aml-infrastructure
smart-aml-customer        → smart-aml-shared, smart-aml-infrastructure, smart-aml-user(api only)
smart-aml-screening       → smart-aml-shared, smart-aml-infrastructure, smart-aml-customer(api only)
smart-aml-analytics       → smart-aml-shared, smart-aml-infrastructure
smart-aml-reporting       → smart-aml-shared, smart-aml-infrastructure, smart-aml-customer(api only)
smart-aml-audit           → smart-aml-shared, smart-aml-infrastructure
smart-aml-support         → smart-aml-shared, smart-aml-infrastructure
smart-aml-app             → ALL modules (assembler only, no business logic)
```

---

(Truncated here in README; full specification is in the Smart_AML_Modular_Monolith_Prompt.md file.)

