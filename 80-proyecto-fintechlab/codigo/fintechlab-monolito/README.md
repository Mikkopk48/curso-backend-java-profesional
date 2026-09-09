# FintechLab — Monolito modular ejecutable

Proyecto educativo con datos ficticios. No se conecta con bancos ni está preparado para procesar dinero real.

## Pila congelada

Java 21, Spring Boot 3.5.15, MySQL 8.4.11, Flyway, MapStruct 1.6.3, JUnit 5.13.4, Mockito 5.18.0, AssertJ 3.27.7, Testcontainers 1.21.4, Selenium 4.47.0 y springdoc 2.8.13.

## Ejecutar

~~~bash
docker compose up -d mysql
./mvnw clean verify
./mvnw spring-boot:run
~~~

- API: http://localhost:8080
- UI didáctica: http://localhost:8080/
- Swagger UI: http://localhost:8080/swagger-ui.html
- Health: http://localhost:8080/actuator/health

El perfil dev permite todas las peticiones para el laboratorio local. El perfil secure demuestra Resource Server con validación de issuer y audience; necesita un proveedor de identidad real y revisión profesional.

## Flujo de demostración

1. Crea un cliente ficticio.
2. Abre dos cuentas de la misma moneda.
3. Envía una transferencia con Idempotency-Key.
4. Repite exactamente la petición y comprueba que conserva el resultado.
5. Reutiliza la clave con otro payload y observa el conflicto.
6. Intenta superar el saldo y revisa Problem Details y correlation ID.

## Limitación de validación

El curso incluye CI con JDK 21, Maven y MySQL. Si tu entorno no dispone de Docker o Maven, ejecuta verify desde GitHub Actions o una máquina con los requisitos declarados.