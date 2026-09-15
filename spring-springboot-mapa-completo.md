# Mapa completo de Spring Framework y Spring Boot

> **Propósito:** Este documento es un mapa de referencia transversal para estudiar Spring Boot. No intenta enseñar cada tecnología en profundidad; intenta responder constantemente: **"¿Dónde estoy dentro de Spring y qué está pasando ahora?"**

---

# 1. La idea general: ¿qué es todo esto?

Cuando empiezas con Spring Boot es muy fácil encontrarte con una lista enorme de nombres:

- Spring
- Spring Framework
- Spring Boot
- Spring MVC
- Spring Data
- Spring Security
- Spring Core
- Beans
- IoC
- DI
- ApplicationContext
- DispatcherServlet
- Controller
- Service
- Repository
- JPA
- Hibernate
- JDBC
- Jackson
- Tomcat
- Maven
- JUnit
- Mockito

El problema no suele ser que cada término sea demasiado difícil por separado. El problema es **no saber cómo encajan entre sí**.

La mejor forma de pensar Spring Boot es como un sistema compuesto por varias capas y proyectos.

Una aplicación web típica puede verse, de forma simplificada, así:

```text
                         SPRING ECOSYSTEM
                                │
              ┌─────────────────┼─────────────────┐
              │                 │                 │
              ▼                 ▼                 ▼
     Spring Framework      Spring Projects    Spring Boot
              │                 │                 │
              │                 │                 ├─ Auto-configuration
              │                 │                 ├─ Starters
              │                 │                 ├─ Embedded server
              │                 │                 └─ Production features
              │                 │
              ├─ Core           ├─ Spring Data
              ├─ Beans          ├─ Spring Security
              ├─ Context        ├─ Spring Batch
              ├─ AOP            ├─ Spring Cloud
              ├─ MVC            └─ otros
              ├─ Web
              ├─ Validation
              ├─ JDBC
              └─ Transactions

          + tecnologías externas
          ├─ Java / Jakarta APIs
          ├─ Tomcat
          ├─ Hibernate
          ├─ Jackson
          ├─ JDBC drivers
          ├─ Maven / Gradle
          ├─ JUnit
          ├─ Mockito
          └─ Base de datos
```

La primera idea importante es:

> **Spring Boot no es "otro framework distinto de Spring". Spring Boot facilita el uso de Spring Framework y del ecosistema Spring.**

---

# 2. Spring, Spring Framework, Spring Projects y Spring Boot

## 2.1 ¿Qué es Spring?

"Spring" puede referirse al ecosistema completo de proyectos relacionados con Spring, o de manera informal a Spring Framework.

Por eso una frase como "estoy usando Spring" puede significar muchas cosas:

```text
Estoy usando:
    Spring Framework
    + Spring Boot
    + Spring Data
    + Spring Security
    + etc.
```

No todos esos elementos son exactamente el mismo proyecto.

---

## 2.2 ¿Qué es Spring Framework?

**Spring Framework** es el framework base.

Proporciona, entre otras cosas, una infraestructura para:

- Inversión de Control (IoC).
- Inyección de Dependencias (DI).
- Gestión de Beans.
- Configuración.
- Programación orientada a aspectos (AOP).
- Aplicaciones web con Spring MVC.
- Acceso a datos con abstracciones de Spring.
- Transacciones.
- Validación e integración con otras APIs.

Una parte enorme de lo que aprendes como "Spring" comienza aquí.

---

## 2.3 ¿Qué son los Spring Projects?

Alrededor de Spring Framework existe un ecosistema de proyectos:

```text
Spring Framework
    │
    ├── Spring Data
    ├── Spring Security
    ├── Spring Batch
    ├── Spring Integration
    ├── Spring Cloud
    └── otros
```

Estos proyectos amplían el ecosistema para resolver problemas concretos.

Por ejemplo:

| Proyecto | Idea principal |
|---|---|
| Spring Data | Acceso a datos y repositorios |
| Spring Security | Autenticación y autorización |
| Spring Batch | Procesamiento por lotes |
| Spring Cloud | Infraestructura para sistemas distribuidos y cloud |
| Spring Integration | Integración entre sistemas |

No necesitas aprender todos para usar Spring Boot.

---

## 2.4 ¿Qué es Spring Boot?

**Spring Boot es una forma de construir aplicaciones Spring con mucha menos configuración manual.**

Su objetivo principal es reducir el trabajo repetitivo que históricamente implicaba configurar una aplicación Spring.

Spring Boot agrega mecanismos como:

- **Auto-configuration**
- **Starters**
- **Servidor embebido**
- Configuración externalizada
- Integración sencilla con herramientas de desarrollo y testing
- Características de producción como Actuator

La relación conceptual es:

```text
Spring Framework
        +
facilidad de configuración
        +
convenciones
        +
auto-configuration
        +
starters
        +
servidor embebido
        +
características de producción
        ↓
   Spring Boot
```

Por eso:

> **Spring Boot no reemplaza Spring Framework.**

Más bien, hace que trabajar con Spring sea mucho más rápido y convencional.

---

# 3. Mapa del ecosistema

Una forma útil de separarlo es esta:

```text
┌─────────────────────────────────────────────────────┐
│                  SPRING ECOSYSTEM                   │
├─────────────────────────────────────────────────────┤
│                                                     │
│  Spring Framework                                   │
│  ├── Core / IoC / DI                                │
│  ├── Beans                                          │
│  ├── Context / ApplicationContext                   │
│  ├── AOP                                             │
│  ├── MVC                                             │
│  ├── Web                                             │
│  ├── Validation                                     │
│  ├── JDBC                                           │
│  └── Transactions                                   │
│                                                     │
│  Spring Projects                                    │
│  ├── Spring Data                                    │
│  ├── Spring Security                                │
│  ├── Spring Batch                                   │
│  ├── Spring Integration                             │
│  └── Spring Cloud                                   │
│                                                     │
│  Spring Boot                                        │
│  ├── Starters                                       │
│  ├── Auto-configuration                             │
│  ├── Embedded server integration                    │
│  ├── Externalized configuration                     │
│  ├── Actuator                                       │
│  └── Spring Boot Test                               │
│                                                     │
└─────────────────────────────────────────────────────┘

         + tecnologías que NO son Spring

         Java / Jakarta / Tomcat / Hibernate /
         Jackson / Maven / Gradle / JUnit /
         Mockito / PostgreSQL / HikariCP / Flyway
```

Esta separación es importante porque muchos principiantes hablan de todo lo anterior como si fuera "Spring".

No lo es.

---

# 4. Spring Framework: principales módulos

Spring Framework está compuesto por diferentes módulos.

| Módulo | Qué hace | Cuándo aparece |
|---|---|---|
| Spring Core | Fundamentos de IoC y DI | Durante toda la aplicación |
| Spring Beans | Gestión de Beans | Inicio y ejecución |
| Spring Context | ApplicationContext e infraestructura de contexto | Inicio y ejecución |
| Spring AOP | Programación orientada a aspectos | Cuando se usa |
| Spring Expression Language | Expresiones en el ecosistema Spring | Cuando se utiliza |
| Spring MVC | Web MVC y endpoints | Peticiones HTTP |
| Spring Web | Infraestructura web | Peticiones HTTP |
| Spring Validation | Integración para validaciones | Entrada de datos |
| Spring JDBC | Abstracciones sobre JDBC | Acceso a BD |
| Spring ORM | Integración con tecnologías ORM | Persistencia |
| Spring Transactions | Abstracción de transacciones | Operaciones transaccionales |

No debes memorizar esta tabla todavía.

Lo importante es reconocer que **Spring Framework no es una única clase o una única librería**. Es un conjunto de módulos que resuelven distintos problemas.

---

# 5. Spring Core: el corazón conceptual

Aquí aparecen varias palabras que vas a ver constantemente:

- IoC
- DI
- Bean
- Container
- ApplicationContext
- Configuration
- Component Scanning

Todas están conectadas.

---

## 5.1 IoC — Inversion of Control

Normalmente, un objeto Java puede crear directamente sus dependencias:

```java
public class UserController {

    private UserService service = new UserService();
}
```

Aquí el `UserController` decide:

> "Yo mismo voy a crear el objeto que necesito."

Con Spring, la idea cambia.

El objeto puede declarar:

> "Necesito un `UserService`."

Y Spring se ocupa de proporcionar esa dependencia.

Eso es parte de la idea de **Inversion of Control (IoC)**.

La responsabilidad de crear y conectar determinados objetos deja de estar exclusivamente en nuestro código y pasa al framework/container.

### Analogía

Imagina una empresa.

Sin Spring:

```text
Empleado
  ↓
busca personalmente a otro empleado
  ↓
lo contrata
  ↓
lo crea
  ↓
lo configura
```

Con Spring:

```text
Empresa / Container
       ↓
conoce qué empleados existen
       ↓
crea empleados
       ↓
los conecta
       ↓
los entrega donde corresponda
```

No significa que Java deje de crear objetos.

Significa que **Spring asume el control de una parte de su creación, configuración y composición**.

---

# 6. DI — Dependency Injection

Dependency Injection es una consecuencia práctica de IoC.

Una **dependencia** es simplemente un objeto que otro objeto necesita para hacer su trabajo.

Por ejemplo:

```java
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
}
```

`UserController` depende de `UserService`.

La dependencia no es creada dentro del Controller:

```java
// No hacemos esto
this.userService = new UserService();
```

En cambio, se recibe desde afuera.

Eso es **inyección de dependencias**.

---

## 6.1 Constructor Injection

El enfoque habitual recomendado es la inyección por constructor:

```java
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
}
```

Conceptualmente:

```text
Spring Container
      │
      ├── crea UserService
      │
      ├── crea UserController
      │
      └── detecta que UserController necesita UserService
                         │
                         ▼
                 inyecta UserService
```

Esto produce una relación clara:

```text
UserController
      │
      ▼
UserService
      │
      ▼
UserRepository
```

---

# 7. Bean

Un **Bean** es un objeto que está administrado por el Spring Container.

Esta definición es mucho más importante que memorizar una anotación.

Por ejemplo, una instancia de:

```java
UserService
```

puede ser un simple objeto Java.

Pero si Spring la registra y administra dentro de su contexto, pasa a ser un **Spring Bean**.

Por lo tanto:

```text
Objeto Java
   │
   ├── puede ser un objeto normal
   │
   └── puede convertirse en Spring Bean
```

No todos los objetos Java son Beans.

---

# 8. Container

El **Spring Container** es la infraestructura responsable de administrar los Beans.

De forma conceptual:

```text
Spring Container
       │
       ├── conoce Beans
       ├── crea Beans
       ├── configura Beans
       ├── conecta dependencias
       ├── administra su ciclo de vida
       └── permite obtenerlos desde el contexto
```

Una forma útil de imaginarlo es como un **administrador central de objetos**.

---

# 9. ApplicationContext

`ApplicationContext` es una de las abstracciones centrales del contexto de Spring.

Es una implementación/representación de alto nivel del contenedor que proporciona:

- acceso al contexto de la aplicación;
- registro y gestión de Beans;
- resolución de dependencias;
- configuración;
- integración con diferentes servicios del ecosistema Spring.

Para un principiante, una simplificación útil es:

```text
ApplicationContext
        ↓
"el entorno/contexto donde Spring administra la aplicación"
```

Puedes pensar:

```text
ApplicationContext
       │
       ├── UserController
       ├── UserService
       ├── UserRepository
       ├── DataSource
       ├── ...
       └── otros Beans
```

---

# 10. Component Scanning

Spring necesita descubrir qué clases deben convertirse en Beans.

Para eso puede realizar **component scanning**.

Cuando encuentra anotaciones como:

```java
@Component
@Service
@Repository
@Controller
@RestController
```

puede registrar esas clases como componentes administrados por Spring, de acuerdo con la configuración del contexto.

Conceptualmente:

```text
Código fuente
   ↓
Component Scanning
   ↓
clases candidatas
   ↓
Bean Definitions
   ↓
Beans
```

Esto explica parte de la "magia" que ves cuando escribes:

```java
@Service
public class UserService {
}
```

No es que `@Service` por sí sola cree mágicamente una instancia en cualquier contexto.

La anotación hace que la clase sea candidata a ser detectada y registrada por la infraestructura de Spring.

---

# 11. Anotaciones fundamentales

No necesitas memorizar todas las anotaciones de Spring. Primero necesitas reconocer su familia.

## 11.1 Componentes

```java
@Component
@Service
@Repository
@Controller
@RestController
```

Idea general:

```text
@Component
   ↓
componente gestionado por Spring

@Service
   ↓
componente orientado a lógica de negocio

@Repository
   ↓
componente orientado a persistencia

@Controller
   ↓
componente MVC para web

@RestController
   ↓
Controller cuyos métodos suelen devolver datos para respuestas HTTP,
habitualmente JSON
```

`@Service`, `@Repository`, `@Controller` y `@RestController` son especializaciones conceptuales dentro del modelo de componentes de Spring.

---

## 11.2 Inyección

```java
@Autowired
```

`@Autowired` permite expresar que una dependencia debe ser resuelta por Spring.

Aun así, cuando existe un único constructor, puedes utilizar constructor injection sin necesidad de escribir `@Autowired` explícitamente.

Ejemplo:

```java
@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }
}
```

---

## 11.3 Configuración

```java
@Configuration
@Bean
@ComponentScan
```

Ejemplo:

```java
@Configuration
public class AppConfig {

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }
}
```

La idea general:

```text
@Configuration
      ↓
clase de configuración

@Bean
      ↓
el método declara un objeto que Spring debe administrar
```

---

## 11.4 Web

```java
@GetMapping
@PostMapping
@PutMapping
@DeleteMapping
@RequestMapping

@PathVariable
@RequestParam
@RequestBody
```

Ejemplo:

```java
@GetMapping("/users/{id}")
public User getUser(@PathVariable Long id) {
    ...
}
```

Estas anotaciones ayudan a conectar:

```text
HTTP request
     ↓
endpoint
     ↓
método Java
```

---

## 11.5 Validación

```java
@Valid
@NotNull
@NotBlank
@Size
```

Normalmente las validaciones se aplican a DTOs u objetos de entrada.

Ejemplo:

```java
public class CreateUserRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String email;
}
```

La validación permite rechazar entradas inválidas antes de que la lógica de negocio continúe, según cómo se configure el flujo.

---

## 11.6 Persistencia

```java
@Entity
@Id
@GeneratedValue
```

Estas pertenecen al mundo de Jakarta Persistence / JPA, no a Spring Framework en sí.

Por ejemplo:

```java
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;
}
```

Esto se conecta posteriormente con Spring Data JPA, Hibernate y la base de datos.

---

# 12. Spring Boot

Spring Boot tiene varios conceptos fundamentales.

## 12.1 Opinionated

Spring Boot es "opinionated" porque toma decisiones convencionales para evitar que el desarrollador tenga que configurar absolutamente todo desde cero.

Eso no significa:

> "Spring Boot te obliga a hacer las cosas de una única manera."

Significa más bien:

> "Si haces una aplicación típica de esta forma, existen valores y configuraciones predeterminadas razonables."

Esto reduce decisiones repetitivas.

---

# 13. Starters

Un **Starter** agrupa dependencias típicamente necesarias para una determinada capacidad.

Ejemplos:

| Starter | Objetivo principal |
|---|---|
| `spring-boot-starter-web` | Web / REST |
| `spring-boot-starter-data-jpa` | Persistencia JPA |
| `spring-boot-starter-security` | Seguridad |
| `spring-boot-starter-validation` | Validación |
| `spring-boot-starter-test` | Testing |
| `spring-boot-starter-actuator` | Monitoring / management |

Una manera sencilla de imaginar un Starter:

```text
Necesito REST
     ↓
spring-boot-starter-web
     ↓
dependencias relacionadas
     ↓
proyecto listo para empezar a configurar una aplicación web
```

Un Starter no es "todo el framework web dentro de una dependencia".

Es principalmente un **conjunto conveniente de dependencias relacionadas y compatibles**.

---

# 14. Auto-configuration

La **auto-configuration** intenta configurar automáticamente infraestructura razonable en función de lo que existe en la aplicación.

Por ejemplo, si incluyes el Starter de JPA:

```text
spring-boot-starter-data-jpa
```

Spring Boot puede detectar en el classpath las tecnologías relacionadas y crear/configurar gran parte de la infraestructura que una aplicación JPA normalmente necesita.

Conceptualmente:

```text
Dependencias
     ↓
Classpath
     ↓
Spring Boot detecta condiciones
     ↓
Auto-configuration
     ↓
Beans y configuración
     ↓
infraestructura disponible
```

Importante:

> Spring Boot no "adivina" arbitrariamente lo que quieres.

La auto-configuración se apoya en información como:

- clases presentes en el classpath;
- propiedades de configuración;
- Beans existentes;
- condiciones;
- qué configuraciones están habilitadas;
- qué infraestructura ya existe.

La auto-configuración se puede personalizar o reemplazar.

---

# 15. @SpringBootApplication

Una aplicación Spring Boot normalmente comienza con:

```java
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

Conceptualmente:

```text
@SpringBootApplication
        │
        ├── @Configuration
        ├── @EnableAutoConfiguration
        └── @ComponentScan
```

Estas tres ideas son fundamentales.

## `@Configuration`

Indica que la clase puede actuar como fuente de configuración de Beans.

## `@EnableAutoConfiguration`

Activa el mecanismo de auto-configuración de Spring Boot.

## `@ComponentScan`

Indica que Spring debe buscar componentes configurados, según el alcance de escaneo.

No necesitas imaginar que la anotación "es magia".

Puedes pensar:

```text
@SpringBootApplication
        ↓
configuración + auto-configuración + component scanning
```

---

# 16. ¿Qué hace SpringApplication.run()?

Cuando ejecutas:

```java
SpringApplication.run(Application.class, args);
```

Spring Boot empieza a construir el entorno de la aplicación.

De forma conceptual:

```text
main()
  ↓
SpringApplication.run()
  ↓
preparación del entorno
  ↓
creación del ApplicationContext
  ↓
lectura de configuración
  ↓
auto-configuration
  ↓
component scanning
  ↓
registro de Bean Definitions
  ↓
creación de Beans
  ↓
Dependency Injection
  ↓
servidor embebido / infraestructura web
  ↓
aplicación lista
```

Hay muchos detalles internos que no necesitas memorizar al principio.

Lo importante es entender:

> **Antes de recibir la primera petición HTTP, Spring Boot ya tuvo que preparar gran parte de la infraestructura de la aplicación.**

---

# 17. Flujo de arranque de Spring Boot

Este es uno de los modelos mentales más importantes.

```text
mvn spring-boot:run
        │
        ▼
      main()
        │
        ▼
SpringApplication.run()
        │
        ▼
    Environment
        │
        ▼
ApplicationContext
        │
        ├───────────────┐
        ▼               ▼
Component Scanning   Auto-Configuration
        │               │
        └───────┬───────┘
                ▼
        Bean Definitions
                │
                ▼
          Bean Creation
                │
                ▼
       Dependency Injection
                │
                ▼
      Embedded Web Server
                │
                ▼
       Application Ready
```

Después de esto la aplicación queda esperando peticiones.

Lo mismo ocurre conceptualmente cuando ejecutas un JAR:

```bash
java -jar application.jar
```

La diferencia está en cómo se ejecuta el artefacto, no en el modelo mental fundamental de inicialización.

---

# 18. Estructura típica de un proyecto

Una estructura frecuente es:

```text
src/
├── main/
│   ├── java/
│   │   └── com.example.app/
│   │       ├── Application.java
│   │       ├── controller/
│   │       ├── service/
│   │       ├── repository/
│   │       ├── entity/
│   │       ├── dto/
│   │       ├── config/
│   │       └── exception/
│   │
│   └── resources/
│       ├── application.properties
│       ├── application.yml
│       ├── static/
│       └── templates/
│
└── test/
    └── java/
```

Importante:

> **Esta estructura es una convención de organización, no una obligación rígida de Spring Boot.**

Puedes tener una arquitectura distinta.

---

## 18.1 `src/main/java`

Contiene el código principal de la aplicación.

---

## 18.2 `controller/`

Suele contener Controllers.

Por ejemplo:

```java
@RestController
@RequestMapping("/users")
public class UserController {
}
```

Responsabilidad típica:

```text
HTTP
 ↓
Controller
```

El Controller recibe la entrada y coordina la llamada hacia la lógica apropiada.

---

## 18.3 `service/`

Suele contener la lógica de negocio.

```java
@Service
public class UserService {
}
```

Por ejemplo:

```text
Controller
    ↓
Service
    ↓
reglas de negocio
```

---

## 18.4 `repository/`

Suele contener el acceso a datos.

Con Spring Data JPA podrías encontrar:

```java
public interface UserRepository
        extends JpaRepository<User, Long> {
}
```

La implementación concreta puede ser proporcionada por Spring Data.

---

## 18.5 `entity/`

Suele contener entidades persistentes:

```java
@Entity
public class User {
}
```

No es obligatorio llamarla `entity`.

Es simplemente una convención común.

---

## 18.6 `dto/`

DTO significa **Data Transfer Object**.

Los DTOs suelen representar datos que entran o salen de la API sin necesariamente exponer directamente la entidad de persistencia.

Ejemplo:

```java
public class CreateUserRequest {
    private String name;
    private String email;
}
```

---

## 18.7 `config/`

Suele contener clases de configuración:

```java
@Configuration
public class AppConfig {
}
```

---

## 18.8 `exception/`

Suele contener excepciones personalizadas y handlers.

---

## 18.9 `src/main/resources`

Contiene recursos y configuración.

Por ejemplo:

```text
application.properties
application.yml
```

---

## 18.10 `src/test/java`

Contiene tests.

Una estructura típica:

```text
src/test/java/
    com.example.app/
        UserServiceTest.java
        UserControllerTest.java
```

---

# 19. El flujo HTTP completo

Esta es la parte central del mapa.

Imagina:

```http
GET /users/10
```

Un modelo conceptual simplificado es:

```text
CLIENT
  │
  │ HTTP Request
  ▼
Embedded Server
(Tomcat)
  │
  ▼
DispatcherServlet
  │
  ▼
HandlerMapping
  │
  ▼
Controller
  │
  ▼
Service
  │
  ▼
Repository
  │
  ▼
JPA
  │
  ▼
Hibernate
  │
  ▼
JDBC
  │
  ▼
Database
```

Y la vuelta:

```text
Database
   ↓
JDBC
   ↓
Hibernate
   ↓
JPA
   ↓
Repository
   ↓
Service
   ↓
Controller
   ↓
Spring MVC
   ↓
Jackson
   ↓
HTTP Response
   ↓
Client
```

**Este diagrama es una simplificación conceptual.**

Dependiendo de la configuración y de las tecnologías utilizadas puede haber componentes adicionales, capas omitidas o caminos diferentes.

Aun así, como mapa inicial es extremadamente útil.

---

# 20. ¿Qué es HTTP?

Antes de Spring hay un protocolo: **HTTP**.

Una petición puede contener:

```text
HTTP Request
├── Method
├── URL
├── Headers
└── Body
```

Ejemplo:

```http
POST /users HTTP/1.1
Content-Type: application/json
Authorization: Bearer ...

{
    "name": "Mikko",
    "email": "mikko@example.com"
}
```

Una respuesta contiene, entre otras cosas:

```text
HTTP Response
├── Status Code
├── Headers
└── Body
```

Ejemplo:

```http
HTTP/1.1 200 OK
Content-Type: application/json

{
    "id": 10,
    "name": "Mikko"
}
```

Spring no inventó HTTP.

Spring utiliza HTTP para construir aplicaciones web.

---

# 21. Tomcat

**Tomcat** es un servidor web/servlet container externo al proyecto Spring.

En muchas aplicaciones Spring Boot web, Tomcat se utiliza como servidor embebido.

"Embebido" significa que el servidor forma parte de la aplicación que ejecutas.

Tradicionalmente se podría desplegar una aplicación en un servidor previamente instalado.

Con Spring Boot, una aplicación típica puede incluir el servidor como dependencia y arrancarlo junto con la propia aplicación.

Conceptualmente:

```text
java -jar application.jar
        │
        ├── tu aplicación
        ├── Spring
        └── servidor embebido
```

Por eso normalmente no necesitas instalar manualmente un Tomcat externo para ejecutar una aplicación web Spring Boot típica.

---

# 22. DispatcherServlet

El `DispatcherServlet` es una pieza central de **Spring MVC**.

Se lo conoce como **Front Controller**.

¿Por qué?

Porque actúa como punto central de entrada para las peticiones HTTP dentro del modelo MVC de Spring.

Conceptualmente:

```text
HTTP Request
     ↓
DispatcherServlet
     ↓
¿qué endpoint debe manejarla?
     ↓
Controller
```

Recibe la petición y coordina el procesamiento posterior.

No contiene toda tu lógica de negocio.

Su función principal es **orquestar el flujo web**.

---

# 23. HandlerMapping

Una pregunta importante al recibir:

```http
GET /users/10
```

es:

> ¿Qué método Java debe ejecutarse?

Spring necesita mapear:

```text
HTTP method + URL
        ↓
handler
```

`HandlerMapping` participa en esa resolución.

Por ejemplo:

```java
@GetMapping("/users/{id}")
public User getUser(@PathVariable Long id) {
    ...
}
```

El framework puede asociar esa ruta con ese método.

Conceptualmente:

```text
GET /users/10
      ↓
HandlerMapping
      ↓
UserController.getUser(...)
```

---

# 24. Controller

El Controller es la capa de entrada web.

Ejemplo:

```java
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.findById(id);
    }
}
```

Su responsabilidad típica:

- recibir la entrada HTTP;
- extraer parámetros o body;
- delegar al Service;
- construir o devolver el resultado apropiado.

Una buena regla mental:

> **Controller = frontera entre HTTP y la aplicación.**

No significa que absolutamente ningún Controller pueda contener lógica. Significa que la lógica de negocio principal normalmente se mantiene fuera del Controller.

---

# 25. Service

El Service suele contener la **lógica de negocio**.

Ejemplo:

```java
@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User findById(Long id) {
        return repository.findById(id)
                .orElseThrow();
    }
}
```

Un Service puede:

- validar reglas de negocio;
- coordinar varias operaciones;
- llamar a varios repositorios;
- decidir qué operación corresponde;
- definir límites transaccionales.

Modelo mental:

```text
Controller
    ↓
"¿Qué quiere hacer el usuario?"
    ↓
Service
    ↓
"¿Qué debe hacer el sistema?"
```

---

# 26. Repository

El Repository representa la interacción con los datos.

Con Spring Data JPA puede aparecer como:

```java
public interface UserRepository
        extends JpaRepository<User, Long> {
}
```

Desde nuestra perspectiva:

```text
Service
   ↓
Repository
   ↓
persistencia
```

El Repository suele abstraer operaciones como:

```text
find
save
delete
exists
etc.
```

En Spring Data, muchas implementaciones pueden ser generadas o proporcionadas por el framework de forma que el desarrollador no tenga que escribir manualmente todo el código de acceso a datos.

---

# 27. JPA

**JPA** es una especificación de Jakarta Persistence.

No es Hibernate.

JPA define conceptos y APIs para mapear objetos Java a datos persistentes.

Por ejemplo:

```java
@Entity
public class User {

    @Id
    private Long id;
}
```

La idea general es **ORM**:

> Object-Relational Mapping.

Conceptualmente:

```text
Java Object
     ↕
 ORM mapping
     ↕
Database row
```

JPA define reglas y APIs.

Necesita una implementación.

---

# 28. Hibernate

**Hibernate** es una implementación de JPA muy utilizada.

Una forma correcta de pensar la relación es:

```text
JPA
 ↓
especificación / API
 ↓
Hibernate
 ↓
implementación
```

Por eso la frase:

> "JPA convierte la base de datos."

no es suficientemente precisa.

Una mejor forma de pensar es:

```text
JPA define cómo debería funcionar la persistencia ORM
Hibernate implementa esas APIs y comportamiento
```

Spring Data JPA se coloca por encima de esta capa para simplificar todavía más el acceso a datos.

---

# 29. JDBC

**JDBC** es una API de Java para interactuar con bases de datos relacionales.

A un nivel conceptual:

```text
Aplicación
    ↓
Hibernate
    ↓
JDBC
    ↓
Database Driver
    ↓
Database
```

JDBC es una de las capas que permiten que una aplicación Java se comunique con una base de datos relacional.

No necesitas imaginar que Hibernate "habla mágicamente" con PostgreSQL.

Existe infraestructura de acceso a datos debajo.

---

# 30. Database

Finalmente está la base de datos:

```text
PostgreSQL
MySQL
MariaDB
etc.
```

Por ejemplo, el sistema podría terminar ejecutando una consulta equivalente conceptualmente a:

```sql
SELECT *
FROM users
WHERE id = 10;
```

Hibernate puede generar SQL a partir del modelo y operación correspondiente.

La base de datos ejecuta la consulta y devuelve datos.

---

# 31. Jackson

Una aplicación Java normalmente trabaja con objetos:

```java
User
```

Pero HTTP suele transportar texto estructurado como JSON:

```json
{
  "id": 10,
  "name": "Mikko"
}
```

**Jackson** es una librería externa muy utilizada para convertir entre Java y JSON.

Conceptualmente:

```text
Java Object
     ↓
Jackson
     ↓
JSON
```

y al revés:

```text
JSON
     ↓
Jackson
     ↓
Java Object
```

Por ejemplo:

```java
public User getUser() {
    return user;
}
```

Spring MVC puede utilizar Jackson para serializar ese objeto en JSON para la respuesta.

---

# 32. El flujo de un GET completo

Tomemos:

```http
GET /users/10
```

## Paso 1 — Cliente

El cliente podría ser:

- navegador;
- Postman;
- una aplicación móvil;
- otro backend;
- frontend JavaScript.

Envía una petición HTTP.

---

## Paso 2 — La petición llega al servidor

La petición llega al servidor embebido, por ejemplo Tomcat.

```text
Client
  ↓
Tomcat
```

---

## Paso 3 — Spring MVC recibe la petición

La infraestructura web de Spring procesa la petición a través del `DispatcherServlet`.

```text
Tomcat
  ↓
DispatcherServlet
```

---

## Paso 4 — Se busca el endpoint

Spring determina qué handler corresponde a:

```http
GET /users/10
```

```text
DispatcherServlet
      ↓
HandlerMapping
      ↓
UserController.getUser(...)
```

---

## Paso 5 — Controller

El Controller recibe el `id`:

```java
@GetMapping("/users/{id}")
public User getUser(@PathVariable Long id) {
    return userService.findById(id);
}
```

Aquí:

```text
{id}
 ↓
@PathVariable
 ↓
10
```

---

## Paso 6 — Service

El Controller delega:

```java
userService.findById(10L);
```

---

## Paso 7 — Repository

El Service utiliza:

```java
userRepository.findById(10L);
```

---

## Paso 8 — JPA / Hibernate

Spring Data JPA coordina la operación y la infraestructura de persistencia utiliza JPA.

Hibernate puede ejecutar la operación ORM.

---

## Paso 9 — JDBC

La comunicación con la base de datos pasa por infraestructura basada en JDBC.

---

## Paso 10 — Database

La base de datos encuentra el usuario.

```text
Database
   ↓
row
```

---

## Paso 11 — Resultado hacia Java

El resultado vuelve a la capa de persistencia.

Conceptualmente:

```text
Database
   ↓
JDBC
   ↓
Hibernate
   ↓
JPA
   ↓
Repository
   ↓
Service
```

---

## Paso 12 — Controller devuelve resultado

El Service devuelve un `User`.

```text
Service
   ↓
User
   ↓
Controller
```

---

## Paso 13 — Serialización

Spring utiliza su infraestructura web y Jackson para producir JSON.

```text
User
 ↓
Jackson
 ↓
JSON
```

---

## Paso 14 — Respuesta HTTP

Finalmente:

```http
HTTP/1.1 200 OK
Content-Type: application/json
```

```json
{
  "id": 10,
  "name": "Mikko"
}
```

---

## Paso 15 — Cliente

El cliente recibe la respuesta.

El flujo resumido:

```text
Client
  ↓
HTTP
  ↓
Tomcat
  ↓
DispatcherServlet
  ↓
HandlerMapping
  ↓
Controller
  ↓
Service
  ↓
Repository
  ↓
JPA / Hibernate
  ↓
JDBC
  ↓
Database
  ↓
JDBC
  ↓
Hibernate
  ↓
Repository
  ↓
Service
  ↓
Controller
  ↓
Jackson
  ↓
HTTP Response
  ↓
Client
```

---

# 33. El flujo de un POST completo

Ahora:

```http
POST /users
Content-Type: application/json
```

Body:

```json
{
  "name": "Mikko",
  "email": "mikko@example.com"
}
```

El flujo conceptual:

```text
JSON
  ↓
HTTP
  ↓
Tomcat
  ↓
DispatcherServlet
  ↓
HandlerMapping
  ↓
Controller
  ↓
@RequestBody
  ↓
Java DTO
  ↓
Validation
  ↓
Service
  ↓
Repository
  ↓
JPA / Hibernate
  ↓
JDBC
  ↓
Database
```

Y de regreso:

```text
Database
   ↓
Entity
   ↓
Service
   ↓
Controller
   ↓
Java Object / DTO
   ↓
Jackson
   ↓
JSON
   ↓
HTTP Response
```

---

## ¿Qué hace `@RequestBody`?

Conceptualmente:

```text
HTTP Body
   ↓
JSON
   ↓
Jackson
   ↓
Java object
```

Por ejemplo:

```java
@PostMapping("/users")
public User createUser(@RequestBody CreateUserRequest request) {
    ...
}
```

El body JSON se transforma en el objeto de entrada correspondiente.

---

# 34. ¿Dónde aparece la validación?

Podemos tener:

```java
public class CreateUserRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String email;
}
```

Y:

```java
@PostMapping("/users")
public User create(
        @Valid @RequestBody CreateUserRequest request) {
    ...
}
```

El modelo conceptual es:

```text
JSON
  ↓
@RequestBody
  ↓
CreateUserRequest
  ↓
@Valid
  ↓
Validation
  ↓
Controller
  ↓
Service
```

Si la entrada no cumple las reglas, la aplicación puede detener el flujo y producir una respuesta HTTP de error.

---

# 35. ¿Dónde entra Dependency Injection en todo esto?

La inyección no sucede en medio de cada petición como si Spring tuviera que "crear" constantemente los Services.

La infraestructura de Beans se prepara principalmente durante el arranque.

Por ejemplo:

```java
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
}
```

Y:

```java
@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }
}
```

Conceptualmente:

```text
           ApplicationContext
                  │
        ┌─────────┴──────────┐
        ▼                    ▼
 UserController          UserService
        │                    │
        │                    ▼
        │              UserRepository
        │
        └────── dependency ──┘
```

Spring construye y conecta estas piezas durante la inicialización del contexto.

Después, cuando llega una petición:

```text
Request
  ↓
existing Controller Bean
  ↓
existing Service Bean
  ↓
existing Repository infrastructure
```

Esta diferencia es importante.

> **Crear y conectar Beans es principalmente una tarea de inicialización; procesar peticiones es una tarea de runtime.**

---

# 36. Beans y Controller / Service / Repository

Una clase puede ser registrada como Bean mediante diferentes mecanismos.

Por ejemplo:

```java
@Service
public class UserService {
}
```

```java
@Repository
public interface UserRepository {
}
```

```java
@RestController
public class UserController {
}
```

También puedes declarar Beans explícitamente:

```java
@Configuration
public class AppConfig {

    @Bean
    public MyComponent myComponent() {
        return new MyComponent();
    }
}
```

Por eso:

```text
Controller
Service
Repository
Configuration
otros componentes
        ↓
pueden formar parte del ApplicationContext como Beans
```

---

# 37. Spring versus Java

Es útil separar responsabilidades.

| Responsabilidad | Java | Spring |
|---|---:|---:|
| Definir clases | ✓ | |
| Crear métodos | ✓ | |
| Ejecutar instrucciones | ✓ | |
| Objetos Java | ✓ | |
| IoC | | ✓ |
| DI | | ✓ |
| Gestión de Beans | | ✓ |
| Routing HTTP | | ✓ |
| MVC web | | ✓ |
| Gestión declarativa de transacciones | | ✓ |
| Auto-configuration | | ✓ |

Esto no significa que Spring "reemplace" a Java.

La aplicación sigue siendo un programa Java.

Spring proporciona una infraestructura encima de Java.

---

# 38. Tecnologías externas

Estas tecnologías suelen aparecer en un proyecto Spring Boot, pero no por ello pasan a pertenecer a Spring.

| Tecnología | ¿Pertenece a Spring? | Función |
|---|---|---|
| JUnit | No | Testing |
| Mockito | No | Mocking |
| Hibernate | No | ORM / persistencia |
| Jackson | No | JSON |
| Tomcat | No | Servidor web / Servlet |
| Maven | No | Build y dependencias |
| Gradle | No | Build y dependencias |
| PostgreSQL | No | Base de datos |
| HikariCP | No | Connection pool |
| Flyway | No | Migraciones |
| JDBC | No | API Java de acceso a BD |

Spring Boot puede integrarse con todas estas tecnologías.

---

# 39. Maven y Gradle

Maven y Gradle son **herramientas de build**, no frameworks web.

Se encargan, entre otras cosas, de:

- gestionar dependencias;
- compilar;
- ejecutar tests;
- generar artefactos;
- ejecutar tareas de build;
- empaquetar la aplicación.

Por ejemplo, Maven utiliza:

```text
pom.xml
```

Un Starter puede aparecer como dependencia:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

La relación conceptual es:

```text
Maven
  ↓
obtiene dependencias
  ↓
Spring Boot + Spring Framework + bibliotecas
  ↓
compilación / empaquetado
```

---

# 40. ¿Qué es el classpath?

El **classpath** es el conjunto de clases y recursos que están disponibles para la aplicación en tiempo de ejecución.

Es extremadamente importante para comprender la auto-configuración.

Una simplificación útil:

```text
Classpath
    ↓
"¿Qué librerías y clases están disponibles?"
```

Por ejemplo, si existe una determinada librería:

```text
JPA classes presentes
```

Spring Boot puede utilizar esa información como parte de sus decisiones de auto-configuración.

---

# 41. Configuración

Spring Boot utiliza configuración externa para que muchos valores no tengan que estar codificados directamente en Java.

Comúnmente:

```text
application.properties
```

o:

```text
application.yml
```

Ejemplo:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/mydb
    username: postgres
    password: secret
```

La idea conceptual es:

```text
application.yml
       ↓
Environment
       ↓
Spring Boot
       ↓
Beans / auto-configuration
```

---

# 42. Profiles

Los **Profiles** permiten activar configuraciones diferentes para distintos entornos.

Por ejemplo:

```text
application-dev.yml
application-prod.yml
```

Podrías tener:

```text
dev
  ↓
base de datos local

prod
  ↓
base de datos de producción
```

El objetivo es evitar mezclar configuraciones de distintos entornos.

Conceptualmente:

```text
Profile
   ↓
selecciona configuración
   ↓
Environment
   ↓
aplicación
```

---

# 43. Manejo de errores

En aplicaciones REST es habitual centralizar la transformación de excepciones en respuestas HTTP.

Dos anotaciones importantes:

```java
@RestControllerAdvice
@ExceptionHandler
```

Conceptualmente:

```text
HTTP Request
    ↓
Controller
    ↓
Service
    ↓
Exception
    ↓
Exception Handler
    ↓
HTTP Error Response
```

Ejemplo conceptual:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound() {
        ...
    }
}
```

Esto evita que cada Controller tenga que repetir la misma lógica de transformación de errores.

---

# 44. Seguridad

Spring Security es un proyecto del ecosistema Spring orientado a seguridad.

A nivel conceptual puedes imaginar:

```text
HTTP Request
     ↓
Security Filter Chain
     ↓
Authentication
     ↓
Authorization
     ↓
Controller
```

### Authentication

Responde:

> ¿Quién eres?

### Authorization

Responde:

> ¿Tienes permiso para hacer esto?

Por ejemplo:

```text
Request
  ↓
¿usuario autenticado?
  ↓
¿tiene permiso?
  ↓
Controller
```

Este documento no necesita entrar todavía en JWT, OAuth2, sesiones o detalles de configuración.

Lo importante es saber **dónde se ubica Security**.

---

# 45. Transacciones

Una transacción agrupa operaciones de datos en una unidad lógica.

La anotación:

```java
@Transactional
```

permite declarar un límite transaccional.

Frecuentemente se coloca en la capa Service:

```java
@Service
public class TransferService {

    @Transactional
    public void transfer(...) {
        ...
    }
}
```

Modelo mental:

```text
Controller
    ↓
Service
    ↓
@Transactional
    ↓
Repository
    ↓
Database
```

¿Por qué suele pertenecer al Service?

Porque la transacción normalmente representa una **operación de negocio completa**, que puede involucrar varias operaciones de persistencia.

Por ejemplo:

```text
transferencia bancaria
    ↓
debit account A
    ↓
credit account B
```

Ambas operaciones deberían participar en una misma unidad transaccional cuando corresponde.

---

# 46. Testing: ¿dónde encaja?

Testing no es "algo que está afuera" de la arquitectura.

Hay herramientas diferentes para diferentes niveles.

## JUnit

Framework/librería ampliamente utilizado para tests en Java.

```text
Test
 ↓
JUnit
```

---

## Mockito

Sirve para crear mocks y dobles de prueba.

Ejemplo conceptual:

```text
Service
   ↓
Mock Repository
```

Esto permite probar el Service sin depender necesariamente de una base de datos real.

---

## Spring Boot Test

Proporciona soporte para ejecutar tests con infraestructura de Spring Boot.

Puede involucrar:

```text
Spring ApplicationContext
```

dependiendo del tipo de test.

---

## MockMvc

Permite probar la capa web de una aplicación MVC sin tener que convertir cada test de Controller en una prueba manual desde un navegador.

Conceptualmente:

```text
Test
 ↓
MockMvc
 ↓
Controller / Spring MVC
```

---

## Testcontainers

Permite ejecutar dependencias reales en contenedores durante pruebas, por ejemplo una base de datos.

Conceptualmente:

```text
Integration Test
      ↓
Testcontainers
      ↓
PostgreSQL real en container
```

---

## Tipos de tests

Una simplificación útil:

```text
Unit Test
   ↓
clase aislada
   ↓
JUnit + Mockito

Integration Test
   ↓
varias piezas reales
   ↓
Spring Context / Repository / DB

Web Test
   ↓
Controller / HTTP layer
   ↓
MockMvc
```

---

# 47. TDD y BDD

### TDD — Test-Driven Development

La idea general:

```text
Test
 ↓
Implementación
 ↓
Refactor
```

El test ayuda a conducir el desarrollo.

### BDD — Behavior-Driven Development

Se enfoca más en el comportamiento esperado del sistema desde una perspectiva cercana al lenguaje del negocio.

No es necesario aprender TDD o BDD aquí en profundidad.

Lo importante es no confundirlos con JUnit o Mockito:

```text
TDD / BDD
   ↓
prácticas / enfoques de desarrollo

JUnit / Mockito
   ↓
herramientas utilizadas para probar
```

---

# 48. Auto-configuration en un ejemplo

Imagina que tu proyecto incluye:

```text
spring-boot-starter-data-jpa
```

El modelo mental sería:

```text
1. Maven resuelve dependencias
          ↓
2. Librerías JPA/Hibernate quedan en el classpath
          ↓
3. Spring Boot inicia
          ↓
4. Se evalúan condiciones de auto-configuration
          ↓
5. Spring Boot detecta infraestructura disponible
          ↓
6. Configura Beans necesarios cuando las condiciones se cumplen
          ↓
7. Tu aplicación puede trabajar con JPA
```

Esto puede involucrar propiedades, Beans y condiciones adicionales.

No significa:

```text
"agrego una dependencia y Spring sabe absolutamente todo"
```

Significa:

```text
"Spring Boot tiene convenciones e infraestructura para configurar
automáticamente escenarios comunes"
```

---

# 49. El modelo mental completo de una aplicación

Ahora podemos combinar todo:

```text
                         STARTUP
                            │
                            ▼
                  SpringApplication.run()
                            │
                            ▼
                    ApplicationContext
                            │
             ┌──────────────┴──────────────┐
             ▼                             ▼
    Component Scanning              Auto-Configuration
             │                             │
             └──────────────┬──────────────┘
                            ▼
                     Bean Definitions
                            │
                            ▼
                      Bean Creation
                            │
                            ▼
                   Dependency Injection
                            │
                            ▼
                    Embedded Web Server
                            │
                            ▼
                      APPLICATION READY
                            │
                            ▼
                       HTTP REQUEST
                            │
                            ▼
                          Tomcat
                            │
                            ▼
                    DispatcherServlet
                            │
                            ▼
                      HandlerMapping
                            │
                            ▼
                       Controller
                            │
                            ▼
                         Service
                            │
                            ▼
                       Repository
                            │
                            ▼
                     JPA / Hibernate
                            │
                            ▼
                           JDBC
                            │
                            ▼
                        Database
                            │
                            ▼
                        resultado
                            │
                            ▼
                       Repository
                            │
                            ▼
                         Service
                            │
                            ▼
                       Controller
                            │
                            ▼
                         Jackson
                            │
                            ▼
                      HTTP RESPONSE
                            │
                            ▼
                          CLIENT
```

Este es el mapa que deberías tener en la cabeza.

---

# 50. Flujo definitivo en 25 pasos

Puedes resumir el funcionamiento completo así:

1. El programa Java entra por `main()`.
2. Se ejecuta `SpringApplication.run(...)`.
3. Spring Boot prepara el entorno de ejecución.
4. Se carga la configuración disponible.
5. Se crea el `ApplicationContext`.
6. Se activa la configuración de Spring Boot.
7. Se evalúan mecanismos de auto-configuración.
8. Spring realiza component scanning.
9. Encuentra componentes como Controllers y Services.
10. Se registran definiciones de Beans.
11. Spring crea los Beans necesarios.
12. Spring resuelve sus dependencias.
13. Se realiza Dependency Injection.
14. Se inicializa la infraestructura de la aplicación.
15. Se inicia el servidor web embebido, por ejemplo Tomcat.
16. La aplicación alcanza un estado listo para recibir tráfico.
17. Un cliente envía una petición HTTP.
18. Tomcat recibe la petición.
19. `DispatcherServlet` coordina el procesamiento de Spring MVC.
20. Spring determina el handler mediante mecanismos de mapping.
21. Se ejecuta el Controller.
22. El Controller delega al Service y éste puede utilizar un Repository.
23. La infraestructura de persistencia utiliza JPA/Hibernate/JDBC para llegar a la base de datos.
24. El resultado vuelve hacia Controller y se serializa, por ejemplo mediante Jackson.
25. Spring genera la respuesta HTTP y el cliente la recibe.

No todas las aplicaciones ejecutan exactamente estas 25 etapas ni exactamente en este orden interno, pero este modelo es una representación útil del flujo típico.

---

# 51. ¿Qué ocurre antes de la primera petición?

Este detalle es muy importante.

Antes de:

```text
GET /users/10
```

ya ocurrió gran parte de esto:

```text
main()
 ↓
SpringApplication.run()
 ↓
ApplicationContext
 ↓
Configuration
 ↓
Component Scanning
 ↓
Auto-Configuration
 ↓
Bean Definitions
 ↓
Bean Creation
 ↓
Dependency Injection
 ↓
Embedded Server
 ↓
Application Ready
```

Es decir:

> **El sistema no espera a recibir la primera petición para descubrir qué es `UserService`.**

Spring prepara el contexto primero.

Por eso cuando llega una petición puede trabajar con Beans que ya forman parte del contexto de la aplicación.

---

# 52. ¿Qué hace Spring automáticamente y qué hacemos nosotros?

## Nosotros normalmente definimos

```text
Clases
Métodos
Reglas de negocio
Entidades
DTOs
Endpoints
Repositorios
Configuración específica
```

Por ejemplo:

```java
@Service
public class UserService {
}
```

## Spring puede encargarse de

```text
Descubrir componentes
Crear Beans
Resolver dependencias
Conectar objetos
Gestionar ApplicationContext
Configurar infraestructura
Procesar routing web
Aplicar transacciones declarativas
Integrar librerías
```

## Spring Boot añade automatización

```text
Starters
Auto-configuration
Servidor embebido
Configuración convencional
Características de producción
```

---

# 53. Una analogía global

Imagina una fábrica.

## Java

Java es el lenguaje con el que fabricas:

```text
clases
objetos
métodos
```

## Spring Framework

Spring es la infraestructura de la fábrica:

```text
organiza objetos
conecta dependencias
gestiona componentes
coordina infraestructura
```

## Spring Boot

Spring Boot es una fábrica ya preparada:

```text
plantillas
configuración por defecto
herramientas
dependencias convenientes
servidor integrado
automatización
```

## Tu aplicación

Tu código define:

```text
qué fabrica la fábrica
```

Por ejemplo:

```text
UserController
UserService
UserRepository
User
```

Spring decide gran parte de:

```text
cómo crear
cómo conectar
cómo administrar
```

---

# 54. El mapa por capas

Otra forma extremadamente útil de estudiar Spring Boot es pensar en niveles.

```text
┌─────────────────────────────────────┐
│            CLIENTE / HTTP           │
└─────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────┐
│          SERVIDOR WEB               │
│             Tomcat                  │
└─────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────┐
│             SPRING MVC              │
│ DispatcherServlet / HandlerMapping  │
└─────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────┐
│         CAPA DE APLICACIÓN          │
│      Controller → Service           │
└─────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────┐
│          ACCESO A DATOS             │
│      Repository / Spring Data       │
└─────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────┐
│            PERSISTENCIA              │
│         JPA / Hibernate             │
└─────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────┐
│              JDBC                   │
└─────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────┐
│             DATABASE                │
└─────────────────────────────────────┘
```

Y envolviendo todo:

```text
Spring Core / IoC / DI / Beans
Spring Context
Spring Boot
Configuración
Auto-configuration
```

Mientras que otras capacidades atraviesan el sistema:

```text
Security
Validation
Transactions
Error handling
Testing
```

---

# 55. ¿Dónde está cada concepto?

Una pregunta útil cuando estudias:

## IoC

```text
Spring Core
```

## DI

```text
Spring Core / Container
```

## Bean

```text
Spring Beans / ApplicationContext
```

## ApplicationContext

```text
Spring Context
```

## Controller

```text
Spring MVC
```

## DispatcherServlet

```text
Spring MVC
```

## Service

```text
Patrón / convención arquitectónica común
```

## Repository

```text
Convención arquitectónica + Spring Data
```

## JPA

```text
Jakarta Persistence
```

## Hibernate

```text
Tecnología ORM externa
```

## JDBC

```text
API de Java para acceso a bases de datos
```

## Jackson

```text
Librería JSON externa
```

## Tomcat

```text
Servidor / Servlet container externo
```

## Maven

```text
Build tool externo
```

## JUnit

```text
Testing externo
```

## Mockito

```text
Mocking externo
```

---

# 56. Tabla maestra

| Concepto | Tipo | Pertenece a | Función | Momento del flujo |
|---|---|---|---|---|
| Spring | Ecosistema | Spring | Conjunto de proyectos y framework | Global |
| Spring Framework | Framework | Spring | Infraestructura base | Global |
| Spring Boot | Framework/proyecto de Spring | Spring | Simplificar aplicaciones Spring | Inicio + runtime |
| IoC | Concepto | Spring Core | Inversión de control | Inicio/runtime |
| DI | Concepto | Spring Core | Inyección de dependencias | Inicio |
| Bean | Concepto | Spring | Objeto gestionado por Spring | Inicio/runtime |
| Container | Infraestructura | Spring | Gestionar Beans | Inicio/runtime |
| ApplicationContext | Infraestructura | Spring Context | Contexto de la aplicación | Inicio/runtime |
| Component Scanning | Mecanismo | Spring | Descubrir componentes | Inicio |
| Auto-configuration | Mecanismo | Spring Boot | Configurar infraestructura automáticamente | Inicio |
| Starter | Dependencia agregadora | Spring Boot | Agrupar dependencias | Build |
| `@SpringBootApplication` | Anotación | Spring Boot | Configuración principal | Inicio |
| DispatcherServlet | Componente | Spring MVC | Front Controller web | HTTP |
| HandlerMapping | Componente | Spring MVC | Localizar handler | HTTP |
| Controller | Capa/componente | Spring MVC | Entrada HTTP | HTTP |
| Service | Capa/convención | Arquitectura de aplicación | Lógica de negocio | HTTP |
| Repository | Capa/abstracción | Spring Data / arquitectura | Acceso a datos | HTTP/persistencia |
| Spring Data JPA | Proyecto | Spring | Simplificar JPA | Persistencia |
| JPA | Especificación | Jakarta | API de persistencia ORM | Persistencia |
| Hibernate | Implementación ORM | Externo | Implementar JPA / ORM | Persistencia |
| JDBC | API | Java | Acceso a BD relacionales | Persistencia |
| Database | Sistema externo | Externo | Almacenar datos | Persistencia |
| Jackson | Librería | Externo | JSON ↔ Java | HTTP |
| Tomcat | Servidor | Externo | Procesar HTTP/Servlet | HTTP |
| Security Filter Chain | Infraestructura | Spring Security | Filtrar/proteger requests | Antes del Controller |
| `@Transactional` | Anotación | Spring Transactions | Gestionar transacciones | Service/persistencia |
| `@RestControllerAdvice` | Componente | Spring MVC | Manejo global de errores | HTTP |
| JUnit | Framework de testing | Externo | Tests | Tests |
| Mockito | Librería | Externo | Mocks | Tests |
| MockMvc | Herramienta | Spring Test | Probar web MVC | Tests |
| Testcontainers | Herramienta | Externo | Dependencias reales en containers | Integration tests |
| Maven | Build tool | Externo | Dependencias/build | Build |
| Gradle | Build tool | Externo | Dependencias/build | Build |
| Actuator | Proyecto | Spring Boot | Observabilidad/management | Runtime |
| Profile | Mecanismo | Spring / Boot | Configuración por entorno | Inicio/runtime |
| DTO | Patrón de diseño | Arquitectura | Transferir datos | API |
| Entity | Persistencia/modelo | Jakarta/JPA | Representar entidad persistente | Persistencia |

---

# 57. Tabla de tecnologías por "familia"

| Familia | Ejemplos |
|---|---|
| Java | JVM, lenguaje, JDBC |
| Spring Framework | Core, Beans, Context, MVC, Web, Transactions |
| Spring Boot | Starters, Auto-configuration, Actuator, Boot Test |
| Spring Projects | Data, Security, Batch, Cloud, Integration |
| Jakarta | Servlet, Persistence, Validation |
| ORM externo | Hibernate |
| JSON externo | Jackson |
| Servidor externo | Tomcat |
| Build externo | Maven, Gradle |
| Testing externo | JUnit, Mockito, Testcontainers |
| Base de datos | PostgreSQL, MySQL, etc. |
| Connection pool | HikariCP |
| Migraciones | Flyway |

---

# 58. Una distinción importante: capa versus tecnología

No confundas:

```text
Controller
Service
Repository
```

con:

```text
Spring MVC
Spring Data JPA
Hibernate
```

No son categorías equivalentes.

Por ejemplo:

```text
Controller
```

es una **pieza/capa de la arquitectura de tu aplicación**.

Mientras:

```text
Spring MVC
```

es el **framework web que proporciona infraestructura para construir esa capa**.

De forma similar:

```text
Repository
```

es una abstracción/capa de tu arquitectura.

```text
Spring Data JPA
```

es una tecnología que puede facilitar esa capa.

```text
Hibernate
```

es una tecnología de persistencia/ORM que puede implementarla por debajo.

---

# 59. Un segundo mapa: quién depende de quién

Para una aplicación REST típica:

```text
                 Spring Boot
                      │
                      ▼
              Spring Framework
                      │
        ┌─────────────┼─────────────┐
        ▼             ▼             ▼
    Spring Core    Spring MVC   Spring Data
        │             │             │
        ▼             ▼             ▼
      Beans       Controller    Repository
        │
        ▼
ApplicationContext

Spring Data JPA
       ↓
      JPA
       ↓
  Hibernate
       ↓
      JDBC
       ↓
   Database
```

Y:

```text
HTTP
 ↓
Tomcat
 ↓
Spring MVC
 ↓
Controller
```

Mientras:

```text
JSON
 ↓
Jackson
 ↓
Java object
```

Esta separación evita el error de imaginar que "Spring Boot hace todo".

---

# 60. ¿Qué pasa cuando agrego una dependencia?

Imagina:

```xml
spring-boot-starter-data-jpa
```

No significa solamente:

> "Ahora tengo una clase Repository."

La cadena conceptual es:

```text
Maven
  ↓
descarga dependencias
  ↓
JPA / Spring Data / Hibernate y dependencias relacionadas
  ↓
classpath
  ↓
Spring Boot las detecta durante el arranque
  ↓
auto-configuration
  ↓
Beans / infraestructura
  ↓
persistencia disponible
```

Una dependencia puede cambiar lo que Spring Boot considera posible y qué auto-configuraciones pueden activarse.

---

# 61. El rol de `Environment`

El `Environment` representa parte de la información de configuración que Spring utiliza.

Conceptualmente reúne valores procedentes de diferentes fuentes y permite que la aplicación consulte configuración.

Modelo simple:

```text
application.yml
environment variables
command-line args
properties
profiles
        ↓
     Environment
        ↓
Spring / Beans / Auto-configuration
```

No necesitas memorizar todavía cómo resuelve cada propiedad.

Solo recuerda:

> **Environment es una pieza del sistema de configuración que permite a Spring conocer el entorno en el que se está ejecutando la aplicación.**

---

# 62. Una nota importante sobre Controller → Service → Repository

Este patrón es muy frecuente:

```text
Controller
    ↓
Service
    ↓
Repository
```

Pero **no es una ley matemática de Spring**.

Una aplicación podría:

- no tener Service para una operación trivial;
- tener servicios adicionales;
- tener varios repositorios;
- introducir casos de uso;
- tener adaptadores;
- seguir arquitectura hexagonal;
- utilizar CQRS;
- usar otro estilo.

El valor del modelo es pedagógico:

```text
HTTP
 ↓
Controller
 ↓
Business logic
 ↓
Data access
```

No debes asumir:

> "Spring exige estas tres carpetas."

No las exige.

---

# 63. Una nota importante sobre JPA → Hibernate → JDBC

Tampoco debes memorizar:

```text
JPA = Hibernate = JDBC
```

Son conceptos diferentes.

```text
JPA
 ↓
especificación

Hibernate
 ↓
implementación ORM

JDBC
 ↓
API de acceso a BD
```

Y en una aplicación:

```text
Spring Data JPA
        ↓
JPA
        ↓
Hibernate
        ↓
JDBC
        ↓
Driver
        ↓
Database
```

Es un modelo conceptual habitual, no una secuencia que debas interpretar como una llamada directa y única entre cinco clases.

---

# 64. ¿Qué ocurre cuando algo falla?

Imagina:

```text
GET /users/10
```

pero el usuario no existe.

Podría ocurrir:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
"no encontrado"
    ↓
Exception
    ↓
@RestControllerAdvice
    ↓
HTTP 404
```

La respuesta podría ser:

```json
{
  "error": "USER_NOT_FOUND"
}
```

Aquí ves que el flujo no siempre termina en:

```text
200 OK
```

Puede transformarse en una respuesta de error.

---

# 65. ¿Dónde aparece Security?

Antes del Controller pueden existir filtros de seguridad.

Una simplificación:

```text
Client
  ↓
Tomcat
  ↓
Security filters
  ↓
DispatcherServlet
  ↓
Controller
```

Si el usuario no está autenticado:

```text
Request
  ↓
Security
  ↓
401 Unauthorized
```

Si está autenticado pero no autorizado:

```text
Request
  ↓
Security
  ↓
403 Forbidden
```

Esto explica por qué Security conceptualmente aparece **antes de tu Controller** en muchas aplicaciones.

---

# 66. El mapa mental que conviene memorizar

No intentes memorizar 100 clases.

Memoriza primero estas relaciones:

```text
Spring
  ↓
Spring Framework + Spring Projects + Spring Boot
```

```text
Spring Core
  ↓
IoC
  ↓
DI
  ↓
Beans
  ↓
ApplicationContext
```

```text
Spring Boot
  ↓
Starters
  ↓
Auto-configuration
  ↓
Embedded server
```

```text
HTTP
  ↓
Tomcat
  ↓
DispatcherServlet
  ↓
Controller
  ↓
Service
  ↓
Repository
```

```text
Repository
  ↓
Spring Data JPA
  ↓
JPA
  ↓
Hibernate
  ↓
JDBC
  ↓
Database
```

```text
Java Object
  ↕
Jackson
  ↕
JSON
```

Ese mapa te permite ubicar la mayoría de conceptos iniciales.

---

# 67. Si estás estudiando `@Service`

Usa este documento como mapa:

```text
Estoy estudiando @Service
        ↓
@Service es un componente de Spring
        ↓
se relaciona con Beans
        ↓
se registra mediante component scanning
        ↓
vive dentro de ApplicationContext
        ↓
puede recibir dependencias por DI
        ↓
suele representar lógica de negocio
```

Después vuelves al curso principal y estudias:

- ciclo de vida;
- scopes;
- proxies;
- transacciones;
- diseño de servicios;
- testing.

El mapa solo te dice:

> **"Dónde está y cómo se relaciona con el resto."**

---

# 68. Si estás estudiando `@Controller`

```text
@Controller
    ↓
Spring MVC
    ↓
HTTP
    ↓
DispatcherServlet
    ↓
HandlerMapping
    ↓
Controller method
```

Ahora sabes dónde ubicarlo.

---

# 69. Si estás estudiando Repository

```text
Repository
    ↓
Spring Data
    ↓
Spring Data JPA
    ↓
JPA
    ↓
Hibernate
    ↓
JDBC
    ↓
Database
```

Ya no ves `JpaRepository` como algo aislado.

---

# 70. Si estás estudiando DI

```text
DI
 ↓
Spring Core
 ↓
Bean
 ↓
ApplicationContext
 ↓
Container
 ↓
Controller / Service / Repository
```

Y el ejemplo:

```java
public UserController(UserService service) {
    this.service = service;
}
```

deja de parecer una sintaxis aislada.

Ahora sabes:

```text
Spring creó / administra ambos Beans
        ↓
detectó la dependencia
        ↓
resolvió UserService
        ↓
lo pasó al constructor
```

---

# 71. Si estás estudiando DispatcherServlet

Ubícalo aquí:

```text
Client
  ↓
Tomcat
  ↓
DispatcherServlet
  ↓
HandlerMapping
  ↓
Controller
```

No está:

```text
entre Service y Repository
```

Está en la infraestructura web.

---

# 72. Si estás estudiando Testing

Puedes ubicar las herramientas:

```text
JUnit
  ↓
motor / framework de pruebas

Mockito
  ↓
mocks

Spring Boot Test
  ↓
integración con Spring

MockMvc
  ↓
testing de la capa web MVC

Testcontainers
  ↓
infraestructura real para integration tests
```

---

# 73. Si estás estudiando Security

Mapa:

```text
Client
  ↓
Tomcat
  ↓
Security filters
  ↓
Authentication
  ↓
Authorization
  ↓
Controller
```

No necesitas aprender JWT para entender esta ubicación.

---

# 74. El flujo completo con las preocupaciones transversales

Una aplicación real puede parecerse más a:

```text
                         CLIENT
                           │
                           ▼
                       HTTP REQUEST
                           │
                           ▼
                         TOMCAT
                           │
                           ▼
                  SECURITY FILTER CHAIN
                           │
                           ▼
                   DISPATCHER SERVLET
                           │
                           ▼
                    HANDLER MAPPING
                           │
                           ▼
                       CONTROLLER
                           │
                  ┌────────┴────────┐
                  │                 │
                  ▼                 ▼
             VALIDATION         EXCEPTIONS
                  │                 │
                  └────────┬────────┘
                           ▼
                        SERVICE
                           │
                    @Transactional
                           │
                           ▼
                      REPOSITORY
                           │
                           ▼
                      SPRING DATA
                           │
                           ▼
                          JPA
                           │
                           ▼
                       HIBERNATE
                           │
                           ▼
                          JDBC
                           │
                           ▼
                       DATABASE
                           │
                           ▼
                        RESULT
                           │
                           ▼
                       SERVICE
                           │
                           ▼
                      CONTROLLER
                           │
                           ▼
                        JACKSON
                           │
                           ▼
                     HTTP RESPONSE
                           │
                           ▼
                         CLIENT
```

Esto ya se parece mucho más a una aplicación real, aunque sigue siendo una simplificación.

---

# 75. Lo que debes evitar pensar

## ❌ "Spring Boot es el servidor"

No exactamente.

Spring Boot puede arrancar y configurar un servidor embebido como Tomcat, pero Spring Boot no es Tomcat.

---

## ❌ "Hibernate es Spring"

No.

Hibernate es una tecnología ORM externa.

---

## ❌ "JPA es Hibernate"

No.

JPA es una especificación; Hibernate es una implementación muy utilizada.

---

## ❌ "Repository es una clase que Spring exige"

No.

Repository es una convención/abstracción arquitectónica, y Spring Data proporciona mecanismos muy potentes para implementarla.

---

## ❌ "Controller, Service y Repository son módulos de Spring"

No exactamente.

Son piezas de arquitectura de aplicación y/o abstracciones soportadas por diferentes proyectos de Spring.

---

## ❌ "Todo lo que empieza con Spring es Spring Boot"

No.

Hay muchos proyectos Spring que no son Spring Boot.

---

## ❌ "Spring crea un objeto nuevo cada vez que llega una petición"

No necesariamente.

Los Beans tienen un ciclo de vida y scopes. En el caso típico de Beans singleton, se administra una instancia compartida dentro del ApplicationContext.

---

# 76. Qué deberías aprender después

Este documento es un mapa, no el camino completo.

Un orden razonable de profundización sería:

```text
1. Spring Core
   ↓
IoC / DI / Bean / ApplicationContext

2. Spring Boot
   ↓
@SpringBootApplication
Starters
Auto-configuration

3. Spring MVC
   ↓
Controller
DispatcherServlet
Request / Response

4. Arquitectura
   ↓
Controller
Service
Repository
DTO

5. Spring Data JPA
   ↓
Repository
JPA
Hibernate
Persistence

6. Validation
   ↓
@Valid
constraints

7. Error handling
   ↓
@ControllerAdvice

8. Transactions
   ↓
@Transactional

9. Testing
   ↓
JUnit
Mockito
MockMvc
Spring Boot Test

10. Security
   ↓
Filter Chain
Authentication
Authorization
```

Después puedes profundizar en:

```text
Spring Security
Spring Cloud
Messaging
Observability
Containers
Microservices
Reactive
etc.
```

---

# 77. El mapa en una sola pantalla

```text
SPRING ECOSYSTEM
│
├── SPRING FRAMEWORK
│   ├── Spring Core
│   │   ├── IoC
│   │   ├── DI
│   │   ├── Beans
│   │   └── Container
│   │
│   ├── Spring Beans
│   ├── Spring Context
│   │   └── ApplicationContext
│   │
│   ├── Spring AOP
│   ├── Spring MVC
│   │   ├── DispatcherServlet
│   │   ├── HandlerMapping
│   │   └── Controllers
│   │
│   ├── Spring Validation
│   ├── Spring JDBC
│   └── Spring Transactions
│
├── SPRING PROJECTS
│   ├── Spring Data
│   │   └── Spring Data JPA
│   ├── Spring Security
│   ├── Spring Batch
│   ├── Spring Integration
│   └── Spring Cloud
│
└── SPRING BOOT
    ├── @SpringBootApplication
    ├── Starters
    ├── Auto-configuration
    ├── Embedded server
    ├── Externalized configuration
    ├── Actuator
    └── Spring Boot Test

EXTERNAL TECHNOLOGIES
├── Java
├── Jakarta APIs
├── Tomcat
├── Hibernate
├── Jackson
├── JDBC drivers
├── Maven / Gradle
├── JUnit
├── Mockito
├── HikariCP
├── Flyway
└── PostgreSQL / MySQL / etc.
```

---

# 78. Si solo recuerdas 10 cosas

## 1. Spring Framework es la base

Es el framework que aporta gran parte de la infraestructura fundamental de Spring, especialmente IoC, DI, Beans, Context y módulos como MVC.

## 2. Spring Boot simplifica Spring

Spring Boot automatiza y estandariza muchas partes del desarrollo de aplicaciones Spring.

## 3. Spring administra Beans

Un Bean es un objeto administrado por el Spring Container.

## 4. IoC significa inversión de control

Parte de la responsabilidad de crear y conectar objetos pasa de tu código hacia la infraestructura de Spring.

## 5. DI proporciona dependencias

Un objeto declara lo que necesita y Spring puede proporcionárselo.

## 6. `ApplicationContext` es central

Es el contexto donde Spring registra y administra gran parte de la infraestructura y los Beans de la aplicación.

## 7. `DispatcherServlet` coordina el flujo web

En Spring MVC actúa como Front Controller para las peticiones HTTP.

## 8. Controller → Service → Repository es un modelo común

Controller recibe la petición, Service suele contener la lógica de negocio y Repository se ocupa del acceso a datos.

## 9. JPA, Hibernate, JDBC y Database no son lo mismo

Pueden aparecer juntos en una aplicación, pero cumplen funciones diferentes.

## 10. Spring Boot conecta y automatiza muchas piezas

El gran valor de Spring Boot está en reducir configuración repetitiva y proporcionar convenciones e infraestructura lista para usar.

---

# 79. Glosario

### Spring

Nombre utilizado para referirse al ecosistema de proyectos Spring y, en muchos contextos, de forma informal al Spring Framework.

### Spring Framework

Framework base de Spring que proporciona IoC, DI, Beans, Context, MVC, transacciones y otras capacidades fundamentales.

### Spring Boot

Proyecto de Spring que facilita la creación, configuración y ejecución de aplicaciones Spring.

### IoC

Inversión de Control. Parte del control sobre creación, configuración y composición de objetos pasa a una infraestructura como el Spring Container.

### DI

Dependency Injection. Técnica mediante la cual un objeto recibe desde fuera las dependencias que necesita.

### Bean

Objeto administrado por el Spring Container.

### Container

Infraestructura de Spring responsable de gestionar Beans, dependencias y parte de su ciclo de vida.

### ApplicationContext

Abstracción central del contexto de Spring que proporciona gestión de Beans, configuración y otros servicios del ecosistema.

### Component

Clase reconocida como candidata para ser gestionada por Spring.

### Controller

Componente de la capa web que recibe y procesa entradas HTTP y normalmente delega en la lógica de aplicación.

### Service

Capa de aplicación utilizada habitualmente para expresar lógica de negocio y coordinar operaciones.

### Repository

Abstracción o capa dedicada al acceso a datos.

### DTO

Data Transfer Object. Objeto diseñado para transportar datos entre distintas partes de una aplicación.

### Entity

Objeto que representa una entidad persistente en el modelo de persistencia.

### JPA

Especificación de Jakarta para persistencia y ORM en Java.

### Hibernate

Framework ORM y una de las implementaciones más utilizadas de JPA.

### JDBC

API de Java para acceder a bases de datos, especialmente relacionales.

### Servlet

API y modelo de componentes para aplicaciones web Java tradicionales sobre el que se construye infraestructura como Spring MVC.

### DispatcherServlet

Front Controller de Spring MVC que recibe y coordina el procesamiento de peticiones web.

### REST

Estilo arquitectónico muy utilizado para diseñar APIs basadas en recursos y HTTP.

### JSON

Formato textual estructurado usado frecuentemente para intercambiar datos entre sistemas.

### HTTP

Protocolo utilizado para la comunicación entre clientes y servidores web.

### Starter

Dependencia de conveniencia de Spring Boot que agrupa dependencias relacionadas con una capacidad concreta.

### Auto-configuration

Mecanismo de Spring Boot que configura automáticamente infraestructura cuando se cumplen determinadas condiciones.

### Classpath

Conjunto de clases y recursos disponibles para la aplicación en tiempo de ejecución.

### Dependency

Librería o componente externo que una aplicación necesita para compilar o ejecutarse.

### Maven

Herramienta de build y gestión de dependencias utilizada ampliamente en proyectos Java.

### Gradle

Otra herramienta de build y gestión de dependencias para proyectos Java y otros lenguajes.

### Profile

Mecanismo para activar conjuntos de configuración diferentes según el entorno o escenario de ejecución.

### Transaction

Unidad lógica de trabajo sobre datos que debe cumplir las propiedades transaccionales definidas por el sistema de persistencia.

### Tomcat

Servidor web / servlet container externo que puede utilizarse embebido con Spring Boot.

### Jackson

Librería utilizada ampliamente para serializar y deserializar datos JSON.

### JUnit

Framework ampliamente utilizado para escribir y ejecutar pruebas en Java.

### Mockito

Librería para crear mocks y otros dobles de prueba.

### Actuator

Proyecto de Spring Boot que proporciona endpoints y capacidades para observabilidad y administración de aplicaciones.

---

# 80. Cómo utilizar este documento mientras estudias Spring Boot

Este archivo debería funcionar como un **mapa**, mientras que tu curso principal funciona como el **camino profundo de aprendizaje**.

Cada vez que aparezca un concepto nuevo, no intentes memorizarlo inmediatamente.

Primero pregunta:

```text
¿Qué es?
   ↓
¿A qué parte de Spring pertenece?
   ↓
¿Dónde aparece?
   ↓
¿De qué depende?
   ↓
¿En qué momento del flujo participa?
```

Por ejemplo:

```text
Estoy estudiando @Service
        ↓
Buscar Service
        ↓
Ver que se relaciona con Bean
        ↓
Ver que normalmente contiene lógica de negocio
        ↓
Ver que puede recibir dependencias por DI
        ↓
Volver al curso
        ↓
Estudiar @Service en profundidad
```

Haz lo mismo con:

```text
@Controller
@Repository
JPA
Dependency Injection
Bean
DispatcherServlet
Testing
Security
@Transactional
```

El objetivo no es repetir este documento de memoria.

El objetivo es poder mirar cualquier concepto y decir:

> **"Sé dónde está dentro del sistema."**

---

# 81. Cómo pensar cuando aparece un concepto nuevo

Supongamos que mañana encuentras:

```text
HandlerInterceptor
```

En lugar de intentar memorizar inmediatamente qué hace internamente, empieza por ubicarlo:

```text
¿Está relacionado con HTTP?
        ↓
Sí
        ↓
Entonces probablemente está en Spring MVC / Web
        ↓
¿En qué parte del flujo?
        ↓
Antes / después del Controller
```

O aparece:

```text
EntityManager
```

Piensas:

```text
¿Persistencia?
        ↓
Sí
        ↓
JPA
        ↓
Hibernate / ORM
```

O aparece:

```text
Filter
```

Preguntas:

```text
¿HTTP?
¿Security?
¿Servlet?
¿Spring MVC?
```

Así el aprendizaje deja de ser una lista de términos aislados y se convierte en un mapa de relaciones.

---

# 82. La distinción más importante: infraestructura y código de negocio

Una aplicación Spring Boot suele mezclar dos mundos.

## Infraestructura

```text
Spring
Spring Boot
Tomcat
Jackson
Hibernate
JDBC
ApplicationContext
DispatcherServlet
Security
```

## Código de negocio

```text
User
Account
Transfer
Order
Payment
Customer
Invoice
```

Por ejemplo, en un sistema bancario:

```text
HTTP
 ↓
Controller
 ↓
TransferService
 ↓
AccountRepository
 ↓
JPA / Hibernate
 ↓
Database
```

Pero:

```text
Transfer
Account
Customer
```

son conceptos del negocio.

Spring proporciona el **andamiaje**.

Tu aplicación define el **comportamiento del dominio y del caso de uso**.

---

# 83. El modelo final

Puedes reducir toda la arquitectura a cuatro preguntas.

## Pregunta 1 — ¿Cómo arranca?

```text
main()
 ↓
SpringApplication.run()
 ↓
ApplicationContext
 ↓
Beans
 ↓
DI
 ↓
Server
 ↓
Ready
```

## Pregunta 2 — ¿Cómo entra una petición?

```text
Client
 ↓
HTTP
 ↓
Tomcat
 ↓
DispatcherServlet
 ↓
Controller
```

## Pregunta 3 — ¿Cómo se ejecuta el negocio?

```text
Controller
 ↓
Service
 ↓
Repository
```

## Pregunta 4 — ¿Cómo llega a los datos y vuelve?

```text
Repository
 ↓
Spring Data
 ↓
JPA
 ↓
Hibernate
 ↓
JDBC
 ↓
Database
 ↓
resultado
 ↓
Java Object
 ↓
Jackson
 ↓
JSON
 ↓
HTTP Response
```

Ese es el mapa.

---

# 84. Resumen definitivo

Una aplicación Spring Boot puede entenderse como varias piezas superpuestas:

```text
                         SPRING BOOT
                              │
        ┌─────────────────────┴────────────────────┐
        │                                          │
        ▼                                          ▼
   Spring Framework                         Spring Projects
        │                                          │
        ├── Core / IoC / DI                       ├── Data
        ├── Beans                                 ├── Security
        ├── Context                               └── ...
        ├── MVC
        └── Transactions

                     +
              Auto-configuration
                     +
                  Starters
                     +
              Embedded server
                     +
              Configuration
```

Y durante el runtime:

```text
CLIENT
  ↓
HTTP
  ↓
TOMCAT
  ↓
SECURITY (si corresponde)
  ↓
DISPATCHER SERVLET
  ↓
HANDLER MAPPING
  ↓
CONTROLLER
  ↓
SERVICE
  ↓
REPOSITORY
  ↓
SPRING DATA / JPA
  ↓
HIBERNATE
  ↓
JDBC
  ↓
DATABASE
```

La respuesta vuelve:

```text
DATABASE
  ↓
JPA / Hibernate / JDBC
  ↓
Repository
  ↓
Service
  ↓
Controller
  ↓
Jackson
  ↓
JSON
  ↓
HTTP RESPONSE
  ↓
CLIENT
```

Mientras todo esto funciona gracias a una infraestructura previa de:

```text
IoC
DI
Beans
ApplicationContext
Component Scanning
Auto-configuration
Configuration
```

La idea central que debes conservar es:

> **Spring Framework proporciona la infraestructura. Spring Boot simplifica y automatiza gran parte de su configuración y ejecución. Spring administra Beans y dependencias. Spring MVC procesa el flujo web. Tu arquitectura organiza Controller, Service y Repository. Spring Data y las tecnologías de persistencia conectan la aplicación con la base de datos. Otras tecnologías externas, como Tomcat, Hibernate, Jackson, Maven, JUnit y Mockito, se integran con ese ecosistema sin convertirse por ello en Spring.**

Y, sobre todo:

```text
            ARRANQUE
               │
               ▼
      SpringApplication.run()
               │
               ▼
       ApplicationContext
               │
        ┌──────┴──────┐
        ▼             ▼
 Component Scan   Auto-config
        │             │
        └──────┬──────┘
               ▼
             Beans
               │
               ▼
              DI
               │
               ▼
          Server Ready
               │
               ▼
         HTTP Request
               │
               ▼
            Tomcat
               │
               ▼
       DispatcherServlet
               │
               ▼
          Controller
               │
               ▼
            Service
               │
               ▼
          Repository
               │
               ▼
        JPA / Hibernate
               │
               ▼
             JDBC
               │
               ▼
           Database
               │
               ▼
            Response
               │
               ▼
            Jackson
               │
               ▼
         HTTP Response
               │
               ▼
             Client
```

Ese diagrama es la referencia mental que conviene llevar contigo mientras avanzas por el curso.
