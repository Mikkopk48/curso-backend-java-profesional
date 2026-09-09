# Unidad 16 — Spring Cloud OpenFeign: teoría profunda

## Lugar dentro del recorrido

Esta unidad corresponde a la etapa **Cliente de notificaciones**. Parte de la API inicial de la unidad 5 y añade una capacidad profesional sin volver a explicar Java, POO o CRUD desde cero.

## Conocimientos previos

- Controladores, servicios y repositorios básicos.
- Lectura de Java y ejecución de una aplicación Spring Boot.
- Conceptos recuperados en <code>00-objetivos-y-ruta.md</code>.

## Objetivos observables

Al terminar, podrás explicar el riesgo que resuelve cada concepto, seguir su funcionamiento, aplicarlo a FintechLab, escribir evidencia y defender cuándo no conviene usarlo.

## Problema central

Un cliente declarativo simplifica sintaxis, no elimina la red ni sus fallos. En un ejercicio pequeño puede parecer suficiente memorizar una API. En producción importan además concurrencia, datos inválidos, latencia, compatibilidad, trazabilidad y recuperación.

## Modelo mental y analogía

Feign es un intérprete que convierte una interfaz local en mensajes remotos.

**Límite:** La llamada sigue teniendo latencia, versiones incompatibles y respuestas parciales.

## Funcionamiento técnico progresivo

La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. El recorrido útil siempre contiene cinco preguntas: quién inicia, qué cruza la frontera, qué estado participa, dónde puede fallar y qué evidencia queda.

~~~java
@FeignClient(name = "notification-service", configuration = NotificationClientConfig.class)
interface NotificationClient {
    @PostMapping("/internal/notifications")
    void send(@RequestHeader("X-Correlation-ID") String correlationId,
              @RequestBody NotificationCommand command);
}
~~~

El ejemplo es deliberadamente pequeño. No es la solución de los ejercicios: muestra vocabulario y forma, mientras las consignas cambian datos, restricciones y estructura.

## Explicación conectada: Feign no convierte la red en local

Spring Cloud OpenFeign crea un proxy de una interfaz anotada. Al invocarlo, construye URI, query, headers y body; un encoder serializa, un cliente ejecuta y un decoder transforma la respuesta. El nombre del servicio puede resolverse mediante balanceo y discovery. Todo esto sucede después de una llamada Java aparentemente simple.

El DTO de contrato pertenece al límite remoto y debe versionarse. No compartas entidades ni una biblioteca de dominio entre servicios: eso acopla despliegues. Propaga correlation ID y, cuando corresponda, identidad de forma controlada; nunca registres Authorization.

Connect timeout limita establecer conexión; read timeout limita esperar datos. Clasifica errores con ErrorDecoder: 404 puede ser resultado de dominio remoto, 401/403 identidad, 429 capacidad y 5xx fallo temporal. Conserva status y un código seguro sin filtrar payload.

Retry solo se considera si la operación es idempotente y el presupuesto lo permite. Una POST remota puede haberse ejecutado. Circuit breaker observa una ventana de resultados y abre; un fallback puede devolver dato degradado, pero fingir éxito de notificación o saldo es peligroso. El balanceo no garantiza afinidad ni estado compartido.

Prueba la interfaz contra WireMock: verifica path, body, header, 404, timeout, 503 y JSON inválido. Una integración con discovery comprueba resolución de nombre. La facilidad declarativa debe ir acompañada por métricas de latencia, errores y circuito.

## Atlas completo del temario oficial

Las subsecciones siguientes cubren cada punto del programa. La etiqueta **Diplomatura** identifica el núcleo oficial; las decisiones de producción y el vínculo con FintechLab constituyen profundización y ampliación profesional.

### 1. problema de invocar servicios remotos

**Problema e intuición.** problema de invocar servicios remotos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Spring Cloud OpenFeign, problema de invocar servicios remotos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Configurar timeouts, errores y observabilidad; reintentar solo operaciones cuya semántica lo permite. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica problema de invocar servicios remotos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 2. cliente imperativo frente a cliente declarativo

**Problema e intuición.** cliente imperativo frente a cliente declarativo aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Spring Cloud OpenFeign, cliente imperativo frente a cliente declarativo se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Configurar timeouts, errores y observabilidad; reintentar solo operaciones cuya semántica lo permite. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica cliente imperativo frente a cliente declarativo con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 3. Spring Cloud OpenFeign

**Problema e intuición.** Spring Cloud OpenFeign aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Spring Cloud OpenFeign, Spring Cloud OpenFeign se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Configurar timeouts, errores y observabilidad; reintentar solo operaciones cuya semántica lo permite. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica Spring Cloud OpenFeign con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 4. definición de interfaces

**Problema e intuición.** definición de interfaces aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Spring Cloud OpenFeign, definición de interfaces se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Configurar timeouts, errores y observabilidad; reintentar solo operaciones cuya semántica lo permite. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica definición de interfaces con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 5. serialización de parámetros y cuerpos

**Problema e intuición.** serialización de parámetros y cuerpos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Spring Cloud OpenFeign, serialización de parámetros y cuerpos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Configurar timeouts, errores y observabilidad; reintentar solo operaciones cuya semántica lo permite. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica serialización de parámetros y cuerpos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 6. DTO de contrato

**Problema e intuición.** DTO de contrato aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Spring Cloud OpenFeign, DTO de contrato se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Configurar timeouts, errores y observabilidad; reintentar solo operaciones cuya semántica lo permite. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica DTO de contrato con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 7. headers

**Problema e intuición.** headers aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Spring Cloud OpenFeign, headers se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Configurar timeouts, errores y observabilidad; reintentar solo operaciones cuya semántica lo permite. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica headers con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 8. propagación segura de identidad

**Problema e intuición.** propagación segura de identidad aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Spring Cloud OpenFeign, propagación segura de identidad se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Configurar timeouts, errores y observabilidad; reintentar solo operaciones cuya semántica lo permite. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica propagación segura de identidad con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 9. configuración

**Problema e intuición.** configuración aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Spring Cloud OpenFeign, configuración se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Configurar timeouts, errores y observabilidad; reintentar solo operaciones cuya semántica lo permite. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica configuración con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 10. timeouts de conexión y lectura

**Problema e intuición.** timeouts de conexión y lectura aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Timeout limita espera, retry repite, circuit breaker deja de insistir temporalmente y bulkhead aísla recursos. Combinarlos sin presupuesto puede amplificar una caída. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Configurar timeouts, errores y observabilidad; reintentar solo operaciones cuya semántica lo permite. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica timeouts de conexión y lectura con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 11. manejo de errores

**Problema e intuición.** manejo de errores aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Spring Cloud OpenFeign, manejo de errores se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Configurar timeouts, errores y observabilidad; reintentar solo operaciones cuya semántica lo permite. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica manejo de errores con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 12. ErrorDecoder o mecanismo compatible

**Problema e intuición.** ErrorDecoder o mecanismo compatible aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Spring Cloud OpenFeign, ErrorDecoder o mecanismo compatible se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Configurar timeouts, errores y observabilidad; reintentar solo operaciones cuya semántica lo permite. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica ErrorDecoder o mecanismo compatible con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 13. logging sin filtrar secretos

**Problema e intuición.** logging sin filtrar secretos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Spring Cloud OpenFeign, logging sin filtrar secretos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Configurar timeouts, errores y observabilidad; reintentar solo operaciones cuya semántica lo permite. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica logging sin filtrar secretos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 14. balanceo

**Problema e intuición.** balanceo aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Spring Cloud OpenFeign, balanceo se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Configurar timeouts, errores y observabilidad; reintentar solo operaciones cuya semántica lo permite. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica balanceo con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 15. retries

**Problema e intuición.** retries aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Spring Cloud OpenFeign, retries se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Configurar timeouts, errores y observabilidad; reintentar solo operaciones cuya semántica lo permite. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica retries con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 16. peligro de reintentar operaciones no idempotentes

**Problema e intuición.** peligro de reintentar operaciones no idempotentes aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Una operación es idempotente cuando repetir la misma intención produce el mismo efecto observable sobre el estado del servidor. No significa que cada respuesta deba ser byte por byte idéntica. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Configurar timeouts, errores y observabilidad; reintentar solo operaciones cuya semántica lo permite. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica peligro de reintentar operaciones no idempotentes con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 17. integración con circuit breaker

**Problema e intuición.** integración con circuit breaker aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Timeout limita espera, retry repite, circuit breaker deja de insistir temporalmente y bulkhead aísla recursos. Combinarlos sin presupuesto puede amplificar una caída. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Configurar timeouts, errores y observabilidad; reintentar solo operaciones cuya semántica lo permite. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica integración con circuit breaker con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 18. fallbacks y sus riesgos

**Problema e intuición.** fallbacks y sus riesgos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Spring Cloud OpenFeign, fallbacks y sus riesgos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Configurar timeouts, errores y observabilidad; reintentar solo operaciones cuya semántica lo permite. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica fallbacks y sus riesgos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 19. paginación

**Problema e intuición.** paginación aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Spring Cloud OpenFeign, paginación se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Configurar timeouts, errores y observabilidad; reintentar solo operaciones cuya semántica lo permite. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica paginación con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 20. evolución de contratos

**Problema e intuición.** evolución de contratos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Spring Cloud OpenFeign, evolución de contratos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Configurar timeouts, errores y observabilidad; reintentar solo operaciones cuya semántica lo permite. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica evolución de contratos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 21. pruebas con WireMock

**Problema e intuición.** pruebas con WireMock aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Los dobles controlan colaboraciones: un stub devuelve datos preparados, un mock además verifica interacciones y un fake implementa una versión simplificada con comportamiento real. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Configurar timeouts, errores y observabilidad; reintentar solo operaciones cuya semántica lo permite. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica pruebas con WireMock con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 22. pruebas de integración

**Problema e intuición.** pruebas de integración aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Spring Cloud OpenFeign, pruebas de integración se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Configurar timeouts, errores y observabilidad; reintentar solo operaciones cuya semántica lo permite. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica pruebas de integración con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 23. acoplamiento entre servicios

**Problema e intuición.** acoplamiento entre servicios aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Spring Cloud OpenFeign, acoplamiento entre servicios se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Configurar timeouts, errores y observabilidad; reintentar solo operaciones cuya semántica lo permite. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica acoplamiento entre servicios con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

## Decisiones y alternativas

1. Define primero la invariante o el riesgo.
2. Compara una opción simple con otra más operable.
3. Registra el costo introducido: latencia, acoplamiento, complejidad o mantenimiento.
4. Elige: Configurar timeouts, errores y observabilidad; reintentar solo operaciones cuya semántica lo permite.
5. Documenta qué dato futuro haría cambiar la decisión.

## Errores frecuentes

- Copiar una anotación sin conocer quién la interpreta ni cuándo.
- Confundir el caso feliz con el contrato completo.
- Añadir infraestructura sin una necesidad observable.
- Escribir un test que replica la implementación en vez de cubrir riesgo.
- Registrar datos sensibles para facilitar debugging.
- Afirmar una garantía sin una restricción, prueba o medición que la sostenga.

## Qué ocurre en producción

Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. Un profesional diseña para degradación, documenta límites, mide antes de optimizar y deja una ruta de recuperación. FintechLab usa datos ficticios y no debe conectarse con bancos ni procesar dinero real.

## Cómo reconocer que realmente lo entendiste

- Puedes explicarlo sin depender del nombre de una anotación.
- Puedes dibujar el recorrido y marcar al menos tres fallos.
- Puedes cambiar el ejemplo sin copiarlo.
- Puedes escribir una prueba que falla cuando se rompe la regla.
- Puedes nombrar una alternativa y justificar el trade-off.

## Resumen sencillo

La unidad enseña a convertir Spring Cloud OpenFeign en decisiones verificables. La idea profesional no es acumular herramientas, sino conectar propósito, mecanismo, fallo, evidencia y costo.

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
