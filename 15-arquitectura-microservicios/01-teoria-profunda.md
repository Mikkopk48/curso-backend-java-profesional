# Unidad 15 — Arquitectura de microservicios: teoría profunda

## Lugar dentro del recorrido

Esta unidad corresponde a la etapa **Decisión de separación**. Parte de la API inicial de la unidad 5 y añade una capacidad profesional sin volver a explicar Java, POO o CRUD desde cero.

## Conocimientos previos

- Controladores, servicios y repositorios básicos.
- Lectura de Java y ejecución de una aplicación Spring Boot.
- Conceptos recuperados en <code>00-objetivos-y-ruta.md</code>.

## Objetivos observables

Al terminar, podrás explicar el riesgo que resuelve cada concepto, seguir su funcionamiento, aplicarlo a FintechLab, escribir evidencia y defender cuándo no conviene usarlo.

## Problema central

Distribuir módulos cambia llamadas locales por redes y consistencia inmediata por coordinación. En un ejercicio pequeño puede parecer suficiente memorizar una API. En producción importan además concurrencia, datos inválidos, latencia, compatibilidad, trazabilidad y recuperación.

## Modelo mental y analogía

Separar una empresa en sedes concede autonomía, pero agrega logística y fallos de comunicación.

**Límite:** Los servicios no son personas: requieren protocolos exactos, observabilidad y automatización.

## Funcionamiento técnico progresivo

La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. El recorrido útil siempre contiene cinco preguntas: quién inicia, qué cruza la frontera, qué estado participa, dónde puede fallar y qué evidencia queda.

~~~text
Límite consistente: cuentas + transferencias + movimientos
Límite separable: notificaciones simuladas
Razón: el saldo exige atomicidad local; notificar tolera entrega posterior idempotente.
~~~

El ejemplo es deliberadamente pequeño. No es la solución de los ejercicios: muestra vocabulario y forma, mientras las consignas cambian datos, restricciones y estructura.

## Explicación conectada: el costo de cruzar una red

### Tres formas de organizar

Un monolito despliega como unidad. Puede estar bien estructurado o ser una masa acoplada. Un monolito modular conserva un proceso, pero aplica límites internos y propiedad de modelos. Microservicios despliegan por separado y poseen datos; ofrecen autonomía cuando equipos y ritmos lo necesitan, pero agregan red, operación y consistencia distribuida.

Un bounded context delimita lenguaje y reglas. No es una tabla ni una clase. Cuentas, saldos, movimientos y transferencia forman en FintechLab un límite consistente; notificación tiene otra razón de cambio y tolera demora. Separar un servicio por entidad produce chatty calls y transacciones distribuidas.

### Fallos parciales y datos

Una llamada remota puede llegar aunque el cliente agote su timeout. Puede fallar DNS, conexión, TLS, serialización, servidor o respuesta. Timeout limita espera; retry repite y puede amplificar carga; circuit breaker deja de llamar temporalmente; bulkhead separa recursos. Define un presupuesto total: tres reintentos con timeouts largos pueden multiplicar latencia.

Base por servicio significa propiedad, no necesariamente un motor físico por proceso. Otro servicio no consulta tablas ajenas. Sin transacción global, se acepta consistencia eventual o se rediseña el límite. Saga coordina pasos locales y compensaciones; una compensación no siempre restaura el mundo. Transactional outbox guarda evento con el cambio y lo publica después; consumidores idempotentes manejan duplicados.

CAP trata decisiones durante partición. No es “elegir dos” para cada operación siempre. Un catálogo puede devolver dato antiguo; un débito puede preferir rechazar antes que aceptar sin autoridad.

### Infraestructura y observabilidad

API Gateway concentra routing y políticas de borde, pero puede volverse cuello de botella. Service discovery resuelve instancias; Config Server distribuye parámetros. Logs con correlation/trace ID, métricas de latencia/error/saturación y trazas permiten reconstruir recorridos. Sin observabilidad, la distribución convierte causas en conjeturas.

Contratos evolucionan de forma compatible y se prueban entre productor y consumidor. Seguridad requiere identidad de servicio, TLS y permisos. Strangler migra una capacidad gradualmente. El criterio profesional sigue siendo: no separar hasta que beneficio organizacional u operativo supere el costo.

## Atlas completo del temario oficial

Las subsecciones siguientes cubren cada punto del programa. La etiqueta **Diplomatura** identifica el núcleo oficial; las decisiones de producción y el vínculo con FintechLab constituyen profundización y ampliación profesional.

### 1. monolito, monolito modular y microservicios

**Problema e intuición.** monolito, monolito modular y microservicios aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Arquitectura de microservicios, monolito, monolito modular y microservicios se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Mantener el monolito modular hasta que un límite de negocio y una necesidad operativa justifiquen el costo. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica monolito, monolito modular y microservicios con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 2. ventajas, costos y señales reales para elegir

**Problema e intuición.** ventajas, costos y señales reales para elegir aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Arquitectura de microservicios, ventajas, costos y señales reales para elegir se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Mantener el monolito modular hasta que un límite de negocio y una necesidad operativa justifiquen el costo. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica ventajas, costos y señales reales para elegir con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 3. sistema distribuido

**Problema e intuición.** sistema distribuido aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Arquitectura de microservicios, sistema distribuido se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Mantener el monolito modular hasta que un límite de negocio y una necesidad operativa justifiquen el costo. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica sistema distribuido con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 4. límites de negocio

**Problema e intuición.** límites de negocio aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Arquitectura de microservicios, límites de negocio se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Mantener el monolito modular hasta que un límite de negocio y una necesidad operativa justifiquen el costo. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica límites de negocio con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 5. bounded context como introducción

**Problema e intuición.** bounded context como introducción aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Arquitectura de microservicios, bounded context como introducción se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Mantener el monolito modular hasta que un límite de negocio y una necesidad operativa justifiquen el costo. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica bounded context como introducción con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 6. cohesión y acoplamiento

**Problema e intuición.** cohesión y acoplamiento aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Arquitectura de microservicios, cohesión y acoplamiento se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Mantener el monolito modular hasta que un límite de negocio y una necesidad operativa justifiquen el costo. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica cohesión y acoplamiento con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 7. base de datos por servicio

**Problema e intuición.** base de datos por servicio aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Arquitectura de microservicios, base de datos por servicio se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Mantener el monolito modular hasta que un límite de negocio y una necesidad operativa justifiquen el costo. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica base de datos por servicio con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 8. propiedad de datos

**Problema e intuición.** propiedad de datos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Arquitectura de microservicios, propiedad de datos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Mantener el monolito modular hasta que un límite de negocio y una necesidad operativa justifiquen el costo. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica propiedad de datos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 9. comunicación síncrona y asíncrona

**Problema e intuición.** comunicación síncrona y asíncrona aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Arquitectura de microservicios, comunicación síncrona y asíncrona se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Mantener el monolito modular hasta que un límite de negocio y una necesidad operativa justifiquen el costo. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica comunicación síncrona y asíncrona con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 10. fallos parciales

**Problema e intuición.** fallos parciales aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Arquitectura de microservicios, fallos parciales se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Mantener el monolito modular hasta que un límite de negocio y una necesidad operativa justifiquen el costo. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica fallos parciales con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 11. latencia

**Problema e intuición.** latencia aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Arquitectura de microservicios, latencia se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Mantener el monolito modular hasta que un límite de negocio y una necesidad operativa justifiquen el costo. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica latencia con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 12. consistencia fuerte y eventual

**Problema e intuición.** consistencia fuerte y eventual aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Arquitectura de microservicios, consistencia fuerte y eventual se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Mantener el monolito modular hasta que un límite de negocio y una necesidad operativa justifiquen el costo. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica consistencia fuerte y eventual con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 13. transacciones distribuidas

**Problema e intuición.** transacciones distribuidas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** La transacción debe rodear el caso de uso que preserva una invariante. Una anotación en un método no corrige llamadas que evitan el proxy ni efectos remotos no transaccionales. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Mantener el monolito modular hasta que un límite de negocio y una necesidad operativa justifiquen el costo. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica transacciones distribuidas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 14. saga como introducción

**Problema e intuición.** saga como introducción aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Outbox guarda el cambio de negocio y un evento en la misma transacción local; un publicador posterior reduce la ventana de pérdida, pero exige idempotencia en consumidores. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Mantener el monolito modular hasta que un límite de negocio y una necesidad operativa justifiquen el costo. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica saga como introducción con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 15. transactional outbox como introducción

**Problema e intuición.** transactional outbox como introducción aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Outbox guarda el cambio de negocio y un evento en la misma transacción local; un publicador posterior reduce la ventana de pérdida, pero exige idempotencia en consumidores. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Mantener el monolito modular hasta que un límite de negocio y una necesidad operativa justifiquen el costo. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica transactional outbox como introducción con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 16. API Gateway

**Problema e intuición.** API Gateway aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Arquitectura de microservicios, API Gateway se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Mantener el monolito modular hasta que un límite de negocio y una necesidad operativa justifiquen el costo. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica API Gateway con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 17. service discovery

**Problema e intuición.** service discovery aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Arquitectura de microservicios, service discovery se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Mantener el monolito modular hasta que un límite de negocio y una necesidad operativa justifiquen el costo. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica service discovery con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 18. configuración distribuida

**Problema e intuición.** configuración distribuida aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Arquitectura de microservicios, configuración distribuida se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Mantener el monolito modular hasta que un límite de negocio y una necesidad operativa justifiquen el costo. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica configuración distribuida con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 19. resiliencia

**Problema e intuición.** resiliencia aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Arquitectura de microservicios, resiliencia se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Mantener el monolito modular hasta que un límite de negocio y una necesidad operativa justifiquen el costo. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica resiliencia con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 20. timeout, retry, circuit breaker y bulkhead

**Problema e intuición.** timeout, retry, circuit breaker y bulkhead aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Timeout limita espera, retry repite, circuit breaker deja de insistir temporalmente y bulkhead aísla recursos. Combinarlos sin presupuesto puede amplificar una caída. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Mantener el monolito modular hasta que un límite de negocio y una necesidad operativa justifiquen el costo. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica timeout, retry, circuit breaker y bulkhead con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 21. observabilidad

**Problema e intuición.** observabilidad aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Arquitectura de microservicios, observabilidad se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Mantener el monolito modular hasta que un límite de negocio y una necesidad operativa justifiquen el costo. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica observabilidad con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 22. logs, métricas y trazas

**Problema e intuición.** logs, métricas y trazas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Arquitectura de microservicios, logs, métricas y trazas se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Mantener el monolito modular hasta que un límite de negocio y una necesidad operativa justifiquen el costo. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica logs, métricas y trazas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 23. despliegue independiente

**Problema e intuición.** despliegue independiente aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Arquitectura de microservicios, despliegue independiente se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Mantener el monolito modular hasta que un límite de negocio y una necesidad operativa justifiquen el costo. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica despliegue independiente con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 24. versionado de contratos

**Problema e intuición.** versionado de contratos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Arquitectura de microservicios, versionado de contratos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Mantener el monolito modular hasta que un límite de negocio y una necesidad operativa justifiquen el costo. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica versionado de contratos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 25. pruebas de contratos

**Problema e intuición.** pruebas de contratos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Arquitectura de microservicios, pruebas de contratos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Mantener el monolito modular hasta que un límite de negocio y una necesidad operativa justifiquen el costo. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica pruebas de contratos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 26. seguridad entre servicios

**Problema e intuición.** seguridad entre servicios aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Arquitectura de microservicios, seguridad entre servicios se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Mantener el monolito modular hasta que un límite de negocio y una necesidad operativa justifiquen el costo. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica seguridad entre servicios con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 27. patrón Strangler para migraciones

**Problema e intuición.** patrón Strangler para migraciones aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Arquitectura de microservicios, patrón Strangler para migraciones se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Mantener el monolito modular hasta que un límite de negocio y una necesidad operativa justifiquen el costo. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica patrón Strangler para migraciones con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 28. anti-patrones de microservicios

**Problema e intuición.** anti-patrones de microservicios aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Arquitectura de microservicios, anti-patrones de microservicios se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Mantener el monolito modular hasta que un límite de negocio y una necesidad operativa justifiquen el costo. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica anti-patrones de microservicios con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 29. cuándo no utilizar microservicios

**Problema e intuición.** cuándo no utilizar microservicios aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Arquitectura de microservicios, cuándo no utilizar microservicios se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Mantener el monolito modular hasta que un límite de negocio y una necesidad operativa justifiquen el costo. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica cuándo no utilizar microservicios con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

## Decisiones y alternativas

1. Define primero la invariante o el riesgo.
2. Compara una opción simple con otra más operable.
3. Registra el costo introducido: latencia, acoplamiento, complejidad o mantenimiento.
4. Elige: Mantener el monolito modular hasta que un límite de negocio y una necesidad operativa justifiquen el costo.
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

La unidad enseña a convertir Arquitectura de microservicios en decisiones verificables. La idea profesional no es acumular herramientas, sino conectar propósito, mecanismo, fallo, evidencia y costo.

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
