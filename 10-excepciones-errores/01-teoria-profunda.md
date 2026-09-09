# Unidad 10 — Excepciones y contrato de errores: teoría profunda

## Lugar dentro del recorrido

Esta unidad corresponde a la etapa **Errores consistentes y trazables**. Parte de la API inicial de la unidad 5 y añade una capacidad profesional sin volver a explicar Java, POO o CRUD desde cero.

## Conocimientos previos

- Controladores, servicios y repositorios básicos.
- Lectura de Java y ejecución de una aplicación Spring Boot.
- Conceptos recuperados en <code>00-objetivos-y-ruta.md</code>.

## Objetivos observables

Al terminar, podrás explicar el riesgo que resuelve cada concepto, seguir su funcionamiento, aplicarlo a FintechLab, escribir evidencia y defender cuándo no conviene usarlo.

## Problema central

Un error bien diseñado conserva significado al cruzar capas sin filtrar detalles peligrosos. En un ejercicio pequeño puede parecer suficiente memorizar una API. En producción importan además concurrencia, datos inválidos, latencia, compatibilidad, trazabilidad y recuperación.

## Modelo mental y analogía

Un hospital traduce un síntoma técnico en un diagnóstico útil para cada destinatario.

**Límite:** Una excepción no siempre es recuperable y un mensaje humano no sustituye un tipo o código estable.

## Funcionamiento técnico progresivo

La excepción desenrolla la pila hasta encontrar un manejador; en Spring, resolvers y ControllerAdvice traducen el fallo a una representación HTTP. El recorrido útil siempre contiene cinco preguntas: quién inicia, qué cruza la frontera, qué estado participa, dónde puede fallar y qué evidencia queda.

~~~java
@ExceptionHandler(InsufficientFundsException.class)
ProblemDetail insufficient(InsufficientFundsException ex) {
    var problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    problem.setTitle("Transferencia rechazada");
    return problem;
}
~~~

El ejemplo es deliberadamente pequeño. No es la solución de los ejercicios: muestra vocabulario y forma, mientras las consignas cambian datos, restricciones y estructura.

## Explicación conectada: conservar significado al fallar

### Jerarquía y propagación

<code>Throwable</code> se divide principalmente en Error y Exception. Un Error suele representar condiciones de JVM que la aplicación no debería convertir en éxito. Checked exceptions obligan a declarar o capturar; unchecked heredan RuntimeException. No elijas unchecked para ignorar fallos: elige un contrato que comunique si el llamador puede actuar.

<code>throw</code> crea o propaga; <code>throws</code> declara. Al lanzar, la pila se desenrolla hasta un catch compatible. <code>finally</code> se ejecuta al salir del bloque, pero try-with-resources es más seguro para cerrar recursos y conserva excepciones suprimidas.

Captura donde puedes recuperar, añadir contexto o traducir. Un <code>catch (Exception)</code> que registra y continúa transforma un fallo en estado corrupto. Relanzar una excepción nueva sin causa borra diagnóstico.

### Excepciones de dominio y traducción

Saldo insuficiente, cuenta cerrada e idempotency conflict son resultados de negocio, no SQLException. El repositorio puede lanzar un error técnico; la aplicación traduce restricciones conocidas a un código estable. No conviertas todo fallo de base en 400: muchos son 500 o 503.

<code>@ControllerAdvice</code> y <code>@ExceptionHandler</code> concentran traducción HTTP. Problem Details aporta type, title, status, detail e instance; FintechLab añade code y correlationId. El cliente programa contra code/type, no contra un texto cambiante. Validaciones pueden incluir campos rechazados sin devolver el valor sensible.

### Logging y recuperación

El log interno conserva stack trace para operadores; la respuesta pública no. Registra evento, código, IDs técnicos y correlation ID. Nunca Authorization, contraseña, token o payload financiero completo. Un correlation ID no es prueba de identidad.

Retry corresponde a fallos transitorios y operaciones repetibles. Un error de validación no mejora al reintentar. Fail fast evita continuar con configuración inválida. Degradar puede ser válido para notificación, no para fingir que una transferencia se confirmó. Cada camino de error necesita prueba del status, cuerpo seguro, rollback y log útil.

## Atlas completo del temario oficial

Las subsecciones siguientes cubren cada punto del programa. La etiqueta **Diplomatura** identifica el núcleo oficial; las decisiones de producción y el vínculo con FintechLab constituyen profundización y ampliación profesional.

### 1. error frente a excepción

**Problema e intuición.** error frente a excepción aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Excepciones y contrato de errores, error frente a excepción se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La excepción desenrolla la pila hasta encontrar un manejador; en Spring, resolvers y ControllerAdvice traducen el fallo a una representación HTTP. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** convierte conflictos, datos inválidos y recursos ausentes en Problem Details trazables mediante correlation ID. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Capturar demasiado pronto borra la causa; propagar detalles técnicos puede revelar estructura o secretos. La regla es: Capturar donde se puede actuar o traducir; preservar causa y contexto seguro. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica error frente a excepción con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 2. jerarquía Throwable

**Problema e intuición.** jerarquía Throwable aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Excepciones y contrato de errores, jerarquía Throwable se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La excepción desenrolla la pila hasta encontrar un manejador; en Spring, resolvers y ControllerAdvice traducen el fallo a una representación HTTP. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** convierte conflictos, datos inválidos y recursos ausentes en Problem Details trazables mediante correlation ID. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Capturar demasiado pronto borra la causa; propagar detalles técnicos puede revelar estructura o secretos. La regla es: Capturar donde se puede actuar o traducir; preservar causa y contexto seguro. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica jerarquía Throwable con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 3. checked y unchecked exceptions

**Problema e intuición.** checked y unchecked exceptions aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Una checked exception forma parte de la firma y exige tratamiento declarado; una unchecked puede propagarse sin declaración. La elección debe comunicar si el llamador puede recuperarse. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La excepción desenrolla la pila hasta encontrar un manejador; en Spring, resolvers y ControllerAdvice traducen el fallo a una representación HTTP. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** convierte conflictos, datos inválidos y recursos ausentes en Problem Details trazables mediante correlation ID. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Capturar demasiado pronto borra la causa; propagar detalles técnicos puede revelar estructura o secretos. La regla es: Capturar donde se puede actuar o traducir; preservar causa y contexto seguro. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica checked y unchecked exceptions con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 4. try, catch, finally

**Problema e intuición.** try, catch, finally aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Excepciones y contrato de errores, try, catch, finally se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La excepción desenrolla la pila hasta encontrar un manejador; en Spring, resolvers y ControllerAdvice traducen el fallo a una representación HTTP. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** convierte conflictos, datos inválidos y recursos ausentes en Problem Details trazables mediante correlation ID. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Capturar demasiado pronto borra la causa; propagar detalles técnicos puede revelar estructura o secretos. La regla es: Capturar donde se puede actuar o traducir; preservar causa y contexto seguro. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica try, catch, finally con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 5. throw y throws

**Problema e intuición.** throw y throws aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Excepciones y contrato de errores, throw y throws se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La excepción desenrolla la pila hasta encontrar un manejador; en Spring, resolvers y ControllerAdvice traducen el fallo a una representación HTTP. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** convierte conflictos, datos inválidos y recursos ausentes en Problem Details trazables mediante correlation ID. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Capturar demasiado pronto borra la causa; propagar detalles técnicos puede revelar estructura o secretos. La regla es: Capturar donde se puede actuar o traducir; preservar causa y contexto seguro. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica throw y throws con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 6. propagación

**Problema e intuición.** propagación aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Excepciones y contrato de errores, propagación se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La excepción desenrolla la pila hasta encontrar un manejador; en Spring, resolvers y ControllerAdvice traducen el fallo a una representación HTTP. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** convierte conflictos, datos inválidos y recursos ausentes en Problem Details trazables mediante correlation ID. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Capturar demasiado pronto borra la causa; propagar detalles técnicos puede revelar estructura o secretos. La regla es: Capturar donde se puede actuar o traducir; preservar causa y contexto seguro. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica propagación con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 7. excepciones de dominio

**Problema e intuición.** excepciones de dominio aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Excepciones y contrato de errores, excepciones de dominio se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La excepción desenrolla la pila hasta encontrar un manejador; en Spring, resolvers y ControllerAdvice traducen el fallo a una representación HTTP. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** convierte conflictos, datos inválidos y recursos ausentes en Problem Details trazables mediante correlation ID. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Capturar demasiado pronto borra la causa; propagar detalles técnicos puede revelar estructura o secretos. La regla es: Capturar donde se puede actuar o traducir; preservar causa y contexto seguro. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica excepciones de dominio con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 8. cuándo capturar y cuándo dejar propagar

**Problema e intuición.** cuándo capturar y cuándo dejar propagar aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** CAP describe el comportamiento durante una partición de red: no es una orden de elegir permanentemente dos letras para todo el sistema. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La excepción desenrolla la pila hasta encontrar un manejador; en Spring, resolvers y ControllerAdvice traducen el fallo a una representación HTTP. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** convierte conflictos, datos inválidos y recursos ausentes en Problem Details trazables mediante correlation ID. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Capturar demasiado pronto borra la causa; propagar detalles técnicos puede revelar estructura o secretos. La regla es: Capturar donde se puede actuar o traducir; preservar causa y contexto seguro. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica cuándo capturar y cuándo dejar propagar con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 9. evitar catch genérico y excepciones silenciadas

**Problema e intuición.** evitar catch genérico y excepciones silenciadas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Excepciones y contrato de errores, evitar catch genérico y excepciones silenciadas se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La excepción desenrolla la pila hasta encontrar un manejador; en Spring, resolvers y ControllerAdvice traducen el fallo a una representación HTTP. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** convierte conflictos, datos inválidos y recursos ausentes en Problem Details trazables mediante correlation ID. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Capturar demasiado pronto borra la causa; propagar detalles técnicos puede revelar estructura o secretos. La regla es: Capturar donde se puede actuar o traducir; preservar causa y contexto seguro. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica evitar catch genérico y excepciones silenciadas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 10. try-with-resources

**Problema e intuición.** try-with-resources aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Excepciones y contrato de errores, try-with-resources se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La excepción desenrolla la pila hasta encontrar un manejador; en Spring, resolvers y ControllerAdvice traducen el fallo a una representación HTTP. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** convierte conflictos, datos inválidos y recursos ausentes en Problem Details trazables mediante correlation ID. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Capturar demasiado pronto borra la causa; propagar detalles técnicos puede revelar estructura o secretos. La regla es: Capturar donde se puede actuar o traducir; preservar causa y contexto seguro. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica try-with-resources con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 11. traducción entre errores técnicos y de negocio

**Problema e intuición.** traducción entre errores técnicos y de negocio aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Excepciones y contrato de errores, traducción entre errores técnicos y de negocio se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La excepción desenrolla la pila hasta encontrar un manejador; en Spring, resolvers y ControllerAdvice traducen el fallo a una representación HTTP. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** convierte conflictos, datos inválidos y recursos ausentes en Problem Details trazables mediante correlation ID. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Capturar demasiado pronto borra la causa; propagar detalles técnicos puede revelar estructura o secretos. La regla es: Capturar donde se puede actuar o traducir; preservar causa y contexto seguro. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica traducción entre errores técnicos y de negocio con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 12. @ControllerAdvice

**Problema e intuición.** @ControllerAdvice aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Excepciones y contrato de errores, @ControllerAdvice se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La excepción desenrolla la pila hasta encontrar un manejador; en Spring, resolvers y ControllerAdvice traducen el fallo a una representación HTTP. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** convierte conflictos, datos inválidos y recursos ausentes en Problem Details trazables mediante correlation ID. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Capturar demasiado pronto borra la causa; propagar detalles técnicos puede revelar estructura o secretos. La regla es: Capturar donde se puede actuar o traducir; preservar causa y contexto seguro. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica @ControllerAdvice con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 13. @ExceptionHandler

**Problema e intuición.** @ExceptionHandler aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Excepciones y contrato de errores, @ExceptionHandler se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La excepción desenrolla la pila hasta encontrar un manejador; en Spring, resolvers y ControllerAdvice traducen el fallo a una representación HTTP. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** convierte conflictos, datos inválidos y recursos ausentes en Problem Details trazables mediante correlation ID. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Capturar demasiado pronto borra la causa; propagar detalles técnicos puede revelar estructura o secretos. La regla es: Capturar donde se puede actuar o traducir; preservar causa y contexto seguro. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica @ExceptionHandler con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 14. respuestas Problem Details según el estándar compatible

**Problema e intuición.** respuestas Problem Details según el estándar compatible aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Problem Details define una forma interoperable de describir errores HTTP con campos estables; los detalles internos y stack traces no pertenecen al contrato público. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La excepción desenrolla la pila hasta encontrar un manejador; en Spring, resolvers y ControllerAdvice traducen el fallo a una representación HTTP. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** convierte conflictos, datos inválidos y recursos ausentes en Problem Details trazables mediante correlation ID. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Capturar demasiado pronto borra la causa; propagar detalles técnicos puede revelar estructura o secretos. La regla es: Capturar donde se puede actuar o traducir; preservar causa y contexto seguro. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica respuestas Problem Details según el estándar compatible con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 15. códigos HTTP apropiados

**Problema e intuición.** códigos HTTP apropiados aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Excepciones y contrato de errores, códigos HTTP apropiados se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La excepción desenrolla la pila hasta encontrar un manejador; en Spring, resolvers y ControllerAdvice traducen el fallo a una representación HTTP. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** convierte conflictos, datos inválidos y recursos ausentes en Problem Details trazables mediante correlation ID. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Capturar demasiado pronto borra la causa; propagar detalles técnicos puede revelar estructura o secretos. La regla es: Capturar donde se puede actuar o traducir; preservar causa y contexto seguro. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica códigos HTTP apropiados con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 16. mensajes seguros

**Problema e intuición.** mensajes seguros aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Excepciones y contrato de errores, mensajes seguros se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La excepción desenrolla la pila hasta encontrar un manejador; en Spring, resolvers y ControllerAdvice traducen el fallo a una representación HTTP. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** convierte conflictos, datos inválidos y recursos ausentes en Problem Details trazables mediante correlation ID. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Capturar demasiado pronto borra la causa; propagar detalles técnicos puede revelar estructura o secretos. La regla es: Capturar donde se puede actuar o traducir; preservar causa y contexto seguro. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica mensajes seguros con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 17. logging con contexto

**Problema e intuición.** logging con contexto aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Excepciones y contrato de errores, logging con contexto se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La excepción desenrolla la pila hasta encontrar un manejador; en Spring, resolvers y ControllerAdvice traducen el fallo a una representación HTTP. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** convierte conflictos, datos inválidos y recursos ausentes en Problem Details trazables mediante correlation ID. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Capturar demasiado pronto borra la causa; propagar detalles técnicos puede revelar estructura o secretos. La regla es: Capturar donde se puede actuar o traducir; preservar causa y contexto seguro. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica logging con contexto con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 18. correlation ID

**Problema e intuición.** correlation ID aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Excepciones y contrato de errores, correlation ID se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La excepción desenrolla la pila hasta encontrar un manejador; en Spring, resolvers y ControllerAdvice traducen el fallo a una representación HTTP. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** convierte conflictos, datos inválidos y recursos ausentes en Problem Details trazables mediante correlation ID. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Capturar demasiado pronto borra la causa; propagar detalles técnicos puede revelar estructura o secretos. La regla es: Capturar donde se puede actuar o traducir; preservar causa y contexto seguro. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica correlation ID con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 19. no exponer stack traces ni secretos

**Problema e intuición.** no exponer stack traces ni secretos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Excepciones y contrato de errores, no exponer stack traces ni secretos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La excepción desenrolla la pila hasta encontrar un manejador; en Spring, resolvers y ControllerAdvice traducen el fallo a una representación HTTP. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** convierte conflictos, datos inválidos y recursos ausentes en Problem Details trazables mediante correlation ID. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Capturar demasiado pronto borra la causa; propagar detalles técnicos puede revelar estructura o secretos. La regla es: Capturar donde se puede actuar o traducir; preservar causa y contexto seguro. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica no exponer stack traces ni secretos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 20. recuperación, reintento y fallo rápido

**Problema e intuición.** recuperación, reintento y fallo rápido aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Excepciones y contrato de errores, recuperación, reintento y fallo rápido se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La excepción desenrolla la pila hasta encontrar un manejador; en Spring, resolvers y ControllerAdvice traducen el fallo a una representación HTTP. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** convierte conflictos, datos inválidos y recursos ausentes en Problem Details trazables mediante correlation ID. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Capturar demasiado pronto borra la causa; propagar detalles técnicos puede revelar estructura o secretos. La regla es: Capturar donde se puede actuar o traducir; preservar causa y contexto seguro. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica recuperación, reintento y fallo rápido con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 21. pruebas de caminos de error

**Problema e intuición.** pruebas de caminos de error aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Excepciones y contrato de errores, pruebas de caminos de error se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La excepción desenrolla la pila hasta encontrar un manejador; en Spring, resolvers y ControllerAdvice traducen el fallo a una representación HTTP. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** convierte conflictos, datos inválidos y recursos ausentes en Problem Details trazables mediante correlation ID. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Capturar demasiado pronto borra la causa; propagar detalles técnicos puede revelar estructura o secretos. La regla es: Capturar donde se puede actuar o traducir; preservar causa y contexto seguro. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica pruebas de caminos de error con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

## Decisiones y alternativas

1. Define primero la invariante o el riesgo.
2. Compara una opción simple con otra más operable.
3. Registra el costo introducido: latencia, acoplamiento, complejidad o mantenimiento.
4. Elige: Capturar donde se puede actuar o traducir; preservar causa y contexto seguro.
5. Documenta qué dato futuro haría cambiar la decisión.

## Errores frecuentes

- Copiar una anotación sin conocer quién la interpreta ni cuándo.
- Confundir el caso feliz con el contrato completo.
- Añadir infraestructura sin una necesidad observable.
- Escribir un test que replica la implementación en vez de cubrir riesgo.
- Registrar datos sensibles para facilitar debugging.
- Afirmar una garantía sin una restricción, prueba o medición que la sostenga.

## Qué ocurre en producción

Capturar demasiado pronto borra la causa; propagar detalles técnicos puede revelar estructura o secretos. Un profesional diseña para degradación, documenta límites, mide antes de optimizar y deja una ruta de recuperación. FintechLab usa datos ficticios y no debe conectarse con bancos ni procesar dinero real.

## Cómo reconocer que realmente lo entendiste

- Puedes explicarlo sin depender del nombre de una anotación.
- Puedes dibujar el recorrido y marcar al menos tres fallos.
- Puedes cambiar el ejemplo sin copiarlo.
- Puedes escribir una prueba que falla cuando se rompe la regla.
- Puedes nombrar una alternativa y justificar el trade-off.

## Resumen sencillo

La unidad enseña a convertir Excepciones y contrato de errores en decisiones verificables. La idea profesional no es acumular herramientas, sino conectar propósito, mecanismo, fallo, evidencia y costo.

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
