# Unidad 6 — HTTP I: GET y DELETE: teoría profunda

## Lugar dentro del recorrido

Esta unidad corresponde a la etapa **Contrato de consulta y baja**. Parte de la API inicial de la unidad 5 y añade una capacidad profesional sin volver a explicar Java, POO o CRUD desde cero.

## Conocimientos previos

- Controladores, servicios y repositorios básicos.
- Lectura de Java y ejecución de una aplicación Spring Boot.
- Conceptos recuperados en <code>00-objetivos-y-ruta.md</code>.

## Objetivos observables

Al terminar, podrás explicar el riesgo que resuelve cada concepto, seguir su funcionamiento, aplicarlo a FintechLab, escribir evidencia y defender cuándo no conviene usarlo.

## Problema central

HTTP es un contrato semántico, no una colección de anotaciones. En un ejercicio pequeño puede parecer suficiente memorizar una API. En producción importan además concurrencia, datos inválidos, latencia, compatibilidad, trazabilidad y recuperación.

## Modelo mental y analogía

Una API se parece a una ventanilla con formularios y reglas públicas: la dirección identifica el trámite y el método expresa la intención.

**Límite:** Una ventanilla es centralizada; HTTP atraviesa redes, proxies y cachés que pueden repetir, retrasar o transformar mensajes.

## Funcionamiento técnico progresivo

Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. El recorrido útil siempre contiene cinco preguntas: quién inicia, qué cruza la frontera, qué estado participa, dónde puede fallar y qué evidencia queda.

~~~java
// AccountQueryController.java
@GetMapping("/{accountId}")
ResponseEntity<AccountResponse> find(@PathVariable UUID accountId,
                                     @RequestHeader(value = "If-None-Match", required = false) String validator) {
    VersionedAccount result = query.find(accountId);
    if (result.etag().equals(validator)) return ResponseEntity.status(304).eTag(result.etag()).build();
    return ResponseEntity.ok().eTag(result.etag()).body(result.body());
}
~~~

El ejemplo es deliberadamente pequeño. No es la solución de los ejercicios: muestra vocabulario y forma, mientras las consignas cambian datos, restricciones y estructura.

## Explicación conectada: de bytes en la red a un contrato HTTP

### Una petición no es una llamada de método remota

Un cliente no invoca directamente tu controller. Construye un mensaje con método, target, versión y headers; opcionalmente añade un body. Ese mensaje atraviesa DNS, conexión, TLS, proxies y el servidor antes de que Spring MVC pueda convertirlo en objetos Java. La respuesta realiza el camino inverso. Por eso una excepción local, un timeout de red y un status 503 son fenómenos distintos aunque el usuario vea “no funcionó”.

La URI identifica un recurso o colección, no la clase que lo implementa. <code>/accounts/{id}</code> nombra una cuenta; <code>/accounts/{id}/close</code> puede ser una acción justificable, pero primero debes preguntar si cerrar es una transición del recurso representable con PUT/PATCH o una operación con semántica propia. Los path parameters identifican partes jerárquicas; query parameters modifican la vista de una colección: filtro, orden y paginación. Un filtro no debería cambiar datos.

### Representación y negociación

El recurso “cuenta” no es su JSON. JSON es una representación elegida mediante Content-Type y, en respuestas negociadas, Accept. Spring usa HttpMessageConverter para serializar y deserializar; Jackson puede rechazar forma, tipo o formato antes de entrar al caso de uso. Esto explica por qué la validación sintáctica pertenece al borde y las reglas como “no sobregirar” pertenecen al dominio.

Headers como Authorization, Content-Type, Accept, Cache-Control, ETag, If-None-Match, Location y X-Correlation-ID transportan metadatos. No coloques secretos o información personal en la URI: aparece en logs, historial y métricas con mayor facilidad.

### GET, caché y condiciones

GET solicita una representación sin pedir un cambio de estado. Es seguro en el sentido HTTP y también idempotente. Puede registrar métricas, pero no debería crear una transferencia. Una colección vacía normalmente responde 200 con una lista vacía; un identificador inexistente suele responder 404. Paginación por offset es fácil de navegar, pero cambios concurrentes pueden repetir o saltar elementos; un cursor estable evita parte de ese problema a cambio de no permitir saltos arbitrarios.

ETag representa una versión opaca. El cliente guarda ETag y luego envía If-None-Match. Si la versión sigue igual, 304 evita el body. Last-Modified/If-Modified-Since usa tiempo y suele tener menor precisión. Cache-Control decide quién puede almacenar y por cuánto tiempo. Datos financieros personalizados requieren analizar privacidad antes de habilitar caché compartida.

### DELETE y significado de la ausencia

DELETE solicita eliminar la asociación identificada por la URI. Repetirlo no debe eliminar recursos adicionales: por eso es idempotente. La primera ejecución puede devolver 204; una repetición puede devolver 404 y seguir siendo idempotente porque el estado final no cambia. 410 comunica que el recurso existió y fue retirado de forma conocida.

Un borrado físico elimina filas y puede romper auditoría o claves foráneas. Un borrado lógico conserva datos con un estado, pero todas las consultas deben respetarlo y la retención debe tener fundamento. En FintechLab, “cerrar cuenta” es más correcto que borrar historial: exige saldo cero, conserva movimientos y responde conflicto cuando la regla no se cumple.

### Status como resultado, no decoración

200 incluye representación; 204 no lleva body; 304 pertenece a validación de caché; 400 indica petición inválida; 401 autenticación ausente o inválida; 403 permiso insuficiente; 404 recurso no encontrado; 409 conflicto con estado; 410 retirada conocida; 500 fallo inesperado propio y 503 indisponibilidad temporal. El status no sustituye un cuerpo de error estable.

Con Spring, usa ResponseEntity cuando necesitas controlar status o headers y retornos simples cuando el contrato es inequívoco. Prueba el endpoint con MockMvc o un cliente HTTP y verifica método, URI, headers, status, media type y JSON; no te limites a comprobar que el controller fue llamado.

## Atlas completo del temario oficial

Las subsecciones siguientes cubren cada punto del programa. La etiqueta **Diplomatura** identifica el núcleo oficial; las decisiones de producción y el vínculo con FintechLab constituyen profundización y ampliación profesional.

### 1. papel de HTTP en una arquitectura cliente-servidor

**Problema e intuición.** papel de HTTP en una arquitectura cliente-servidor aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En HTTP I: GET y DELETE, papel de HTTP en una arquitectura cliente-servidor se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Diseñar primero el recurso y el comportamiento observable; elegir el código de estado después de conocer el resultado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica papel de HTTP en una arquitectura cliente-servidor con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 2. anatomía real de una petición y una respuesta

**Problema e intuición.** anatomía real de una petición y una respuesta aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En HTTP I: GET y DELETE, anatomía real de una petición y una respuesta se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Diseñar primero el recurso y el comportamiento observable; elegir el código de estado después de conocer el resultado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica anatomía real de una petición y una respuesta con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 3. método, URI, versión, headers y body

**Problema e intuición.** método, URI, versión, headers y body aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En HTTP I: GET y DELETE, método, URI, versión, headers y body se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Diseñar primero el recurso y el comportamiento observable; elegir el código de estado después de conocer el resultado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica método, URI, versión, headers y body con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 4. recursos y diseño de URI

**Problema e intuición.** recursos y diseño de URI aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En HTTP I: GET y DELETE, recursos y diseño de URI se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Diseñar primero el recurso y el comportamiento observable; elegir el código de estado después de conocer el resultado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica recursos y diseño de URI con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 5. path parameters y query parameters

**Problema e intuición.** path parameters y query parameters aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En HTTP I: GET y DELETE, path parameters y query parameters se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Diseñar primero el recurso y el comportamiento observable; elegir el código de estado después de conocer el resultado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica path parameters y query parameters con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 6. headers importantes

**Problema e intuición.** headers importantes aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En HTTP I: GET y DELETE, headers importantes se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Diseñar primero el recurso y el comportamiento observable; elegir el código de estado después de conocer el resultado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica headers importantes con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 7. representación y negociación de contenido

**Problema e intuición.** representación y negociación de contenido aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En HTTP I: GET y DELETE, representación y negociación de contenido se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Diseñar primero el recurso y el comportamiento observable; elegir el código de estado después de conocer el resultado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica representación y negociación de contenido con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 8. JSON y tipos de medios

**Problema e intuición.** JSON y tipos de medios aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En HTTP I: GET y DELETE, JSON y tipos de medios se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Diseñar primero el recurso y el comportamiento observable; elegir el código de estado después de conocer el resultado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica JSON y tipos de medios con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 9. métodos seguros e idempotentes

**Problema e intuición.** métodos seguros e idempotentes aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Una operación es idempotente cuando repetir la misma intención produce el mismo efecto observable sobre el estado del servidor. No significa que cada respuesta deba ser byte por byte idéntica. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Diseñar primero el recurso y el comportamiento observable; elegir el código de estado después de conocer el resultado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica métodos seguros e idempotentes con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 10. semántica de GET

**Problema e intuición.** semántica de GET aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En HTTP I: GET y DELETE, semántica de GET se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Diseñar primero el recurso y el comportamiento observable; elegir el código de estado después de conocer el resultado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica semántica de GET con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 11. semántica de DELETE

**Problema e intuición.** semántica de DELETE aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En HTTP I: GET y DELETE, semántica de DELETE se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Diseñar primero el recurso y el comportamiento observable; elegir el código de estado después de conocer el resultado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica semántica de DELETE con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 12. códigos 200, 204, 304, 400, 401, 403, 404, 409, 410, 500 y 503

**Problema e intuición.** códigos 200, 204, 304, 400, 401, 403, 404, 409, 410, 500 y 503 aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** 401 indica ausencia o invalidez de autenticación adecuada; 403 indica que la identidad autenticada no posee permiso para la acción. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Diseñar primero el recurso y el comportamiento observable; elegir el código de estado después de conocer el resultado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica códigos 200, 204, 304, 400, 401, 403, 404, 409, 410, 500 y 503 con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 13. diferencia entre colección y recurso individual

**Problema e intuición.** diferencia entre colección y recurso individual aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En HTTP I: GET y DELETE, diferencia entre colección y recurso individual se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Diseñar primero el recurso y el comportamiento observable; elegir el código de estado después de conocer el resultado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica diferencia entre colección y recurso individual con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 14. filtros, ordenamiento y paginación

**Problema e intuición.** filtros, ordenamiento y paginación aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En HTTP I: GET y DELETE, filtros, ordenamiento y paginación se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Diseñar primero el recurso y el comportamiento observable; elegir el código de estado después de conocer el resultado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica filtros, ordenamiento y paginación con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 15. caché, ETag, Last-Modified y peticiones condicionales

**Problema e intuición.** caché, ETag, Last-Modified y peticiones condicionales aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Un validador permite preguntar si una representación cambió. Si la condición se cumple, el servidor evita transferir el cuerpo y puede responder 304. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Diseñar primero el recurso y el comportamiento observable; elegir el código de estado después de conocer el resultado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica caché, ETag, Last-Modified y peticiones condicionales con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 16. qué debería responder DELETE

**Problema e intuición.** qué debería responder DELETE aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En HTTP I: GET y DELETE, qué debería responder DELETE se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Diseñar primero el recurso y el comportamiento observable; elegir el código de estado después de conocer el resultado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica qué debería responder DELETE con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 17. borrado lógico frente a borrado físico

**Problema e intuición.** borrado lógico frente a borrado físico aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En HTTP I: GET y DELETE, borrado lógico frente a borrado físico se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Diseñar primero el recurso y el comportamiento observable; elegir el código de estado después de conocer el resultado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica borrado lógico frente a borrado físico con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 18. uso de curl, Postman y Swagger UI

**Problema e intuición.** uso de curl, Postman y Swagger UI aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** OpenAPI es la especificación del contrato; Swagger agrupa herramientas históricas y Swagger UI renderiza y permite invocar operaciones descritas. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Diseñar primero el recurso y el comportamiento observable; elegir el código de estado después de conocer el resultado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica uso de curl, Postman y Swagger UI con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 19. implementación con Spring MVC

**Problema e intuición.** implementación con Spring MVC aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En HTTP I: GET y DELETE, implementación con Spring MVC se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Diseñar primero el recurso y el comportamiento observable; elegir el código de estado después de conocer el resultado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica implementación con Spring MVC con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 20. ResponseEntity

**Problema e intuición.** ResponseEntity aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** ResponseEntity representa status, headers y body como una decisión explícita del endpoint; no sustituye el diseño del contrato. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Diseñar primero el recurso y el comportamiento observable; elegir el código de estado después de conocer el resultado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica ResponseEntity con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 21. pruebas de endpoints

**Problema e intuición.** pruebas de endpoints aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En HTTP I: GET y DELETE, pruebas de endpoints se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Diseñar primero el recurso y el comportamiento observable; elegir el código de estado después de conocer el resultado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica pruebas de endpoints con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 22. errores comunes al diseñar APIs

**Problema e intuición.** errores comunes al diseñar APIs aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En HTTP I: GET y DELETE, errores comunes al diseñar APIs se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Diseñar primero el recurso y el comportamiento observable; elegir el código de estado después de conocer el resultado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica errores comunes al diseñar APIs con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

## Decisiones y alternativas

1. Define primero la invariante o el riesgo.
2. Compara una opción simple con otra más operable.
3. Registra el costo introducido: latencia, acoplamiento, complejidad o mantenimiento.
4. Elige: Diseñar primero el recurso y el comportamiento observable; elegir el código de estado después de conocer el resultado.
5. Documenta qué dato futuro haría cambiar la decisión.

## Errores frecuentes

- Copiar una anotación sin conocer quién la interpreta ni cuándo.
- Confundir el caso feliz con el contrato completo.
- Añadir infraestructura sin una necesidad observable.
- Escribir un test que replica la implementación en vez de cubrir riesgo.
- Registrar datos sensibles para facilitar debugging.
- Afirmar una garantía sin una restricción, prueba o medición que la sostenga.

## Qué ocurre en producción

Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. Un profesional diseña para degradación, documenta límites, mide antes de optimizar y deja una ruta de recuperación. FintechLab usa datos ficticios y no debe conectarse con bancos ni procesar dinero real.

## Cómo reconocer que realmente lo entendiste

- Puedes explicarlo sin depender del nombre de una anotación.
- Puedes dibujar el recorrido y marcar al menos tres fallos.
- Puedes cambiar el ejemplo sin copiarlo.
- Puedes escribir una prueba que falla cuando se rompe la regla.
- Puedes nombrar una alternativa y justificar el trade-off.

## Resumen sencillo

La unidad enseña a convertir HTTP I: GET y DELETE en decisiones verificables. La idea profesional no es acumular herramientas, sino conectar propósito, mecanismo, fallo, evidencia y costo.

## Recuperación activa — sin respuestas

1. Explica la gran idea de la unidad en menos de noventa segundos.
2. Dibuja el recorrido interno de una operación de FintechLab.
3. Señala un fallo que el ejemplo mínimo no cubre.
4. Compara dos alternativas y nombra el dato que decidiría entre ellas.
5. ¿Qué afirmación necesitaría una prueba y cuál una medición?

## Siguiente paso

Continúa con <code>80-laboratorio-y-practica.md</code>. No consultes soluciones: conserva commits, salidas de pruebas y decisiones como evidencia.

## Fuentes oficiales

Consulta el catálogo versionado de <code>90-apendices/03-fuentes-oficiales-y-migracion.md</code>. La explicación necesaria para estudiar está contenida aquí; las fuentes sirven para verificar APIs y profundizar.
