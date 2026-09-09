# Unidad 11 — Laboratorio y práctica deliberada

## Contrato de trabajo

Este archivo no contiene respuestas ni implementación final. Trabaja en una rama segura, realiza commits pequeños y conserva comandos, logs sanitizados y capturas cuando aporten evidencia.

## Microejercicios de 5 a 15 minutos

1. Define con tus palabras tres términos de la unidad y escribe el límite de cada definición.
2. Predice el resultado de una variante del ejemplo antes de ejecutarla.
3. Señala en un diagrama dónde puede fallar la operación.
4. Formula una pregunta útil para el docente que incluya versión y contexto.

## Laboratorio guiado — Contrato publicable de la API

### Objetivo

Incorporar la capacidad de la unidad sin romper las invariantes de FintechLab.

### Preparación

1. Ejecuta la suite disponible y guarda el estado inicial.
2. Crea una rama <code>practice/u11</code>.
3. Lee el contrato OpenAPI y la migración relacionada.
4. Escribe tres riesgos antes de programar.

### Iteración

1. Añade un test o comprobación que exprese el comportamiento requerido.
2. Realiza el cambio mínimo que permita avanzar.
3. Ejecuta la comprobación más pequeña y después la suite relevante.
4. Simula un fallo y observa qué mensaje, estado y log quedan.
5. Refactoriza nombres y límites sin cambiar comportamiento.
6. Registra la decisión en una nota breve.

### Cierre

Demuestra el caso feliz, el caso límite y el fallo. Explica qué no demuestra tu evidencia y qué probarías en un ambiente más cercano a producción.

## Desafío de debugging

Un reporte afirma: “funciona localmente, pero falla de forma intermitente”. El log posee timestamp, nivel y mensaje, pero no correlation ID; la prueba comparte datos y una dependencia no tiene timeout.

Tu tarea es formular hipótesis ordenadas, añadir observabilidad segura, reducir el caso y demostrar una causa. No se acepta aumentar esperas ni capturar <code>Exception</code> para ocultar el fallo.

## Revisión de código

Revisa un cambio que mezcla validación HTTP, regla de negocio, acceso a datos y logging de payload en un mismo método. Escribe comentarios priorizados: bloqueo, importante y mejora. Cada comentario debe explicar riesgo y proponer dirección, no reescribir toda la solución.

## Ejercicios evaluables

## U11-E01 — explicación: contrato de API

- **Objetivo:** aplicar contrato de API y justificar su relación con YAML desde cero.
- **Concepto evaluado:** contrato de API.
- **Dificultad:** 1/5.
- **Tipo:** explicación.
- **Tiempo estimado:** 22 minutos.

### Escenario

FintechLab incorpora la etapa **Contrato publicable de la API**. El caso feliz funciona, pero el equipo necesita evidencia de que el diseño conserva su significado ante una entrada límite, una repetición o un fallo de dependencia.

### Requisitos funcionales

1. Describe el comportamiento observable antes de modificar código.
2. Implementa o analiza una variante distinta del ejemplo teórico.
3. Incluye un caso feliz, un borde y un fallo relevante.
4. Justifica el status, la excepción, la consulta, el test o la decisión aplicable.

### Restricciones

- Usa datos ficticios y <code>BigDecimal</code> para importes.
- No expongas entidades JPA, secretos ni stack traces.
- No cambies el contrato para ocultar un test fallido.
- Evita dependencias nuevas si no puedes explicar el riesgo que cubren.

### Archivos modificables

Trabaja únicamente en la rama de práctica y en el paquete de la unidad: código de aplicación, test asociado y documentación de decisión cuando corresponda.

### Comportamiento esperado

La evidencia debe distinguir éxito, borde y error, permanecer determinista y permitir que otra persona reproduzca el resultado.

### Criterios de aceptación

- El proyecto conserva compilación según la pila declarada.
- El nombre del test o evidencia comunica el comportamiento.
- La decisión menciona al menos un trade-off.
- Los logs no contienen tokens, contraseñas ni datos sensibles.
- La explicación puede defenderse sin leer la implementación línea por línea.

### Comandos de comprobación

~~~bash
./mvnw -q test
./mvnw -q verify
~~~

Ejecuta el segundo comando cuando el ejercicio use infraestructura de integración.

### Casos límite

- entrada ausente o con formato correcto pero regla inválida;
- repetición de la misma intención;
- dependencia lenta o no disponible;
- dos operaciones que compiten por el mismo dato.

### Rúbrica

Corrección funcional 25 %, comprensión 20 %, diseño 15 %, errores 10 %, pruebas 15 %, seguridad 5 %, mantenibilidad 5 % y justificación 5 %.

### Escalera de pistas

1. **Pista 1:** escribe primero la garantía observable de contrato de API.
2. **Pista 2:** localiza la frontera donde YAML desde cero cambia el resultado.
3. **Pista 3:** prepara una entrada mínima, ejecuta una sola acción y verifica estado más evidencia; no copies el ejemplo del capítulo.

## U11-E02 — implementación: estructura de un documento OpenAPI

- **Objetivo:** aplicar estructura de un documento OpenAPI y justificar su relación con formatos y validaciones.
- **Concepto evaluado:** estructura de un documento OpenAPI.
- **Dificultad:** 2/5.
- **Tipo:** implementación.
- **Tiempo estimado:** 34 minutos.

### Escenario

FintechLab incorpora la etapa **Contrato publicable de la API**. El caso feliz funciona, pero el equipo necesita evidencia de que el diseño conserva su significado ante una entrada límite, una repetición o un fallo de dependencia.

### Requisitos funcionales

1. Describe el comportamiento observable antes de modificar código.
2. Implementa o analiza una variante distinta del ejemplo teórico.
3. Incluye un caso feliz, un borde y un fallo relevante.
4. Justifica el status, la excepción, la consulta, el test o la decisión aplicable.

### Restricciones

- Usa datos ficticios y <code>BigDecimal</code> para importes.
- No expongas entidades JPA, secretos ni stack traces.
- No cambies el contrato para ocultar un test fallido.
- Evita dependencias nuevas si no puedes explicar el riesgo que cubren.

### Archivos modificables

Trabaja únicamente en la rama de práctica y en el paquete de la unidad: código de aplicación, test asociado y documentación de decisión cuando corresponda.

### Comportamiento esperado

La evidencia debe distinguir éxito, borde y error, permanecer determinista y permitir que otra persona reproduzca el resultado.

### Criterios de aceptación

- El proyecto conserva compilación según la pila declarada.
- El nombre del test o evidencia comunica el comportamiento.
- La decisión menciona al menos un trade-off.
- Los logs no contienen tokens, contraseñas ni datos sensibles.
- La explicación puede defenderse sin leer la implementación línea por línea.

### Comandos de comprobación

~~~bash
./mvnw -q test
./mvnw -q verify
~~~

Ejecuta el segundo comando cuando el ejercicio use infraestructura de integración.

### Casos límite

- entrada ausente o con formato correcto pero regla inválida;
- repetición de la misma intención;
- dependencia lenta o no disponible;
- dos operaciones que compiten por el mismo dato.

### Rúbrica

Corrección funcional 25 %, comprensión 20 %, diseño 15 %, errores 10 %, pruebas 15 %, seguridad 5 %, mantenibilidad 5 % y justificación 5 %.

### Escalera de pistas

1. **Pista 1:** escribe primero la garantía observable de estructura de un documento OpenAPI.
2. **Pista 2:** localiza la frontera donde formatos y validaciones cambia el resultado.
3. **Pista 3:** prepara una entrada mínima, ejecuta una sola acción y verifica estado más evidencia; no copies el ejemplo del capítulo.

## U11-E03 — debugging: responses

- **Objetivo:** aplicar responses y justificar su relación con prueba manual desde Swagger UI.
- **Concepto evaluado:** responses.
- **Dificultad:** 3/5.
- **Tipo:** debugging.
- **Tiempo estimado:** 46 minutos.

### Escenario

FintechLab incorpora la etapa **Contrato publicable de la API**. El caso feliz funciona, pero el equipo necesita evidencia de que el diseño conserva su significado ante una entrada límite, una repetición o un fallo de dependencia.

### Requisitos funcionales

1. Describe el comportamiento observable antes de modificar código.
2. Implementa o analiza una variante distinta del ejemplo teórico.
3. Incluye un caso feliz, un borde y un fallo relevante.
4. Justifica el status, la excepción, la consulta, el test o la decisión aplicable.

### Restricciones

- Usa datos ficticios y <code>BigDecimal</code> para importes.
- No expongas entidades JPA, secretos ni stack traces.
- No cambies el contrato para ocultar un test fallido.
- Evita dependencias nuevas si no puedes explicar el riesgo que cubren.

### Archivos modificables

Trabaja únicamente en la rama de práctica y en el paquete de la unidad: código de aplicación, test asociado y documentación de decisión cuando corresponda.

### Comportamiento esperado

La evidencia debe distinguir éxito, borde y error, permanecer determinista y permitir que otra persona reproduzca el resultado.

### Criterios de aceptación

- El proyecto conserva compilación según la pila declarada.
- El nombre del test o evidencia comunica el comportamiento.
- La decisión menciona al menos un trade-off.
- Los logs no contienen tokens, contraseñas ni datos sensibles.
- La explicación puede defenderse sin leer la implementación línea por línea.

### Comandos de comprobación

~~~bash
./mvnw -q test
./mvnw -q verify
~~~

Ejecuta el segundo comando cuando el ejercicio use infraestructura de integración.

### Casos límite

- entrada ausente o con formato correcto pero regla inválida;
- repetición de la misma intención;
- dependencia lenta o no disponible;
- dos operaciones que compiten por el mismo dato.

### Rúbrica

Corrección funcional 25 %, comprensión 20 %, diseño 15 %, errores 10 %, pruebas 15 %, seguridad 5 %, mantenibilidad 5 % y justificación 5 %.

### Escalera de pistas

1. **Pista 1:** escribe primero la garantía observable de responses.
2. **Pista 2:** localiza la frontera donde prueba manual desde Swagger UI cambia el resultado.
3. **Pista 3:** prepara una entrada mínima, ejecuta una sola acción y verifica estado más evidencia; no copies el ejemplo del capítulo.

## U11-E04 — revisión: formatos y validaciones

- **Objetivo:** aplicar formatos y validaciones y justificar su relación con generación de clientes y límites de esa automatización.
- **Concepto evaluado:** formatos y validaciones.
- **Dificultad:** 3/5.
- **Tipo:** revisión.
- **Tiempo estimado:** 58 minutos.

### Escenario

FintechLab incorpora la etapa **Contrato publicable de la API**. El caso feliz funciona, pero el equipo necesita evidencia de que el diseño conserva su significado ante una entrada límite, una repetición o un fallo de dependencia.

### Requisitos funcionales

1. Describe el comportamiento observable antes de modificar código.
2. Implementa o analiza una variante distinta del ejemplo teórico.
3. Incluye un caso feliz, un borde y un fallo relevante.
4. Justifica el status, la excepción, la consulta, el test o la decisión aplicable.

### Restricciones

- Usa datos ficticios y <code>BigDecimal</code> para importes.
- No expongas entidades JPA, secretos ni stack traces.
- No cambies el contrato para ocultar un test fallido.
- Evita dependencias nuevas si no puedes explicar el riesgo que cubren.

### Archivos modificables

Trabaja únicamente en la rama de práctica y en el paquete de la unidad: código de aplicación, test asociado y documentación de decisión cuando corresponda.

### Comportamiento esperado

La evidencia debe distinguir éxito, borde y error, permanecer determinista y permitir que otra persona reproduzca el resultado.

### Criterios de aceptación

- El proyecto conserva compilación según la pila declarada.
- El nombre del test o evidencia comunica el comportamiento.
- La decisión menciona al menos un trade-off.
- Los logs no contienen tokens, contraseñas ni datos sensibles.
- La explicación puede defenderse sin leer la implementación línea por línea.

### Comandos de comprobación

~~~bash
./mvnw -q test
./mvnw -q verify
~~~

Ejecuta el segundo comando cuando el ejercicio use infraestructura de integración.

### Casos límite

- entrada ausente o con formato correcto pero regla inválida;
- repetición de la misma intención;
- dependencia lenta o no disponible;
- dos operaciones que compiten por el mismo dato.

### Rúbrica

Corrección funcional 25 %, comprensión 20 %, diseño 15 %, errores 10 %, pruebas 15 %, seguridad 5 %, mantenibilidad 5 % y justificación 5 %.

### Escalera de pistas

1. **Pista 1:** escribe primero la garantía observable de formatos y validaciones.
2. **Pista 2:** localiza la frontera donde generación de clientes y límites de esa automatización cambia el resultado.
3. **Pista 3:** prepara una entrada mínima, ejecuta una sola acción y verifica estado más evidencia; no copies el ejemplo del capítulo.

## U11-E05 — diseño: documentación de errores

- **Objetivo:** aplicar documentación de errores y justificar su relación con enfoque design-first.
- **Concepto evaluado:** documentación de errores.
- **Dificultad:** 4/5.
- **Tipo:** diseño.
- **Tiempo estimado:** 70 minutos.

### Escenario

FintechLab incorpora la etapa **Contrato publicable de la API**. El caso feliz funciona, pero el equipo necesita evidencia de que el diseño conserva su significado ante una entrada límite, una repetición o un fallo de dependencia.

### Requisitos funcionales

1. Describe el comportamiento observable antes de modificar código.
2. Implementa o analiza una variante distinta del ejemplo teórico.
3. Incluye un caso feliz, un borde y un fallo relevante.
4. Justifica el status, la excepción, la consulta, el test o la decisión aplicable.

### Restricciones

- Usa datos ficticios y <code>BigDecimal</code> para importes.
- No expongas entidades JPA, secretos ni stack traces.
- No cambies el contrato para ocultar un test fallido.
- Evita dependencias nuevas si no puedes explicar el riesgo que cubren.

### Archivos modificables

Trabaja únicamente en la rama de práctica y en el paquete de la unidad: código de aplicación, test asociado y documentación de decisión cuando corresponda.

### Comportamiento esperado

La evidencia debe distinguir éxito, borde y error, permanecer determinista y permitir que otra persona reproduzca el resultado.

### Criterios de aceptación

- El proyecto conserva compilación según la pila declarada.
- El nombre del test o evidencia comunica el comportamiento.
- La decisión menciona al menos un trade-off.
- Los logs no contienen tokens, contraseñas ni datos sensibles.
- La explicación puede defenderse sin leer la implementación línea por línea.

### Comandos de comprobación

~~~bash
./mvnw -q test
./mvnw -q verify
~~~

Ejecuta el segundo comando cuando el ejercicio use infraestructura de integración.

### Casos límite

- entrada ausente o con formato correcto pero regla inválida;
- repetición de la misma intención;
- dependencia lenta o no disponible;
- dos operaciones que compiten por el mismo dato.

### Rúbrica

Corrección funcional 25 %, comprensión 20 %, diseño 15 %, errores 10 %, pruebas 15 %, seguridad 5 %, mantenibilidad 5 % y justificación 5 %.

### Escalera de pistas

1. **Pista 1:** escribe primero la garantía observable de documentación de errores.
2. **Pista 2:** localiza la frontera donde enfoque design-first cambia el resultado.
3. **Pista 3:** prepara una entrada mínima, ejecuta una sola acción y verifica estado más evidencia; no copies el ejemplo del capítulo.

## U11-E06 — testing: linting y validación del contrato

- **Objetivo:** aplicar linting y validación del contrato y justificar su relación con components y schemas.
- **Concepto evaluado:** linting y validación del contrato.
- **Dificultad:** 4/5.
- **Tipo:** testing.
- **Tiempo estimado:** 82 minutos.

### Escenario

FintechLab incorpora la etapa **Contrato publicable de la API**. El caso feliz funciona, pero el equipo necesita evidencia de que el diseño conserva su significado ante una entrada límite, una repetición o un fallo de dependencia.

### Requisitos funcionales

1. Describe el comportamiento observable antes de modificar código.
2. Implementa o analiza una variante distinta del ejemplo teórico.
3. Incluye un caso feliz, un borde y un fallo relevante.
4. Justifica el status, la excepción, la consulta, el test o la decisión aplicable.

### Restricciones

- Usa datos ficticios y <code>BigDecimal</code> para importes.
- No expongas entidades JPA, secretos ni stack traces.
- No cambies el contrato para ocultar un test fallido.
- Evita dependencias nuevas si no puedes explicar el riesgo que cubren.

### Archivos modificables

Trabaja únicamente en la rama de práctica y en el paquete de la unidad: código de aplicación, test asociado y documentación de decisión cuando corresponda.

### Comportamiento esperado

La evidencia debe distinguir éxito, borde y error, permanecer determinista y permitir que otra persona reproduzca el resultado.

### Criterios de aceptación

- El proyecto conserva compilación según la pila declarada.
- El nombre del test o evidencia comunica el comportamiento.
- La decisión menciona al menos un trade-off.
- Los logs no contienen tokens, contraseñas ni datos sensibles.
- La explicación puede defenderse sin leer la implementación línea por línea.

### Comandos de comprobación

~~~bash
./mvnw -q test
./mvnw -q verify
~~~

Ejecuta el segundo comando cuando el ejercicio use infraestructura de integración.

### Casos límite

- entrada ausente o con formato correcto pero regla inválida;
- repetición de la misma intención;
- dependencia lenta o no disponible;
- dos operaciones que compiten por el mismo dato.

### Rúbrica

Corrección funcional 25 %, comprensión 20 %, diseño 15 %, errores 10 %, pruebas 15 %, seguridad 5 %, mantenibilidad 5 % y justificación 5 %.

### Escalera de pistas

1. **Pista 1:** escribe primero la garantía observable de linting y validación del contrato.
2. **Pista 2:** localiza la frontera donde components y schemas cambia el resultado.
3. **Pista 3:** prepara una entrada mínima, ejecuta una sola acción y verifica estado más evidencia; no copies el ejemplo del capítulo.

## U11-E07 — integración: versionado de API

- **Objetivo:** aplicar versionado de API y justificar su relación con documentación de errores.
- **Concepto evaluado:** versionado de API.
- **Dificultad:** 5/5.
- **Tipo:** integración.
- **Tiempo estimado:** 94 minutos.

### Escenario

FintechLab incorpora la etapa **Contrato publicable de la API**. El caso feliz funciona, pero el equipo necesita evidencia de que el diseño conserva su significado ante una entrada límite, una repetición o un fallo de dependencia.

### Requisitos funcionales

1. Describe el comportamiento observable antes de modificar código.
2. Implementa o analiza una variante distinta del ejemplo teórico.
3. Incluye un caso feliz, un borde y un fallo relevante.
4. Justifica el status, la excepción, la consulta, el test o la decisión aplicable.

### Restricciones

- Usa datos ficticios y <code>BigDecimal</code> para importes.
- No expongas entidades JPA, secretos ni stack traces.
- No cambies el contrato para ocultar un test fallido.
- Evita dependencias nuevas si no puedes explicar el riesgo que cubren.

### Archivos modificables

Trabaja únicamente en la rama de práctica y en el paquete de la unidad: código de aplicación, test asociado y documentación de decisión cuando corresponda.

### Comportamiento esperado

La evidencia debe distinguir éxito, borde y error, permanecer determinista y permitir que otra persona reproduzca el resultado.

### Criterios de aceptación

- El proyecto conserva compilación según la pila declarada.
- El nombre del test o evidencia comunica el comportamiento.
- La decisión menciona al menos un trade-off.
- Los logs no contienen tokens, contraseñas ni datos sensibles.
- La explicación puede defenderse sin leer la implementación línea por línea.

### Comandos de comprobación

~~~bash
./mvnw -q test
./mvnw -q verify
~~~

Ejecuta el segundo comando cuando el ejercicio use infraestructura de integración.

### Casos límite

- entrada ausente o con formato correcto pero regla inválida;
- repetición de la misma intención;
- dependencia lenta o no disponible;
- dos operaciones que compiten por el mismo dato.

### Rúbrica

Corrección funcional 25 %, comprensión 20 %, diseño 15 %, errores 10 %, pruebas 15 %, seguridad 5 %, mantenibilidad 5 % y justificación 5 %.

### Escalera de pistas

1. **Pista 1:** escribe primero la garantía observable de versionado de API.
2. **Pista 2:** localiza la frontera donde documentación de errores cambia el resultado.
3. **Pista 3:** prepara una entrada mínima, ejecuta una sola acción y verifica estado más evidencia; no copies el ejemplo del capítulo.

## U11-E08 — desafío opcional: diferencia entre OpenAPI, Swagger y Swagger UI

- **Objetivo:** aplicar diferencia entre OpenAPI, Swagger y Swagger UI y justificar su relación con cambios rompientes.
- **Concepto evaluado:** diferencia entre OpenAPI, Swagger y Swagger UI.
- **Dificultad:** 5/5.
- **Tipo:** desafío opcional.
- **Tiempo estimado:** 106 minutos.

### Escenario

FintechLab incorpora la etapa **Contrato publicable de la API**. El caso feliz funciona, pero el equipo necesita evidencia de que el diseño conserva su significado ante una entrada límite, una repetición o un fallo de dependencia.

### Requisitos funcionales

1. Describe el comportamiento observable antes de modificar código.
2. Implementa o analiza una variante distinta del ejemplo teórico.
3. Incluye un caso feliz, un borde y un fallo relevante.
4. Justifica el status, la excepción, la consulta, el test o la decisión aplicable.

### Restricciones

- Usa datos ficticios y <code>BigDecimal</code> para importes.
- No expongas entidades JPA, secretos ni stack traces.
- No cambies el contrato para ocultar un test fallido.
- Evita dependencias nuevas si no puedes explicar el riesgo que cubren.

### Archivos modificables

Trabaja únicamente en la rama de práctica y en el paquete de la unidad: código de aplicación, test asociado y documentación de decisión cuando corresponda.

### Comportamiento esperado

La evidencia debe distinguir éxito, borde y error, permanecer determinista y permitir que otra persona reproduzca el resultado.

### Criterios de aceptación

- El proyecto conserva compilación según la pila declarada.
- El nombre del test o evidencia comunica el comportamiento.
- La decisión menciona al menos un trade-off.
- Los logs no contienen tokens, contraseñas ni datos sensibles.
- La explicación puede defenderse sin leer la implementación línea por línea.

### Comandos de comprobación

~~~bash
./mvnw -q test
./mvnw -q verify
~~~

Ejecuta el segundo comando cuando el ejercicio use infraestructura de integración.

### Casos límite

- entrada ausente o con formato correcto pero regla inválida;
- repetición de la misma intención;
- dependencia lenta o no disponible;
- dos operaciones que compiten por el mismo dato.

### Rúbrica

Corrección funcional 25 %, comprensión 20 %, diseño 15 %, errores 10 %, pruebas 15 %, seguridad 5 %, mantenibilidad 5 % y justificación 5 %.

### Escalera de pistas

1. **Pista 1:** escribe primero la garantía observable de diferencia entre OpenAPI, Swagger y Swagger UI.
2. **Pista 2:** localiza la frontera donde cambios rompientes cambia el resultado.
3. **Pista 3:** prepara una entrada mínima, ejecuta una sola acción y verifica estado más evidencia; no copies el ejemplo del capítulo.

## Autoevaluación — sin respuestas

1. ¿Qué problema resuelve la unidad y qué costo introduce?
2. ¿Qué componente interpreta la configuración o anotación principal?
3. ¿Qué fallo parcial o concurrente puede ocurrir?
4. ¿Qué prueba cubre el mayor riesgo y qué no demuestra?
5. ¿Cuándo elegirías una alternativa más sencilla?

## Defensa oral

Expón durante cinco minutos: contexto, invariante, decisión, evidencia, limitación y siguiente mejora. Una respuesta sólida distingue hechos observados de inferencias.

## Rúbrica común de cinco niveles

| Nivel | Evidencia observable |
|---|---|
| 1. Inicial | Reconoce términos, pero mezcla responsabilidades o no reproduce el resultado. |
| 2. En desarrollo | Resuelve el caso feliz con explicación parcial y pruebas frágiles. |
| 3. Competente | Cumple contrato, cubre bordes principales y justifica una decisión. |
| 4. Sólido | Añade fallos, seguridad, mantenibilidad y límites explícitos. |
| 5. Destacado | Integra riesgos, trade-offs, evidencia reproducible y defensa clara sin sobreingeniería. |

## Aporte a FintechLab

El resultado se integra solo después de pasar la revisión y no debe conectarse con sistemas financieros reales.
