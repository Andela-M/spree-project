# Spree E-Commerce Test Automation

A comprehensive test automation framework for the Spree Commerce platform, featuring both API and Web UI testing with detailed Allure reporting.

## Overview

This project provides automated testing for Spree Commerce v5.1.7, covering:
- **API Tests**: Platform API user CRUD operations (Create, Read, Update, Delete)
- **Web UI Tests**: Customer registration and login flows
- **Allure Reporting**: Professional test reports with detailed test execution metrics

The framework uses a hybrid testing approach, combining API calls for efficient test data setup with UI validation for critical user workflows.

## Prerequisites

- **Java 17** or higher
- **Maven 3.6+**
- **Chrome Browser** (latest version)
- **Allure Command-line** (included in `allure-2.29.0/`)

## Getting Started

### 1. Start the Spree Application

The Spree demo application must be running before executing tests.

The Spree demo is a custom configured db version of the https://github.com/spree/spree_starter Spree Started

Download the app from this URL: 
https://we.tl/t-aEAhjioP1A

The app was uploaded to WeTransfer, because it contains files too big to push to Github.

Unzip and then:

#### Windows
```powershell
cd spree_starter
# Right-click run-demo.ps1 → Run with PowerShell
# OR
powershell -ExecutionPolicy Bypass -File .\run-demo.ps1
```

#### Mac/Linux
```bash
cd spree_starter
chmod +x run-demo.sh && ./run-demo.sh
```

The application will be available at: **http://localhost:3000**

**Admin Credentials:**
- Email: `admin@example.com`
- Password: `Password123!`

For detailed setup instructions, see [instructions.md](instructions.md)

### 2. Configure Test Environment

Test configuration is managed in `src/main/resources/config.properties`:
```properties
baseEspreeUrl=http://localhost:3000
platformApiUrl=http://localhost:3000/api/v2/platform
```

## Running Tests

### Run All Tests
```bash
./mvnw clean test
```

### Run Specific Test Class
```bash
# API Tests
./mvnw test -Dtest=UserCRUD

# Web Tests
./mvnw test -Dtest=LoginTests
./mvnw test -Dtest=RegisterTests
```

### Run Tests by Category
```bash
# API Tests only
./mvnw test -Dtest=espreetests.api.*

# Web Tests only
./mvnw test -Dtest=espreetests.web.*
```

## Allure Reports

### Generate and View Report
```bash
# Generate report from test results
allure-2.29.0/bin/allure generate target/allure-results --clean -o target/allure-report

# Open report in browser
allure-2.29.0/bin/allure open target/allure-report
```

### One-Command Report Generation
```bash
# Run tests and serve report
./mvnw clean test && allure-2.29.0/bin/allure serve target/allure-results
```

The Allure report includes:
- Test execution overview with pass/fail statistics
- Detailed test steps and assertions
- Test categorization by Epic, Feature, and Story
- Severity levels and issue tracking links
- Test execution history and trends

## Project Structure

```
espree-project/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/spree/
│   │   │   │   ├── api/          # API client classes
│   │   │   │   └── pages/        # Page Object Model classes
│   │   │   └── testframework/    # Core framework utilities
│   │   └── resources/
│   │       └── config.properties # Test configuration
│   └── test/
│       ├── java/espreetests/
│       │   ├── api/              # API test cases
│       │   ├── web/              # Web UI test cases
│       │   └── helper/           # Test data helpers
│       └── resources/
├── spree_starter/                # Spree demo application
├── allure-2.29.0/                # Allure CLI tool
├── pom.xml                       # Maven configuration
└── README.md
```

## Technologies Used

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 17 | Programming language |
| Maven | 3.x | Build and dependency management |
| JUnit 5 | 5.11.3 | Test framework |
| Selenium WebDriver | 4.35.0 | Web UI automation |
| RestAssured | 5.5.6 | API testing |
| Allure | 2.29.0 | Test reporting |
| Lombok | 1.18.38 | Code generation |
| JavaFaker | 1.0.2 | Test data generation |

## Test Features

### Page Object Model
- Clean separation of page structure and test logic
- Reusable page components (e.g., `NavigationSection`)
- Centralized element locators

### Test Data Management
- Dynamic test data generation using JavaFaker
- Unique email and password generation for each test run
- Configurable via properties files

### Allure Annotations
- `@Epic`, `@Feature`, `@Story` for test organization
- `@Severity` levels for test prioritization
- `@Description` for detailed test documentation
- `@Issue` and `@TmsLink` for test management integration

### Known Issues

**Platform API Table Mismatch**: The Platform API `/users` endpoints have a known bug where:
- POST creates users in the `spree_users` table
- GET, PATCH, DELETE query the `spree_admin_users` table

**Impact**: Only user creation tests pass; read, update, and delete operations return 404. Test users cannot be cleaned up via API.

**Workaround**: Each test creates unique users via JavaFaker. In production, cleanup would require database access or admin console.

## Support

For issues or questions, please refer to:
- [Spree Commerce Documentation](https://docs.spreecommerce.org/)
- [Allure Framework Documentation](https://docs.qameta.io/allure/)
