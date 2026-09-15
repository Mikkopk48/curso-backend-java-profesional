# Unidad 5 — IoC, inyección de dependencias, beans y configuración

> **Cómo leer esta unidad.** Cada sección sigue el mismo patrón: primero el problema concreto que existe *sin* Spring, después la analogía que fija la idea, después el mecanismo real y, al final, la evidencia que podés observar para comprobar que entendiste. Si una explicación te suena a magia, es que todavía no encontraste quién procesa esa anotación. Ese es el hilo conductor de toda la unidad.

---

## 1. El problema: crear objetos no basta

En Java podés construir una aplicación entera a mano:

```java
CustomerRepository repository = new InMemoryCustomerRepository();
EmailGateway gateway = new ConsoleEmailGateway();
CustomerService service = new CustomerService(repository, gateway);
CustomerController controller = new CustomerController(service);
```

Ese código es válido y, en una aplicación de cuatro clases, es incluso preferible: es explícito, no tiene ninguna dependencia de framework y lo entendés leyéndolo de arriba a abajo. Conviene decir esto desde el principio, porque el error más común al aprender Spring es creer que la inyección de dependencias es *mejor* por definición. No lo es. Resuelve un problema específico que aparece con la escala.

### Qué se rompe exactamente cuando el grafo crece

Mirá qué responsabilidades quedaron concentradas en ese bloque de cuatro líneas:

1. **Conocer todas las implementaciones concretas.** Ese archivo sabe que el repositorio es *InMemory* y que el gateway es *Console*. Cambiar a JPA implica tocar el ensamblador.
2. **Respetar el orden topológico.** No podés construir `CustomerService` antes que `repository`. Con 4 objetos es obvio; con 120 es un orden que alguien tiene que mantener a mano.
3. **Decidir qué se comparte.** Si `CustomerService` y `ReportService` deben usar *la misma* instancia de `repository`, alguien debe recordar pasar la misma variable. Un `new` de más y tenés dos caches, dos pools, dos estados.
4. **Cerrar en orden inverso.** Los pools de conexiones, los clientes HTTP y los executors hay que apagarlos, y en el orden contrario al de creación.
5. **Repetir todo esto en los tests, en el arranque web, en el job batch.**

Nada de eso es lógica de negocio. Es *plomería de construcción*.

### La analogía: la obra y el capataz

Pensá en construir una casa. Las tareas de plomería, electricidad y albañilería son el trabajo real (tu lógica de negocio). Pero alguien tiene que decidir que el electricista entra *después* de que se levantaron las paredes y *antes* de que se revoque; que hay un solo tablero eléctrico compartido y no uno por habitación; y que al final se cierran las llaves de paso en el orden correcto.

En la versión manual, vos sos el capataz **y además** cada uno de los obreros. En la versión con Spring, vos declarás qué oficios existen y qué necesita cada uno para trabajar, y el contenedor hace de capataz. La clave de la analogía: *el capataz no sabe soldar*. Spring no mejora tu lógica; solo coordina el armado.

Spring actúa como un **ensamblador**. Vos declarás qué objetos administra y qué necesita cada uno; el contenedor construye el grafo completo.

---

## 2. Cuatro términos que debés separar

Estos cuatro términos se usan como sinónimos en tutoriales y esa confusión es la raíz de la mitad de los malentendidos posteriores. Separalos ahora.

### 2.1 Dependencia

Una clase `A` depende de `B` cuando necesita `B` para cumplir su responsabilidad.

```java
class CustomerService {
    private final CustomerRepository repository;

    CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }
}
```

`CustomerRepository` es una dependencia de `CustomerService`. **Esto es Java puro; todavía no hay Spring en ninguna parte.** El concepto de dependencia existe desde que existen los objetos: es simplemente "para hacer mi trabajo, necesito a otro".

### 2.2 Inyección de dependencias (DI)

La clase **recibe** la dependencia desde afuera en lugar de construirla internamente.

```java
// Evitar dentro de un servicio:
private final CustomerRepository repository = new JpaCustomerRepository(...);
```

Esa línea parece inofensiva, pero acabás de soldar dos decisiones: *qué* implementación se usa y *cómo* se construye. Ya no podés testear el servicio sin una base de datos real, ni reemplazar la implementación sin editar la clase.

**Analogía:** una cafetera que tiene el agua soldada adentro contra una cafetera con entrada de agua. La segunda funciona con agua de red, con bidón o, en un test, con un vaso medidor. No es que la primera "esté mal programada": es que no es sustituible.

Recibir por constructor te da tres cosas concretas:

- **Sustituir la implementación** sin tocar la clase que la usa.
- **Comprobar los requisitos al crear el objeto**: si falta algo, falla ahí, no tres capas más abajo en producción.
- **Probar el servicio sin levantar el framework**, con un `new` y un doble de prueba.

Un matiz importante: **la inyección de dependencias no requiere Spring.** El primer bloque de código de esta unidad —el ensamblado manual— ya *es* inyección de dependencias. Spring automatiza el ensamblado, no inventa el patrón.

### 2.3 Inversión de control (IoC)

IoC describe un cambio de **quién dirige**: quién decide cuándo se crea algo, cuándo se llama y cuándo se destruye.

En un programa clásico, tu código llama a las bibliotecas. En un framework, el framework llama a tu código: vos escribís las piezas y el framework decide cuándo invocarlas. Eso a veces se resume como *"no nos llames, nosotros te llamamos"*.

**Analogía:** la diferencia entre cocinar en tu casa y cocinar en la línea de un restaurante. En tu casa vos decidís el orden completo. En la línea, el jefe de cocina canta las órdenes y vos ejecutás tu estación cuando te toca. Tu técnica de cocina no cambió; cambió quién controla el flujo.

Relación entre los dos términos, que es lo que suele quedar ambiguo:

| | IoC | Inyección de dependencias |
|---|---|---|
| Qué es | Un **principio** sobre quién controla el flujo y el ciclo de vida | Una **técnica** concreta para pasar colaboradores |
| Alcance | Amplio: también incluye callbacks, event listeners, el `main` de Spring Boot, los hooks de ciclo de vida | Estrecho: cómo llega `repository` a `CustomerService` |
| Se puede tener uno sin el otro | Sí: un framework de UI que llama a tus handlers hace IoC sin inyectar nada | Sí: el ensamblado manual del inicio inyecta dependencias sin ningún contenedor |

**La DI es un mecanismo para aplicar IoC; no son la misma palabra.** Cuando alguien dice "el contenedor IoC de Spring", está usando "IoC" como nombre comercial del ensamblador. Está bien, pero sabé que conceptualmente son cosas distintas.

### 2.4 Bean

Un **bean** es un objeto administrado por el contenedor de Spring. Todo bean es un objeto Java; no todo objeto Java es un bean.

```java
CustomerService service = new CustomerService(repository); // objeto manual, no es un bean
```

Si vos lo creás con `new`, Spring normalmente no conoce ese objeto: no le inyecta nada, no lo envuelve en proxies, no ejecuta sus hooks de ciclo de vida y sus `@Transactional` no hacen absolutamente nada. Si lo crea o registra el contenedor, ese objeto puede participar en inyección, proxies, transacciones y ciclo de vida.

**Analogía:** un empleado en blanco versus alguien que entró a la oficina y se sentó en un escritorio. Los dos pueden escribir código. Pero solo uno figura en el sistema, recibe los beneficios de la empresa, aparece en el organigrama y es alcanzado por las políticas internas. El segundo simplemente está ahí.

Esta distinción es la causa número uno de la pregunta *"¿por qué mi `@Transactional` no funciona?"*. Volvemos a eso en la sección 8.

---

## 3. ApplicationContext: el contenedor

El `ApplicationContext` conserva **definiciones** de beans (la receta), crea **instancias** (el plato), resuelve dependencias, aplica postprocesadores y publica eventos.

Esa distinción entre definición e instancia importa: Spring primero lee *todas* las definiciones —qué clases hay, qué constructores tienen, qué condiciones las activan— y recién después empieza a instanciar. Por eso puede detectar una ambigüedad o una dependencia faltante **antes** de construir nada.

Imaginá un grafo dirigido, donde cada flecha significa "necesita a":

```
CustomerController
        │
        ▼
  CustomerService
    │           │
    ▼           ▼
CustomerRepository   NotificationGateway
```

Spring debe encontrar **exactamente un** candidato adecuado para cada flecha obligatoria:

- **Cero candidatos** → el arranque falla por dependencia ausente (`No qualifying bean of type...`).
- **Varios candidatos sin criterio para elegir** → falla por ambigüedad (`expected single matching bean but found 2`).

Esto se llama **fail-fast** y es una decisión de diseño deliberada, no un defecto. Es mejor que la aplicación no arranque a las 3 de la mañana durante el despliegue que descubrir un `NullPointerException` en la petición de un cliente real a las 11. Un arranque roto se ve en el log, en el pipeline de CI y en el health check. Una referencia nula en runtime se ve en un ticket de soporte.

**Analogía:** el contenedor es el control de abordaje antes del vuelo, no el auxiliar que descubre en pleno aire que falta un pasajero.

---

## 4. Dos formas principales de declarar beans

### 4.1 Escaneo de componentes

Anotás una clase y Spring la detecta dentro del área escaneada:

```java
@Service
class CustomerService {
    private final CustomerRepository repository;

    CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }
}
```

**Qué significa "el área escaneada".** `@SpringBootApplication` incluye `@ComponentScan` sin argumentos, lo que significa: escaneá el package de esta clase **y todos sus sub-packages**. De ahí la convención de poner la clase principal en la raíz del proyecto (`com.empresa.app`) y todo lo demás debajo (`com.empresa.app.customer`, `com.empresa.app.web`). Si ponés una clase anotada en un package *hermano* —`com.otraempresa.util`— Spring no la ve y vas a recibir un `No qualifying bean` que te va a parecer inexplicable. Es la causa silenciosa más frecuente de ese error.

**Estereotipos frecuentes:**

| Anotación | Significado humano | Comportamiento adicional relevante |
|---|---|---|
| `@Component` | Componente general | Lo registra como candidato |
| `@Service` | Servicio o caso de uso | Expresa intención; se registra como componente |
| `@Repository` | Adaptador de persistencia | Expresa intención **y** traduce excepciones de persistencia a la jerarquía `DataAccessException` de Spring |
| `@Controller` | Componente MVC | Sus métodos devuelven nombres de vistas salvo configuración adicional |
| `@RestController` | Controller HTTP con body | Combina `@Controller` + `@ResponseBody`: serializa el retorno al body de la respuesta |

Un detalle que vale la pena entender de `@Repository`: sin él, tu código de servicio quedaría atado a excepciones específicas del proveedor (`HibernateException`, `SQLException`). Con él, Spring las traduce a excepciones propias, así tu capa de negocio no sabe qué motor de persistencia hay abajo. Es el único estereotipo de la tabla que aporta un comportamiento técnico real y no solo intención.

**`@Service` no vuelve correcto un método de negocio.** Su mayor valor inmediato es comunicar el rol al lector humano y permitir que Spring encuentre la clase. Si mañana renombraras todos tus `@Service` a `@Component`, la aplicación seguiría funcionando igual; lo que perderías es información para quien lee.

### 4.2 Configuración explícita con `@Bean`

Para clases de bibliotecas que no podés anotar, o cuando querés controlar la construcción:

```java
@Configuration
class ClockConfiguration {

    @Bean
    Clock applicationClock() {
        return Clock.systemUTC();
    }
}
```

Ahora `Clock` puede inyectarse en cualquier parte:

```java
@Service
class CustomerService {
    private final Clock clock;

    CustomerService(Clock clock) {
        this.clock = clock;
    }
}
```

**Por qué importa este ejemplo en particular.** `Clock` parece un detalle irrelevante hasta que intentás testear una regla como "el token expira a las 24 horas". Con `Instant.now()` hardcodeado, el test tiene que esperar 24 horas o mentir. Con un `Clock` inyectado, el test pasa un `Clock.fixed(...)` y controla el tiempo. **Lo mismo vale para cualquier fuente de no-determinismo: el reloj, el generador de UUID, el generador aleatorio.** Si es no-determinista, inyectalo.

**Nombres:** el nombre del bean es, por defecto, el nombre del método (`applicationClock`). Eso importa cuando usás `@Qualifier`, así que elegí nombres estables y no renombres métodos `@Bean` a la ligera.

**Criterio de elección entre las dos formas:**

- **Escaneo** para tus componentes propios con un rol claro dentro de tu arquitectura.
- **`@Bean`** para construcción explícita, objetos de bibliotecas externas, o variantes del mismo tipo que requieren configuración distinta (dos `RestClient` apuntando a dos APIs, por ejemplo).

---

## 5. Por qué se recomienda el constructor

Comparemos los tres estilos con el mismo criterio: *¿qué me dice la clase sobre lo que necesita, y qué pasa si algo falta?*

### 5.1 Inyección por campo

```java
@Autowired
private CustomerRepository repository;
```

Es la más breve y por eso la más tentadora. Sus problemas concretos:

- **Oculta el contrato.** La firma pública de la clase no dice nada. Solo leyendo los campos privados descubrís qué necesita.
- **Permite un objeto inválido.** `new CustomerService()` compila y devuelve un objeto con `repository == null`. La explosión llega después, en un lugar distinto al del error.
- **No podés usar `final`.** El campo queda mutable aunque nunca debiera cambiar.
- **Obliga al framework o a reflexión para testear.** En un test Java puro no tenés forma de poner ese campo sin `ReflectionTestUtils` o levantar el contexto entero.
- **Esconde el mal diseño.** Agregar la dependencia número doce es una línea más; nadie se da cuenta.

### 5.2 Inyección por setter

```java
@Autowired
void setRepository(CustomerRepository repository) {
    this.repository = repository;
}
```

Puede ser útil para una dependencia **realmente opcional** o reconfigurable en caliente. Pero permite que exista un objeto a medio construir: entre el `new` y el `set` hay una ventana donde el objeto es inválido. En la práctica, esa ventana casi nunca se justifica.

### 5.3 Inyección por constructor

```java
private final CustomerRepository repository;

CustomerService(CustomerRepository repository) {
    this.repository = repository;
}
```

Ventajas, en orden de importancia:

1. **Hace imposible un objeto inválido.** No existe forma de tener un `CustomerService` sin repositorio. El compilador lo garantiza.
2. **Permite `final`**, lo que también facilita razonar sobre concurrencia.
3. **Muestra el diseño en la firma.** Alguien que mira el constructor sabe exactamente de qué depende la clase, sin leer el cuerpo.
4. **Facilita pruebas**: `new CustomerService(fakeRepo, fixedClock)` y listo, sin framework.

Si la clase tiene **un solo constructor**, no necesitás `@Autowired`: Spring lo usa automáticamente desde la versión 4.3.

### 5.4 El constructor como sensor de diseño

Un constructor con ocho dependencias **no es un problema de Spring**; es una señal de que la clase concentra demasiadas responsabilidades. Y esto es precisamente lo valioso del constructor: es incómodo de escribir cuando el diseño es malo.

**Analogía:** una mochila de excursión con la lista de contenido cosida por fuera. Si la lista tiene cuarenta ítems, la mochila está mal armada, y lo ves *antes* de cargártela a la espalda. La inyección por campo es la mochila sin lista: pesa lo mismo, pero recién te enterás en la subida.

Cuando el constructor crece, las salidas honestas son: extraer un servicio intermedio que agrupe un subconjunto coherente, mover lógica al dominio, o aceptar que esa clase era en realidad tres.

---

## 6. Interfaces, implementaciones y repositorios generados

### 6.1 No toda clase necesita una interfaz

La inyección **no exige** una interfaz para cada clase. La costumbre de crear `FooService` + `FooServiceImpl` para todo es ruido: duplica archivos, agrega un salto de navegación y no aporta nada si nunca va a haber una segunda implementación.

Creá una interfaz cuando represente una **frontera útil**: un punto donde tu dominio habla con el mundo exterior, o donde existen implementaciones genuinamente intercambiables.

```java
interface NotificationGateway {
    void customerCreated(String customerId);
}

@Component
class LogNotificationGateway implements NotificationGateway {
    // implementación
}
```

Spring puede inyectar `LogNotificationGateway` donde se pide `NotificationGateway` porque es **asignable** a ese tipo. La resolución es por tipo, no por nombre de clase.

**Regla práctica:** la interfaz se justifica si podés nombrar hoy mismo una segunda implementación plausible (un fake para tests cuenta, si además te evita un mock complicado) o si la interfaz protege tu dominio de un detalle técnico externo.

### 6.2 Repositorios generados por Spring Data

Con Spring Data, declarás una interfaz y el framework crea un proxy en tiempo de ejecución:

```java
interface CustomerRepository extends JpaRepository<CustomerEntity, String> {
    boolean existsByEmailIgnoreCase(String email);
}
```

**No falta ningún `CustomerRepositoryImpl`.** Y esto es lo que más desconcierta la primera vez, así que conviene desarmar el truco:

1. Al arrancar, Spring Data escanea las interfaces que extienden `Repository` (directa o indirectamente).
2. Por cada una, **parsea el nombre de cada método** según una gramática fija: `exists` + `By` + `Email` + `IgnoreCase` se traduce a una consulta contra la propiedad `email` de la entidad, comparando sin distinguir mayúsculas.
3. Genera en memoria una clase que implementa la interfaz, con esa consulta adentro, y registra esa instancia como bean.

Consecuencias prácticas de entender el mecanismo:

- Si escribís mal una propiedad (`existsByMail...` cuando la entidad tiene `email`), **falla al arrancar**, no en runtime. El parser no encuentra la propiedad y lo dice.
- El nombre del método es código ejecutable, no documentación. Renombrarlo cambia la consulta.
- Esto funciona solo porque el starter, JPA y el escaneo de repositorios están activos. Sacá el starter y la interfaz vuelve a ser una interfaz vacía sin implementación.

**Analogía:** un formulario oficial donde el nombre del trámite determina qué hace la oficina. Vos no escribís el procedimiento; escribís el nombre exacto del trámite y la oficina sabe qué ejecutar. Si escribís mal el nombre, el trámite no existe y te lo rechazan en la ventanilla, no tres meses después.

---

## 7. Un bean no siempre es la clase visible

Spring puede envolver un objeto con un **proxy** para agregar comportamiento transversal: transacciones, seguridad, caché, métricas, reintentos.

El proxy es un objeto que **implementa la misma interfaz o extiende la misma clase**, recibe la llamada primero, hace su trabajo alrededor y delega en el objeto real:

```
llamada ──▶ [ proxy ]
              │  1. abre transacción
              ▼
           [ objeto real ]  ── ejecuta tu método
              │
              │  2. commit si terminó bien / rollback si voló una excepción
              ▼
            retorna
```

Cuando inyectás `CustomerService`, la variable puede estar apuntando al proxy, no a tu instancia. Tu código no lo nota porque el tipo es compatible.

**Analogía:** el secretario frente a la oficina del director. Vos creés que hablás con el director; en realidad hablás con el secretario, que anota la visita en el registro, te hace pasar, y al salir cierra el acta. Si en cambio el director te hace pasar por la puerta lateral —sin pasar por el escritorio del secretario—, no queda registro de nada.

### La consecuencia que hay que memorizar: la auto-invocación

```java
@Service
class CustomerService {

    public void importarLote(List<Customer> lote) {
        for (Customer c : lote) {
            guardar(c);          // ⚠️ llamada interna: NO pasa por el proxy
        }
    }

    @Transactional
    public void guardar(Customer c) {
        // esta anotación no tiene ningún efecto cuando se llega acá desde importarLote
    }
}
```

Cuando `importarLote` llama a `guardar`, esa llamada es un `this.guardar(c)` normal de Java. Nunca sale del objeto, así que nunca atraviesa el proxy, así que la transacción nunca se abre. La anotación está ahí, se ve en el código, y no hace nada. Es la puerta lateral de la analogía.

Las unidades avanzadas profundizan este límite (y sus salidas: extraer la clase, auto-inyección, `TransactionTemplate`). Por ahora conservá la regla general:

> **Las anotaciones como `@Transactional` no son instrucciones que Java ejecute. Requieren infraestructura de Spring alrededor de un bean administrado por Spring.** Si el objeto lo creaste vos con `new`, o si la llamada no cruza el límite del objeto, la anotación es un comentario decorado.

---

## 8. Ambigüedad y selección

Si hay dos implementaciones del mismo tipo:

```java
@Component
class EmailNotificationGateway implements NotificationGateway {}

@Component
class SmsNotificationGateway implements NotificationGateway {}
```

Spring no puede adivinar cuál inyectar y falla al arrancar. Esto no es un capricho: elegir una al azar significaría que la mitad de los despliegues manda SMS y la otra mitad mails, de forma no reproducible.

Soluciones conscientes, en el orden en que conviene considerarlas:

1. **Eliminar el candidato accidental.** Muchas veces el segundo bean es una clase de ejemplo, una copia vieja o algo que quedó de un experimento. Empezá siempre por preguntarte si el segundo candidato debería existir.
2. **Inyectar una colección** si en realidad los necesitás a todos:
   ```java
   CustomerService(List<NotificationGateway> gateways) { ... }
   ```
   Spring inyecta todos los candidatos. Útil para patrones tipo "notificar por todos los canales disponibles".
3. **Marcar uno como `@Primary`**: "cuando no se especifique nada, usá este". Es la opción correcta cuando hay un default claro y evidente.
4. **Usar `@Qualifier` con un nombre estable**: elección explícita, punto por punto.
5. **Separar por perfiles** si pertenecen a ambientes diferentes (consola en local, SMS real en producción).

```java
CustomerService(@Qualifier("emailNotificationGateway") NotificationGateway gateway) {
    this.gateway = gateway;
}
```

**No uses `@Primary` para tapar una ambigüedad que no comprendés.** Es tentador porque hace desaparecer el error de arranque en un intento. Pero el error de arranque era la única evidencia de que había dos implementaciones compitiendo; si lo silenciás sin entender, lo que hiciste fue elegir una al azar de forma permanente y sin dejar rastro.

---

## 9. Dependencias opcionales

Una dependencia que el caso de uso **necesita** no debería ser opcional. Suena obvio, pero se viola seguido: alguien no logra que un bean se resuelva, lo envuelve en `Optional<T>` para que arranque, y el sistema pasa a tener un modo degradado silencioso que nadie documentó.

Para capacidades genuinamente opcionales existen `Optional<T>`, `ObjectProvider<T>` o condiciones de configuración. Todas añaden ramas al código: cada `if (gateway.isPresent())` es un camino más que testear y razonar.

Antes de usarlas, hacete la pregunta correcta: **¿el sistema puede cumplir su contrato sin esa capacidad?**

- "Puede registrar clientes sin enviar la métrica a Datadog" → sí, es opcional de verdad.
- "Puede registrar clientes sin guardar en la base" → no, eso no es opcional, eso está roto.

---

## 10. Ciclo de vida y alcance (scope)

### 10.1 Singleton: qué significa y qué no

El alcance predeterminado es **singleton por contexto**: Spring crea **una** instancia del bean y la comparte con todos los que la piden.

Dos aclaraciones que se omiten seguido y causan errores reales:

- **No es el singleton global de la JVM.** Es "uno por `ApplicationContext`". Si en un test levantás dos contextos, hay dos instancias. No confundas con el patrón Singleton clásico de constructor privado.
- **No garantiza nada sobre concurrencia.** Spring te da una instancia compartida; qué tan segura sea esa instancia entre hilos depende enteramente de cómo la escribiste vos.

### 10.2 La consecuencia práctica: nada de estado mutable de petición

Una aplicación web atiende muchas peticiones concurrentes usando **el mismo** objeto service, en hilos distintos, al mismo tiempo.

```java
@Service
class UnsafeService {
    private String currentCustomerId; // ⚠️ se mezcla entre peticiones
}
```

Qué pasa realmente: el hilo A escribe `"cliente-1"`, el hilo B escribe `"cliente-2"` un microsegundo después, y el hilo A lee `"cliente-2"` y le manda la factura del cliente 2 al cliente 1. Este bug es especialmente cruel porque **no aparece en desarrollo**: con un solo usuario probando, nunca hay dos hilos pisándose. Aparece en producción, bajo carga, de forma intermitente y no reproducible.

**Analogía:** un mostrador único de atención con un solo anotador compartido. Mientras haya una persona por vez, funciona perfecto. Con cinco personas hablando a la vez, cada una tacha lo que escribió la anterior. La solución no es escribir más rápido: es que cada persona tenga su propia hoja.

Esa "hoja propia" son las **variables locales del método**: viven en la pila de cada hilo y son inherentemente aisladas. Regla:

- Estado de petición → **variables locales** o parámetros.
- Estado compartido que debe persistir → **la base de datos**, no un campo del service.
- Estado compartido en memoria genuinamente necesario → estructuras diseñadas para concurrencia (`ConcurrentHashMap`, `AtomicLong`) y con plena conciencia de lo que estás haciendo.

**Los campos de un singleton deberían ser sus dependencias (`final`, inmutables) y su configuración. Nada más.**

### 10.3 Otros scopes

Existen `request`, `session`, `prototype` y otros, pero **no son necesarios para un CRUD inicial**. Elegirlos sin necesidad hace el ciclo de vida mucho más difícil de razonar —en particular cuando un bean de scope corto se inyecta dentro de uno singleton, que es un problema con su propia teoría (proxies de scope) y su propio conjunto de errores.

### 10.4 Inicialización y cierre

```java
@PostConstruct
void initialize() { }

@PreDestroy
void close() { }
```

`@PostConstruct` corre después de que todas las dependencias fueron inyectadas —por eso no podés poner esa lógica en el constructor si depende de algo que se inyecta después—. `@PreDestroy` corre al cerrar el contexto ordenadamente.

**No hagas migraciones manuales ni operaciones remotas largas en `@PostConstruct`.** Motivos concretos: el arranque se cuelga esperando una red que puede no responder; si falla, el error queda sepultado en el medio del log de arranque; y nada de eso queda registrado en un mecanismo observable. Las migraciones tienen herramientas propias (Flyway, Liquibase) que llevan control de versión, son idempotentes y dejan rastro. Usá mecanismos específicos y observables.

---

## 11. Dependencias circulares

```
CustomerService ──▶ NotificationService ──▶ CustomerService
```

El contenedor no puede construir limpiamente un ciclo de constructores, y la razón es puramente lógica, no una limitación de Spring: para construir A necesita B ya construido, y para construir B necesita A ya construido. No hay orden posible. Es el mismo problema que tendrías escribiendo el ensamblado a mano.

**Una dependencia circular casi siempre indica responsabilidades mezcladas, no una anotación faltante.** El síntoma es técnico; la causa es de diseño. Si A y B se necesitan mutuamente, lo más probable es que exista un tercer concepto que todavía no nombraste, y que ambos estén tironeando de él.

Posibles rediseños, de más a menos común:

1. **Extraer una tercera responsabilidad.** Lo que A necesita de B y lo que B necesita de A suelen ser dos cosas distintas; una de ellas es una clase nueva que ambos usan.
2. **Invertir una dependencia mediante una interfaz de frontera.** B define la interfaz que necesita; A la implementa. La flecha de compilación cambia de sentido aunque la llamada siga fluyendo igual.
3. **Publicar un evento** cuando la consistencia no necesita ser inmediata. `CustomerService` publica `CustomerCreated` y `NotificationService` lo escucha, sin conocerse mutuamente.
4. **Mover la coordinación a un caso de uso superior**, que llame a A y después a B. Muchas veces el ciclo existe porque falta el orquestador.

**Evitá activar `spring.main.allow-circular-references` o usar `@Lazy` como arreglo automático.** Ambos hacen que arranque, pero lo que hacen es diferir el problema: te quedás con un grafo que no podés dibujar y un orden de inicialización que depende de detalles internos. Primero rompé el ciclo conceptual; si el diseño es correcto, el ciclo desaparece solo.

---

## 12. Configuración: lo que cambia entre ambientes

La configuración representa valores que varían **sin recompilar**: puertos, URLs, tamaños de pool, flags y credenciales suministradas por el entorno.

```yaml
# application.yml
server:
  port: 8080

spring:
  application:
    name: fintechlab-inicio

learning:
  welcome-message: "Entorno local"
  maximum-page-size: 50
```

La misma estructura en `application.properties`:

```properties
server.port=8080
spring.application.name=fintechlab-inicio
learning.welcome-message=Entorno local
learning.maximum-page-size=50
```

Elegí un formato y sé consistente dentro de un proyecto. YAML agrupa mejor y es sensible a la indentación (un espacio de más y la propiedad queda en otro nivel, sin error visible); properties es plano, explícito y no tiene esa clase de trampa, pero se vuelve repetitivo.

### 12.1 Leer una propiedad aislada

```java
@Component
class WelcomeMessage {
    private final String value;

    WelcomeMessage(@Value("${learning.welcome-message}") String value) {
        this.value = value;
    }
}
```

`@Value` es útil para **un** valor suelto. Para un grupo, usá configuración tipada:

```java
@ConfigurationProperties(prefix = "learning")
public record LearningProperties(
        String welcomeMessage,
        int maximumPageSize) {
}
```

Registralo con `@ConfigurationPropertiesScan` en la clase de aplicación, o con `@EnableConfigurationProperties(LearningProperties.class)`.

Por qué la configuración tipada es mejor en cuanto hay más de dos valores:

- **Agrupa la intención**: las propiedades de un mismo subsistema viven juntas y se ven como un objeto.
- **Convierte tipos**: `maximumPageSize` es un `int`, no un `String` que parseás a mano en cada uso.
- **Permite validación** (`@Validated` + `@Min`, `@NotBlank`): si el valor es inválido, falla al arrancar, no en la primera petición que lo usa.
- **Evita cadenas mágicas repetidas.** `"${learning.maximum-page-size}"` escrito en cinco archivos es un typo esperando ocurrir; el compilador no revisa strings.
- **El IDE autocompleta** y podés navegar quién usa qué.

Notá además la conversión de nombres: `welcome-message` en el YAML (*kebab-case*) mapea a `welcomeMessage` en Java (*camelCase*). Spring Boot hace ese enlace relajado automáticamente, y es también por qué `SERVER_PORT` (variable de entorno) mapea a `server.port`.

### 12.2 Fuentes y precedencia

Spring Boot combina varias fuentes en un orden de precedencia: argumentos de línea de comandos, propiedades del sistema, variables de entorno, archivos de perfil, archivo base. Una fuente de mayor precedencia **reemplaza** el valor de otra más baja.

```bash
SERVER_PORT=9090 ./mvnw spring-boot:run
```

Comprender que existe una precedencia evita el misterio de *"cambié el YAML y no pasó nada"*. Cuando eso te ocurra, no busques un bug en Spring: buscá **quién está ganando**. Una variable de entorno, un argumento de línea, un perfil activo o un `application.yml` empaquetado dentro del jar además del que estás editando.

**Analogía:** varias capas de reglas, de la ley general al reglamento interno de la oficina. La más específica gana. Cuando algo no se aplica como esperás, el problema no es que la ley no exista: es que hay una norma más cercana que la sobrescribe.

No necesitás memorizar toda la lista de precedencia. Sí necesitás dos cosas: saber **inspeccionar qué fuentes le estás entregando al proceso**, y **registrar el perfil activo al arrancar** para que el log te diga en qué configuración está corriendo realmente.

### 12.3 Perfiles

Un perfil activa configuración o beans para un ambiente o propósito:

- `application.yml` → base común (siempre se carga).
- `application-mysql.yml` → diferencias para MySQL.
- `application-test.yml` → configuración de pruebas cuando hace falta.

El archivo de perfil **no reemplaza** al base: se superpone. Lo que no redefine, lo hereda.

Activación por variable de entorno:

```bash
SPRING_PROFILES_ACTIVE=mysql ./mvnw spring-boot:run
```

O como argumento:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=mysql
```

También podés seleccionar beans por perfil:

```java
@Profile("local")
@Component
class ConsoleNotificationGateway implements NotificationGateway {}
```

Esto conecta con la sección 8: dos implementaciones del mismo tipo dejan de ser ambiguas si solo una está activa por perfil, porque el contenedor ve un único candidato.

Dos advertencias:

- **Un perfil no es un mecanismo de seguridad.** Si versionás una contraseña en `application-prod.yml`, sigue expuesta en el repositorio para cualquiera con acceso al código, esté el perfil activo o no.
- **Usá pocos perfiles, con propósito documentado.** Si terminás con `prod-eu-readonly-v2`, dejaste de tener una aplicación configurable y pasaste a tener docenas de aplicaciones invisibles dentro del mismo código, y nadie puede decirte cuál se está ejecutando.

---

## 13. Variables de entorno y secretos

```yaml
spring:
  datasource:
    url: ${DB_URL}
    username: ${DB_USER}
    password: ${DB_PASSWORD}
```

- `${DB_URL}` **exige** un valor: si falta, la aplicación no arranca. Eso es deseable para un secreto obligatorio.
- `${DB_PORT:3306}` define `3306` como predeterminado si la variable no está.

Elegir entre las dos formas es una decisión de diseño: poné default solo cuando el valor por defecto sea correcto y seguro. Nunca pongas un default para una contraseña.

**Reglas mínimas:**

1. **Nunca versionar credenciales reales.** Y tené presente que git recuerda: borrar la línea en un commit posterior no borra el secreto del historial. Si se filtró, hay que rotarlo, no solo borrarlo.
2. **No escribir passwords, tokens ni bodies sensibles en logs.** Los logs se copian, se envían a servicios externos y los ve mucha más gente que el código.
3. **Usar valores ficticios solo para el entorno local**, claramente marcados como tales.
4. **Fallar de forma explícita si falta un secreto obligatorio.** Un arranque fallido es infinitamente preferible a una aplicación que arranca conectándose a la base equivocada, o sin autenticación.
5. **Usar el gestor de secretos de la plataforma en producción** (el del proveedor cloud, el de tu orquestador, o lo que corresponda).

`.env` facilita el desarrollo local, pero **tratalo como secreto** e incluilo en `.gitignore`. Proporcioná un `.env.example` con las claves y sin los valores reales, para que quien clone el repo sepa qué variables necesita definir.

---

## 14. Configuración frente a constantes de negocio

No todo valor debe ser configurable. Este es uno de los criterios que más se subestima.

El puerto cambia por ambiente. Pero una regla fundamental como *"un importe no puede ser negativo"* pertenece al dominio y no debería poder alterarse accidentalmente con una variable de entorno mal escrita en un despliegue.

La pregunta que decide:

> **¿Cambiar este valor requiere una decisión de negocio y pruebas, o solo adaptar el despliegue?**

- Requiere decisión de negocio y pruebas → es **código o datos gobernados** (con revisión, versionado y tests).
- Solo adapta el despliegue → es **configuración**.

Ejemplos para calibrar:

| Valor | Qué es | Por qué |
|---|---|---|
| `server.port` | Configuración | Cambia por ambiente, no afecta ninguna regla |
| Tamaño del pool de conexiones | Configuración | Ajuste operativo según infraestructura |
| URL de la API externa | Configuración | Distinta en sandbox y producción |
| "El IVA es 21%" | Datos gobernados | Cambia por decisión legal, necesita fecha de vigencia e historial |
| "El importe no puede ser negativo" | Código de dominio | Es una invariante; si se puede desactivar, no era una invariante |
| "Máximo 50 resultados por página" | Zona gris | Configuración si es un límite técnico de rendimiento; dominio si es una regla del producto |

**Analogía:** el volumen de la radio de un auto es configuración; el límite de revoluciones del motor no debería serlo. Los dos son "números", pero uno se ajusta al gusto y el otro protege algo que no querés que el usuario pueda romper.

---

## 15. Anotaciones: metadatos, no hechizos

Esta sección es el eje conceptual de toda la unidad.

Una anotación, en Java, **no ejecuta nada**. Es metadata pegada a una clase, método o campo. Sin algo que la lea, es exactamente tan efectiva como un comentario. Si escribís tu propia `@MiAnotacion` y no escribís el procesador, no pasa absolutamente nada.

**Analogía:** una anotación es un post-it pegado a una carpeta. El post-it que dice "URGENTE" no acelera nada por sí mismo. Funciona únicamente si hay alguien en la oficina cuyo trabajo es revisar las carpetas y priorizar las que tienen ese post-it. Sacá a esa persona y el post-it sigue ahí, perfectamente visible y perfectamente inútil.

Por eso, para cada anotación que uses, hacete estas cinco preguntas:

1. **¿Quién la procesa?** (el escaneo de componentes, la infraestructura transaccional, Jackson, JPA, el validador...)
2. **¿Cuándo la procesa?** (al arrancar el contexto, al compilar, en cada llamada)
3. **¿Qué objeto o proxy produce?** (¿mi clase, o mi clase envuelta?)
4. **¿Qué ocurre si la clase no es un bean?** (casi siempre: nada, en silencio)
5. **¿Cómo puedo observar el resultado?** (log de arranque, log SQL, debugger, Actuator)

Cada anotación pertenece a un subsistema distinto, y mezclarlos es la fuente de la confusión:

| Anotación | Quién la procesa | Qué pasa si el objeto no es bean |
|---|---|---|
| `@Service` | El escaneo de componentes, al arrancar | Nunca se registra; nadie puede inyectarla |
| `@Transactional` | La infraestructura transaccional, vía proxy, en cada llamada | No se abre ninguna transacción, sin error |
| `@Valid` | El validador, en un límite compatible (ej. un controller) | No se valida nada |
| `@Entity` | JPA / Hibernate, al construir el `EntityManagerFactory` | Irrelevante: las entidades no son beans; las gestiona JPA |

Notá el caso de `@Entity`: es la excepción que confirma la regla. Una entidad JPA **no** es un bean de Spring, la crea Hibernate o tu código, y nunca deberías inyectarle dependencias. Pertenece a otro subsistema por completo.

---

## 16. Cómo inspeccionar el grafo sin depender de la memoria

El objetivo no es memorizar el grafo, sino tener técnicas para reconstruirlo cuando haga falta.

Señales útiles, de la más barata a la más pesada:

- **El constructor enumera las dependencias obligatorias.** Es la documentación más confiable que existe, porque el compilador la mantiene actualizada.
- **El log de arranque muestra los fallos de resolución**, con el tipo faltante y la cadena de quién lo pedía. Leelo entero, de abajo hacia arriba: la causa raíz suele estar al final.
- **El IDE navega de la interfaz a sus implementaciones** (y te dice si hay una o cinco, que es justo lo que necesitás saber ante una ambigüedad).
- **Una prueba Java puede construir el service manualmente.** Si podés hacer `new CustomerService(...)` en un test sin framework, entendiste sus dependencias. Si no podés, eso mismo es el diagnóstico.
- **Actuator puede exponer información controlada** (`/actuator/beans`, `/actuator/env`, `/actuator/conditions`) en entornos seguros.

**No expongas el inventario completo de beans públicamente en producción.** `/actuator/beans` revela tu arquitectura interna y `/actuator/env` puede filtrar configuración. Las herramientas de diagnóstico también amplían la superficie de información disponible para alguien que no debería tenerla.

---

## 17. Errores comunes y lectura precisa

Los mensajes de error de Spring son largos pero informativos. La habilidad a desarrollar es **traducir el mensaje a una pregunta sobre el grafo**.

### `No qualifying bean of type...`

Spring encontró **cero** candidatos. Revisá, en este orden:

1. ¿La clase está anotada (`@Service`, `@Component`...) o declarada con `@Bean`?
2. ¿Está debajo del package escaneado? (la causa silenciosa más frecuente; ver 4.1)
3. ¿La dependencia requerida tiene el tipo correcto? (¿pedís la interfaz y registraste solo la implementación de otra interfaz?)
4. ¿Un perfil o una condición la desactivó?
5. ¿Falta un starter que genere ese bean?

### `expected single matching bean but found 2`

Hay **varios** candidatos. Decidí cuál corresponde con los criterios de la sección 8. **No borres uno al azar** para que compile: el segundo candidato existe por alguna razón, y si no la entendés todavía, averiguala antes de eliminarlo.

### `requested bean is currently in creation`

Hay un **ciclo**. Dibujá flechas desde los constructores involucrados; el mensaje suele listar la cadena completa. No busques la anotación faltante: buscá el concepto faltante (sección 11).

### Propiedad no resuelta

Comprobá el nombre exacto, el perfil activo y la fuente. Distinguí entre dos causas que se parecen mucho:

- **Variable ausente**: el placeholder no encontró valor en ninguna fuente.
- **Propiedad mal indentada en YAML**: el valor existe, pero bajo otra clave. `port: 8080` con un espacio de más queda en otro nivel del árbol y Spring nunca lo busca ahí. No hay error de sintaxis; simplemente es otra propiedad.

### El bean existe pero la anotación no hace efecto

El caso más frustrante y el más instructivo. Tres verificaciones, en orden:

1. ¿El objeto lo creó Spring, o hay un `new` escondido en algún lado?
2. ¿La llamada atraviesa el proxy, o es una auto-invocación interna? (sección 7)
3. ¿Está activa la infraestructura correspondiente? (¿el starter, la `@EnableX`, el gestor de transacciones?)

---

## 18. Ejemplo completo de composición

```java
public interface CustomerClock {
    Instant now();
}

@Configuration
class TimeConfiguration {

    @Bean
    CustomerClock customerClock(Clock clock) {
        return () -> Instant.now(clock);
    }

    @Bean
    Clock clock() {
        return Clock.systemUTC();
    }
}

@Service
class RegistrationService {
    private final CustomerRepository repository;
    private final CustomerClock clock;

    RegistrationService(CustomerRepository repository, CustomerClock clock) {
        this.repository = repository;
        this.clock = clock;
    }
}
```

**Qué hace el contexto, paso a paso:**

1. Lee las definiciones: dos métodos `@Bean` en `TimeConfiguration`, `RegistrationService` por escaneo, `CustomerRepository` generado por Spring Data.
2. Ve que `customerClock(Clock clock)` necesita un `Clock`. **Un método `@Bean` con parámetros es también un punto de inyección**: sus argumentos se resuelven como cualquier otra dependencia. Por eso el orden en que escribiste los métodos en el archivo es irrelevante.
3. Crea `Clock` con `Clock.systemUTC()`.
4. Se lo pasa al método que crea `CustomerClock`, que devuelve una lambda. **Nota: el bean es la lambda, no una clase con nombre.** Un bean no tiene que ser una clase que vos escribiste; es cualquier objeto que el contenedor administre.
5. Encuentra el `CustomerRepository` generado como proxy por Spring Data.
6. Construye `RegistrationService` pasándole los dos.

**Y el pago de todo este diseño está en el test:**

```java
@Test
void registra_con_fecha_controlada() {
    var repo = new InMemoryCustomerRepository();
    CustomerClock reloj = () -> Instant.parse("2026-01-01T00:00:00Z");

    var service = new RegistrationService(repo, reloj);
    // ...assert sobre una fecha exacta y reproducible
}
```

Sin Spring, sin base de datos, sin esperar, en milisegundos, y con un resultado idéntico hoy y dentro de dos años. Ese es el retorno concreto de haber inyectado por constructor y de haber tratado el tiempo como una dependencia.

---

## 19. Reglas de diseño para esta unidad

1. **Dependencias obligatorias por constructor**, campos `final`.
2. **Campos de beans singleton sin estado mutable de petición.**
3. **`@Bean` para construcción externa o explícita; estereotipos para roles propios.**
4. **Interfaces solo en fronteras útiles**, no por reflejo.
5. **Secretos fuera del repositorio**, siempre.
6. **Perfiles pocos y con propósito documentado.**
7. **Un error de grafo se resuelve entendiendo el grafo**, no probando anotaciones hasta que compile.
8. **Una anotación se justifica por el mecanismo que activa.** Si no sabés quién la procesa, todavía no sabés qué hace.

---

## 20. Práctica de recuperación

Tomá `CustomerService` del proyecto de referencia y hacé estas acciones **sin modificarlo** (salvo el paso 5, que revertís):

1. Enumerá sus dependencias obligatorias leyendo solo el constructor.
2. Identificá quién crea cada una: ¿escaneo, `@Bean`, o proxy generado?
3. Dibujá las flechas hasta el controller, en papel.
4. Indicá qué objeto podrías construir con `new` en una prueba y qué tendrías que pasarle.
5. **Predecí el mensaje exacto** que vas a ver si eliminás temporalmente `@Service`. Escribilo antes de probar.
6. Comprobá la predicción y revertí el cambio.

Extensiones, si querés forzar más el entendimiento:

7. Agregá una segunda implementación de alguna interfaz y predecí el error antes de arrancar.
8. Cambiá un valor de `application.yml` y sobreescribilo con una variable de entorno; verificá cuál gana.

**No conserves el proyecto roto.** El objetivo del ejercicio no es romper cosas: es **relacionar una causa con una evidencia**, de modo que la próxima vez que veas ese mensaje ya sepas qué lo produce sin tener que investigar de cero.

---

## 21. Resumen

- **IoC** cambia quién controla la composición y el ciclo de vida; es un principio, no una biblioteca.
- **DI** entrega dependencias desde afuera; el constructor hace explícito el contrato y falla temprano.
- Un **bean** es un objeto gestionado por Spring. Un objeto creado con `new` no participa de nada de lo que hace el framework.
- El **contexto** resuelve un grafo y falla al arrancar si falta un candidato o sobran sin criterio. Fallar temprano es la característica, no el defecto.
- El alcance **singleton** exige evitar estado mutable de cada petición; usá variables locales y la base de datos.
- Los **proxies** siguen reglas observables: envuelven el objeto, y las llamadas internas los esquivan.
- **Propiedades y perfiles** adaptan ambientes y tienen una precedencia definida; no deben contener secretos reales.
- Las **anotaciones necesitan un procesador y un contexto**: no actúan por sí mismas.

Continuá con `03-api-rest-controller-service-repository-crud.md`, donde el grafo se convierte en una API visible.
