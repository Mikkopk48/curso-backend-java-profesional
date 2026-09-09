# Unidad 21 — Mapas y Streams: teoría profunda

## Lugar dentro del recorrido

Esta unidad corresponde a la etapa **Agregaciones por cliente y moneda**. Parte de la API inicial de la unidad 5 y añade una capacidad profesional sin volver a explicar Java, POO o CRUD desde cero.

## Conocimientos previos

- Controladores, servicios y repositorios básicos.
- Lectura de Java y ejecución de una aplicación Spring Boot.
- Conceptos recuperados en <code>00-objetivos-y-ruta.md</code>.

## Objetivos observables

Al terminar, podrás explicar el riesgo que resuelve cada concepto, seguir su funcionamiento, aplicarlo a FintechLab, escribir evidencia y defender cuándo no conviene usarlo.

## Problema central

Map expresa acceso por identidad de clave; la calidad de esa identidad determina la corrección. En un ejercicio pequeño puede parecer suficiente memorizar una API. En producción importan además concurrencia, datos inválidos, latencia, compatibilidad, trazabilidad y recuperación.

## Modelo mental y analogía

Es un fichero donde cada ficha se recupera por una clave estable.

**Límite:** Los hashes no ordenan ni garantizan costo constante en todos los casos.

## Funcionamiento técnico progresivo

El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. El recorrido útil siempre contiene cinco preguntas: quién inicia, qué cruza la frontera, qué estado participa, dónde puede fallar y qué evidencia queda.

~~~java
Map<UUID, BigDecimal> totalByClient = movements.stream().collect(
    Collectors.toMap(Movement::clientId, Movement::amount, BigDecimal::add));
~~~

El ejemplo es deliberadamente pequeño. No es la solución de los ejercicios: muestra vocabulario y forma, mientras las consignas cambian datos, restricciones y estructura.

## Explicación conectada: identidad de claves y agregaciones

Map asocia claves únicas con valores. <code>put</code> reemplaza el valor de una clave equivalente; <code>get</code> depende del contrato de igualdad. HashMap calcula hash para ubicar bucket y usa equals para confirmar. Colisiones son normales; la tabla redimensiona al crecer. El costo promedio se aproxima a O(1), no es garantía absoluta.

Si dos objetos son iguales, deben tener el mismo hashCode. Mutar un campo usado por equals/hashCode mientras el objeto es clave lo deja en el bucket antiguo y la búsqueda puede fallar. Prefiere UUID, String o record inmutable. HashMap admite un null key; TreeMap depende de comparación y normalmente no; no uses null como diseño implícito.

LinkedHashMap conserva orden de inserción o acceso. TreeMap ordena por natural/comparator y operaciones cuestan O(log n). Un comparator inconsistente con equals puede hacer que dos claves parezcan la misma. ConcurrentHashMap coordina concurrencia mejor que envolver HashMap, pero operaciones compuestas necesitan métodos atómicos.

<code>getOrDefault</code> simplifica lectura, <code>computeIfAbsent</code> construye valor ausente y <code>merge</code> agrega. Iterar <code>entrySet</code> evita buscar otra vez cada value. Map.copyOf crea copia inmutable superficial.

Con streams, <code>toMap</code> necesita resolver claves duplicadas; sumar BigDecimal es distinto de elegir último. <code>groupingBy</code> crea listas o downstream collectors; <code>partitioningBy</code> separa dos categorías. Mapas anidados por cliente/moneda pueden ser correctos, pero un tipo de dominio suele comunicar mejor. Mide memoria y legibilidad antes de optimizar.

## Atlas completo del temario oficial

Las subsecciones siguientes cubren cada punto del programa. La etiqueta **Diplomatura** identifica el núcleo oficial; las decisiones de producción y el vínculo con FintechLab constituyen profundización y ampliación profesional.

### 1. contrato de Map

**Problema e intuición.** contrato de Map aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapas y Streams, contrato de Map se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Elegir implementación por orden, concurrencia y acceso; nunca mutar una clave mientras está almacenada. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica contrato de Map con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 2. clave y valor

**Problema e intuición.** clave y valor aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapas y Streams, clave y valor se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Elegir implementación por orden, concurrencia y acceso; nunca mutar una clave mientras está almacenada. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica clave y valor con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 3. HashMap

**Problema e intuición.** HashMap aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** HashMap usa hashCode para ubicar candidatos y equals para confirmar identidad. Objetos iguales deben compartir hash; mutar la parte usada como clave vuelve inaccesible la entrada. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Elegir implementación por orden, concurrencia y acceso; nunca mutar una clave mientras está almacenada. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica HashMap con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 4. LinkedHashMap

**Problema e intuición.** LinkedHashMap aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** HashMap usa hashCode para ubicar candidatos y equals para confirmar identidad. Objetos iguales deben compartir hash; mutar la parte usada como clave vuelve inaccesible la entrada. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Elegir implementación por orden, concurrencia y acceso; nunca mutar una clave mientras está almacenada. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica LinkedHashMap con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 5. TreeMap

**Problema e intuición.** TreeMap aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapas y Streams, TreeMap se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Elegir implementación por orden, concurrencia y acceso; nunca mutar una clave mientras está almacenada. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica TreeMap con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 6. orden y comparadores

**Problema e intuición.** orden y comparadores aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapas y Streams, orden y comparadores se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Elegir implementación por orden, concurrencia y acceso; nunca mutar una clave mientras está almacenada. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica orden y comparadores con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 7. equals y hashCode

**Problema e intuición.** equals y hashCode aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** HashMap usa hashCode para ubicar candidatos y equals para confirmar identidad. Objetos iguales deben compartir hash; mutar la parte usada como clave vuelve inaccesible la entrada. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Elegir implementación por orden, concurrencia y acceso; nunca mutar una clave mientras está almacenada. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica equals y hashCode con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 8. colisiones

**Problema e intuición.** colisiones aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapas y Streams, colisiones se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Elegir implementación por orden, concurrencia y acceso; nunca mutar una clave mientras está almacenada. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica colisiones con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 9. buckets

**Problema e intuición.** buckets aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapas y Streams, buckets se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Elegir implementación por orden, concurrencia y acceso; nunca mutar una clave mientras está almacenada. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica buckets con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 10. redimensionamiento como modelo simplificado

**Problema e intuición.** redimensionamiento como modelo simplificado aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapas y Streams, redimensionamiento como modelo simplificado se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Elegir implementación por orden, concurrencia y acceso; nunca mutar una clave mientras está almacenada. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica redimensionamiento como modelo simplificado con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 11. complejidad temporal aproximada

**Problema e intuición.** complejidad temporal aproximada aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapas y Streams, complejidad temporal aproximada se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Elegir implementación por orden, concurrencia y acceso; nunca mutar una clave mientras está almacenada. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica complejidad temporal aproximada con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 12. claves mutables y sus peligros

**Problema e intuición.** claves mutables y sus peligros aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapas y Streams, claves mutables y sus peligros se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Elegir implementación por orden, concurrencia y acceso; nunca mutar una clave mientras está almacenada. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica claves mutables y sus peligros con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 13. nulls

**Problema e intuición.** nulls aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapas y Streams, nulls se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Elegir implementación por orden, concurrencia y acceso; nunca mutar una clave mientras está almacenada. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica nulls con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 14. computeIfAbsent, merge y getOrDefault

**Problema e intuición.** computeIfAbsent, merge y getOrDefault aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** PUT expresa reemplazo de la representación en la URI conocida y debe ser idempotente; PATCH describe una modificación parcial cuyo formato necesita contrato propio. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Elegir implementación por orden, concurrencia y acceso; nunca mutar una clave mientras está almacenada. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica computeIfAbsent, merge y getOrDefault con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 15. iteración eficiente

**Problema e intuición.** iteración eficiente aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapas y Streams, iteración eficiente se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Elegir implementación por orden, concurrencia y acceso; nunca mutar una clave mientras está almacenada. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica iteración eficiente con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 16. entrySet

**Problema e intuición.** entrySet aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapas y Streams, entrySet se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Elegir implementación por orden, concurrencia y acceso; nunca mutar una clave mientras está almacenada. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica entrySet con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 17. mapas inmutables

**Problema e intuición.** mapas inmutables aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapas y Streams, mapas inmutables se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Elegir implementación por orden, concurrencia y acceso; nunca mutar una clave mientras está almacenada. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica mapas inmutables con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 18. ConcurrentHashMap como introducción

**Problema e intuición.** ConcurrentHashMap como introducción aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** HashMap usa hashCode para ubicar candidatos y equals para confirmar identidad. Objetos iguales deben compartir hash; mutar la parte usada como clave vuelve inaccesible la entrada. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Elegir implementación por orden, concurrencia y acceso; nunca mutar una clave mientras está almacenada. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica ConcurrentHashMap como introducción con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 19. transformación entre List y Map

**Problema e intuición.** transformación entre List y Map aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapas y Streams, transformación entre List y Map se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Elegir implementación por orden, concurrencia y acceso; nunca mutar una clave mientras está almacenada. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica transformación entre List y Map con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 20. Collectors.toMap

**Problema e intuición.** Collectors.toMap aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapas y Streams, Collectors.toMap se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Elegir implementación por orden, concurrencia y acceso; nunca mutar una clave mientras está almacenada. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica Collectors.toMap con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 21. resolución de claves duplicadas

**Problema e intuición.** resolución de claves duplicadas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapas y Streams, resolución de claves duplicadas se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Elegir implementación por orden, concurrencia y acceso; nunca mutar una clave mientras está almacenada. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica resolución de claves duplicadas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 22. groupingBy

**Problema e intuición.** groupingBy aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapas y Streams, groupingBy se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Elegir implementación por orden, concurrencia y acceso; nunca mutar una clave mientras está almacenada. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica groupingBy con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 23. partitioningBy

**Problema e intuición.** partitioningBy aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapas y Streams, partitioningBy se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Elegir implementación por orden, concurrencia y acceso; nunca mutar una clave mientras está almacenada. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica partitioningBy con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 24. mapas anidados

**Problema e intuición.** mapas anidados aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapas y Streams, mapas anidados se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Elegir implementación por orden, concurrencia y acceso; nunca mutar una clave mientras está almacenada. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica mapas anidados con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 25. conteos y agregaciones

**Problema e intuición.** conteos y agregaciones aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapas y Streams, conteos y agregaciones se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Elegir implementación por orden, concurrencia y acceso; nunca mutar una clave mientras está almacenada. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica conteos y agregaciones con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 26. rendimiento y legibilidad

**Problema e intuición.** rendimiento y legibilidad aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapas y Streams, rendimiento y legibilidad se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Elegir implementación por orden, concurrencia y acceso; nunca mutar una clave mientras está almacenada. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica rendimiento y legibilidad con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 27. cuándo un Map es una mala elección

**Problema e intuición.** cuándo un Map es una mala elección aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapas y Streams, cuándo un Map es una mala elección se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Elegir implementación por orden, concurrencia y acceso; nunca mutar una clave mientras está almacenada. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica cuándo un Map es una mala elección con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

## Decisiones y alternativas

1. Define primero la invariante o el riesgo.
2. Compara una opción simple con otra más operable.
3. Registra el costo introducido: latencia, acoplamiento, complejidad o mantenimiento.
4. Elige: Elegir implementación por orden, concurrencia y acceso; nunca mutar una clave mientras está almacenada.
5. Documenta qué dato futuro haría cambiar la decisión.

## Errores frecuentes

- Copiar una anotación sin conocer quién la interpreta ni cuándo.
- Confundir el caso feliz con el contrato completo.
- Añadir infraestructura sin una necesidad observable.
- Escribir un test que replica la implementación en vez de cubrir riesgo.
- Registrar datos sensibles para facilitar debugging.
- Afirmar una garantía sin una restricción, prueba o medición que la sostenga.

## Qué ocurre en producción

Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. Un profesional diseña para degradación, documenta límites, mide antes de optimizar y deja una ruta de recuperación. FintechLab usa datos ficticios y no debe conectarse con bancos ni procesar dinero real.

## Cómo reconocer que realmente lo entendiste

- Puedes explicarlo sin depender del nombre de una anotación.
- Puedes dibujar el recorrido y marcar al menos tres fallos.
- Puedes cambiar el ejemplo sin copiarlo.
- Puedes escribir una prueba que falla cuando se rompe la regla.
- Puedes nombrar una alternativa y justificar el trade-off.

## Resumen sencillo

La unidad enseña a convertir Mapas y Streams en decisiones verificables. La idea profesional no es acumular herramientas, sino conectar propósito, mecanismo, fallo, evidencia y costo.

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
