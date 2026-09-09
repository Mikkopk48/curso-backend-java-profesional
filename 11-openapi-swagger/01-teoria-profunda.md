# Unidad 11 — OpenAPI y Swagger: teoría profunda

## Lugar dentro del recorrido

Esta unidad corresponde a la etapa **Contrato publicable de la API**. Parte de la API inicial de la unidad 5 y añade una capacidad profesional sin volver a explicar Java, POO o CRUD desde cero.

## Conocimientos previos

- Controladores, servicios y repositorios básicos.
- Lectura de Java y ejecución de una aplicación Spring Boot.
- Conceptos recuperados en <code>00-objetivos-y-ruta.md</code>.

## Objetivos observables

Al terminar, podrás explicar el riesgo que resuelve cada concepto, seguir su funcionamiento, aplicarlo a FintechLab, escribir evidencia y defender cuándo no conviene usarlo.

## Problema central

La documentación contractual permite que equipos y herramientas acuerden antes de ejecutar. En un ejercicio pequeño puede parecer suficiente memorizar una API. En producción importan además concurrencia, datos inválidos, latencia, compatibilidad, trazabilidad y recuperación.

## Modelo mental y analogía

OpenAPI es el plano; Swagger UI es una forma de recorrerlo, no el edificio.

**Límite:** Un contrato válido puede describir una API mal diseñada o diferir de la implementación.

## Funcionamiento técnico progresivo

El documento OpenAPI es procesado por validadores, generadores y una UI; las referencias resuelven componentes reutilizables y las operaciones forman el contrato visible. El recorrido útil siempre contiene cinco preguntas: quién inicia, qué cruza la frontera, qué estado participa, dónde puede fallar y qué evidencia queda.

~~~yaml
paths:
  /api/transfers:
    post:
      operationId: createTransfer
      responses:
        '201': { description: Transferencia registrada }
        '409': { $ref: '#/components/responses/BusinessConflict' }
~~~

El ejemplo es deliberadamente pequeño. No es la solución de los ejercicios: muestra vocabulario y forma, mientras las consignas cambian datos, restricciones y estructura.

## Explicación conectada: un contrato que herramientas puedan verificar

### OpenAPI, Swagger y dos enfoques

OpenAPI define un documento de contrato. Swagger es una familia de herramientas; Swagger UI representa el documento y permite enviar requests. Code-first deriva contrato del código y reduce duplicación, pero hereda nombres y omisiones. Design-first acuerda el documento antes de implementar y favorece revisión de consumidores, pero exige comprobar que implementación y contrato no diverjan.

El documento comienza con <code>openapi</code>, <code>info</code>, <code>servers</code>, <code>paths</code> y <code>components</code>. Cada path contiene operaciones; parameters ubica path/query/header; requestBody describe contenido; responses enumera resultados por status. Un schema define tipos, required, formatos, límites y composición. <code>$ref</code> reutiliza componentes.

### Precisión del contrato

Documentar solo 200 deja al consumidor adivinar 400, 401, 403, 404 y 409. Incluye Problem Details y seguridad bearer. Un ejemplo ilustra, pero no sustituye restricciones. <code>format: uuid</code> o <code>date-time</code> añade semántica; una herramienta puede tratar formatos como anotaciones, por lo que los tests siguen siendo necesarios.

La especificación de FintechLab separa CreateTransfer de Transfer, exige Idempotency-Key y documenta 201 frente a repetición 200. La entidad JPA no aparece.

### Evolución

Añadir un campo opcional suele ser compatible; volverlo required rompe clientes. Renombrar enum, cambiar tipo, eliminar response o endurecer validación puede ser rompiente. Versionar toda la API en la URL no es la única estrategia: primero intenta evolución aditiva y deprecación medible.

Linting detecta referencias rotas, operations sin ID y estilos inconsistentes. Contract tests comparan implementación. Generar clientes reduce código repetido, pero produce una API tan buena como el documento y requiere controlar versiones del generador. Revisa el diff del contrato en pull requests como código de producción.

## Atlas completo del temario oficial

Las subsecciones siguientes cubren cada punto del programa. La etiqueta **Diplomatura** identifica el núcleo oficial; las decisiones de producción y el vínculo con FintechLab constituyen profundización y ampliación profesional.

### 1. diferencia entre OpenAPI, Swagger y Swagger UI

**Problema e intuición.** diferencia entre OpenAPI, Swagger y Swagger UI aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** OpenAPI es la especificación del contrato; Swagger agrupa herramientas históricas y Swagger UI renderiza y permite invocar operaciones descritas. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El documento OpenAPI es procesado por validadores, generadores y una UI; las referencias resuelven componentes reutilizables y las operaciones forman el contrato visible. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** describe cuentas y transferencias sin exponer entidades JPA ni detalles internos. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** La documentación puede compilar y seguir mintiendo sobre seguridad, errores o reglas. La regla es: Tratar el contrato como artefacto versionado, probado y revisado por consumidores. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica diferencia entre OpenAPI, Swagger y Swagger UI con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 2. contrato de API

**Problema e intuición.** contrato de API aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En OpenAPI y Swagger, contrato de API se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El documento OpenAPI es procesado por validadores, generadores y una UI; las referencias resuelven componentes reutilizables y las operaciones forman el contrato visible. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** describe cuentas y transferencias sin exponer entidades JPA ni detalles internos. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** La documentación puede compilar y seguir mintiendo sobre seguridad, errores o reglas. La regla es: Tratar el contrato como artefacto versionado, probado y revisado por consumidores. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica contrato de API con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 3. enfoque code-first

**Problema e intuición.** enfoque code-first aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En OpenAPI y Swagger, enfoque code-first se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El documento OpenAPI es procesado por validadores, generadores y una UI; las referencias resuelven componentes reutilizables y las operaciones forman el contrato visible. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** describe cuentas y transferencias sin exponer entidades JPA ni detalles internos. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** La documentación puede compilar y seguir mintiendo sobre seguridad, errores o reglas. La regla es: Tratar el contrato como artefacto versionado, probado y revisado por consumidores. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica enfoque code-first con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 4. enfoque design-first

**Problema e intuición.** enfoque design-first aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En OpenAPI y Swagger, enfoque design-first se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El documento OpenAPI es procesado por validadores, generadores y una UI; las referencias resuelven componentes reutilizables y las operaciones forman el contrato visible. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** describe cuentas y transferencias sin exponer entidades JPA ni detalles internos. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** La documentación puede compilar y seguir mintiendo sobre seguridad, errores o reglas. La regla es: Tratar el contrato como artefacto versionado, probado y revisado por consumidores. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica enfoque design-first con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 5. estructura de un documento OpenAPI

**Problema e intuición.** estructura de un documento OpenAPI aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** OpenAPI es la especificación del contrato; Swagger agrupa herramientas históricas y Swagger UI renderiza y permite invocar operaciones descritas. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El documento OpenAPI es procesado por validadores, generadores y una UI; las referencias resuelven componentes reutilizables y las operaciones forman el contrato visible. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** describe cuentas y transferencias sin exponer entidades JPA ni detalles internos. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** La documentación puede compilar y seguir mintiendo sobre seguridad, errores o reglas. La regla es: Tratar el contrato como artefacto versionado, probado y revisado por consumidores. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica estructura de un documento OpenAPI con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 6. YAML desde cero

**Problema e intuición.** YAML desde cero aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En OpenAPI y Swagger, YAML desde cero se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El documento OpenAPI es procesado por validadores, generadores y una UI; las referencias resuelven componentes reutilizables y las operaciones forman el contrato visible. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** describe cuentas y transferencias sin exponer entidades JPA ni detalles internos. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** La documentación puede compilar y seguir mintiendo sobre seguridad, errores o reglas. La regla es: Tratar el contrato como artefacto versionado, probado y revisado por consumidores. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica YAML desde cero con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 7. paths, operations, parameters y requestBody

**Problema e intuición.** paths, operations, parameters y requestBody aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En OpenAPI y Swagger, paths, operations, parameters y requestBody se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El documento OpenAPI es procesado por validadores, generadores y una UI; las referencias resuelven componentes reutilizables y las operaciones forman el contrato visible. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** describe cuentas y transferencias sin exponer entidades JPA ni detalles internos. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** La documentación puede compilar y seguir mintiendo sobre seguridad, errores o reglas. La regla es: Tratar el contrato como artefacto versionado, probado y revisado por consumidores. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica paths, operations, parameters y requestBody con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 8. responses

**Problema e intuición.** responses aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En OpenAPI y Swagger, responses se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El documento OpenAPI es procesado por validadores, generadores y una UI; las referencias resuelven componentes reutilizables y las operaciones forman el contrato visible. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** describe cuentas y transferencias sin exponer entidades JPA ni detalles internos. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** La documentación puede compilar y seguir mintiendo sobre seguridad, errores o reglas. La regla es: Tratar el contrato como artefacto versionado, probado y revisado por consumidores. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica responses con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 9. components y schemas

**Problema e intuición.** components y schemas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En OpenAPI y Swagger, components y schemas se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El documento OpenAPI es procesado por validadores, generadores y una UI; las referencias resuelven componentes reutilizables y las operaciones forman el contrato visible. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** describe cuentas y transferencias sin exponer entidades JPA ni detalles internos. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** La documentación puede compilar y seguir mintiendo sobre seguridad, errores o reglas. La regla es: Tratar el contrato como artefacto versionado, probado y revisado por consumidores. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica components y schemas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 10. referencias reutilizables

**Problema e intuición.** referencias reutilizables aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En OpenAPI y Swagger, referencias reutilizables se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El documento OpenAPI es procesado por validadores, generadores y una UI; las referencias resuelven componentes reutilizables y las operaciones forman el contrato visible. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** describe cuentas y transferencias sin exponer entidades JPA ni detalles internos. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** La documentación puede compilar y seguir mintiendo sobre seguridad, errores o reglas. La regla es: Tratar el contrato como artefacto versionado, probado y revisado por consumidores. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica referencias reutilizables con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 11. formatos y validaciones

**Problema e intuición.** formatos y validaciones aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En OpenAPI y Swagger, formatos y validaciones se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El documento OpenAPI es procesado por validadores, generadores y una UI; las referencias resuelven componentes reutilizables y las operaciones forman el contrato visible. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** describe cuentas y transferencias sin exponer entidades JPA ni detalles internos. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** La documentación puede compilar y seguir mintiendo sobre seguridad, errores o reglas. La regla es: Tratar el contrato como artefacto versionado, probado y revisado por consumidores. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica formatos y validaciones con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 12. ejemplos

**Problema e intuición.** ejemplos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En OpenAPI y Swagger, ejemplos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El documento OpenAPI es procesado por validadores, generadores y una UI; las referencias resuelven componentes reutilizables y las operaciones forman el contrato visible. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** describe cuentas y transferencias sin exponer entidades JPA ni detalles internos. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** La documentación puede compilar y seguir mintiendo sobre seguridad, errores o reglas. La regla es: Tratar el contrato como artefacto versionado, probado y revisado por consumidores. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica ejemplos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 13. autenticación documentada

**Problema e intuición.** autenticación documentada aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En OpenAPI y Swagger, autenticación documentada se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El documento OpenAPI es procesado por validadores, generadores y una UI; las referencias resuelven componentes reutilizables y las operaciones forman el contrato visible. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** describe cuentas y transferencias sin exponer entidades JPA ni detalles internos. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** La documentación puede compilar y seguir mintiendo sobre seguridad, errores o reglas. La regla es: Tratar el contrato como artefacto versionado, probado y revisado por consumidores. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica autenticación documentada con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 14. documentación de errores

**Problema e intuición.** documentación de errores aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En OpenAPI y Swagger, documentación de errores se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El documento OpenAPI es procesado por validadores, generadores y una UI; las referencias resuelven componentes reutilizables y las operaciones forman el contrato visible. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** describe cuentas y transferencias sin exponer entidades JPA ni detalles internos. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** La documentación puede compilar y seguir mintiendo sobre seguridad, errores o reglas. La regla es: Tratar el contrato como artefacto versionado, probado y revisado por consumidores. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica documentación de errores con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 15. generación de documentación desde Spring

**Problema e intuición.** generación de documentación desde Spring aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En OpenAPI y Swagger, generación de documentación desde Spring se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El documento OpenAPI es procesado por validadores, generadores y una UI; las referencias resuelven componentes reutilizables y las operaciones forman el contrato visible. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** describe cuentas y transferencias sin exponer entidades JPA ni detalles internos. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** La documentación puede compilar y seguir mintiendo sobre seguridad, errores o reglas. La regla es: Tratar el contrato como artefacto versionado, probado y revisado por consumidores. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica generación de documentación desde Spring con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 16. prueba manual desde Swagger UI

**Problema e intuición.** prueba manual desde Swagger UI aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** OpenAPI es la especificación del contrato; Swagger agrupa herramientas históricas y Swagger UI renderiza y permite invocar operaciones descritas. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El documento OpenAPI es procesado por validadores, generadores y una UI; las referencias resuelven componentes reutilizables y las operaciones forman el contrato visible. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** describe cuentas y transferencias sin exponer entidades JPA ni detalles internos. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** La documentación puede compilar y seguir mintiendo sobre seguridad, errores o reglas. La regla es: Tratar el contrato como artefacto versionado, probado y revisado por consumidores. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica prueba manual desde Swagger UI con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 17. linting y validación del contrato

**Problema e intuición.** linting y validación del contrato aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En OpenAPI y Swagger, linting y validación del contrato se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El documento OpenAPI es procesado por validadores, generadores y una UI; las referencias resuelven componentes reutilizables y las operaciones forman el contrato visible. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** describe cuentas y transferencias sin exponer entidades JPA ni detalles internos. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** La documentación puede compilar y seguir mintiendo sobre seguridad, errores o reglas. La regla es: Tratar el contrato como artefacto versionado, probado y revisado por consumidores. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica linting y validación del contrato con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 18. compatibilidad hacia atrás

**Problema e intuición.** compatibilidad hacia atrás aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En OpenAPI y Swagger, compatibilidad hacia atrás se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El documento OpenAPI es procesado por validadores, generadores y una UI; las referencias resuelven componentes reutilizables y las operaciones forman el contrato visible. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** describe cuentas y transferencias sin exponer entidades JPA ni detalles internos. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** La documentación puede compilar y seguir mintiendo sobre seguridad, errores o reglas. La regla es: Tratar el contrato como artefacto versionado, probado y revisado por consumidores. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica compatibilidad hacia atrás con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 19. cambios rompientes

**Problema e intuición.** cambios rompientes aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En OpenAPI y Swagger, cambios rompientes se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El documento OpenAPI es procesado por validadores, generadores y una UI; las referencias resuelven componentes reutilizables y las operaciones forman el contrato visible. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** describe cuentas y transferencias sin exponer entidades JPA ni detalles internos. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** La documentación puede compilar y seguir mintiendo sobre seguridad, errores o reglas. La regla es: Tratar el contrato como artefacto versionado, probado y revisado por consumidores. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica cambios rompientes con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 20. versionado de API

**Problema e intuición.** versionado de API aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En OpenAPI y Swagger, versionado de API se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El documento OpenAPI es procesado por validadores, generadores y una UI; las referencias resuelven componentes reutilizables y las operaciones forman el contrato visible. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** describe cuentas y transferencias sin exponer entidades JPA ni detalles internos. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** La documentación puede compilar y seguir mintiendo sobre seguridad, errores o reglas. La regla es: Tratar el contrato como artefacto versionado, probado y revisado por consumidores. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica versionado de API con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 21. generación de clientes y límites de esa automatización

**Problema e intuición.** generación de clientes y límites de esa automatización aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En OpenAPI y Swagger, generación de clientes y límites de esa automatización se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El documento OpenAPI es procesado por validadores, generadores y una UI; las referencias resuelven componentes reutilizables y las operaciones forman el contrato visible. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** describe cuentas y transferencias sin exponer entidades JPA ni detalles internos. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** La documentación puede compilar y seguir mintiendo sobre seguridad, errores o reglas. La regla es: Tratar el contrato como artefacto versionado, probado y revisado por consumidores. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica generación de clientes y límites de esa automatización con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 22. documentación útil frente a documentación meramente generada

**Problema e intuición.** documentación útil frente a documentación meramente generada aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En OpenAPI y Swagger, documentación útil frente a documentación meramente generada se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El documento OpenAPI es procesado por validadores, generadores y una UI; las referencias resuelven componentes reutilizables y las operaciones forman el contrato visible. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** describe cuentas y transferencias sin exponer entidades JPA ni detalles internos. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** La documentación puede compilar y seguir mintiendo sobre seguridad, errores o reglas. La regla es: Tratar el contrato como artefacto versionado, probado y revisado por consumidores. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica documentación útil frente a documentación meramente generada con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

## Decisiones y alternativas

1. Define primero la invariante o el riesgo.
2. Compara una opción simple con otra más operable.
3. Registra el costo introducido: latencia, acoplamiento, complejidad o mantenimiento.
4. Elige: Tratar el contrato como artefacto versionado, probado y revisado por consumidores.
5. Documenta qué dato futuro haría cambiar la decisión.

## Errores frecuentes

- Copiar una anotación sin conocer quién la interpreta ni cuándo.
- Confundir el caso feliz con el contrato completo.
- Añadir infraestructura sin una necesidad observable.
- Escribir un test que replica la implementación en vez de cubrir riesgo.
- Registrar datos sensibles para facilitar debugging.
- Afirmar una garantía sin una restricción, prueba o medición que la sostenga.

## Qué ocurre en producción

La documentación puede compilar y seguir mintiendo sobre seguridad, errores o reglas. Un profesional diseña para degradación, documenta límites, mide antes de optimizar y deja una ruta de recuperación. FintechLab usa datos ficticios y no debe conectarse con bancos ni procesar dinero real.

## Cómo reconocer que realmente lo entendiste

- Puedes explicarlo sin depender del nombre de una anotación.
- Puedes dibujar el recorrido y marcar al menos tres fallos.
- Puedes cambiar el ejemplo sin copiarlo.
- Puedes escribir una prueba que falla cuando se rompe la regla.
- Puedes nombrar una alternativa y justificar el trade-off.

## Resumen sencillo

La unidad enseña a convertir OpenAPI y Swagger en decisiones verificables. La idea profesional no es acumular herramientas, sino conectar propósito, mecanismo, fallo, evidencia y costo.

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
