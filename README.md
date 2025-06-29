# Bookstore API Test Framework

[![Allure Report](https://img.shields.io/badge/Allure-Report-blue)](https://TheMeTrees.github.io/bookstore-api-test/)

API testing framework using Java, RestAssured, JUnit 5, and Allure, integrated with GitHub Actions CI and Docker.
---

## Tech Stack

- **Java 21**
- **JUnit 5**
- **RestAssured**
- **Maven**
- **Allure Reporting**
- **Docker**
- **GitHub Actions**

---

## Running tests locally and generating Allure report

### Ensure Java 21, Manen and Allure CLI are installed

1. Being in root directory
2. mvn clean test -Denv=dev "-Dapi.version=/api/v1"
3. allure generate target/allure-results --clean -o allure-report --single-file
4. allure open allure-report

### In case Allure CLI is not installed
1. Being in root directory
2. mvn clean test -Denv=dev "-Dapi.version=/api/v1"
3. mvn allure:report
4. mvn allure:serve

## Running tests in Docker

### Ensure Docker engine is installed

1. Being in root directory
2. docker build -t api-tests .
3. docker run --rm -v ${pwd}/allure-report:/app/allure-report api-tests
4. allure open allure-report

---

## Configuration
| Property    | Purpose                                   |
|:------------|:------------------------------------------|
| env         | Specifies the environment (dev, qa, etc.) |
| api.version | API version path (e.g. /api/v1)           |

Example:
mvn test -Denv=dev -Dapi.version=/api/v1

---

## GitHub Actions CI
Every push or pull request to the main branch triggers a GitHub Actions workflow:
1. Runs tests
2. Generates an Allure report
3. Publishes the report to GitHub Pages (gh-pages branch)

---

## Allure Report
Reports are published on https://themetrees.github.io/bookstore-api-test/

---

## Features

1. Isolated test classes for each API endpoint
2. Happy path and edge cases scenarios
3. Logs and error handling per test
4. One command run with full reporting