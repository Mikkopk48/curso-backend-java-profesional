# Unidad 9 — HTTP II: POST y PUT: teoría profunda

## Lugar dentro del recorrido

Esta unidad corresponde a la etapa **Altas, actualizaciones e idempotencia**. Parte de la API inicial de la unidad 5 y añade una capacidad profesional sin volver a explicar Java, POO o CRUD desde cero.

## Conocimientos previos

- Controladores, servicios y repositorios básicos.
- Lectura de Java y ejecución de una aplicación Spring Boot.
- Conceptos recuperados en <code>00-objetivos-y-ruta.md</code>.

## Objetivos observables

Al terminar, podrás explicar el riesgo que resuelve cada concepto, seguir su funcionamiento, aplicarlo a FintechLab, escribir evidencia y defender cuándo no conviene usarlo.

## Problema central

Escribir datos exige definir duplicados, concurrencia y validación, no solo recibir JSON. En un ejercicio pequeño puede parecer suficiente memorizar una API. En producción importan además concurrencia, datos inválidos, latencia, compatibilidad, trazabilidad y recuperación.

## Modelo mental y analogía

Registrar una transferencia se parece a entregar una orden numerada: repetir el papel no debe duplicar el movimiento.

**Límite:** Una base de datos concurrente admite carreras que no existen en una oficina atendida de a una persona.

## Funcionamiento técnico progresivo

Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. El recorrido útil siempre contiene cinco preguntas: quién inicia, qué cruza la frontera, qué estado participa, dónde puede fallar y qué evidencia queda.

~~~java
public record CreateTransferRequest(
    @NotNull UUID originAccountId,
    @NotNull UUID destinationAccountId,
    @NotNull @DecimalMin("0.01") BigDecimal amount,
    @NotBlank String currency) {}
~~~

El ejemplo es deliberadamente pequeño. No es la solución de los ejercicios: muestra vocabulario y forma, mientras las consignas cambian datos, restricciones y estructura.

## Explicación conectada: escribir sin duplicar ni perder cambios

### POST, PUT y PATCH

POST envía una representación a un recurso que decide cómo procesarla; al crear, 201 y Location comunican el nuevo identificador. POST no es idempotente por definición. PUT dirige un reemplazo completo a una URI conocida y es idempotente: repetir el mismo reemplazo deja el mismo estado. PATCH expresa un cambio parcial mediante un formato documentado; no es “PUT con menos campos”.

El body cruza Jackson y Bean Validation. Un DTO de entrada define campos aceptados; otro de salida estabiliza el contrato. Exponer una entidad deja que cambios de persistencia modifiquen la API, facilita mass assignment y puede disparar lazy loading. La validación de forma comprueba null, longitud, patrón y rango; el servicio comprueba saldo, moneda, estado e identidad.

### 409 y concurrencia optimista

Dos clientes pueden leer versión 3 y editar. Con <code>@Version</code>, la primera escritura produce versión 4; la segunda intenta actualizar versión 3, afecta cero filas y genera un conflicto optimista. La API puede devolver 409 y pedir al cliente recuperar el estado. Sobrescribir silenciosamente pierde cambios.

El versionado no resuelve toda la transferencia: dos débitos concurrentes necesitan lock, update condicional o política optimista diseñada. La regla se demuestra con una prueba concurrente o restricción, no con una comprobación previa aislada.

### Idempotencia de una transferencia

El cliente crea una Idempotency-Key por intención. El servidor calcula una huella del request, consulta la clave y guarda clave, huella y resultado en la misma transacción. Si se repite con igual huella devuelve el resultado; si cambia el payload responde 409. Una restricción UNIQUE cierra la carrera entre dos requests simultáneas.

La clave necesita alcance, longitud, retención y política de privacidad. No uses el body completo como clave ni guardes tokens. Reintentar tras timeout es seguro solo si el mecanismo persiste antes de confirmar efectos.

### Controller y service

El controller traduce HTTP: headers, DTO, status y Location. El caso de uso coordina regla y transacción. El repositorio persiste. Si el controller calcula saldo o captura excepciones de SQL, la regla queda duplicada y difícil de probar. Prueba POST/PUT tanto en éxito como en validación, conflicto, recurso ausente y repetición.

## Atlas completo del temario oficial

Las subsecciones siguientes cubren cada punto del programa. La etiqueta **Diplomatura** identifica el núcleo oficial; las decisiones de producción y el vínculo con FintechLab constituyen profundización y ampliación profesional.

### 1. semántica de POST

**Problema e intuición.** semántica de POST aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En HTTP II: POST y PUT, semántica de POST se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Separar forma válida, regla de negocio y control de concurrencia; hacer explícita la semántica de repetición. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica semántica de POST con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 2. creación de recursos

**Problema e intuición.** creación de recursos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En HTTP II: POST y PUT, creación de recursos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Separar forma válida, regla de negocio y control de concurrencia; hacer explícita la semántica de repetición. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica creación de recursos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 3. 201 Created y header Location

**Problema e intuición.** 201 Created y header Location aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Tras crear un recurso, 201 comunica creación y Location identifica la URI del recurso resultante cuando puede expresarse. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Separar forma válida, regla de negocio y control de concurrencia; hacer explícita la semántica de repetición. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica 201 Created y header Location con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 4. semántica de PUT

**Problema e intuición.** semántica de PUT aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** PUT expresa reemplazo de la representación en la URI conocida y debe ser idempotente; PATCH describe una modificación parcial cuyo formato necesita contrato propio. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Separar forma válida, regla de negocio y control de concurrencia; hacer explícita la semántica de repetición. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica semántica de PUT con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 5. reemplazo completo e idempotencia

**Problema e intuición.** reemplazo completo e idempotencia aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Una operación es idempotente cuando repetir la misma intención produce el mismo efecto observable sobre el estado del servidor. No significa que cada respuesta deba ser byte por byte idéntica. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Separar forma válida, regla de negocio y control de concurrencia; hacer explícita la semántica de repetición. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica reemplazo completo e idempotencia con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 6. diferencia entre PUT y PATCH

**Problema e intuición.** diferencia entre PUT y PATCH aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** PUT expresa reemplazo de la representación en la URI conocida y debe ser idempotente; PATCH describe una modificación parcial cuyo formato necesita contrato propio. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Separar forma válida, regla de negocio y control de concurrencia; hacer explícita la semántica de repetición. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica diferencia entre PUT y PATCH con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 7. request body

**Problema e intuición.** request body aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En HTTP II: POST y PUT, request body se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Separar forma válida, regla de negocio y control de concurrencia; hacer explícita la semántica de repetición. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica request body con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 8. serialización y deserialización

**Problema e intuición.** serialización y deserialización aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En HTTP II: POST y PUT, serialización y deserialización se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Separar forma válida, regla de negocio y control de concurrencia; hacer explícita la semántica de repetición. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica serialización y deserialización con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 9. DTO de entrada y salida

**Problema e intuición.** DTO de entrada y salida aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En HTTP II: POST y PUT, DTO de entrada y salida se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Separar forma válida, regla de negocio y control de concurrencia; hacer explícita la semántica de repetición. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica DTO de entrada y salida con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 10. validación con Bean Validation

**Problema e intuición.** validación con Bean Validation aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En HTTP II: POST y PUT, validación con Bean Validation se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Separar forma válida, regla de negocio y control de concurrencia; hacer explícita la semántica de repetición. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica validación con Bean Validation con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 11. errores de validación consistentes

**Problema e intuición.** errores de validación consistentes aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En HTTP II: POST y PUT, errores de validación consistentes se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Separar forma válida, regla de negocio y control de concurrencia; hacer explícita la semántica de repetición. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica errores de validación consistentes con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 12. diseño de comandos

**Problema e intuición.** diseño de comandos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En HTTP II: POST y PUT, diseño de comandos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Separar forma válida, regla de negocio y control de concurrencia; hacer explícita la semántica de repetición. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica diseño de comandos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 13. actualización concurrente

**Problema e intuición.** actualización concurrente aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En HTTP II: POST y PUT, actualización concurrente se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Separar forma válida, regla de negocio y control de concurrencia; hacer explícita la semántica de repetición. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica actualización concurrente con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 14. optimistic locking y versionado

**Problema e intuición.** optimistic locking y versionado aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En HTTP II: POST y PUT, optimistic locking y versionado se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Separar forma válida, regla de negocio y control de concurrencia; hacer explícita la semántica de repetición. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica optimistic locking y versionado con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 15. peticiones duplicadas

**Problema e intuición.** peticiones duplicadas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En HTTP II: POST y PUT, peticiones duplicadas se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Separar forma válida, regla de negocio y control de concurrencia; hacer explícita la semántica de repetición. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica peticiones duplicadas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 16. claves de idempotencia

**Problema e intuición.** claves de idempotencia aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Una operación es idempotente cuando repetir la misma intención produce el mismo efecto observable sobre el estado del servidor. No significa que cada respuesta deba ser byte por byte idéntica. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Separar forma válida, regla de negocio y control de concurrencia; hacer explícita la semántica de repetición. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica claves de idempotencia con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 17. conflicto 409

**Problema e intuición.** conflicto 409 aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En HTTP II: POST y PUT, conflicto 409 se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Separar forma válida, regla de negocio y control de concurrencia; hacer explícita la semántica de repetición. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica conflicto 409 con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 18. validación sintáctica frente a reglas de negocio

**Problema e intuición.** validación sintáctica frente a reglas de negocio aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En HTTP II: POST y PUT, validación sintáctica frente a reglas de negocio se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Separar forma válida, regla de negocio y control de concurrencia; hacer explícita la semántica de repetición. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica validación sintáctica frente a reglas de negocio con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 19. límites entre controller y service

**Problema e intuición.** límites entre controller y service aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En HTTP II: POST y PUT, límites entre controller y service se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Separar forma válida, regla de negocio y control de concurrencia; hacer explícita la semántica de repetición. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica límites entre controller y service con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 20. cargas JSON anidadas

**Problema e intuición.** cargas JSON anidadas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En HTTP II: POST y PUT, cargas JSON anidadas se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Separar forma válida, regla de negocio y control de concurrencia; hacer explícita la semántica de repetición. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica cargas JSON anidadas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 21. compatibilidad de contratos

**Problema e intuición.** compatibilidad de contratos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En HTTP II: POST y PUT, compatibilidad de contratos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Separar forma válida, regla de negocio y control de concurrencia; hacer explícita la semántica de repetición. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica compatibilidad de contratos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 22. pruebas de POST y PUT

**Problema e intuición.** pruebas de POST y PUT aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** PUT expresa reemplazo de la representación en la URI conocida y debe ser idempotente; PATCH describe una modificación parcial cuyo formato necesita contrato propio. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring MVC recibe el mensaje en el servidor, selecciona un handler mediante sus mappings, convierte representaciones con HttpMessageConverter y escribe una respuesta que todavía puede atravesar filtros y proxies. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** se aplica a los recursos clientes, cuentas, movimientos y transferencias, usando respuestas observables y datos ficticios. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Confundir sintaxis con semántica produce contratos incompatibles, cachés incorrectas y duplicación de efectos. La regla es: Separar forma válida, regla de negocio y control de concurrencia; hacer explícita la semántica de repetición. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica pruebas de POST y PUT con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

## Decisiones y alternativas

1. Define primero la invariante o el riesgo.
2. Compara una opción simple con otra más operable.
3. Registra el costo introducido: latencia, acoplamiento, complejidad o mantenimiento.
4. Elige: Separar forma válida, regla de negocio y control de concurrencia; hacer explícita la semántica de repetición.
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

La unidad enseña a convertir HTTP II: POST y PUT en decisiones verificables. La idea profesional no es acumular herramientas, sino conectar propósito, mecanismo, fallo, evidencia y costo.

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
