# FintechLab — Laboratorio de microservicios

Este laboratorio existe para observar red, descubrimiento y configuración; no reemplaza el monolito modular ni divide la transacción monetaria.

## Módulos

- discovery-server: registro Eureka local;
- config-server: configuración versionada mediante backend Git;
- notification-service: notificación ficticia y separable;
- transfer-gateway: consumidor didáctico con OpenFeign.

## Orden local

~~~bash
mvn clean verify
mvn -pl discovery-server spring-boot:run
CONFIG_GIT_URI="file:$PWD/config-repo" mvn -pl config-server spring-boot:run
mvn -pl notification-service spring-boot:run
mvn -pl transfer-gateway spring-boot:run
~~~

Ejecuta cada proceso en una terminal. Duplica notification-service con otro puerto para observar balanceo. Simula timeout, 404, 503 y respuesta incompatible con WireMock durante los ejercicios. No añadas retry a operaciones no idempotentes sin una clave y análisis explícitos.

Config Server no almacena secretos: su repositorio contiene solo valores ficticios. En un sistema real las credenciales se inyectan mediante un gestor especializado.