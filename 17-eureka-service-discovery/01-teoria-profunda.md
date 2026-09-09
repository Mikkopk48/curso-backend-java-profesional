# Unidad 17 — Eureka y service discovery: teoría profunda

## Lugar dentro del recorrido

Esta unidad corresponde a la etapa **Instancias dinámicas**. Parte de la API inicial de la unidad 5 y añade una capacidad profesional sin volver a explicar Java, POO o CRUD desde cero.

## Conocimientos previos

- Controladores, servicios y repositorios básicos.
- Lectura de Java y ejecución de una aplicación Spring Boot.
- Conceptos recuperados en <code>00-objetivos-y-ruta.md</code>.

## Objetivos observables

Al terminar, podrás explicar el riesgo que resuelve cada concepto, seguir su funcionamiento, aplicarlo a FintechLab, escribir evidencia y defender cuándo no conviene usarlo.

## Problema central

El descubrimiento desacopla el nombre lógico de direcciones que cambian. En un ejercicio pequeño puede parecer suficiente memorizar una API. En producción importan además concurrencia, datos inválidos, latencia, compatibilidad, trazabilidad y recuperación.

## Modelo mental y analogía

Un directorio actualizado indica qué sucursales están disponibles.

**Límite:** El registro puede estar temporalmente desactualizado y no reemplaza comprobaciones de salud completas.

## Funcionamiento técnico progresivo

La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. El recorrido útil siempre contiene cinco preguntas: quién inicia, qué cruza la frontera, qué estado participa, dónde puede fallar y qué evidencia queda.

~~~yaml
spring:
  application:
    name: notification-service
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
~~~

El ejemplo es deliberadamente pequeño. No es la solución de los ejercicios: muestra vocabulario y forma, mientras las consignas cambian datos, restricciones y estructura.

## Explicación conectada: nombres estables para instancias cambiantes

En desarrollo, llamar localhost:8091 parece suficiente. En despliegues reales las instancias nacen, cambian de IP y mueren. Un registry mantiene la relación entre nombre lógico, instancia, dirección y metadata. Eureka Server ofrece el registro; Eureka Client registra su aplicación y consulta la lista.

Cada instancia envía heartbeats para renovar un lease. Si deja de renovar, no desaparece de inmediato: hay ventanas, cachés y umbrales. Por eso service discovery no demuestra salud profunda. Un health check puede indicar que el proceso responde mientras la base está agotada.

El cliente obtiene varias instancias y un load balancer elige. Ejecutar notification-service en dos puertos permite observar alternancia; debes incluir instance ID en una respuesta o log seguro para producir evidencia. Cuando una instancia cae, la caché puede conservarla brevemente y una llamada fallar antes de actualizarse.

Self-preservation evita expulsar gran parte del registro si Eureka sospecha una pérdida de red general. Mejora disponibilidad del directorio, pero puede conservar entradas viejas. No lo desactives en producción solo para que una demo “se limpie rápido”.

Eureka difiere de DNS: el cliente integra registro, leases y metadata. Kubernetes ya ofrece Services y DNS; añadir Eureka allí suele duplicar capacidades. Tiene sentido en una pila Spring fuera de una plataforma que ya resuelve descubrimiento. Alta disponibilidad requiere varios servidores y evitar que el registry sea un punto único, pero el laboratorio local usa uno y lo declara.

## Atlas completo del temario oficial

Las subsecciones siguientes cubren cada punto del programa. La etiqueta **Diplomatura** identifica el núcleo oficial; las decisiones de producción y el vínculo con FintechLab constituyen profundización y ampliación profesional.

### 1. direcciones dinámicas

**Problema e intuición.** direcciones dinámicas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Eureka y service discovery, direcciones dinámicas se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Usarlo donde las instancias son dinámicas y no existe ya una plataforma que resuelva descubrimiento. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica direcciones dinámicas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 2. service registry

**Problema e intuición.** service registry aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Eureka y service discovery, service registry se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Usarlo donde las instancias son dinámicas y no existe ya una plataforma que resuelva descubrimiento. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica service registry con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 3. registro y descubrimiento

**Problema e intuición.** registro y descubrimiento aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Eureka y service discovery, registro y descubrimiento se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Usarlo donde las instancias son dinámicas y no existe ya una plataforma que resuelva descubrimiento. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica registro y descubrimiento con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 4. Eureka Server

**Problema e intuición.** Eureka Server aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Eureka mantiene registros mediante renovaciones y cachés. La ausencia de heartbeat tarda en reflejarse; self-preservation evita expulsiones masivas ante pérdida de comunicación. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Usarlo donde las instancias son dinámicas y no existe ya una plataforma que resuelva descubrimiento. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica Eureka Server con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 5. Eureka Client

**Problema e intuición.** Eureka Client aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Eureka mantiene registros mediante renovaciones y cachés. La ausencia de heartbeat tarda en reflejarse; self-preservation evita expulsiones masivas ante pérdida de comunicación. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Usarlo donde las instancias son dinámicas y no existe ya una plataforma que resuelva descubrimiento. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica Eureka Client con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 6. identidad de aplicación e instancia

**Problema e intuición.** identidad de aplicación e instancia aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Eureka y service discovery, identidad de aplicación e instancia se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Usarlo donde las instancias son dinámicas y no existe ya una plataforma que resuelva descubrimiento. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica identidad de aplicación e instancia con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 7. heartbeats

**Problema e intuición.** heartbeats aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Eureka mantiene registros mediante renovaciones y cachés. La ausencia de heartbeat tarda en reflejarse; self-preservation evita expulsiones masivas ante pérdida de comunicación. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Usarlo donde las instancias son dinámicas y no existe ya una plataforma que resuelva descubrimiento. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica heartbeats con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 8. leases

**Problema e intuición.** leases aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Eureka mantiene registros mediante renovaciones y cachés. La ausencia de heartbeat tarda en reflejarse; self-preservation evita expulsiones masivas ante pérdida de comunicación. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Usarlo donde las instancias son dinámicas y no existe ya una plataforma que resuelva descubrimiento. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica leases con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 9. registro, renovación y eliminación

**Problema e intuición.** registro, renovación y eliminación aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Eureka y service discovery, registro, renovación y eliminación se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Usarlo donde las instancias son dinámicas y no existe ya una plataforma que resuelva descubrimiento. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica registro, renovación y eliminación con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 10. cachés

**Problema e intuición.** cachés aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Eureka y service discovery, cachés se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Usarlo donde las instancias son dinámicas y no existe ya una plataforma que resuelva descubrimiento. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica cachés con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 11. múltiples instancias

**Problema e intuición.** múltiples instancias aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Eureka y service discovery, múltiples instancias se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Usarlo donde las instancias son dinámicas y no existe ya una plataforma que resuelva descubrimiento. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica múltiples instancias con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 12. balanceo del lado del cliente

**Problema e intuición.** balanceo del lado del cliente aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Eureka y service discovery, balanceo del lado del cliente se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Usarlo donde las instancias son dinámicas y no existe ya una plataforma que resuelva descubrimiento. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica balanceo del lado del cliente con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 13. integración con Feign

**Problema e intuición.** integración con Feign aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Eureka y service discovery, integración con Feign se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Usarlo donde las instancias son dinámicas y no existe ya una plataforma que resuelva descubrimiento. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica integración con Feign con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 14. salud de instancias

**Problema e intuición.** salud de instancias aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Eureka y service discovery, salud de instancias se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Usarlo donde las instancias son dinámicas y no existe ya una plataforma que resuelva descubrimiento. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica salud de instancias con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 15. arranque y caída de servicios

**Problema e intuición.** arranque y caída de servicios aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Eureka y service discovery, arranque y caída de servicios se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Usarlo donde las instancias son dinámicas y no existe ya una plataforma que resuelva descubrimiento. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica arranque y caída de servicios con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 16. self-preservation explicado con cuidado

**Problema e intuición.** self-preservation explicado con cuidado aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Eureka mantiene registros mediante renovaciones y cachés. La ausencia de heartbeat tarda en reflejarse; self-preservation evita expulsiones masivas ante pérdida de comunicación. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Usarlo donde las instancias son dinámicas y no existe ya una plataforma que resuelva descubrimiento. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica self-preservation explicado con cuidado con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 17. límites y riesgos

**Problema e intuición.** límites y riesgos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Eureka y service discovery, límites y riesgos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Usarlo donde las instancias son dinámicas y no existe ya una plataforma que resuelva descubrimiento. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica límites y riesgos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 18. alta disponibilidad a nivel conceptual

**Problema e intuición.** alta disponibilidad a nivel conceptual aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Eureka y service discovery, alta disponibilidad a nivel conceptual se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Usarlo donde las instancias son dinámicas y no existe ya una plataforma que resuelva descubrimiento. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica alta disponibilidad a nivel conceptual con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 19. diferencia con DNS y con descubrimiento de Kubernetes

**Problema e intuición.** diferencia con DNS y con descubrimiento de Kubernetes aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Eureka y service discovery, diferencia con DNS y con descubrimiento de Kubernetes se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Usarlo donde las instancias son dinámicas y no existe ya una plataforma que resuelva descubrimiento. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica diferencia con DNS y con descubrimiento de Kubernetes con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 20. escenarios donde Eureka tiene sentido y donde no

**Problema e intuición.** escenarios donde Eureka tiene sentido y donde no aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Eureka mantiene registros mediante renovaciones y cachés. La ausencia de heartbeat tarda en reflejarse; self-preservation evita expulsiones masivas ante pérdida de comunicación. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La invocación se serializa, cruza red, se enruta a una instancia y vuelve como respuesta o fallo; cada componente observa solo una parte del estado global. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa notificaciones únicamente como laboratorio y mantiene transferencia y saldo dentro del mismo límite consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Timeouts, reintentos y datos separados crean resultados ambiguos y fallos parciales. La regla es: Usarlo donde las instancias son dinámicas y no existe ya una plataforma que resuelva descubrimiento. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica escenarios donde Eureka tiene sentido y donde no con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

## Decisiones y alternativas

1. Define primero la invariante o el riesgo.
2. Compara una opción simple con otra más operable.
3. Registra el costo introducido: latencia, acoplamiento, complejidad o mantenimiento.
4. Elige: Usarlo donde las instancias son dinámicas y no existe ya una plataforma que resuelva descubrimiento.
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

La unidad enseña a convertir Eureka y service discovery en decisiones verificables. La idea profesional no es acumular herramientas, sino conectar propósito, mecanismo, fallo, evidencia y costo.

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
