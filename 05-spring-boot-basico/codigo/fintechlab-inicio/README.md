# FintechLab Inicio

Proyecto de referencia de la Unidad 5. Implementa un CRUD deliberadamente pequeño para aprender el recorrido Spring Boot → controller → service → repository → base de datos. Solo utiliza datos ficticios.

## Requisitos

- JDK 21.
- Docker con Compose únicamente para la etapa MySQL.

## Inicio con H2

~~~bash
./mvnw test
./mvnw spring-boot:run
curl -i http://localhost:8080/actuator/health
~~~

H2 vive en memoria y se reinicia con la aplicación. No requiere Docker.

## Inicio con MySQL

~~~bash
docker compose up -d mysql
SPRING_PROFILES_ACTIVE=mysql ./mvnw spring-boot:run
~~~

Los valores del Compose son únicamente locales y ficticios. En otro ambiente, define `DB_URL`, `DB_USER` y `DB_PASSWORD` fuera del repositorio.

## Petición de prueba

~~~bash
curl -i -X POST http://localhost:8080/api/customers \
  -H 'Content-Type: application/json' \
  -d '{"fullName":"Ana Demo","email":"ana@example.test"}'
~~~

Continúa con el laboratorio de la unidad; este proyecto es un ejemplo resuelto del mecanismo, no la solución de sus ejercicios evaluables.
