# FintechLab — Estrategia de pruebas

## Riesgo y nivel

Reglas monetarias se prueban como unidades sin Spring. Mapping no trivial se prueba en compilación. Repositorios y migraciones usan MySQL con Testcontainers. El flujo HTTP completo usa puerto aleatorio. Feign se prueba contra WireMock. Selenium cubre únicamente crear y consultar una transferencia en la UI mínima.

Cada test controla sus datos, no depende del orden ni del reloj real y afirma comportamiento. Cobertura es una señal secundaria. Los fallos remotos incluyen timeout, 404, 503 y JSON inválido.
