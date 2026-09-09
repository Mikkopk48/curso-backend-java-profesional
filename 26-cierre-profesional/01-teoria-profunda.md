# Unidad 26 — Cierre profesional de FintechLab: teoría profunda

## Lugar dentro del recorrido

Esta unidad corresponde a la etapa **Entrega y defensa técnica**. Parte de la API inicial de la unidad 5 y añade una capacidad profesional sin volver a explicar Java, POO o CRUD desde cero.

## Conocimientos previos

- Controladores, servicios y repositorios básicos.
- Lectura de Java y ejecución de una aplicación Spring Boot.
- Conceptos recuperados en <code>00-objetivos-y-ruta.md</code>.

## Objetivos observables

Al terminar, podrás explicar el riesgo que resuelve cada concepto, seguir su funcionamiento, aplicarlo a FintechLab, escribir evidencia y defender cuándo no conviene usarlo.

## Problema central

Cerrar un proyecto significa hacerlo reproducible, explicable y operable. En un ejercicio pequeño puede parecer suficiente memorizar una API. En producción importan además concurrencia, datos inválidos, latencia, compatibilidad, trazabilidad y recuperación.

## Modelo mental y analogía

Antes de entregar un avión se revisan sistemas, documentación y procedimientos de contingencia.

**Límite:** Una checklist no sustituye criterio ni evidencia; cada marca debe poder demostrarse.

## Funcionamiento técnico progresivo

La revisión recorre contratos, dependencias, datos, fallos y operación como un sistema; la evidencia conecta cada afirmación con código o prueba. El recorrido útil siempre contiene cinco preguntas: quién inicia, qué cruza la frontera, qué estado participa, dónde puede fallar y qué evidencia queda.

~~~text
Una afirmación de calidad debe enlazar evidencia:
"La transferencia es idempotente" -> test concurrente + restricción UNIQUE + contrato.
~~~

El ejemplo es deliberadamente pequeño. No es la solución de los ejercicios: muestra vocabulario y forma, mientras las consignas cambian datos, restricciones y estructura.

## Explicación conectada: preparar una entrega que otra persona pueda operar

Recorre una petición de extremo a extremo: conexión y filtros, autenticación, DTO, validación, controller, caso de uso, transacción, SQL, commit, mapping, respuesta y observabilidad. Para una llamada remota añade serialización, discovery, load balancing, timeout, respuesta y circuito. Para login añade issuer, token, claims y autorización.

Crea una matriz de fallos: entrada inválida, permiso, cuenta inexistente, saldo, deadlock, base caída, timeout remoto, Config Server, registry, pipeline y navegador. Para cada fila documenta detección, impacto, respuesta, log/métrica, retry permitido y recuperación. Sin esta matriz, la demo solo prueba el camino feliz.

La documentación final contiene README reproducible, arquitectura, ADR, OpenAPI, migraciones, tests, pipeline, seguridad, ejemplos ficticios y limitaciones. Un comando desde clon limpio vale más que “en mi equipo funciona”.

Code review prioriza corrección y seguridad, después mantenibilidad y estilo. Busca invariantes, ownership de datos, transacciones, errores, secretos, queries, tests y compatibilidad. Un comentario explica riesgo y dirección.

La demo se ensaya: arranque limpio, caso feliz, repetición, error, contrato, test y decisión. Prepara un plan si falla Internet o Docker. La defensa responde por qué monolito modular, dónde está atomicidad, qué no cubren tests y cuál deuda priorizarías.

Deuda técnica se registra con impacto, evidencia, opción y costo de no actuar. No prometas resolver todo. Un cierre profesional hace visible lo conocido y lo desconocido.

## Atlas completo del temario oficial

Las subsecciones siguientes cubren cada punto del programa. La etiqueta **Diplomatura** identifica el núcleo oficial; las decisiones de producción y el vínculo con FintechLab constituyen profundización y ampliación profesional.

### 1. mapa completo del sistema

**Problema e intuición.** mapa completo del sistema aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Cierre profesional de FintechLab, mapa completo del sistema se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La revisión recorre contratos, dependencias, datos, fallos y operación como un sistema; la evidencia conecta cada afirmación con código o prueba. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** consolida arquitectura, OpenAPI, migraciones, tests, pipeline, seguridad y deuda conocida. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Marcar controles sin reproducirlos crea confianza falsa. La regla es: Documentar límites conocidos y priorizar riesgos sobre pulido cosmético. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica mapa completo del sistema con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 2. recorrido de una petición desde el cliente hasta la base de datos

**Problema e intuición.** recorrido de una petición desde el cliente hasta la base de datos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Cierre profesional de FintechLab, recorrido de una petición desde el cliente hasta la base de datos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La revisión recorre contratos, dependencias, datos, fallos y operación como un sistema; la evidencia conecta cada afirmación con código o prueba. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** consolida arquitectura, OpenAPI, migraciones, tests, pipeline, seguridad y deuda conocida. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Marcar controles sin reproducirlos crea confianza falsa. La regla es: Documentar límites conocidos y priorizar riesgos sobre pulido cosmético. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica recorrido de una petición desde el cliente hasta la base de datos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 3. recorrido de una llamada entre microservicios

**Problema e intuición.** recorrido de una llamada entre microservicios aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Cierre profesional de FintechLab, recorrido de una llamada entre microservicios se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La revisión recorre contratos, dependencias, datos, fallos y operación como un sistema; la evidencia conecta cada afirmación con código o prueba. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** consolida arquitectura, OpenAPI, migraciones, tests, pipeline, seguridad y deuda conocida. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Marcar controles sin reproducirlos crea confianza falsa. La regla es: Documentar límites conocidos y priorizar riesgos sobre pulido cosmético. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica recorrido de una llamada entre microservicios con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 4. recorrido de autenticación y autorización

**Problema e intuición.** recorrido de autenticación y autorización aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Cierre profesional de FintechLab, recorrido de autenticación y autorización se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La revisión recorre contratos, dependencias, datos, fallos y operación como un sistema; la evidencia conecta cada afirmación con código o prueba. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** consolida arquitectura, OpenAPI, migraciones, tests, pipeline, seguridad y deuda conocida. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Marcar controles sin reproducirlos crea confianza falsa. La regla es: Documentar límites conocidos y priorizar riesgos sobre pulido cosmético. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica recorrido de autenticación y autorización con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 5. matriz de fallos posibles

**Problema e intuición.** matriz de fallos posibles aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Cierre profesional de FintechLab, matriz de fallos posibles se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La revisión recorre contratos, dependencias, datos, fallos y operación como un sistema; la evidencia conecta cada afirmación con código o prueba. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** consolida arquitectura, OpenAPI, migraciones, tests, pipeline, seguridad y deuda conocida. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Marcar controles sin reproducirlos crea confianza falsa. La regla es: Documentar límites conocidos y priorizar riesgos sobre pulido cosmético. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica matriz de fallos posibles con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 6. checklist de calidad

**Problema e intuición.** checklist de calidad aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Cierre profesional de FintechLab, checklist de calidad se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La revisión recorre contratos, dependencias, datos, fallos y operación como un sistema; la evidencia conecta cada afirmación con código o prueba. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** consolida arquitectura, OpenAPI, migraciones, tests, pipeline, seguridad y deuda conocida. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Marcar controles sin reproducirlos crea confianza falsa. La regla es: Documentar límites conocidos y priorizar riesgos sobre pulido cosmético. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica checklist de calidad con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 7. checklist de seguridad

**Problema e intuición.** checklist de seguridad aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Cierre profesional de FintechLab, checklist de seguridad se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La revisión recorre contratos, dependencias, datos, fallos y operación como un sistema; la evidencia conecta cada afirmación con código o prueba. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** consolida arquitectura, OpenAPI, migraciones, tests, pipeline, seguridad y deuda conocida. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Marcar controles sin reproducirlos crea confianza falsa. La regla es: Documentar límites conocidos y priorizar riesgos sobre pulido cosmético. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica checklist de seguridad con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 8. checklist de base de datos

**Problema e intuición.** checklist de base de datos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Cierre profesional de FintechLab, checklist de base de datos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La revisión recorre contratos, dependencias, datos, fallos y operación como un sistema; la evidencia conecta cada afirmación con código o prueba. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** consolida arquitectura, OpenAPI, migraciones, tests, pipeline, seguridad y deuda conocida. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Marcar controles sin reproducirlos crea confianza falsa. La regla es: Documentar límites conocidos y priorizar riesgos sobre pulido cosmético. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica checklist de base de datos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 9. checklist de pruebas

**Problema e intuición.** checklist de pruebas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Cierre profesional de FintechLab, checklist de pruebas se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La revisión recorre contratos, dependencias, datos, fallos y operación como un sistema; la evidencia conecta cada afirmación con código o prueba. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** consolida arquitectura, OpenAPI, migraciones, tests, pipeline, seguridad y deuda conocida. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Marcar controles sin reproducirlos crea confianza falsa. La regla es: Documentar límites conocidos y priorizar riesgos sobre pulido cosmético. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica checklist de pruebas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 10. checklist de CI/CD

**Problema e intuición.** checklist de CI/CD aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Cierre profesional de FintechLab, checklist de CI/CD se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La revisión recorre contratos, dependencias, datos, fallos y operación como un sistema; la evidencia conecta cada afirmación con código o prueba. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** consolida arquitectura, OpenAPI, migraciones, tests, pipeline, seguridad y deuda conocida. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Marcar controles sin reproducirlos crea confianza falsa. La regla es: Documentar límites conocidos y priorizar riesgos sobre pulido cosmético. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica checklist de CI/CD con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 11. guía de code review

**Problema e intuición.** guía de code review aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Cierre profesional de FintechLab, guía de code review se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La revisión recorre contratos, dependencias, datos, fallos y operación como un sistema; la evidencia conecta cada afirmación con código o prueba. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** consolida arquitectura, OpenAPI, migraciones, tests, pipeline, seguridad y deuda conocida. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Marcar controles sin reproducirlos crea confianza falsa. La regla es: Documentar límites conocidos y priorizar riesgos sobre pulido cosmético. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica guía de code review con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 12. documentación del proyecto

**Problema e intuición.** documentación del proyecto aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Cierre profesional de FintechLab, documentación del proyecto se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La revisión recorre contratos, dependencias, datos, fallos y operación como un sistema; la evidencia conecta cada afirmación con código o prueba. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** consolida arquitectura, OpenAPI, migraciones, tests, pipeline, seguridad y deuda conocida. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Marcar controles sin reproducirlos crea confianza falsa. La regla es: Documentar límites conocidos y priorizar riesgos sobre pulido cosmético. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica documentación del proyecto con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 13. ADR finales

**Problema e intuición.** ADR finales aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Cierre profesional de FintechLab, ADR finales se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La revisión recorre contratos, dependencias, datos, fallos y operación como un sistema; la evidencia conecta cada afirmación con código o prueba. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** consolida arquitectura, OpenAPI, migraciones, tests, pipeline, seguridad y deuda conocida. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Marcar controles sin reproducirlos crea confianza falsa. La regla es: Documentar límites conocidos y priorizar riesgos sobre pulido cosmético. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica ADR finales con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 14. preparación de una demostración

**Problema e intuición.** preparación de una demostración aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Cierre profesional de FintechLab, preparación de una demostración se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La revisión recorre contratos, dependencias, datos, fallos y operación como un sistema; la evidencia conecta cada afirmación con código o prueba. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** consolida arquitectura, OpenAPI, migraciones, tests, pipeline, seguridad y deuda conocida. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Marcar controles sin reproducirlos crea confianza falsa. La regla es: Documentar límites conocidos y priorizar riesgos sobre pulido cosmético. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica preparación de una demostración con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 15. preparación de defensa oral

**Problema e intuición.** preparación de defensa oral aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Cierre profesional de FintechLab, preparación de defensa oral se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La revisión recorre contratos, dependencias, datos, fallos y operación como un sistema; la evidencia conecta cada afirmación con código o prueba. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** consolida arquitectura, OpenAPI, migraciones, tests, pipeline, seguridad y deuda conocida. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Marcar controles sin reproducirlos crea confianza falsa. La regla es: Documentar límites conocidos y priorizar riesgos sobre pulido cosmético. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica preparación de defensa oral con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 16. revisión de deuda técnica

**Problema e intuición.** revisión de deuda técnica aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Cierre profesional de FintechLab, revisión de deuda técnica se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La revisión recorre contratos, dependencias, datos, fallos y operación como un sistema; la evidencia conecta cada afirmación con código o prueba. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** consolida arquitectura, OpenAPI, migraciones, tests, pipeline, seguridad y deuda conocida. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Marcar controles sin reproducirlos crea confianza falsa. La regla es: Documentar límites conocidos y priorizar riesgos sobre pulido cosmético. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica revisión de deuda técnica con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 17. plan de mejora posterior

**Problema e intuición.** plan de mejora posterior aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Cierre profesional de FintechLab, plan de mejora posterior se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La revisión recorre contratos, dependencias, datos, fallos y operación como un sistema; la evidencia conecta cada afirmación con código o prueba. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** consolida arquitectura, OpenAPI, migraciones, tests, pipeline, seguridad y deuda conocida. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Marcar controles sin reproducirlos crea confianza falsa. La regla es: Documentar límites conocidos y priorizar riesgos sobre pulido cosmético. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica plan de mejora posterior con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 18. instrucciones reproducibles

**Problema e intuición.** instrucciones reproducibles aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Cierre profesional de FintechLab, instrucciones reproducibles se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La revisión recorre contratos, dependencias, datos, fallos y operación como un sistema; la evidencia conecta cada afirmación con código o prueba. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** consolida arquitectura, OpenAPI, migraciones, tests, pipeline, seguridad y deuda conocida. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Marcar controles sin reproducirlos crea confianza falsa. La regla es: Documentar límites conocidos y priorizar riesgos sobre pulido cosmético. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica instrucciones reproducibles con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 19. arquitectura

**Problema e intuición.** arquitectura aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Cierre profesional de FintechLab, arquitectura se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La revisión recorre contratos, dependencias, datos, fallos y operación como un sistema; la evidencia conecta cada afirmación con código o prueba. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** consolida arquitectura, OpenAPI, migraciones, tests, pipeline, seguridad y deuda conocida. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Marcar controles sin reproducirlos crea confianza falsa. La regla es: Documentar límites conocidos y priorizar riesgos sobre pulido cosmético. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica arquitectura con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 20. decisiones

**Problema e intuición.** decisiones aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Cierre profesional de FintechLab, decisiones se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La revisión recorre contratos, dependencias, datos, fallos y operación como un sistema; la evidencia conecta cada afirmación con código o prueba. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** consolida arquitectura, OpenAPI, migraciones, tests, pipeline, seguridad y deuda conocida. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Marcar controles sin reproducirlos crea confianza falsa. La regla es: Documentar límites conocidos y priorizar riesgos sobre pulido cosmético. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica decisiones con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 21. contrato OpenAPI

**Problema e intuición.** contrato OpenAPI aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** OpenAPI es la especificación del contrato; Swagger agrupa herramientas históricas y Swagger UI renderiza y permite invocar operaciones descritas. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La revisión recorre contratos, dependencias, datos, fallos y operación como un sistema; la evidencia conecta cada afirmación con código o prueba. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** consolida arquitectura, OpenAPI, migraciones, tests, pipeline, seguridad y deuda conocida. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Marcar controles sin reproducirlos crea confianza falsa. La regla es: Documentar límites conocidos y priorizar riesgos sobre pulido cosmético. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica contrato OpenAPI con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 22. migraciones

**Problema e intuición.** migraciones aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Cierre profesional de FintechLab, migraciones se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La revisión recorre contratos, dependencias, datos, fallos y operación como un sistema; la evidencia conecta cada afirmación con código o prueba. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** consolida arquitectura, OpenAPI, migraciones, tests, pipeline, seguridad y deuda conocida. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Marcar controles sin reproducirlos crea confianza falsa. La regla es: Documentar límites conocidos y priorizar riesgos sobre pulido cosmético. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica migraciones con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 23. tests

**Problema e intuición.** tests aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Cierre profesional de FintechLab, tests se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La revisión recorre contratos, dependencias, datos, fallos y operación como un sistema; la evidencia conecta cada afirmación con código o prueba. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** consolida arquitectura, OpenAPI, migraciones, tests, pipeline, seguridad y deuda conocida. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Marcar controles sin reproducirlos crea confianza falsa. La regla es: Documentar límites conocidos y priorizar riesgos sobre pulido cosmético. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica tests con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 24. pipeline

**Problema e intuición.** pipeline aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Cierre profesional de FintechLab, pipeline se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La revisión recorre contratos, dependencias, datos, fallos y operación como un sistema; la evidencia conecta cada afirmación con código o prueba. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** consolida arquitectura, OpenAPI, migraciones, tests, pipeline, seguridad y deuda conocida. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Marcar controles sin reproducirlos crea confianza falsa. La regla es: Documentar límites conocidos y priorizar riesgos sobre pulido cosmético. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica pipeline con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 25. seguridad

**Problema e intuición.** seguridad aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Cierre profesional de FintechLab, seguridad se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La revisión recorre contratos, dependencias, datos, fallos y operación como un sistema; la evidencia conecta cada afirmación con código o prueba. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** consolida arquitectura, OpenAPI, migraciones, tests, pipeline, seguridad y deuda conocida. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Marcar controles sin reproducirlos crea confianza falsa. La regla es: Documentar límites conocidos y priorizar riesgos sobre pulido cosmético. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica seguridad con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 26. logs útiles sin datos sensibles

**Problema e intuición.** logs útiles sin datos sensibles aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Cierre profesional de FintechLab, logs útiles sin datos sensibles se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La revisión recorre contratos, dependencias, datos, fallos y operación como un sistema; la evidencia conecta cada afirmación con código o prueba. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** consolida arquitectura, OpenAPI, migraciones, tests, pipeline, seguridad y deuda conocida. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Marcar controles sin reproducirlos crea confianza falsa. La regla es: Documentar límites conocidos y priorizar riesgos sobre pulido cosmético. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica logs útiles sin datos sensibles con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 27. ejemplos de peticiones

**Problema e intuición.** ejemplos de peticiones aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Cierre profesional de FintechLab, ejemplos de peticiones se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La revisión recorre contratos, dependencias, datos, fallos y operación como un sistema; la evidencia conecta cada afirmación con código o prueba. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** consolida arquitectura, OpenAPI, migraciones, tests, pipeline, seguridad y deuda conocida. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Marcar controles sin reproducirlos crea confianza falsa. La regla es: Documentar límites conocidos y priorizar riesgos sobre pulido cosmético. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica ejemplos de peticiones con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 28. limitaciones conocidas

**Problema e intuición.** limitaciones conocidas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Cierre profesional de FintechLab, limitaciones conocidas se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La revisión recorre contratos, dependencias, datos, fallos y operación como un sistema; la evidencia conecta cada afirmación con código o prueba. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** consolida arquitectura, OpenAPI, migraciones, tests, pipeline, seguridad y deuda conocida. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Marcar controles sin reproducirlos crea confianza falsa. La regla es: Documentar límites conocidos y priorizar riesgos sobre pulido cosmético. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica limitaciones conocidas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

## Decisiones y alternativas

1. Define primero la invariante o el riesgo.
2. Compara una opción simple con otra más operable.
3. Registra el costo introducido: latencia, acoplamiento, complejidad o mantenimiento.
4. Elige: Documentar límites conocidos y priorizar riesgos sobre pulido cosmético.
5. Documenta qué dato futuro haría cambiar la decisión.

## Errores frecuentes

- Copiar una anotación sin conocer quién la interpreta ni cuándo.
- Confundir el caso feliz con el contrato completo.
- Añadir infraestructura sin una necesidad observable.
- Escribir un test que replica la implementación en vez de cubrir riesgo.
- Registrar datos sensibles para facilitar debugging.
- Afirmar una garantía sin una restricción, prueba o medición que la sostenga.

## Qué ocurre en producción

Marcar controles sin reproducirlos crea confianza falsa. Un profesional diseña para degradación, documenta límites, mide antes de optimizar y deja una ruta de recuperación. FintechLab usa datos ficticios y no debe conectarse con bancos ni procesar dinero real.

## Cómo reconocer que realmente lo entendiste

- Puedes explicarlo sin depender del nombre de una anotación.
- Puedes dibujar el recorrido y marcar al menos tres fallos.
- Puedes cambiar el ejemplo sin copiarlo.
- Puedes escribir una prueba que falla cuando se rompe la regla.
- Puedes nombrar una alternativa y justificar el trade-off.

## Resumen sencillo

La unidad enseña a convertir Cierre profesional de FintechLab en decisiones verificables. La idea profesional no es acumular herramientas, sino conectar propósito, mecanismo, fallo, evidencia y costo.

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

## Checklists de cierre verificable

### Calidad

- [ ] Contrato coherente y versionado; evidencia: __________
- [ ] Nombres y módulos reflejan dominio; evidencia: __________
- [ ] Errores son observables y seguros; evidencia: __________
- [ ] Deuda técnica priorizada con motivo; evidencia: __________

### Seguridad

- [ ] Permisos de mínimo privilegio; evidencia: __________
- [ ] Issuer, audience y tiempo validados; evidencia: __________
- [ ] Secretos fuera de Git y logs; evidencia: __________
- [ ] Dependencias y superficie revisadas; evidencia: __________

### Base de datos

- [ ] Migraciones reproducibles; evidencia: __________
- [ ] Restricciones protegen invariantes; evidencia: __________
- [ ] Transacciones cubren el caso de uso; evidencia: __________
- [ ] Índices sustentados por consultas y EXPLAIN; evidencia: __________

### Pruebas

- [ ] Unidades cubren reglas; evidencia: __________
- [ ] Integración usa infraestructura representativa; evidencia: __________
- [ ] Contratos remotos simulan fallos; evidencia: __________
- [ ] E2E se reserva para flujos críticos; evidencia: __________

### CI/CD

- [ ] Build limpio desde cero; evidencia: __________
- [ ] Permisos mínimos; evidencia: __________
- [ ] Artefacto identificable; evidencia: __________
- [ ] Rollback y promoción documentados; evidencia: __________

Una casilla sin evidencia permanece sin marcar. La evidencia puede ser un archivo, test, comando reproducible, consulta, captura o ADR.
