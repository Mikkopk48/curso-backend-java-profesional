# Unidad 18 — Laboratorio y práctica deliberada

## Contrato de trabajo

Este archivo no contiene respuestas ni implementación final. Trabaja en una rama segura, realiza commits pequeños y conserva comandos, logs sanitizados y capturas cuando aporten evidencia.

## Microejercicios de 5 a 15 minutos

1. Define con tus palabras tres términos de la unidad y escribe el límite de cada definición.
2. Predice el resultado de una variante del ejemplo antes de ejecutarla.
3. Señala en un diagrama dónde puede fallar la operación.
4. Formula una pregunta útil para el docente que incluya versión y contexto.

## Laboratorio guiado — Configuración centralizada

### Objetivo

Incorporar la capacidad de la unidad sin romper las invariantes de FintechLab.

### Preparación

1. Ejecuta la suite disponible y guarda el estado inicial.
2. Crea una rama <code>practice/u18</code>.
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

## U18-E01 — explicación: configuración por ambiente

- **Objetivo:** aplicar configuración por ambiente y justificar su relación con Config Server y Config Client.
- **Concepto evaluado:** configuración por ambiente.
- **Dificultad:** 1/5.
- **Tipo:** explicación.
- **Tiempo estimado:** 22 minutos.

### Escenario

FintechLab incorpora la etapa **Configuración centralizada**. El caso feliz funciona, pero el equipo necesita evidencia de que el diseño conserva su significado ante una entrada límite, una repetición o un fallo de dependencia.

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

1. **Pista 1:** escribe primero la garantía observable de configuración por ambiente.
2. **Pista 2:** localiza la frontera donde Config Server y Config Client cambia el resultado.
3. **Pista 3:** prepara una entrada mínima, ejecuta una sola acción y verifica estado más evidencia; no copies el ejemplo del capítulo.

## U18-E02 — implementación: YAML

- **Objetivo:** aplicar YAML y justificar su relación con actualización de configuración.
- **Concepto evaluado:** YAML.
- **Dificultad:** 2/5.
- **Tipo:** implementación.
- **Tiempo estimado:** 34 minutos.

### Escenario

FintechLab incorpora la etapa **Configuración centralizada**. El caso feliz funciona, pero el equipo necesita evidencia de que el diseño conserva su significado ante una entrada límite, una repetición o un fallo de dependencia.

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

1. **Pista 1:** escribe primero la garantía observable de YAML.
2. **Pista 2:** localiza la frontera donde actualización de configuración cambia el resultado.
3. **Pista 3:** prepara una entrada mínima, ejecuta una sola acción y verifica estado más evidencia; no copies el ejemplo del capítulo.

## U18-E03 — debugging: nombres de aplicación, perfiles y labels

- **Objetivo:** aplicar nombres de aplicación, perfiles y labels y justificar su relación con cifrado cuando sea compatible.
- **Concepto evaluado:** nombres de aplicación, perfiles y labels.
- **Dificultad:** 3/5.
- **Tipo:** debugging.
- **Tiempo estimado:** 46 minutos.

### Escenario

FintechLab incorpora la etapa **Configuración centralizada**. El caso feliz funciona, pero el equipo necesita evidencia de que el diseño conserva su significado ante una entrada límite, una repetición o un fallo de dependencia.

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

1. **Pista 1:** escribe primero la garantía observable de nombres de aplicación, perfiles y labels.
2. **Pista 2:** localiza la frontera donde cifrado cuando sea compatible cambia el resultado.
3. **Pista 3:** prepara una entrada mínima, ejecuta una sola acción y verifica estado más evidencia; no copies el ejemplo del capítulo.

## U18-E04 — revisión: actualización de configuración

- **Objetivo:** aplicar actualización de configuración y justificar su relación con alternativas en contenedores y Kubernetes.
- **Concepto evaluado:** actualización de configuración.
- **Dificultad:** 3/5.
- **Tipo:** revisión.
- **Tiempo estimado:** 58 minutos.

### Escenario

FintechLab incorpora la etapa **Configuración centralizada**. El caso feliz funciona, pero el equipo necesita evidencia de que el diseño conserva su significado ante una entrada límite, una repetición o un fallo de dependencia.

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

1. **Pista 1:** escribe primero la garantía observable de actualización de configuración.
2. **Pista 2:** localiza la frontera donde alternativas en contenedores y Kubernetes cambia el resultado.
3. **Pista 3:** prepara una entrada mínima, ejecuta una sola acción y verifica estado más evidencia; no copies el ejemplo del capítulo.

## U18-E05 — diseño: secretos

- **Objetivo:** aplicar secretos y justificar su relación con YAML.
- **Concepto evaluado:** secretos.
- **Dificultad:** 4/5.
- **Tipo:** diseño.
- **Tiempo estimado:** 70 minutos.

### Escenario

FintechLab incorpora la etapa **Configuración centralizada**. El caso feliz funciona, pero el equipo necesita evidencia de que el diseño conserva su significado ante una entrada límite, una repetición o un fallo de dependencia.

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

1. **Pista 1:** escribe primero la garantía observable de secretos.
2. **Pista 2:** localiza la frontera donde YAML cambia el resultado.
3. **Pista 3:** prepara una entrada mínima, ejecuta una sola acción y verifica estado más evidencia; no copies el ejemplo del capítulo.

## U18-E06 — testing: separación entre configuración y credenciales

- **Objetivo:** aplicar separación entre configuración y credenciales y justificar su relación con fail-fast y retry.
- **Concepto evaluado:** separación entre configuración y credenciales.
- **Dificultad:** 4/5.
- **Tipo:** testing.
- **Tiempo estimado:** 82 minutos.

### Escenario

FintechLab incorpora la etapa **Configuración centralizada**. El caso feliz funciona, pero el equipo necesita evidencia de que el diseño conserva su significado ante una entrada límite, una repetición o un fallo de dependencia.

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

1. **Pista 1:** escribe primero la garantía observable de separación entre configuración y credenciales.
2. **Pista 2:** localiza la frontera donde fail-fast y retry cambia el resultado.
3. **Pista 3:** prepara una entrada mínima, ejecuta una sola acción y verifica estado más evidencia; no copies el ejemplo del capítulo.

## U18-E07 — integración: pruebas

- **Objetivo:** aplicar pruebas y justificar su relación con por qué un repositorio de configuración no es automáticamente un gestor de secretos.
- **Concepto evaluado:** pruebas.
- **Dificultad:** 5/5.
- **Tipo:** integración.
- **Tiempo estimado:** 94 minutos.

### Escenario

FintechLab incorpora la etapa **Configuración centralizada**. El caso feliz funciona, pero el equipo necesita evidencia de que el diseño conserva su significado ante una entrada límite, una repetición o un fallo de dependencia.

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

1. **Pista 1:** escribe primero la garantía observable de pruebas.
2. **Pista 2:** localiza la frontera donde por qué un repositorio de configuración no es automáticamente un gestor de secretos cambia el resultado.
3. **Pista 3:** prepara una entrada mínima, ejecuta una sola acción y verifica estado más evidencia; no copies el ejemplo del capítulo.

## U18-E08 — desafío opcional: configuración por ambiente

- **Objetivo:** aplicar configuración por ambiente y justificar su relación con pruebas.
- **Concepto evaluado:** configuración por ambiente.
- **Dificultad:** 5/5.
- **Tipo:** desafío opcional.
- **Tiempo estimado:** 106 minutos.

### Escenario

FintechLab incorpora la etapa **Configuración centralizada**. El caso feliz funciona, pero el equipo necesita evidencia de que el diseño conserva su significado ante una entrada límite, una repetición o un fallo de dependencia.

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

1. **Pista 1:** escribe primero la garantía observable de configuración por ambiente.
2. **Pista 2:** localiza la frontera donde pruebas cambia el resultado.
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
