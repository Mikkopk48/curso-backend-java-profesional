# Unidad 13 — MySQL, modelo relacional, JPA y transacciones: teoría profunda

## Lugar dentro del recorrido

Esta unidad corresponde a la etapa **Persistencia robusta de cuentas**. Parte de la API inicial de la unidad 5 y añade una capacidad profesional sin volver a explicar Java, POO o CRUD desde cero.

## Conocimientos previos

- Controladores, servicios y repositorios básicos.
- Lectura de Java y ejecución de una aplicación Spring Boot.
- Conceptos recuperados en <code>00-objetivos-y-ruta.md</code>.

## Objetivos observables

Al terminar, podrás explicar el riesgo que resuelve cada concepto, seguir su funcionamiento, aplicarlo a FintechLab, escribir evidencia y defender cuándo no conviene usarlo.

## Problema central

La base de datos protege invariantes compartidas cuando muchas peticiones compiten. En un ejercicio pequeño puede parecer suficiente memorizar una API. En producción importan además concurrencia, datos inválidos, latencia, compatibilidad, trazabilidad y recuperación.

## Modelo mental y analogía

Es un libro mayor con reglas de integridad, índices de consulta y cierres transaccionales.

**Límite:** No es una hoja de cálculo: ejecuta planes, administra locks, mantiene versiones y puede fallar parcialmente.

## Funcionamiento técnico progresivo

MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. El recorrido útil siempre contiene cinco preguntas: quién inicia, qué cruza la frontera, qué estado participa, dónde puede fallar y qué evidencia queda.

~~~sql
START TRANSACTION;
SELECT balance FROM account WHERE id = ? FOR UPDATE;
UPDATE account SET balance = balance - ? WHERE id = ? AND balance >= ?;
INSERT INTO movement(id, account_id, amount, currency, occurred_at) VALUES (?, ?, ?, ?, ?);
COMMIT;
~~~

El ejemplo es deliberadamente pequeño. No es la solución de los ejercicios: muestra vocabulario y forma, mientras las consignas cambian datos, restricciones y estructura.

## Explicación conectada: de la relación al plan de ejecución

### Modelo e integridad

Una tabla representa una relación con filas y columnas de dominios definidos. Primary key identifica; una candidate key también podría hacerlo; foreign key obliga a que la referencia exista. NOT NULL, UNIQUE, CHECK y DEFAULT hacen que la base rechace estados imposibles incluso si otra aplicación escribe.

DECIMAL almacena dinero decimal; además necesitas moneda y escala. VARCHAR, DATE, TIMESTAMP y BINARY tienen costos y semánticas distintas. DDL define estructura, DML cambia datos, DQL consulta y TCL controla transacción.

Primera forma normal evita grupos repetidos; segunda elimina dependencia parcial de una clave compuesta; tercera evita dependencias transitivas entre atributos no clave. Normalizar reduce anomalías. Desnormalizar puede acelerar lecturas, pero exige una estrategia para mantener copias consistentes.

### SQL como transformación de conjuntos

SELECT construye una relación resultado; WHERE filtra antes de agrupar; GROUP BY forma grupos; HAVING filtra grupos; ORDER BY ordena y LIMIT/OFFSET recorta. INNER JOIN conserva coincidencias; LEFT JOIN conserva todas las filas izquierdas. Una condición en WHERE sobre la tabla derecha puede convertir accidentalmente un LEFT en comportamiento de INNER.

Subconsultas y CTE expresan pasos; una vista encapsula una consulta, no garantiza materialización. INSERT, UPDATE y DELETE deben revisar filas afectadas. Parámetros preparados evitan inyección; concatenar entrada en SQL es inseguro.

### Índices y EXPLAIN

Un índice B-tree mantiene claves ordenadas y punteros. Un índice compuesto (account_id, occurred_at) ayuda al prefijo account_id y al orden temporal; invertir columnas cambia consultas útiles. Alta selectividad suele ayudar, pero tamaño, distribución y cobertura importan. Cada índice consume espacio y encarece escrituras.

EXPLAIN muestra orden de joins, método de acceso, índice y filas estimadas. No concluyas con una base vacía. Mide datos representativos y tiempo varias veces, diferenciando caché. OFFSET profundo obliga a descartar filas; keyset pagination continúa desde la última clave.

### Transacción y concurrencia

Atomicidad confirma todo o nada; consistencia depende de invariantes declaradas; aislamiento limita interferencias y durabilidad conserva commit. Dirty read observa no confirmado; non-repeatable read cambia una fila; phantom altera el conjunto. MySQL InnoDB combina MVCC y locks según consulta y aislamiento.

Locks incompatibles esperan; adquirir recursos en distinto orden puede crear deadlock, y MySQL aborta una transacción. La aplicación debe reintentar de forma limitada si la operación es segura. En una transferencia, bloquea ambas cuentas en orden estable o usa un update condicional/versión con estrategia explícita.

### JDBC, pool, JPA y Hibernate

JDBC abre Connection, prepara Statement y recorre ResultSet. Un pool reutiliza conexiones; agotarlo bloquea requests, así que tamaño y timeout se miden. JPA define API y mapping; Hibernate implementa. El persistence context mantiene una instancia por identidad, rastrea cambios y hace flush. Flush sincroniza SQL, commit confirma.

Entidades pasan por transient, managed, detached y removed. En relaciones, owning side controla la foreign key; cascade propaga operaciones y orphanRemoval elimina huérfanos: no se activan por costumbre. Lazy evita carga inicial, pero acceder por fila causa N+1. Diseña fetch join, EntityGraph o proyección por caso.

<code>@Transactional</code> suele funcionar mediante proxy: una llamada interna al mismo objeto puede evitarlo. Define la transacción en el caso de uso, no en cada repository. Flyway versiona esquema; nunca edites una migración ya aplicada. Testcontainers verifica SQL y migraciones con MySQL real. Backups solo sirven si la restauración se practica.

## Atlas completo del temario oficial

Las subsecciones siguientes cubren cada punto del programa. La etiqueta **Diplomatura** identifica el núcleo oficial; las decisiones de producción y el vínculo con FintechLab constituyen profundización y ampliación profesional.

### 1. modelo relacional

**Problema e intuición.** modelo relacional aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, modelo relacional se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica modelo relacional con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 2. tablas, filas, columnas y dominios

**Problema e intuición.** tablas, filas, columnas y dominios aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, tablas, filas, columnas y dominios se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica tablas, filas, columnas y dominios con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 3. claves primarias y candidatas

**Problema e intuición.** claves primarias y candidatas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, claves primarias y candidatas se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica claves primarias y candidatas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 4. claves foráneas

**Problema e intuición.** claves foráneas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, claves foráneas se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica claves foráneas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 5. integridad de entidad y referencial

**Problema e intuición.** integridad de entidad y referencial aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, integridad de entidad y referencial se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica integridad de entidad y referencial con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 6. restricciones NOT NULL, UNIQUE, CHECK y DEFAULT

**Problema e intuición.** restricciones NOT NULL, UNIQUE, CHECK y DEFAULT aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, restricciones NOT NULL, UNIQUE, CHECK y DEFAULT se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica restricciones NOT NULL, UNIQUE, CHECK y DEFAULT con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 7. tipos de datos

**Problema e intuición.** tipos de datos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, tipos de datos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica tipos de datos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 8. DECIMAL para dinero

**Problema e intuición.** DECIMAL para dinero aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** DECIMAL y BigDecimal representan valores decimales con escala controlada. Para dinero también hacen falta moneda, regla de redondeo e invariantes. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica DECIMAL para dinero con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 9. DDL, DML, DQL y TCL

**Problema e intuición.** DDL, DML, DQL y TCL aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, DDL, DML, DQL y TCL se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica DDL, DML, DQL y TCL con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 10. SELECT, INSERT, UPDATE y DELETE

**Problema e intuición.** SELECT, INSERT, UPDATE y DELETE aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, SELECT, INSERT, UPDATE y DELETE se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica SELECT, INSERT, UPDATE y DELETE con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 11. WHERE, ORDER BY, LIMIT y OFFSET

**Problema e intuición.** WHERE, ORDER BY, LIMIT y OFFSET aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, WHERE, ORDER BY, LIMIT y OFFSET se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica WHERE, ORDER BY, LIMIT y OFFSET con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 12. joins

**Problema e intuición.** joins aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, joins se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica joins con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 13. agregaciones

**Problema e intuición.** agregaciones aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, agregaciones se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica agregaciones con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 14. GROUP BY y HAVING

**Problema e intuición.** GROUP BY y HAVING aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, GROUP BY y HAVING se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica GROUP BY y HAVING con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 15. subconsultas

**Problema e intuición.** subconsultas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, subconsultas se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica subconsultas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 16. CTE cuando sean compatibles y útiles

**Problema e intuición.** CTE cuando sean compatibles y útiles aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, CTE cuando sean compatibles y útiles se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica CTE cuando sean compatibles y útiles con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 17. vistas

**Problema e intuición.** vistas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, vistas se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica vistas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 18. diseño de esquema

**Problema e intuición.** diseño de esquema aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, diseño de esquema se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica diseño de esquema con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 19. normalización 1FN, 2FN y 3FN

**Problema e intuición.** normalización 1FN, 2FN y 3FN aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, normalización 1FN, 2FN y 3FN se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica normalización 1FN, 2FN y 3FN con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 20. desnormalización consciente

**Problema e intuición.** desnormalización consciente aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, desnormalización consciente se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica desnormalización consciente con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 21. índices

**Problema e intuición.** índices aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Un índice es una estructura adicional que acelera ciertos accesos y encarece escrituras. EXPLAIN permite inspeccionar el plan; la selectividad ayuda a estimar su utilidad. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica índices con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 22. índices compuestos y orden de columnas

**Problema e intuición.** índices compuestos y orden de columnas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Un índice es una estructura adicional que acelera ciertos accesos y encarece escrituras. EXPLAIN permite inspeccionar el plan; la selectividad ayuda a estimar su utilidad. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica índices compuestos y orden de columnas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 23. selectividad

**Problema e intuición.** selectividad aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Un índice es una estructura adicional que acelera ciertos accesos y encarece escrituras. EXPLAIN permite inspeccionar el plan; la selectividad ayuda a estimar su utilidad. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica selectividad con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 24. EXPLAIN

**Problema e intuición.** EXPLAIN aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Un índice es una estructura adicional que acelera ciertos accesos y encarece escrituras. EXPLAIN permite inspeccionar el plan; la selectividad ayuda a estimar su utilidad. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica EXPLAIN con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 25. costo de consultas

**Problema e intuición.** costo de consultas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, costo de consultas se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica costo de consultas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 26. transacciones

**Problema e intuición.** transacciones aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** La transacción debe rodear el caso de uso que preserva una invariante. Una anotación en un método no corrige llamadas que evitan el proxy ni efectos remotos no transaccionales. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica transacciones con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 27. ACID

**Problema e intuición.** ACID aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** ACID agrupa atomicidad, consistencia, aislamiento y durabilidad. No elimina todos los defectos: las reglas deben estar modeladas y el aislamiento elegido según concurrencia. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica ACID con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 28. niveles de aislamiento

**Problema e intuición.** niveles de aislamiento aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** El aislamiento define qué interferencias concurrentes pueden observarse. Aumentarlo reduce anomalías, pero puede elevar contención y abortos. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica niveles de aislamiento con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 29. dirty read, non-repeatable read y phantom read

**Problema e intuición.** dirty read, non-repeatable read y phantom read aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** El aislamiento define qué interferencias concurrentes pueden observarse. Aumentarlo reduce anomalías, pero puede elevar contención y abortos. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica dirty read, non-repeatable read y phantom read con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 30. locks

**Problema e intuición.** locks aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, locks se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica locks con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 31. deadlocks

**Problema e intuición.** deadlocks aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, deadlocks se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica deadlocks con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 32. concurrencia sobre saldos

**Problema e intuición.** concurrencia sobre saldos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, concurrencia sobre saldos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica concurrencia sobre saldos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 33. commit y rollback

**Problema e intuición.** commit y rollback aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, commit y rollback se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica commit y rollback con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 34. pools de conexiones

**Problema e intuición.** pools de conexiones aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, pools de conexiones se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica pools de conexiones con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 35. JDBC como base conceptual

**Problema e intuición.** JDBC como base conceptual aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, JDBC como base conceptual se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica JDBC como base conceptual con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 36. JPA e Hibernate

**Problema e intuición.** JPA e Hibernate aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, JPA e Hibernate se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica JPA e Hibernate con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 37. entidades y ciclo de vida

**Problema e intuición.** entidades y ciclo de vida aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, entidades y ciclo de vida se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica entidades y ciclo de vida con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 38. persistence context

**Problema e intuición.** persistence context aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, persistence context se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica persistence context con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 39. dirty checking

**Problema e intuición.** dirty checking aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, dirty checking se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica dirty checking con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 40. relaciones uno a uno, uno a muchos y muchos a muchos

**Problema e intuición.** relaciones uno a uno, uno a muchos y muchos a muchos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, relaciones uno a uno, uno a muchos y muchos a muchos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica relaciones uno a uno, uno a muchos y muchos a muchos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 41. dueño de una relación

**Problema e intuición.** dueño de una relación aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, dueño de una relación se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica dueño de una relación con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 42. cascadas

**Problema e intuición.** cascadas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, cascadas se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica cascadas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 43. orphan removal

**Problema e intuición.** orphan removal aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, orphan removal se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica orphan removal con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 44. lazy y eager loading

**Problema e intuición.** lazy y eager loading aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** N+1 aparece cuando una consulta inicial dispara otra por cada fila relacionada. Cambiar todo a eager suele trasladar el problema; se diseñan consultas por caso de uso. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica lazy y eager loading con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 45. problema N+1

**Problema e intuición.** problema N+1 aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** N+1 aparece cuando una consulta inicial dispara otra por cada fila relacionada. Cambiar todo a eager suele trasladar el problema; se diseñan consultas por caso de uso. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica problema N+1 con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 46. consultas derivadas y JPQL

**Problema e intuición.** consultas derivadas y JPQL aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, consultas derivadas y JPQL se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica consultas derivadas y JPQL con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 47. paginación

**Problema e intuición.** paginación aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, paginación se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica paginación con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 48. proyecciones

**Problema e intuición.** proyecciones aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, proyecciones se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica proyecciones con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 49. @Transactional

**Problema e intuición.** @Transactional aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** La transacción debe rodear el caso de uso que preserva una invariante. Una anotación en un método no corrige llamadas que evitan el proxy ni efectos remotos no transaccionales. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica @Transactional con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 50. límites transaccionales

**Problema e intuición.** límites transaccionales aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** La transacción debe rodear el caso de uso que preserva una invariante. Una anotación en un método no corrige llamadas que evitan el proxy ni efectos remotos no transaccionales. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica límites transaccionales con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 51. migraciones con Flyway o Liquibase

**Problema e intuición.** migraciones con Flyway o Liquibase aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, migraciones con Flyway o Liquibase se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica migraciones con Flyway o Liquibase con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 52. datos de prueba

**Problema e intuición.** datos de prueba aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, datos de prueba se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica datos de prueba con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 53. Testcontainers

**Problema e intuición.** Testcontainers aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Testcontainers inicia dependencias reales y desechables para reducir diferencias con producción, a cambio de más tiempo y necesidad de un runtime de contenedores. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica Testcontainers con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 54. backups y restauración a nivel conceptual

**Problema e intuición.** backups y restauración a nivel conceptual aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, backups y restauración a nivel conceptual se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica backups y restauración a nivel conceptual con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 55. seguridad de consultas e inyección SQL

**Problema e intuición.** seguridad de consultas e inyección SQL aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En MySQL, modelo relacional, JPA y transacciones, seguridad de consultas e inyección SQL se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MySQL analiza SQL, elige un plan con estadísticas e índices, ejecuta operaciones bajo reglas de aislamiento y registra cambios para commit o rollback; Hibernate coordina entidades mediante el persistence context. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** mantiene cuentas, saldos, movimientos e idempotencia como una operación consistente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. La regla es: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica seguridad de consultas e inyección SQL con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

## Decisiones y alternativas

1. Define primero la invariante o el riesgo.
2. Compara una opción simple con otra más operable.
3. Registra el costo introducido: latencia, acoplamiento, complejidad o mantenimiento.
4. Elige: Modelar invariantes primero; medir consultas y ubicar la transacción alrededor del caso de uso.
5. Documenta qué dato futuro haría cambiar la decisión.

## Errores frecuentes

- Copiar una anotación sin conocer quién la interpreta ni cuándo.
- Confundir el caso feliz con el contrato completo.
- Añadir infraestructura sin una necesidad observable.
- Escribir un test que replica la implementación en vez de cubrir riesgo.
- Registrar datos sensibles para facilitar debugging.
- Afirmar una garantía sin una restricción, prueba o medición que la sostenga.

## Qué ocurre en producción

Sin restricciones, índices y límites transaccionales correctos aparecen datos imposibles, bloqueos, N+1 y carreras. Un profesional diseña para degradación, documenta límites, mide antes de optimizar y deja una ruta de recuperación. FintechLab usa datos ficticios y no debe conectarse con bancos ni procesar dinero real.

## Cómo reconocer que realmente lo entendiste

- Puedes explicarlo sin depender del nombre de una anotación.
- Puedes dibujar el recorrido y marcar al menos tres fallos.
- Puedes cambiar el ejemplo sin copiarlo.
- Puedes escribir una prueba que falla cuando se rompe la regla.
- Puedes nombrar una alternativa y justificar el trade-off.

## Resumen sencillo

La unidad enseña a convertir MySQL, modelo relacional, JPA y transacciones en decisiones verificables. La idea profesional no es acumular herramientas, sino conectar propósito, mecanismo, fallo, evidencia y costo.

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
