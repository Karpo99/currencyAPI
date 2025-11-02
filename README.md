
Новий
+27
-0

# Currency API

Currency API is a Spring Boot 3 reactive service that aggregates fiat and cryptocurrency exchange rates. It uses R2DBC for MySQL access and Liquibase for database migrations.

## Prerequisites
- Java 17+
- Maven 3.9+
- Running MySQL instance with a database matching the credentials in `src/main/resources/application.yml`

## Getting started
1. Clone the repository and move into the project directory.
2. (Optional) Update the R2DBC and Liquibase connection settings in `src/main/resources/application.yml` to match your local environment.
3. Apply the Liquibase changelog if you want to manage schema migrations manually:
   ```bash
   ./mvnw liquibase:update
   ```
4. Start the application:
   ```bash
   ./mvnw spring-boot:run
   ```

By default the service listens on `http://localhost:8081` and expects mock currency rate endpoints defined under the `mock.*` configuration keys.

