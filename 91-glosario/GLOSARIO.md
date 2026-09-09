# Glosario acumulativo

| Término | Definición sencilla | Definición técnica | Ejemplo o tema relacionado | Primera unidad |
|---|---|---|---|---:|
| ACID | Cuatro propiedades de una transacción. | Atomicidad, consistencia, aislamiento y durabilidad. | transacción | 13 |
| ADR | Registro breve de una decisión. | Architecture Decision Record con contexto, decisión y consecuencias. | arquitectura | 15 |
| ApplicationContext | Contenedor activo de Spring. | Registro y ciclo de vida de beans, recursos, eventos y configuración de una aplicación. | IoC | 5 |
| Artefacto | Archivo producido o distribuido por una construcción. | Resultado versionado de un build, como un JAR, publicado o consumido mediante coordenadas. | Maven | 5 |
| Audience | Destinatario previsto de un token. | Claim aud que restringe qué recurso acepta el JWT. | JWT | 25 |
| Autoconfiguración | Configuración aplicada según lo disponible. | Clases condicionales de Spring Boot activadas por classpath, propiedades y beans. | Spring Boot | 5 |
| Backoff | Espera creciente entre reintentos. | Política temporal que reduce presión durante fallos. | retry | 16 |
| Bean | Objeto administrado por Spring. | Instancia registrada, creada o procesada por un ApplicationContext. | IoC | 5 |
| Bean Validation | Reglas declarativas sobre entrada. | Especificación Jakarta Validation aplicada mediante constraints y validadores. | DTO | 5 |
| BigDecimal | Número decimal controlado. | Tipo inmutable decimal arbitrario con escala y redondeo explícitos. | dinero | 9 |
| Bytecode | Instrucciones que ejecuta la JVM. | Representación intermedia generada al compilar Java y almacenada normalmente en archivos `.class`. | compilación | 5 |
| Bounded context | Límite donde un modelo conserva significado. | Frontera lingüística y de modelo del dominio. | microservicios | 15 |
| Bulkhead | Aislamiento de recursos. | Patrón que limita propagación de agotamiento entre dependencias. | resiliencia | 15 |
| Cache | Copia reutilizable de una respuesta. | Almacenamiento gobernado por validadores y directivas HTTP. | ETag | 6 |
| Circuit breaker | Interruptor temporal ante fallos repetidos. | Máquina de estados que evita llamadas y prueba recuperación. | resiliencia | 16 |
| Claim | Afirmación dentro de un token. | Par nombre–valor que expresa identidad o autorización. | JWT | 25 |
| Classpath | Lugares donde Java busca clases y recursos. | Conjunto ordenado de directorios y JAR disponibles para compilar o ejecutar una aplicación. | JVM | 5 |
| Commit | Fotografía versionada. | Objeto Git con árbol, padres y metadatos. | Git | 20 |
| Compilador | Programa que traduce código fuente. | `javac` analiza Java y genera bytecode `.class`, rechazando errores de sintaxis y tipos. | JDK | 5 |
| Component scanning | Búsqueda de componentes. | Descubrimiento de clases candidatas bajo packages configurados. | estereotipos Spring | 5 |
| Correlation ID | Identificador para seguir una operación. | Valor propagado entre logs y servicios sin ser secreto. | observabilidad | 10 |
| Controller | Adaptador de entrada web. | Bean MVC que mapea mensajes HTTP a invocaciones y respuestas. | API | 5 |
| CSRF | Uso involuntario de credenciales del navegador. | Ataque que induce una petición autenticada automáticamente. | cookies | 25 |
| DataSource | Proveedor de conexiones. | Abstracción JDBC normalmente respaldada por un pool de conexiones. | base de datos | 5 |
| Deadlock | Espera circular entre transacciones. | Ciclo de locks que obliga a abortar una participante. | concurrencia | 13 |
| Dependencia | Código externo que el proyecto necesita. | Artefacto declarado con coordenadas y añadido al classpath de una fase o alcance determinados. | Maven | 5 |
| Dependency Injection | Dependencias entregadas desde fuera. | Técnica de composición donde un objeto recibe colaboradores, preferentemente por constructor. | IoC | 5 |
| DTO | Objeto de entrada o salida. | Contrato de transferencia separado del modelo persistente. | API | 5 |
| E2E | Prueba del recorrido completo. | Test que cruza las fronteras reales necesarias del flujo. | testing | 8 |
| ETag | Versión opaca de una representación. | Validador HTTP usado en peticiones condicionales. | cache | 6 |
| Entity | Objeto con identidad persistente. | Tipo mapeado por JPA a estado gestionado en una tabla o relación. | JPA | 5 |
| Eventual consistency | Los datos convergen después. | Modelo donde réplicas o servicios no reflejan el cambio al instante. | distribución | 15 |
| Feign | Cliente HTTP declarativo. | Proxy generado desde una interfaz y configuración de contrato. | microservicios | 16 |
| Fixture | Datos preparados para una prueba. | Estado controlado, aislado y reproducible del test. | testing | 8 |
| Flyway | Herramienta de migraciones. | Ejecutor ordenado de cambios versionados de esquema. | base de datos | 5 |
| Framework | Estructura reutilizable que dirige parte del programa. | Conjunto de abstracciones y puntos de extensión que invoca código de la aplicación según su ciclo de vida. | Spring Framework | 5 |
| H2 | Base relacional ligera. | Motor embebible o en memoria útil para aprendizaje, no equivalente a MySQL. | pruebas iniciales | 5 |
| HashMap | Mapa basado en hash. | Estructura que usa hashCode y equals para localizar claves. | Map | 21 |
| Hibernate | Implementación ORM de JPA. | Proveedor que administra entidades y traduce operaciones a SQL mediante JDBC. | persistencia | 5 |
| Idempotency-Key | Identidad de una intención repetible. | Clave de protocolo vinculada a request y resultado persistente. | HTTP | 9 |
| Index | Estructura auxiliar de búsqueda. | Orden o estructura mantenida que cambia costo de lectura y escritura. | SQL | 13 |
| Instant | Punto temporal UTC. | Cantidad sobre la línea temporal independiente de zona. | java.time | 12 |
| IoC | El contenedor controla la composición. | Inversión de control aplicada a creación, conexión y ciclo de vida de componentes. | Spring | 5 |
| Issuer | Emisor confiable del token. | Claim iss validado contra una autoridad esperada. | JWT | 25 |
| JAR | Paquete habitual de una aplicación o biblioteca Java. | Archivo ZIP con clases, recursos y metadatos; puede ser ejecutable si declara su punto de entrada. | Maven | 5 |
| JPA | Especificación de persistencia Java. | Jakarta Persistence define entidades, contexto, consultas y transacciones ORM. | Hibernate | 5 |
| JWT | Contenedor de claims firmado. | Formato compacto JWS/JWE; firmar no cifra el payload. | seguridad | 25 |
| JVM | Máquina que ejecuta bytecode Java. | Java Virtual Machine que carga, verifica y ejecuta clases con memoria administrada. | Java | 5 |
| Lazy loading | Carga al acceder. | Estrategia ORM que difiere materialización de una relación. | JPA | 13 |
| Lease | Registro que debe renovarse. | Concesión temporal usada por service discovery. | Eureka | 17 |
| Lock | Control de acceso concurrente. | Mecanismo que coordina operaciones incompatibles. | transacción | 13 |
| MapStruct | Generador de mappers. | Annotation processor que emite Java en compilación. | DTO | 22 |
| Maven | Herramienta de build Java. | Gestiona modelo de proyecto, dependencias, plugins y ciclo de construcción. | pom.xml | 5 |
| Mock | Doble con expectativas. | Objeto controlado que devuelve respuestas y verifica interacciones. | testing | 7 |
| Monolito modular | Una aplicación con límites internos. | Despliegue único organizado en módulos de alta cohesión. | arquitectura | 15 |
| N+1 | Muchas consultas nacidas de una inicial. | Patrón ORM de una query raíz más otra por cada resultado. | JPA | 13 |
| OAuth 2.0 | Protocolo de autorización delegada. | Framework de autorización con roles y grants definidos. | OIDC | 25 |
| OpenAPI | Descripción de una API. | Especificación legible por personas y herramientas. | contrato | 11 |
| Optimistic locking | Detección de edición concurrente. | Control mediante versión que rechaza escritura sobre estado cambiado. | concurrencia | 9 |
| Outbox | Eventos guardados junto al negocio. | Patrón que persiste evento y cambio en una transacción local. | mensajería | 15 |
| POM | Descripción central de un proyecto Maven. | `pom.xml` declara coordenadas, propiedades, dependencias, plugins y herencia del build. | Maven | 5 |
| Problem Details | Formato estándar de error HTTP. | Objeto RFC 9457 con tipo, título, status, detalle e instancia. | errores | 10 |
| Proceso | Programa que se está ejecutando. | Instancia activa con memoria, hilos, recursos y un identificador administrado por el sistema operativo. | ejecución | 5 |
| Projection | Vista de datos para un caso. | Selección tipada que evita cargar una entidad completa. | JPA | 13 |
| Rebase | Reaplicar commits sobre otra base. | Transformación del grafo que crea commits nuevos. | Git | 20 |
| Refresh token | Credencial para renovar acceso. | Token de mayor duración sujeto a rotación y revocación. | OAuth | 25 |
| Repository | Frontera de persistencia. | Abstracción que ofrece operaciones y consultas sin trasladar HTTP al acceso a datos. | Spring Data JPA | 5 |
| Retry | Repetir una operación fallida. | Política limitada por semántica, presupuesto y clasificación de error. | resiliencia | 16 |
| Rollback | Deshacer una transacción. | Finalización que descarta cambios no confirmados. | ACID | 13 |
| Saga | Coordinación con pasos compensables. | Patrón de transacciones locales y acciones de compensación. | distribución | 15 |
| Salt | Valor único añadido al hash. | Entrada aleatoria almacenada que evita hashes iguales y tablas precalculadas. | contraseñas | 25 |
| Selenium WebDriver | Control remoto del navegador. | Protocolo y API para automatizar interacciones de UI. | E2E | 23 |
| Service | Componente de caso de uso. | Bean que orquesta reglas, colaboradores y límites transaccionales de aplicación. | arquitectura por capas | 5 |
| Código fuente | Texto que escribe una persona para definir un programa. | Archivos legibles, como `.java`, que deben compilarse antes de que la JVM ejecute su bytecode. | `src/main/java` | 5 |
| Spring Boot | Arranque y configuración opinada de Spring. | Plataforma que aplica autoconfiguración, starters, servidor embebido y capacidades operativas. | aplicación ejecutable | 5 |
| Spring Framework | Fundamento del ecosistema Spring. | Framework que ofrece contenedor IoC, DI y módulos web, datos y transacciones. | IoC | 5 |
| Spring MVC | Módulo web basado en Servlet. | Dispatcher y adaptadores que seleccionan handlers y convierten mensajes HTTP. | controller | 5 |
| Starter | Conjunto coherente de dependencias. | Descriptor de Spring Boot que incorpora una capacidad y activa autoconfiguración relacionada. | Maven | 5 |
| Stream | Pipeline de elementos. | Secuencia de operaciones perezosas consumida por una terminal. | Java | 12 |
| `target` | Carpeta de resultados generados por Maven. | Directorio de salida que contiene clases compiladas, reportes, recursos procesados y artefactos del build; puede regenerarse. | Maven | 5 |
| Testcontainer | Dependencia real desechable. | Contenedor gestionado durante tests de integración. | testing | 8 |
| Timeout | Límite de espera. | Presupuesto después del cual una operación se considera no completada. | resiliencia | 16 |
| Transaction | Unidad de cambio. | Conjunto atómico ejecutado con un nivel de aislamiento. | ACID | 5 |
| WireMock | Servidor HTTP simulado. | Stub programable para verificar contratos y fallos remotos. | testing | 8 |
| XSS | Script inyectado en un origen. | Vulnerabilidad que ejecuta código con privilegios del sitio. | navegador | 25 |
| ZoneId | Reglas de una zona horaria. | Identificador regional con offsets históricos y futuros. | java.time | 12 |

El glosario ayuda a recuperar vocabulario; la definición completa, límites y decisiones están en la unidad indicada.
