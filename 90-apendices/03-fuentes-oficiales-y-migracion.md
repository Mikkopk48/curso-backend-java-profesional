# Fuentes oficiales y migración de versiones

## Base técnica

- Java: https://docs.oracle.com/en/java/javase/21/
- Spring Boot: https://docs.spring.io/spring-boot/
- Spring Cloud: https://spring.io/projects/spring-cloud
- HTTP Semantics: https://www.rfc-editor.org/rfc/rfc9110
- Problem Details: https://www.rfc-editor.org/rfc/rfc9457
- OpenAPI: https://spec.openapis.org/oas/latest.html
- MySQL 8.4: https://dev.mysql.com/doc/refman/8.4/en/
- JUnit: https://junit.org/junit5/docs/current/user-guide/
- Mockito: https://javadoc.io/doc/org.mockito/mockito-core/latest/org.mockito/module-summary.html
- Testcontainers: https://java.testcontainers.org/
- MapStruct: https://mapstruct.org/documentation/stable/reference/html/
- Selenium: https://www.selenium.dev/documentation/
- OWASP API Security: https://owasp.org/API-Security/
- Git: https://git-scm.com/docs
- GitHub Actions: https://docs.github.com/actions

## Migración

La baseline 3.5/2025.0 conserva la línea JUnit 5 utilizada en la diplomatura. Para migrar, crea rama, consulta matriz Spring Cloud, actualiza Boot y Cloud juntos, lee breaking changes, cambia una capa por vez, ejecuta verify y pruebas de contrato, revisa Jakarta, seguridad y observabilidad. No mezcles dependencias de dos líneas por intuición.
