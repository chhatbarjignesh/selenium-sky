# Selenium Sky

A Java-based automation framework using Selenium, Cucumber, and Maven for UI testing.

## Project Structure

- `src/main/java`: Main framework code
- `src/test/java`: Test cases and runners
- `src/main/resources`: Configuration files
- `src/test/resources/features`: Cucumber feature files
- `target/cucumber-html-reports`: HTML test reports

## Prerequisites

- Java 11+
- Maven 3.6+
- IntelliJ IDEA (recommended)

## Setup

1. Clone the repository:

git clone <repo-url>
cd selenium-sky

2. Install dependencies:

mvn clean install


## Running Tests

Execute all tests with:

mvn test


To run specific tests or features, use:
mvn test -Dcucumber.filter.tags="@yourTag"


## Reports

- **Allure Report**: Generated in `allure-results`. To view:

allure serve allure-results

- **HTML Report**: Available at `target/cucumber-html-reports/report.html`.

## Configuration

Edit `src/main/resources/config.properties` for environment settings.

## License

MIT License
