# Unidad 5 — Laboratorio guiado: FintechLab inicial

## Propósito

Este laboratorio convierte los conceptos de la unidad en una API ejecutable. No tienes que entregar respuestas mientras terminas de leer el curso. Cuando decidas estudiarlo, realiza las etapas en orden y conserva evidencia local.

El proyecto de referencia está en `codigo/fintechlab-inicio`. Es intencionalmente más pequeño que `80-proyecto-fintechlab/codigo/fintechlab-monolito`: no incluye seguridad, transferencias, microservicios ni decisiones avanzadas. Su única misión es hacer visible Spring Boot básico.

## Resultado

Al finalizar tendrás:

- una aplicación Spring Boot iniciada con Maven Wrapper;
- un CRUD HTTP de clientes ficticios;
- separación controller/service/repository/entity/DTO;
- validación y errores controlados;
- persistencia temporal con H2;
- persistencia real local con MySQL y Flyway;
- una prueba unitaria, una slice web y una prueba de contexto;
- un mapa personal del recorrido de una petición;
- un puente explícito hacia la unidad 6 y el FintechLab completo.

## Reglas de seguridad

- Usa nombres y correos ficticios; el dominio `.test` es apropiado para ejemplos.
- No reutilices contraseñas reales en Compose.
- No publiques el puerto de MySQL fuera de tu equipo local.
- No registres bodies completos, credenciales ni tokens.
- No ejecutes comandos destructivos contra una base que no hayas identificado.
- El volumen `fintechlab_inicio_mysql` pertenece solo a este laboratorio; no borres otros volúmenes.

## Etapa 0 — Preparar sin ejecutar a ciegas

### 0.1 Verifica herramientas

Desde `05-spring-boot-basico/codigo/fintechlab-inicio`:

~~~bash
java -version
./mvnw -version
~~~

Ambos deben mostrar Java 21 para la baseline del curso. El JDK del botón Run del editor puede ser distinto del de la terminal; comprueba los dos si los resultados difieren.

Docker no es necesario todavía. Para la etapa MySQL:

~~~bash
docker version
docker compose version
~~~

### 0.2 Lee antes de iniciar

Localiza:

1. parent y cuatro starters principales en `pom.xml`;
2. `FintechLabInicioApplication` y su `main`;
3. `application.yml`;
4. el package `customer`;
5. el package `error`;
6. las tres clases de prueba.

Escribe una predicción: qué es lo primero que verás al iniciar y qué puerto intentará usar.

### 0.3 Estado inicial

~~~bash
./mvnw -q test
~~~

Si falla, conserva el primer `Caused by`, la versión y el comando. No continúes a MySQL hasta que el proyecto básico compile y el contexto H2 arranque.

## Etapa 1 — Arranque con H2

### 1.1 Inicia

~~~bash
./mvnw spring-boot:run
~~~

No cierres esa terminal. Busca la línea `Started FintechLabInicioApplication`. En otra terminal:

~~~bash
curl -i http://localhost:8080/actuator/health
~~~

Evidencia mínima:

- comando de arranque;
- perfil activo o ausencia de perfil explícito;
- puerto;
- status y body de health;
- tiempo aproximado de arranque.

### 1.2 Distingue procesos

El editor puede seguir indexando mientras la aplicación corre. Identifica cuál terminal contiene el proceso. Detén con `Ctrl+C` una vez y comprueba que health deja de responder. Inicia de nuevo una sola vez.

### 1.3 Provoca un puerto ocupado de forma segura

Con la primera instancia activa, intenta iniciar una segunda. Observa el mensaje, detén la segunda y conserva la primera. No finalices procesos al azar.

Explica qué capa falló: la compilación terminó; el contexto avanzó; el servidor no pudo reservar el puerto.

## Etapa 2 — Primera petición y recorrido

### 2.1 Crear

~~~bash
curl -i -X POST http://localhost:8080/api/customers \
  -H 'Content-Type: application/json' \
  -d '{"fullName":"Ana Demo","email":"ana@example.test"}'
~~~

Conserva el ID ficticio de la respuesta.

### 2.2 Leer colección y recurso

~~~bash
curl -i http://localhost:8080/api/customers
curl -i http://localhost:8080/api/customers/REEMPLAZA_CON_ID
~~~

### 2.3 Actualizar

~~~bash
curl -i -X PUT http://localhost:8080/api/customers/REEMPLAZA_CON_ID \
  -H 'Content-Type: application/json' \
  -d '{"fullName":"Ana Ejemplo","email":"ana.nueva@example.test"}'
~~~

### 2.4 Eliminar

~~~bash
curl -i -X DELETE http://localhost:8080/api/customers/REEMPLAZA_CON_ID
curl -i http://localhost:8080/api/customers/REEMPLAZA_CON_ID
~~~

### 2.5 Dibuja la petición POST

Tu diagrama debe nombrar:

- cliente y mensaje HTTP;
- servidor embebido;
- Jackson y Bean Validation;
- controller;
- service;
- repository proxy;
- JPA/Hibernate;
- DataSource/H2;
- objeto de salida y respuesta HTTP.

Para cada flecha, anota el tipo de dato que cruza. No uses “pasa por Spring” como una sola caja.

## Etapa 3 — Observar validación y errores

### 3.1 JSON inválido

Envía deliberadamente una llave sin cerrar. Registra status y tipo de excepción en log. Comprueba si el método del controller se ejecutó.

### 3.2 DTO inválido

~~~bash
curl -i -X POST http://localhost:8080/api/customers \
  -H 'Content-Type: application/json' \
  -d '{"fullName":" ","email":"sin-formato"}'
~~~

Relaciona cada violación con una anotación del record.

### 3.3 Regla de negocio

Crea dos clientes con el mismo email variando mayúsculas y espacios. El segundo debe producir conflicto controlado. Identifica por qué la normalización vive en el service y por qué la base conserva además una restricción única.

### 3.4 Recurso ausente

~~~bash
curl -i http://localhost:8080/api/customers/id-que-no-existe
~~~

Sigue `Optional` desde el repository hasta `CustomerNotFoundException` y después hasta el advice.

### 3.5 Falla accidental

No añadas un handler genérico todavía. Si ocurre un error inesperado, el cliente no debería recibir el stack trace completo. El servidor sí necesita conservar una traza segura para diagnóstico. La unidad 10 diseñará el contrato definitivo.

## Etapa 4 — Reconstrucción de una ruta

Esta etapa evita que el ejemplo se convierta en una plantilla copiada.

1. Crea una rama de práctica.
2. Elige el método de consulta individual.
3. Escribe en papel su contrato de entrada, salida y ausencia.
4. Mueve temporalmente su implementación a un archivo de notas fuera del código.
5. Reconstrúyelo desde el contrato usando imports del IDE solo cuando los comprendas.
6. Ejecuta la prueba más pequeña y una petición manual.
7. Compara con el estado original y revierte si empeoró.

No memorices orden de anotaciones. Comprende quién lee cada una.

## Etapa 5 — Pruebas en tres alcances

### 5.1 Unitaria

Abre `CustomerServiceTest` y explica:

- por qué no aparece `@SpringBootTest`;
- qué objeto es real;
- qué objeto es mock;
- qué regla se comprueba;
- por qué se verifica que `save` no se invoque.

Ejecuta solo esa clase desde el editor y desde Maven. Conserva ambos resultados si difieren.

### 5.2 Web

Abre `CustomerControllerTest`. Sigue la petición simulada hasta la validación. El service es mock porque esta prueba pregunta por la frontera web, no por persistencia.

Modifica temporalmente el JSON para que sea válido. Predice qué ocurrirá si el mock no tiene respuesta configurada. Después revierte.

### 5.3 Contexto

`FintechLabInicioApplicationTest` demuestra que el grafo básico arranca con H2. El hecho de que su cuerpo esté vacío no significa que no ocurra nada: la anotación crea el contexto antes del método.

Ejecuta:

~~~bash
./mvnw -q test
~~~

Clasifica cada test por riesgo y escribe una limitación. Ninguno demuestra todavía MySQL real.

## Etapa 6 — Cambio consciente a MySQL

### 6.1 Inspecciona Compose

Antes de ejecutar, identifica:

- imagen y versión;
- nombre del servicio;
- base, usuario y valores locales;
- puerto host y puerto contenedor;
- healthcheck;
- nombre exacto del volumen.

### 6.2 Inicia únicamente MySQL de este proyecto

~~~bash
docker compose up -d mysql
docker compose ps
docker compose logs mysql
~~~

Espera a que esté healthy. No uses una espera fija como evidencia si el healthcheck informa estado.

### 6.3 Activa el perfil

~~~bash
SPRING_PROFILES_ACTIVE=mysql ./mvnw spring-boot:run
~~~

Busca en log:

- datasource MySQL;
- ejecución de Flyway;
- validación de JPA;
- arranque completo.

### 6.4 Persistencia entre reinicios

1. Crea un cliente ficticio.
2. Detén solo la aplicación Java.
3. Iníciala otra vez con perfil mysql.
4. Lista clientes.
5. Explica por qué H2 no conservaba el dato y MySQL sí.

### 6.5 Inspecciona migraciones

Comprueba que existen `customers` y el historial de Flyway. No modifiques la migración aplicada. Si necesitas añadir un campo de práctica, crea `V2__...sql` en tu rama.

### 6.6 Apagado recuperable

~~~bash
docker compose stop mysql
~~~

`stop` conserva el volumen. `down` elimina contenedores y red, pero conserva el volumen salvo que agregues una opción explícita. No borres el volumen a menos que quieras reiniciar deliberadamente solo los datos de este laboratorio.

## Etapa 7 — Diagnóstico controlado

Provoca uno de estos fallos por vez y revierte antes del siguiente:

### A. Bean ausente

Retira temporalmente `@Service` de `CustomerService`. Predice qué constructor fallará y verifica el mensaje. Restaura la anotación.

### B. Propiedad equivocada

Activa MySQL con un puerto inexistente mediante `DB_URL`. Distingue creación del datasource de conexión rechazada. Restaura la variable.

### C. Esquema incoherente

En una base desechable del laboratorio, añade temporalmente una columna requerida a la entidad sin migración. Observa `ddl-auto: validate`. Revierte código y base; no cambies a `update` como salida.

### D. Mapping inexistente

Cambia solo la URL de `curl`. Compara el `404` del framework con el `404` del error de dominio mediante body y log.

Para cada caso registra:

| Campo | Contenido |
|---|---|
| Síntoma | Qué observaste, sin interpretación |
| Fase | Build, contexto, servidor, base, petición o prueba |
| Causa | El `Caused by` o evidencia más cercana |
| Hipótesis | Una frase refutable |
| Cambio único | Qué modificaste |
| Confirmación | Qué prueba mostró la corrección |

## Etapa 8 — Lectura del proyecto avanzado

No lo ejecutes todavía si la infraestructura te distrae. Abre `80-proyecto-fintechlab/codigo/fintechlab-monolito` y localiza equivalencias:

| Proyecto inicial | Monolito completo | Qué cambia |
|---|---|---|
| `FintechLabInicioApplication` | `FintechLabApplication` | Mismo punto de entrada, más configuración |
| `customer/CustomerController` | `customer/CustomerController` | Contrato más integrado al dominio |
| `customer/CustomerService` | servicio del área customer | Reglas y dependencias reales |
| `CustomerRepository` | repositories de cada capacidad | Más consultas y entidades |
| `GlobalExceptionHandler` | handler global avanzado | Contrato de errores consistente |
| `application-mysql.yml` | perfiles y `application.yml` del monolito | Seguridad y operación adicionales |
| `V1__create_customers.sql` | migración integral | Varias tablas y restricciones |

Tu objetivo es reconocer el mismo esqueleto bajo mayor complejidad. No copies seguridad ni transferencias al proyecto inicial.

## Etapa 9 — Entrega personal de la unidad

Conserva, sin enviarla todavía:

1. un diagrama del arranque;
2. un diagrama de la petición POST;
3. salida de las tres clases de prueba;
4. una petición feliz y tres fallos distintos;
5. evidencia de H2 y MySQL;
6. tabla de diagnóstico de un fallo provocado;
7. una explicación de dos minutos de controller/service/repository;
8. el commit o diff de tu reconstrucción.

## Criterios de aceptación

- El proyecto inicia con H2 sin Docker.
- Health devuelve HTTP y el CRUD usa datos ficticios.
- Los cinco endpoints básicos responden con status coherentes.
- La entidad no se expone directamente.
- Validación, ausencia y duplicado producen categorías distintas.
- Las dependencias entran por constructor.
- Las tres pruebas representan alcances distintos.
- El perfil MySQL aplica Flyway y valida el esquema.
- Un reinicio con MySQL conserva datos.
- Puedes explicar al menos un fallo desde síntoma hasta causa.
- No hay credenciales reales ni datos sensibles en logs o commits.

## Cuando algo no sale

No saltes directamente al proyecto completo. Reduce el caso:

1. ¿compila?
2. ¿carga el contexto con H2?
3. ¿arranca el servidor?
4. ¿responde health?
5. ¿entra al controller?
6. ¿entra al service?
7. ¿ejecuta el repository?
8. ¿la base acepta la operación?

Detente en la primera frontera que no produce la evidencia esperada. Esa es tu zona de investigación.

## Puente a la unidad 6

La Unidad 5 te enseña a construir el mecanismo. La Unidad 6 cuestiona el contrato: semántica de GET y DELETE, recursos, status, cache, idempotencia y diseño de URI. Antes de continuar, conserva el CRUD tal como está; no intentes anticipar todas las mejoras. Aprenderás a refactorizarlo con razones observables.
