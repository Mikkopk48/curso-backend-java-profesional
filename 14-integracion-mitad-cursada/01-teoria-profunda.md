# Unidad 14 — Integración de mitad de cursada: teoría profunda

## Lugar dentro del recorrido

Esta unidad corresponde a la etapa **Entrega parcial defendible**. Parte de la API inicial de la unidad 5 y añade una capacidad profesional sin volver a explicar Java, POO o CRUD desde cero.

## Conocimientos previos

- Controladores, servicios y repositorios básicos.
- Lectura de Java y ejecución de una aplicación Spring Boot.
- Conceptos recuperados en <code>00-objetivos-y-ruta.md</code>.

## Objetivos observables

Al terminar, podrás explicar el riesgo que resuelve cada concepto, seguir su funcionamiento, aplicarlo a FintechLab, escribir evidencia y defender cuándo no conviene usarlo.

## Problema central

Integrar revela contradicciones que los ejercicios aislados no muestran. En un ejercicio pequeño puede parecer suficiente memorizar una API. En producción importan además concurrencia, datos inválidos, latencia, compatibilidad, trazabilidad y recuperación.

## Modelo mental y analogía

Un ensayo general verifica tanto cada parte como sus transiciones.

**Límite:** Una demostración feliz puede ocultar deuda, ausencia de pruebas y datos irreproducibles.

## Funcionamiento técnico progresivo

La entrega ensambla HTTP, validación, dominio, persistencia, migraciones y pruebas; cada frontera puede invalidar supuestos de la anterior. El recorrido útil siempre contiene cinco preguntas: quién inicia, qué cruza la frontera, qué estado participa, dónde puede fallar y qué evidencia queda.

~~~text
Evidencia de entrega
1. commit identificable; 2. comando reproducible; 3. migración limpia;
4. contrato OpenAPI; 5. tests de riesgo; 6. ADR de decisiones.
~~~

El ejemplo es deliberadamente pequeño. No es la solución de los ejercicios: muestra vocabulario y forma, mientras las consignas cambian datos, restricciones y estructura.

## Explicación conectada: convertir piezas en una entrega defendible

La integración prueba contradicciones. Un DTO válido puede violar una restricción; una transacción correcta puede devolver un status incorrecto; OpenAPI puede omitir un error; un test unitario puede mockear precisamente la pieza que falla. Por eso la entrega comienza desde una base limpia: levantar MySQL, aplicar migraciones, ejecutar verify y recorrer API mediante su contrato.

Construye una matriz de requisitos y evidencia. “Hay validación” se enlaza a DTO, caso rechazado y Problem Details. “Es atómica” se enlaza a límite transaccional, restricción y test de rollback. “Es idempotente” necesita clave, huella, UNIQUE y repetición observable. “Está documentada” requiere que OpenAPI describa también error y seguridad.

El historial Git cuenta el razonamiento: commits atómicos, mensajes que explican intención y ausencia de secretos. Una rama con un único commit gigante impide revisar y recuperar. La defensa oral no premia memorizar: explica invariante, alternativa descartada, evidencia, limitación y mejora priorizada.

Requisitos avanzados no compensan un fallo crítico. Antes de añadir caché o microservicios, confirma saldo, moneda, duplicados, migración y tests. La rúbrica de este módulo permite autoevaluarse sin revelar una implementación.

## Atlas completo del temario oficial

Las subsecciones siguientes cubren cada punto del programa. La etiqueta **Diplomatura** identifica el núcleo oficial; las decisiones de producción y el vínculo con FintechLab constituyen profundización y ampliación profesional.

### 1. API HTTP coherente

**Problema e intuición.** API HTTP coherente aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Integración de mitad de cursada, API HTTP coherente se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La entrega ensambla HTTP, validación, dominio, persistencia, migraciones y pruebas; cada frontera puede invalidar supuestos de la anterior. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** produce una versión parcial reproducible y defendible del monolito modular. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Optimizar solo el caso feliz oculta problemas de repetición, concurrencia y recuperación. La regla es: Entregar evidencia reproducible: código, contrato, migraciones, tests, historial y decisiones. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica API HTTP coherente con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 2. GET, DELETE, POST y PUT

**Problema e intuición.** GET, DELETE, POST y PUT aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** PUT expresa reemplazo de la representación en la URI conocida y debe ser idempotente; PATCH describe una modificación parcial cuyo formato necesita contrato propio. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La entrega ensambla HTTP, validación, dominio, persistencia, migraciones y pruebas; cada frontera puede invalidar supuestos de la anterior. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** produce una versión parcial reproducible y defendible del monolito modular. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Optimizar solo el caso feliz oculta problemas de repetición, concurrencia y recuperación. La regla es: Entregar evidencia reproducible: código, contrato, migraciones, tests, historial y decisiones. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica GET, DELETE, POST y PUT con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 3. validaciones

**Problema e intuición.** validaciones aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Integración de mitad de cursada, validaciones se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La entrega ensambla HTTP, validación, dominio, persistencia, migraciones y pruebas; cada frontera puede invalidar supuestos de la anterior. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** produce una versión parcial reproducible y defendible del monolito modular. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Optimizar solo el caso feliz oculta problemas de repetición, concurrencia y recuperación. La regla es: Entregar evidencia reproducible: código, contrato, migraciones, tests, historial y decisiones. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica validaciones con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 4. manejo global de errores

**Problema e intuición.** manejo global de errores aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Integración de mitad de cursada, manejo global de errores se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La entrega ensambla HTTP, validación, dominio, persistencia, migraciones y pruebas; cada frontera puede invalidar supuestos de la anterior. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** produce una versión parcial reproducible y defendible del monolito modular. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Optimizar solo el caso feliz oculta problemas de repetición, concurrencia y recuperación. La regla es: Entregar evidencia reproducible: código, contrato, migraciones, tests, historial y decisiones. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica manejo global de errores con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 5. OpenAPI

**Problema e intuición.** OpenAPI aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** OpenAPI es la especificación del contrato; Swagger agrupa herramientas históricas y Swagger UI renderiza y permite invocar operaciones descritas. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La entrega ensambla HTTP, validación, dominio, persistencia, migraciones y pruebas; cada frontera puede invalidar supuestos de la anterior. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** produce una versión parcial reproducible y defendible del monolito modular. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Optimizar solo el caso feliz oculta problemas de repetición, concurrencia y recuperación. La regla es: Entregar evidencia reproducible: código, contrato, migraciones, tests, historial y decisiones. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica OpenAPI con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 6. MySQL

**Problema e intuición.** MySQL aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Integración de mitad de cursada, MySQL se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La entrega ensambla HTTP, validación, dominio, persistencia, migraciones y pruebas; cada frontera puede invalidar supuestos de la anterior. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** produce una versión parcial reproducible y defendible del monolito modular. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Optimizar solo el caso feliz oculta problemas de repetición, concurrencia y recuperación. La regla es: Entregar evidencia reproducible: código, contrato, migraciones, tests, historial y decisiones. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica MySQL con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 7. JPA

**Problema e intuición.** JPA aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Integración de mitad de cursada, JPA se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La entrega ensambla HTTP, validación, dominio, persistencia, migraciones y pruebas; cada frontera puede invalidar supuestos de la anterior. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** produce una versión parcial reproducible y defendible del monolito modular. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Optimizar solo el caso feliz oculta problemas de repetición, concurrencia y recuperación. La regla es: Entregar evidencia reproducible: código, contrato, migraciones, tests, historial y decisiones. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica JPA con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 8. transacciones

**Problema e intuición.** transacciones aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** La transacción debe rodear el caso de uso que preserva una invariante. Una anotación en un método no corrige llamadas que evitan el proxy ni efectos remotos no transaccionales. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La entrega ensambla HTTP, validación, dominio, persistencia, migraciones y pruebas; cada frontera puede invalidar supuestos de la anterior. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** produce una versión parcial reproducible y defendible del monolito modular. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Optimizar solo el caso feliz oculta problemas de repetición, concurrencia y recuperación. La regla es: Entregar evidencia reproducible: código, contrato, migraciones, tests, historial y decisiones. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica transacciones con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 9. tests unitarios y de integración

**Problema e intuición.** tests unitarios y de integración aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Integración de mitad de cursada, tests unitarios y de integración se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La entrega ensambla HTTP, validación, dominio, persistencia, migraciones y pruebas; cada frontera puede invalidar supuestos de la anterior. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** produce una versión parcial reproducible y defendible del monolito modular. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Optimizar solo el caso feliz oculta problemas de repetición, concurrencia y recuperación. La regla es: Entregar evidencia reproducible: código, contrato, migraciones, tests, historial y decisiones. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica tests unitarios y de integración con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 10. Git con historial comprensible

**Problema e intuición.** Git con historial comprensible aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Integración de mitad de cursada, Git con historial comprensible se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La entrega ensambla HTTP, validación, dominio, persistencia, migraciones y pruebas; cada frontera puede invalidar supuestos de la anterior. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** produce una versión parcial reproducible y defendible del monolito modular. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Optimizar solo el caso feliz oculta problemas de repetición, concurrencia y recuperación. La regla es: Entregar evidencia reproducible: código, contrato, migraciones, tests, historial y decisiones. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica Git con historial comprensible con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 11. consigna

**Problema e intuición.** consigna aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Integración de mitad de cursada, consigna se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La entrega ensambla HTTP, validación, dominio, persistencia, migraciones y pruebas; cada frontera puede invalidar supuestos de la anterior. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** produce una versión parcial reproducible y defendible del monolito modular. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Optimizar solo el caso feliz oculta problemas de repetición, concurrencia y recuperación. La regla es: Entregar evidencia reproducible: código, contrato, migraciones, tests, historial y decisiones. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica consigna con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 12. requisitos mínimos

**Problema e intuición.** requisitos mínimos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Integración de mitad de cursada, requisitos mínimos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La entrega ensambla HTTP, validación, dominio, persistencia, migraciones y pruebas; cada frontera puede invalidar supuestos de la anterior. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** produce una versión parcial reproducible y defendible del monolito modular. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Optimizar solo el caso feliz oculta problemas de repetición, concurrencia y recuperación. La regla es: Entregar evidencia reproducible: código, contrato, migraciones, tests, historial y decisiones. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica requisitos mínimos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 13. requisitos avanzados

**Problema e intuición.** requisitos avanzados aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Integración de mitad de cursada, requisitos avanzados se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La entrega ensambla HTTP, validación, dominio, persistencia, migraciones y pruebas; cada frontera puede invalidar supuestos de la anterior. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** produce una versión parcial reproducible y defendible del monolito modular. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Optimizar solo el caso feliz oculta problemas de repetición, concurrencia y recuperación. La regla es: Entregar evidencia reproducible: código, contrato, migraciones, tests, historial y decisiones. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica requisitos avanzados con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 14. rúbrica de 100 puntos

**Problema e intuición.** rúbrica de 100 puntos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Integración de mitad de cursada, rúbrica de 100 puntos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La entrega ensambla HTTP, validación, dominio, persistencia, migraciones y pruebas; cada frontera puede invalidar supuestos de la anterior. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** produce una versión parcial reproducible y defendible del monolito modular. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Optimizar solo el caso feliz oculta problemas de repetición, concurrencia y recuperación. La regla es: Entregar evidencia reproducible: código, contrato, migraciones, tests, historial y decisiones. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica rúbrica de 100 puntos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 15. checklist de entrega

**Problema e intuición.** checklist de entrega aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Integración de mitad de cursada, checklist de entrega se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La entrega ensambla HTTP, validación, dominio, persistencia, migraciones y pruebas; cada frontera puede invalidar supuestos de la anterior. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** produce una versión parcial reproducible y defendible del monolito modular. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Optimizar solo el caso feliz oculta problemas de repetición, concurrencia y recuperación. La regla es: Entregar evidencia reproducible: código, contrato, migraciones, tests, historial y decisiones. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica checklist de entrega con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 16. preguntas de defensa oral

**Problema e intuición.** preguntas de defensa oral aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Integración de mitad de cursada, preguntas de defensa oral se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La entrega ensambla HTTP, validación, dominio, persistencia, migraciones y pruebas; cada frontera puede invalidar supuestos de la anterior. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** produce una versión parcial reproducible y defendible del monolito modular. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Optimizar solo el caso feliz oculta problemas de repetición, concurrencia y recuperación. La regla es: Entregar evidencia reproducible: código, contrato, migraciones, tests, historial y decisiones. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica preguntas de defensa oral con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 17. guía para autoevaluarse

**Problema e intuición.** guía para autoevaluarse aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Integración de mitad de cursada, guía para autoevaluarse se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La entrega ensambla HTTP, validación, dominio, persistencia, migraciones y pruebas; cada frontera puede invalidar supuestos de la anterior. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** produce una versión parcial reproducible y defendible del monolito modular. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Optimizar solo el caso feliz oculta problemas de repetición, concurrencia y recuperación. La regla es: Entregar evidencia reproducible: código, contrato, migraciones, tests, historial y decisiones. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica guía para autoevaluarse con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 18. lista de evidencias que debe presentar

**Problema e intuición.** lista de evidencias que debe presentar aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Integración de mitad de cursada, lista de evidencias que debe presentar se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La entrega ensambla HTTP, validación, dominio, persistencia, migraciones y pruebas; cada frontera puede invalidar supuestos de la anterior. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** produce una versión parcial reproducible y defendible del monolito modular. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Optimizar solo el caso feliz oculta problemas de repetición, concurrencia y recuperación. La regla es: Entregar evidencia reproducible: código, contrato, migraciones, tests, historial y decisiones. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica lista de evidencias que debe presentar con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

## Decisiones y alternativas

1. Define primero la invariante o el riesgo.
2. Compara una opción simple con otra más operable.
3. Registra el costo introducido: latencia, acoplamiento, complejidad o mantenimiento.
4. Elige: Entregar evidencia reproducible: código, contrato, migraciones, tests, historial y decisiones.
5. Documenta qué dato futuro haría cambiar la decisión.

## Errores frecuentes

- Copiar una anotación sin conocer quién la interpreta ni cuándo.
- Confundir el caso feliz con el contrato completo.
- Añadir infraestructura sin una necesidad observable.
- Escribir un test que replica la implementación en vez de cubrir riesgo.
- Registrar datos sensibles para facilitar debugging.
- Afirmar una garantía sin una restricción, prueba o medición que la sostenga.

## Qué ocurre en producción

Optimizar solo el caso feliz oculta problemas de repetición, concurrencia y recuperación. Un profesional diseña para degradación, documenta límites, mide antes de optimizar y deja una ruta de recuperación. FintechLab usa datos ficticios y no debe conectarse con bancos ni procesar dinero real.

## Cómo reconocer que realmente lo entendiste

- Puedes explicarlo sin depender del nombre de una anotación.
- Puedes dibujar el recorrido y marcar al menos tres fallos.
- Puedes cambiar el ejemplo sin copiarlo.
- Puedes escribir una prueba que falla cuando se rompe la regla.
- Puedes nombrar una alternativa y justificar el trade-off.

## Resumen sencillo

La unidad enseña a convertir Integración de mitad de cursada en decisiones verificables. La idea profesional no es acumular herramientas, sino conectar propósito, mecanismo, fallo, evidencia y costo.

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
