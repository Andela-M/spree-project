# Spree Web Shop (Docker)

A ready-to-run Spree demo shop (Rails + Postgres + Redis) using Docker Compose.  
Reviewers can boot it, load sample products, and access Storefront + Admin in minutes.

**Storefront**: http://localhost:3000  
**Admin**: http://localhost:3000/admin

---

## Prerequisites

- Docker Desktop (running)
- Git (to clone this repo)

---

## Quick Start

### 1) Clone

```bash
git clone <YOUR_REPO_URL> spree_starter
cd spree_starter
```

### 2) Start containers (Windows/macOS/Linux)

```bash
docker compose up -d
```

> First boot pulls images & installs gems — give it a few minutes.

### 3) Load sample data + create admin (same steps on Windows & macOS/Linux)

Open a shell inside the web container:

```bash
docker exec -it spree_starter-web-1 bash
```

Now run these **INSIDE** the container:

```bash
bin/rails db:prepare
bin/rails spree_sample:load
bin/rails r 'email=ENV.fetch("ADMIN_EMAIL","admin@example.com"); pass=ENV.fetch("ADMIN_PASSWORD","Password123!"); u=Spree::User.find_or_initialize_by(email: email); u.password=pass; u.password_confirmation=pass; u.save!; puts "Admin: #{email} / #{pass}"'
exit
```

> If you want custom admin credentials, set env vars before step 2 (e.g. in `.env`):  
> `ADMIN_EMAIL=your@email.com` and `ADMIN_PASSWORD=YourStrongPass!`

### 4) Open the app

- **Storefront** → http://localhost:3000
- **Admin** → http://localhost:3000/admin

Login with the credentials printed in step 3  
**(Default: `admin@example.com` / `Password123!`)**

---

## Useful Commands

### Start / Stop / Logs

```bash
docker compose up -d
docker compose down
docker compose logs -f web
```

### Open a shell in the web container

```bash
docker exec -it spree_starter-web-1 bash
```

### Reset & re-load sample data

```bash
docker exec -it spree_starter-web-1 bash -lc 'bin/rails db:reset && bin/rails spree_sample:load'
```

### Recreate the admin user with default creds

```bash
docker exec -it spree_starter-web-1 bash -lc 'bin/rails r "u=Spree::User.find_or_initialize_by(email:\"admin@example.com\"); u.password=\"Password123!\"; u.password_confirmation=\"Password123!\"; u.save!; puts :ok"'
```

---

## API (very short)

### Storefront API (public client features)

**Base**: `http://localhost:3000/api/v2/storefront`

**Example**:
```http
GET /api/v2/storefront/products
```

Some endpoints require a customer token (password grant).

### Platform API (admin features)

**Base**: `http://localhost:3000/api/v2/platform`

Use `client_credentials` token and call with `Authorization: Bearer <token>`.

> In our test framework we cache tokens once per run and reuse them.

---

## Troubleshooting

### Port 3000 in use

```bash
# edit docker-compose.yml and change the port mapping to 3001:3000
# then:
docker compose down
docker compose up -d
# open http://localhost:3001
```

### Containers crash / won't start

```bash
docker compose logs --tail=200 web
docker compose logs --tail=200 db
docker compose down
docker compose up -d --build
```

### Admin login fails

Re-run the admin creation command (see "Useful Commands").

### No sample products

Run `bin/rails spree_sample:load` again inside the container.

---

## Running Automated Tests

This project includes automated API and UI tests built with:
- **Java 17** + **JUnit 5**
- **Rest Assured** (API testing)
- **Selenium WebDriver** (UI testing)
- **Allure** (test reporting)

### Prerequisites for Testing

1. **Docker** - Spree application must be running (see Quick Start above)
2. **Java 17** or higher
3. **Maven** (or use included Maven wrapper `mvnw`)

### Running Tests

#### Run All Tests
```bash
./mvnw clean test
```

#### Run Specific Test Class
```bash
# Run User CRUD API tests
./mvnw test -Dtest=UserCRUD

# Run specific test method
./mvnw test -Dtest=UserCRUD#userSuccessfullyCreated
```

#### Windows Users
Use `mvnw.cmd` instead:
```cmd
.\mvnw.cmd clean test
.\mvnw.cmd test -Dtest=UserCRUD
```

---

## Viewing Allure Test Reports

Allure provides beautiful, comprehensive test reports with detailed test execution results, screenshots, logs, and categorization.

### Option 1: View with Allure CLI (Recommended)

#### Install Allure CLI

**Windows (using Scoop):**
```cmd
scoop install allure
```

**macOS (using Homebrew):**
```bash
brew install allure
```

**Linux (manual installation):**
```bash
# Download latest version from https://github.com/allure-framework/allure2/releases
# Extract and add to PATH
wget https://github.com/allure-framework/allure2/releases/download/2.29.0/allure-2.29.0.zip
unzip allure-2.29.0.zip
export PATH=$PATH:$(pwd)/allure-2.29.0/bin
```

#### Generate and Open Report

After running tests:
```bash
# Generate and automatically open report in browser
allure serve target/allure-results

# Or generate static report
allure generate target/allure-results --clean -o target/allure-report
allure open target/allure-report
```

The Allure server will start and automatically open the report in your default browser at `http://localhost:PORT`.

### Option 2: View with Maven Plugin

```bash
# Generate report
./mvnw allure:report

# Open report in browser
./mvnw allure:serve
```

### Understanding Allure Reports

The Allure UI provides several views:

1. **Overview** - Summary with pass/fail statistics, test duration, trends
2. **Suites** - Tests organized by test suites and packages
3. **Graphs** - Visual representation of test results (pie charts, trends)
4. **Timeline** - Test execution timeline showing parallel execution
5. **Behaviors** - Tests organized by Epic > Feature > Story
6. **Categories** - Failure categorization (Product defects, Test defects, etc.)
7. **Packages** - Tests organized by Java package structure

### Interpreting Test Results

#### Known Issues in User CRUD Tests

The User CRUD API tests currently show g (categories, images, prices).
- An **Admin panel** for products, orders, users.
- **Automated test suite** with comprehensive API coverage
- **Allure test reports** with detailed test execution results and issue documentation
- Optionally, quick API checks against Storefront/Platform.

---

## License

Spree starter licensing applies; your custom code follows your repository's license.**3 failing tests** which document known issues with the Spree Platform API:

- ✅ **CREATE (POST /users)** - PASSING
- ❌ **READ (GET /users/{id})** - FAILING (404)
- ❌ **UPDATE (PATCH /users/{id})** - FAILING (404)
- ❌ **DELETE (DELETE /users/{id})** - FAILING (404)

**Root Cause:**
The Platform API `/users` endpoints create users in the `spree_users` table but attempt to retrieve them from the `spree_admin_users` table. This is a framework-level limitation in Spree Commerce v5.1.7.

**Impact:**
- User creation works correctly
- User retrieval, update, and deletion require database-level access or admin console
- For production testing, consider using Storefront API for regular user operations

All test failures include detailed descriptions in the Allure report explaining:
- Expected vs. Actual results
- Root cause analysis
- API response details
- Server logs and debugging information

This demonstrates proper QA practices: **identifying and documenting API limitations and bugs**.

---

## What Reviewers Will See

- A running **Storefront** with sample catalo
