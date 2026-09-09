# Unidad 5 — API REST por capas y CRUD

## Qué vas a construir mentalmente

Una API recibe mensajes HTTP y devuelve mensajes HTTP. El controller traduce esa frontera a tipos Java, el service ejecuta un caso de uso y el repository accede a datos. Esa frase es útil solo si puedes seguir una petición real y distinguir qué decisión pertenece a cada lugar.

## HTTP mínimo para empezar

Una petición contiene, como mínimo, método y destino. Puede incluir headers y body:

~~~http
POST /api/customers HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "fullName": "Ana Demo",
  "email": "ana@example.test"
}
~~~

Una respuesta contiene status, headers y, a veces, body:

~~~http
HTTP/1.1 201 Created
Content-Type: application/json
Location: /api/customers/8bb4...

{
  "id": "8bb4...",
  "fullName": "Ana Demo",
  "email": "ana@example.test"
}
~~~

Vocabulario inicial:

| Método | Intención inicial | Resultado frecuente |
|---|---|---|
| GET | Leer | `200 OK` o `404 Not Found` |
| POST | Crear o procesar un comando | `201 Created` para un recurso nuevo |
| PUT | Reemplazar la representación editable | `200 OK` o `204 No Content` |
| DELETE | Eliminar la relación identificada | `204 No Content` o resultado documentado |

Las unidades 6 y 9 profundizan semántica, seguridad, idempotencia, cache y diseño de contratos. Aquí necesitas suficiente HTTP para entender el flujo del código.

## JSON no es un objeto Java

JSON es texto con una estructura. Jackson, integrado por Spring Boot Web, deserializa el body a un tipo Java y serializa el retorno a JSON.

~~~java
public record CreateCustomerRequest(
        String fullName,
        String email) {
}
~~~

El nombre de las propiedades debe poder mapearse a los componentes del record. Una cadena con JSON inválido falla antes de que el service se ejecute. Un JSON válido puede producir un objeto que todavía incumple validaciones.

Distingue:

- **sintaxis JSON:** faltan comillas o una coma;
- **forma/tipo:** se envía un objeto donde se esperaba texto;
- **validación de entrada:** el email tiene formato inválido;
- **regla de negocio:** el email ya pertenece a otro cliente;
- **fallo técnico:** la base de datos no responde.

Cada categoría requiere una respuesta y una evidencia diferentes.

## Controller: adaptador HTTP

~~~java
@RestController
@RequestMapping("/api/customers")
class CustomerController {

    private final CustomerService service;

    CustomerController(CustomerService service) {
        this.service = service;
    }
}
~~~

Responsabilidades adecuadas:

- declarar método, ruta, media type y parámetros HTTP;
- activar validación de entrada;
- convertir datos de la petición al comando del caso de uso;
- delegar;
- transformar el resultado en status, headers y body.

Responsabilidades que no deberían vivir allí:

- escribir consultas SQL;
- decidir reglas como unicidad o saldo suficiente;
- coordinar una transacción de negocio compleja;
- capturar cualquier `Exception` y devolver `200`;
- almacenar estado de usuarios en campos;
- devolver entidades JPA por comodidad.

Un controller delgado no es uno sin lógica: conserva lógica de adaptación HTTP, no lógica de negocio.

## Service: caso de uso

~~~java
@Service
class CustomerService {

    private final CustomerRepository repository;

    CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    CustomerResponse create(CreateCustomerRequest request) {
        // validar regla, construir entidad, persistir, mapear salida
    }
}
~~~

Responsabilidades adecuadas:

- orquestar el caso de uso;
- comprobar reglas que requieren estado o colaboración;
- decidir qué guardar y en qué orden;
- establecer el límite transaccional;
- devolver un resultado independiente de HTTP cuando sea conveniente.

El nombre `service` es amplio. Prefiere métodos que expresen intención (`createCustomer`, `changeEmail`) frente a métodos vagos (`process`, `handle`, `doStuff`).

## Repository: frontera de persistencia

~~~java
interface CustomerRepository extends JpaRepository<CustomerEntity, String> {
    boolean existsByEmailIgnoreCase(String email);
}
~~~

El repository ofrece operaciones de persistencia en lenguaje cercano al dominio. Spring Data JPA genera la implementación de métodos básicos y puede derivar consultas a partir de nombres.

El repository no debe decidir qué status HTTP devolver ni qué mensaje ve un usuario. Tampoco es un lugar para esconder todas las reglas de negocio dentro de una consulta imposible de leer.

## Entity: modelo persistente

~~~java
@Entity
@Table(name = "customers")
class CustomerEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    protected CustomerEntity() {
        // requerido por JPA
    }

    CustomerEntity(String id, String fullName, String email) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
    }

    void update(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }
}
~~~

La entidad representa estado persistente y comportamiento coherente con ese estado. JPA necesita una identidad y una forma de construirla. Que un campo tenga `nullable = false` ayuda al esquema, pero no sustituye validar el body ni expresar reglas en la aplicación.

## DTO: contrato de frontera

Un DTO transporta datos. Separa el contrato externo del modelo persistente.

~~~java
public record CustomerResponse(
        String id,
        String fullName,
        String email) {

    static CustomerResponse from(CustomerEntity entity) {
        return new CustomerResponse(
                entity.id(),
                entity.fullName(),
                entity.email());
    }
}
~~~

Razones para no devolver la entidad:

- un cambio de tabla no debería cambiar la API accidentalmente;
- relaciones JPA pueden cargar datos inesperados o causar recursión;
- la entidad puede contener campos internos;
- entrada y salida suelen tener campos diferentes;
- el cliente no debe controlar ID, estado o auditoría reservados.

Para el primer CRUD, mapear a mano hace visible la transformación. MapStruct aparece en la unidad 22 cuando el costo repetitivo justifique automatizarla.

## Flujo CREATE completo

### DTO validado

~~~java
public record CreateCustomerRequest(
        @NotBlank @Size(max = 120) String fullName,
        @NotBlank @Email @Size(max = 180) String email) {
}
~~~

### Controller

~~~java
@PostMapping
ResponseEntity<CustomerResponse> create(
        @Valid @RequestBody CreateCustomerRequest request) {

    CustomerResponse created = service.create(request);
    URI location = URI.create("/api/customers/" + created.id());
    return ResponseEntity.created(location).body(created);
}
~~~

### Service

~~~java
@Transactional
CustomerResponse create(CreateCustomerRequest request) {
    String normalizedEmail = request.email().trim().toLowerCase(Locale.ROOT);
    if (repository.existsByEmailIgnoreCase(normalizedEmail)) {
        throw new CustomerEmailAlreadyExistsException(normalizedEmail);
    }

    CustomerEntity entity = new CustomerEntity(
            UUID.randomUUID().toString(),
            request.fullName().trim(),
            normalizedEmail);

    return CustomerResponse.from(repository.save(entity));
}
~~~

### Recorrido

1. Spring MVC selecciona el método por POST y ruta.
2. Jackson crea `CreateCustomerRequest` desde JSON.
3. Bean Validation evalúa las anotaciones porque existe `@Valid`.
4. El controller delega un objeto válido en forma.
5. El service normaliza y comprueba unicidad.
6. Se crea una entidad con identidad del servidor.
7. El repository persiste dentro de la transacción.
8. La entidad guardada se mapea a salida.
9. El controller responde `201`, `Location` y JSON.

La comprobación de existencia mejora el mensaje, pero la restricción única de la base sigue siendo necesaria por concurrencia. Dos peticiones pueden superar el `exists` antes de que una inserte. Una unidad posterior profundiza ese conflicto.

## Flujo READ

~~~java
@GetMapping("/{customerId}")
CustomerResponse findById(@PathVariable String customerId) {
    return service.findById(customerId);
}
~~~

~~~java
@Transactional(readOnly = true)
CustomerResponse findById(String customerId) {
    return repository.findById(customerId)
            .map(CustomerResponse::from)
            .orElseThrow(() -> new CustomerNotFoundException(customerId));
}
~~~

`@PathVariable` toma el segmento de la ruta. `Optional` expresa que el repository puede no encontrar una fila. `orElseThrow` traduce ausencia persistente a una excepción de aplicación que luego se convierte en `404`.

Para una colección:

~~~java
@GetMapping
List<CustomerResponse> findAll() {
    return service.findAll();
}
~~~

Una lista vacía no es normalmente un error. La paginación se introduce en la unidad 6; no uses `findAll` sin límite en una tabla grande.

## Flujo UPDATE

~~~java
public record UpdateCustomerRequest(
        @NotBlank @Size(max = 120) String fullName,
        @NotBlank @Email @Size(max = 180) String email) {
}
~~~

~~~java
@PutMapping("/{customerId}")
CustomerResponse update(
        @PathVariable String customerId,
        @Valid @RequestBody UpdateCustomerRequest request) {
    return service.update(customerId, request);
}
~~~

~~~java
@Transactional
CustomerResponse update(String customerId, UpdateCustomerRequest request) {
    CustomerEntity entity = repository.findById(customerId)
            .orElseThrow(() -> new CustomerNotFoundException(customerId));

    String normalizedEmail = request.email().trim().toLowerCase(Locale.ROOT);
    if (repository.existsByEmailIgnoreCaseAndIdNot(normalizedEmail, customerId)) {
        throw new CustomerEmailAlreadyExistsException(normalizedEmail);
    }

    entity.update(request.fullName().trim(), normalizedEmail);
    return CustomerResponse.from(entity);
}
~~~

Dentro de una transacción, JPA observa cambios de una entidad administrada y puede emitir el `UPDATE` al confirmar; no siempre hace falta llamar `save` de nuevo. Para un principiante es importante saberlo: no es que el cambio “se guarde por magia”, sino que el contexto de persistencia compara estado administrado.

PUT completo e idempotencia se profundizan en la unidad 9. Aquí el body representa todos los campos editables del recurso.

## Flujo DELETE

~~~java
@DeleteMapping("/{customerId}")
ResponseEntity<Void> delete(@PathVariable String customerId) {
    service.delete(customerId);
    return ResponseEntity.noContent().build();
}
~~~

~~~java
@Transactional
void delete(String customerId) {
    CustomerEntity entity = repository.findById(customerId)
            .orElseThrow(() -> new CustomerNotFoundException(customerId));
    repository.delete(entity);
}
~~~

Este borrado físico es aceptable para un recurso ficticio de aprendizaje sin historial. En FintechLab real, clientes, cuentas y movimientos requieren retención, auditoría y reglas; más adelante decidirás entre cierre, estado y borrado lógico. Nunca copies un DELETE de tutorial a datos regulados sin analizar el dominio.

## Bean Validation: la primera barrera

Anotaciones frecuentes:

| Anotación | Comprueba | No comprueba |
|---|---|---|
| `@NotNull` | valor distinto de `null` | texto vacío |
| `@NotBlank` | texto no nulo con contenido no blanco | unicidad |
| `@Size` | longitud o tamaño | significado del dato |
| `@Email` | forma aproximada de email | que la dirección exista |
| `@Positive` | número mayor que cero | saldo disponible |
| `@Past` / `@Future` | relación temporal | regla completa de negocio |

`@Valid` en el parámetro activa validación del objeto y de objetos anidados marcados apropiadamente. Sin `@Valid`, las anotaciones del DTO pueden quedar como decoración.

Valida de nuevo en otros límites si el caso de uso puede invocarse sin HTTP. La seguridad de una regla no debe depender exclusivamente del controller.

## Error global controlado

Una API no debería filtrar stack traces ni devolver formatos distintos según el controller.

~~~java
@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    ResponseEntity<ApiError> notFound(CustomerNotFoundException exception) {
        ApiError body = new ApiError(
                "CUSTOMER_NOT_FOUND",
                exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(CustomerEmailAlreadyExistsException.class)
    ResponseEntity<ApiError> conflict(CustomerEmailAlreadyExistsException exception) {
        ApiError body = new ApiError(
                "CUSTOMER_EMAIL_ALREADY_EXISTS",
                exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }
}
~~~

`@RestControllerAdvice` aplica a controllers y serializa cuerpos. `@ExceptionHandler` selecciona un método por tipo. La unidad 10 diseña un contrato de errores profesional y Problem Details; aquí basta con no perder status ni exponer detalles internos.

Para validación, captura `MethodArgumentNotValidException` y devuelve campos de forma estable. No incluyas el valor rechazado si puede ser sensible.

## Parámetros web básicos

~~~java
@GetMapping
List<CustomerResponse> search(
        @RequestParam(defaultValue = "") String name,
        @RequestHeader(value = "X-Correlation-ID", required = false) String correlationId) {
    // delegación
}
~~~

- `@PathVariable`: identidad incorporada en la ruta;
- `@RequestParam`: filtro u opción de la consulta;
- `@RequestHeader`: metadato de la petición;
- `@RequestBody`: representación en el body.

No uses query parameters para enviar contraseñas, tokens ni datos sensibles: las URLs aparecen fácilmente en logs e historiales.

## Status y `ResponseEntity`

Spring puede responder `200` al serializar un objeto directamente. Usa `ResponseEntity` cuando necesitas controlar status o headers.

~~~java
return ResponseEntity.ok(response);
return ResponseEntity.created(location).body(response);
return ResponseEntity.noContent().build();
return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
~~~

No devuelvas siempre `200` con `{ "success": false }`. Los clientes, proxies y herramientas entienden el protocolo mediante el status.

## Organización de paquetes

Dos esquemas comunes:

### Por capa

~~~text
controller/
service/
repository/
entity/
dto/
~~~

Es familiar en proyectos pequeños, pero una funcionalidad queda dispersa.

### Por capacidad

~~~text
customer/
  CustomerController.java
  CustomerService.java
  CustomerRepository.java
  CustomerEntity.java
transfer/
  ...
~~~

Agrupa lo que cambia junto y facilita evolucionar a un monolito modular. El proyecto de referencia usa capacidad primero y un package transversal pequeño para errores.

No conviertas cada clase en un package. El objetivo es expresar límites, no maximizar carpetas.

## CRUD no significa arquitectura completa

CRUD son cuatro operaciones sobre estado: create, read, update y delete. Aprenderlo sirve porque ejercita el recorrido entero. Pero un backend profesional también contiene:

- acciones de negocio que no son simples actualizaciones;
- invariantes y concurrencia;
- autorización;
- idempotencia;
- auditoría;
- integraciones y fallos parciales;
- migraciones y operación;
- contratos y compatibilidad.

Trata este CRUD como un laboratorio de cableado, no como plantilla universal.

## Antipatrones y correcciones

### Controller gigante

**Síntoma:** valida negocio, llama al repository, captura errores y mapea todo.

**Riesgo:** acopla HTTP, persistencia y reglas; dificulta probar.

**Dirección:** extraer el caso de uso al service y mantener adaptación HTTP.

### Service que solo delega

~~~java
return repository.findAll();
~~~

No siempre es incorrecto, pero una capa sin política ni transformación puede no aportar nada. Mantén el service si expresa la frontera del caso de uso que crecerá; evita capas rituales en ejemplos triviales.

### Repository desde el controller

Acopla el contrato web al modelo persistente y salta reglas. Usa el caso de uso.

### Entity como request y response

Permite mass assignment, filtra estructura interna y mezcla ciclos de vida. Define DTO específicos.

### `try/catch (Exception)` en cada método

Pierde categorías y suele convertir fallos reales en respuestas incorrectas. Deja propagar y traduce excepciones conocidas en un advice.

### Devolver `null`

Un `null` puede convertirse en una respuesta ambigua o fallar tarde. Usa ausencia explícita en el repository y una excepción o resultado del caso de uso.

### Campos públicos o setters para todo

Permiten estados inválidos. Expón operaciones que mantengan invariantes.

## Recorrido de depuración de una petición

Cuando un endpoint no funciona, no cambies todas las capas:

1. Confirma método, URL, puerto y `Content-Type`.
2. Observa status y body completos.
3. Comprueba si el mapping del controller coincide.
4. Verifica si falla deserialización o validación antes del método.
5. Coloca un breakpoint o log seguro en la entrada del caso de uso.
6. Comprueba la regla y los argumentos del repository.
7. Inspecciona la causa de persistencia.
8. Añade una prueba que reproduzca el fallo antes de corregirlo.

Una respuesta `404` puede significar que no existe mapping o que tu aplicación decidió recurso ausente. El body y los logs deben permitir distinguirlas.

## Comprobación manual con `curl`

Crear:

~~~bash
curl -i -X POST http://localhost:8080/api/customers \
  -H 'Content-Type: application/json' \
  -d '{"fullName":"Ana Demo","email":"ana@example.test"}'
~~~

Listar:

~~~bash
curl -i http://localhost:8080/api/customers
~~~

Consultar usando el ID recibido:

~~~bash
curl -i http://localhost:8080/api/customers/ID_FICTICIO
~~~

Enviar entrada inválida:

~~~bash
curl -i -X POST http://localhost:8080/api/customers \
  -H 'Content-Type: application/json' \
  -d '{"fullName":"","email":"no-es-email"}'
~~~

Guarda request, response y versión. Una captura de “funciona” sin la petición completa es evidencia débil.

## Checklist de revisión por capa

### Controller

- [ ] Mappings claros y sin verbos accidentales en la ruta.
- [ ] Entrada con DTO y `@Valid`.
- [ ] Sin consultas ni reglas de negocio.
- [ ] Status y headers coherentes.
- [ ] Sin estado mutable de petición.

### Service

- [ ] Nombre de caso de uso.
- [ ] Dependencias por constructor.
- [ ] Reglas y normalización explícitas.
- [ ] Límite transaccional consciente.
- [ ] Sin detalles HTTP innecesarios.

### Repository y entity

- [ ] Identidad y restricciones declaradas.
- [ ] Consultas con propósito legible.
- [ ] Entidad no expuesta como contrato.
- [ ] No se cargan colecciones ilimitadas por accidente.

### Errores

- [ ] Ausencia, conflicto, entrada inválida y fallo inesperado son distinguibles.
- [ ] No se filtran stack traces ni secretos.
- [ ] El cliente recibe una forma estable.

## Resumen

- HTTP es la frontera; Jackson y Spring MVC adaptan mensajes a objetos.
- Controller, service y repository representan responsabilidades distintas, no escalones obligatorios sin sentido.
- Los DTO protegen el contrato; las entidades protegen persistencia y estado.
- Bean Validation comprueba forma; el service conserva reglas de negocio.
- El CRUD enseña el recorrido, pero no reemplaza diseño de dominio.
- Los errores se traducen de forma central y los status conservan significado.
- Depura siguiendo el recorrido y cambia una hipótesis a la vez.

Continúa con `04-jpa-mysql-pruebas-y-diagnostico.md` para entender qué sucede desde el repository hasta la base y cómo probar sin depender de una demostración manual.
