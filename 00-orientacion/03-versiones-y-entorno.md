# Versiones y entorno reproducible

## Baseline congelada del curso

Verificada con documentación oficial el 27 de agosto de 2026:

| Componente | Versión |
|---|---:|
| Java | 21 LTS |
| Spring Boot | 3.5.15 |
| Spring Cloud | 2025.0.3 |
| JUnit Jupiter | 5.13.4 |
| Mockito | 5.18.0 |
| AssertJ | 3.27.7 |
| MySQL | 8.4.11 LTS |
| Testcontainers | 1.21.4 |
| MapStruct | 1.6.3 |
| WireMock | 3.13.2 |
| Selenium | 4.47.0 |
| springdoc-openapi | 2.8.13 |

La línea se congela para reproducibilidad y compatibilidad con JUnit 5 y la familia Spring Cloud estudiada. La línea Spring Cloud 2025.0 ya finalizó soporte comunitario; un proyecto nuevo debe evaluar migrar a Spring Boot 4.1.1, Spring Cloud 2025.1.3 y JUnit 6. El apéndice de migración separa aprendizaje estable de actualización de dependencias.

## Herramientas

JDK 21, Maven Wrapper, Git, Docker con Compose y un editor. Ejecuta <code>java -version</code>, <code>./mvnw -version</code>, <code>docker version</code> y <code>docker compose version</code>. No guardes credenciales en archivos versionados.

## Política

No cambies una dependencia aislada sin consultar la matriz oficial, leer release notes y ejecutar verify. La versión utilizada por el docente puede diferir; registra esa diferencia antes de adaptar código.
