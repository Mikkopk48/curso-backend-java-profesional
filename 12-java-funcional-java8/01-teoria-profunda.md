# Unidad 12 — Java funcional, Streams y tiempo: teoría profunda

## Lugar dentro del recorrido

Esta unidad corresponde a la etapa **Análisis de movimientos**. Parte de la API inicial de la unidad 5 y añade una capacidad profesional sin volver a explicar Java, POO o CRUD desde cero.

## Conocimientos previos

- Controladores, servicios y repositorios básicos.
- Lectura de Java y ejecución de una aplicación Spring Boot.
- Conceptos recuperados en <code>00-objetivos-y-ruta.md</code>.

## Objetivos observables

Al terminar, podrás explicar el riesgo que resuelve cada concepto, seguir su funcionamiento, aplicarlo a FintechLab, escribir evidencia y defender cuándo no conviene usarlo.

## Problema central

Las abstracciones funcionales expresan transformaciones, pero no eliminan costos ni efectos. En un ejercicio pequeño puede parecer suficiente memorizar una API. En producción importan además concurrencia, datos inválidos, latencia, compatibilidad, trazabilidad y recuperación.

## Modelo mental y analogía

Un stream es una cinta de procesamiento: cada estación transforma o filtra elementos.

**Límite:** Una colección existe; un stream describe un recorrido consumible y puede esconder trabajo costoso.

## Funcionamiento técnico progresivo

El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. El recorrido útil siempre contiene cinco preguntas: quién inicia, qué cruza la frontera, qué estado participa, dónde puede fallar y qué evidencia queda.

~~~java
Map<String, BigDecimal> totals = movements.stream()
    .collect(Collectors.groupingBy(Movement::currency,
        Collectors.reducing(BigDecimal.ZERO, Movement::amount, BigDecimal::add)));
~~~

El ejemplo es deliberadamente pequeño. No es la solución de los ejercicios: muestra vocabulario y forma, mientras las consignas cambian datos, restricciones y estructura.

## Explicación conectada: comportamiento como valor y pipelines legibles

### Interfaces funcionales y lambdas

Una interfaz funcional tiene un único método abstracto. Predicate prueba y devuelve boolean; Function transforma; Consumer produce un efecto; Supplier entrega un valor. <code>@FunctionalInterface</code> hace que el compilador proteja la intención. Una lambda implementa ese método sin una clase anónima explícita; una referencia a método es una forma más breve cuando el nombre ya explica.

Las variables locales capturadas deben ser final o effectively final. La lambda puede ejecutarse después de que el método local terminó, por eso Java captura un valor estable. Esto no vuelve inmutable el objeto apuntado; mutar una lista capturada sigue siendo un efecto.

Métodos default permitieron evolucionar interfaces como Collection sin romper todas las implementaciones. Un método static pertenece a la interfaz y no se hereda polimórficamente como instancia.

### Stream como descripción de recorrido

Un stream no almacena: enlaza una fuente, operaciones intermedias perezosas y una terminal. <code>filter</code> conserva, <code>map</code> transforma, <code>flatMap</code> aplana, <code>sorted</code> ordena, <code>distinct</code> elimina duplicados según igualdad, <code>limit/skip</code> recortan. <code>anyMatch</code> puede cortar temprano; <code>reduce</code> combina con identidad y operación asociativa.

Collectors materializa resultados. <code>groupingBy</code> agrupa movimientos; <code>partitioningBy</code> crea dos grupos booleanos; <code>joining</code> concatena; <code>toMap</code> necesita decidir qué ocurre con claves duplicadas. Omitir la función de merge puede lanzar excepción legítimamente.

Efectos laterales dentro de map/peek rompen razonamiento y paralelización. Un pipeline de quince pasos no es superior a un bucle con nombres. Streams paralelos usan un pool compartido y solo ayudan con trabajo grande, divisible, CPU-bound, sin efectos y medido.

### Optional y tiempo

Optional comunica ausencia en retornos; <code>orElse</code> evalúa su alternativa inmediatamente y <code>orElseGet</code> de forma perezosa. No lo uses para ocultar null en entidades ni como parámetro universal.

LocalDate modela fecha; LocalDateTime fecha y hora sin zona; Instant un punto UTC; ZoneId reglas regionales; Duration tiempo basado en segundos y Period en unidades de calendario. Persiste Instant para eventos y convierte a zona en el borde de presentación. “Variable temporal” puede significar una variable intermedia; no debe confundirse con esta API.

## Atlas completo del temario oficial

Las subsecciones siguientes cubren cada punto del programa. La etiqueta **Diplomatura** identifica el núcleo oficial; las decisiones de producción y el vínculo con FintechLab constituyen profundización y ampliación profesional.

### 1. motivación de la programación funcional en Java

**Problema e intuición.** motivación de la programación funcional en Java aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Java funcional, Streams y tiempo, motivación de la programación funcional en Java se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Preferir pipelines cortos, puros y legibles; volver a código imperativo cuando hace visible la intención. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica motivación de la programación funcional en Java con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 2. funciones como comportamiento

**Problema e intuición.** funciones como comportamiento aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Java funcional, Streams y tiempo, funciones como comportamiento se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Preferir pipelines cortos, puros y legibles; volver a código imperativo cuando hace visible la intención. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica funciones como comportamiento con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 3. interfaces funcionales

**Problema e intuición.** interfaces funcionales aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Java funcional, Streams y tiempo, interfaces funcionales se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Preferir pipelines cortos, puros y legibles; volver a código imperativo cuando hace visible la intención. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica interfaces funcionales con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 4. @FunctionalInterface

**Problema e intuición.** @FunctionalInterface aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Java funcional, Streams y tiempo, @FunctionalInterface se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Preferir pipelines cortos, puros y legibles; volver a código imperativo cuando hace visible la intención. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica @FunctionalInterface con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 5. Predicate, Function, Consumer, Supplier y variantes

**Problema e intuición.** Predicate, Function, Consumer, Supplier y variantes aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Java funcional, Streams y tiempo, Predicate, Function, Consumer, Supplier y variantes se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Preferir pipelines cortos, puros y legibles; volver a código imperativo cuando hace visible la intención. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica Predicate, Function, Consumer, Supplier y variantes con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 6. lambdas

**Problema e intuición.** lambdas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Java funcional, Streams y tiempo, lambdas se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Preferir pipelines cortos, puros y legibles; volver a código imperativo cuando hace visible la intención. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica lambdas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 7. captura de variables y effectively final

**Problema e intuición.** captura de variables y effectively final aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Una lambda solo captura variables locales finales o efectivamente finales porque el valor capturado debe ser estable respecto del alcance que ya terminó. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Preferir pipelines cortos, puros y legibles; volver a código imperativo cuando hace visible la intención. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica captura de variables y effectively final con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 8. referencias a métodos

**Problema e intuición.** referencias a métodos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Java funcional, Streams y tiempo, referencias a métodos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Preferir pipelines cortos, puros y legibles; volver a código imperativo cuando hace visible la intención. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica referencias a métodos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 9. métodos default y static en interfaces

**Problema e intuición.** métodos default y static en interfaces aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Java funcional, Streams y tiempo, métodos default y static en interfaces se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Preferir pipelines cortos, puros y legibles; volver a código imperativo cuando hace visible la intención. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica métodos default y static en interfaces con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 10. streams como pipeline

**Problema e intuición.** streams como pipeline aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Java funcional, Streams y tiempo, streams como pipeline se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Preferir pipelines cortos, puros y legibles; volver a código imperativo cuando hace visible la intención. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica streams como pipeline con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 11. fuente, operaciones intermedias y terminales

**Problema e intuición.** fuente, operaciones intermedias y terminales aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Java funcional, Streams y tiempo, fuente, operaciones intermedias y terminales se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Preferir pipelines cortos, puros y legibles; volver a código imperativo cuando hace visible la intención. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica fuente, operaciones intermedias y terminales con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 12. evaluación perezosa

**Problema e intuición.** evaluación perezosa aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Java funcional, Streams y tiempo, evaluación perezosa se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Preferir pipelines cortos, puros y legibles; volver a código imperativo cuando hace visible la intención. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica evaluación perezosa con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 13. map, filter, flatMap, sorted, distinct, limit y skip

**Problema e intuición.** map, filter, flatMap, sorted, distinct, limit y skip aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** flatMap transforma cada elemento en cero o más elementos y aplana un nivel, útil cuando una cuenta posee muchos movimientos. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Preferir pipelines cortos, puros y legibles; volver a código imperativo cuando hace visible la intención. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica map, filter, flatMap, sorted, distinct, limit y skip con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 14. anyMatch, allMatch, findFirst y reduce

**Problema e intuición.** anyMatch, allMatch, findFirst y reduce aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Java funcional, Streams y tiempo, anyMatch, allMatch, findFirst y reduce se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Preferir pipelines cortos, puros y legibles; volver a código imperativo cuando hace visible la intención. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica anyMatch, allMatch, findFirst y reduce con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 15. Collectors

**Problema e intuición.** Collectors aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Java funcional, Streams y tiempo, Collectors se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Preferir pipelines cortos, puros y legibles; volver a código imperativo cuando hace visible la intención. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica Collectors con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 16. groupingBy, partitioningBy, joining y toMap

**Problema e intuición.** groupingBy, partitioningBy, joining y toMap aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Java funcional, Streams y tiempo, groupingBy, partitioningBy, joining y toMap se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Preferir pipelines cortos, puros y legibles; volver a código imperativo cuando hace visible la intención. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica groupingBy, partitioningBy, joining y toMap con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 17. claves duplicadas

**Problema e intuición.** claves duplicadas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Java funcional, Streams y tiempo, claves duplicadas se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Preferir pipelines cortos, puros y legibles; volver a código imperativo cuando hace visible la intención. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica claves duplicadas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 18. Optional

**Problema e intuición.** Optional aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Optional modela un resultado posiblemente ausente, especialmente en retornos. Usarlo como campo universal, parámetro o sustituto de validación suele empeorar el modelo. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Preferir pipelines cortos, puros y legibles; volver a código imperativo cuando hace visible la intención. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica Optional con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 19. usos correctos e incorrectos de Optional

**Problema e intuición.** usos correctos e incorrectos de Optional aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Optional modela un resultado posiblemente ausente, especialmente en retornos. Usarlo como campo universal, parámetro o sustituto de validación suele empeorar el modelo. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Preferir pipelines cortos, puros y legibles; volver a código imperativo cuando hace visible la intención. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica usos correctos e incorrectos de Optional con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 20. API java.time

**Problema e intuición.** API java.time aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Instant representa un punto en la línea temporal UTC; LocalDateTime carece de zona y ZoneId aporta reglas regionales. La conversión debe ocurrir en un límite explícito. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Preferir pipelines cortos, puros y legibles; volver a código imperativo cuando hace visible la intención. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica API java.time con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 21. LocalDate, LocalDateTime, Instant, Duration, Period y ZoneId

**Problema e intuición.** LocalDate, LocalDateTime, Instant, Duration, Period y ZoneId aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Instant representa un punto en la línea temporal UTC; LocalDateTime carece de zona y ZoneId aporta reglas regionales. La conversión debe ocurrir en un límite explícito. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Preferir pipelines cortos, puros y legibles; volver a código imperativo cuando hace visible la intención. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica LocalDate, LocalDateTime, Instant, Duration, Period y ZoneId con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 22. inmutabilidad

**Problema e intuición.** inmutabilidad aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Java funcional, Streams y tiempo, inmutabilidad se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Preferir pipelines cortos, puros y legibles; volver a código imperativo cuando hace visible la intención. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica inmutabilidad con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 23. streams paralelos y por qué no deben utilizarse sin comprender el costo

**Problema e intuición.** streams paralelos y por qué no deben utilizarse sin comprender el costo aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Java funcional, Streams y tiempo, streams paralelos y por qué no deben utilizarse sin comprender el costo se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Preferir pipelines cortos, puros y legibles; volver a código imperativo cuando hace visible la intención. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica streams paralelos y por qué no deben utilizarse sin comprender el costo con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 24. efectos secundarios

**Problema e intuición.** efectos secundarios aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Java funcional, Streams y tiempo, efectos secundarios se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Preferir pipelines cortos, puros y legibles; volver a código imperativo cuando hace visible la intención. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica efectos secundarios con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 25. legibilidad frente a cadenas excesivas

**Problema e intuición.** legibilidad frente a cadenas excesivas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Java funcional, Streams y tiempo, legibilidad frente a cadenas excesivas se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Preferir pipelines cortos, puros y legibles; volver a código imperativo cuando hace visible la intención. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica legibilidad frente a cadenas excesivas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 26. variables locales o intermedias

**Problema e intuición.** variables locales o intermedias aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Java funcional, Streams y tiempo, variables locales o intermedias se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Preferir pipelines cortos, puros y legibles; volver a código imperativo cuando hace visible la intención. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica variables locales o intermedias con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 27. captura de variables en lambdas

**Problema e intuición.** captura de variables en lambdas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Una lambda solo captura variables locales finales o efectivamente finales porque el valor capturado debe ser estable respecto del alcance que ya terminó. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Preferir pipelines cortos, puros y legibles; volver a código imperativo cuando hace visible la intención. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica captura de variables en lambdas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 28. API temporal de fechas y horas

**Problema e intuición.** API temporal de fechas y horas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Java funcional, Streams y tiempo, API temporal de fechas y horas se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El compilador comprueba tipos y genera bytecode; streams construyen una canalización evaluada al llegar a una operación terminal, mientras Map delega acceso en comparación u orden de claves. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** calcula agrupaciones de movimientos sin sustituir las garantías transaccionales de la base. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Efectos secundarios, claves mutables o pipelines opacos introducen defectos difíciles de localizar. La regla es: Preferir pipelines cortos, puros y legibles; volver a código imperativo cuando hace visible la intención. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica API temporal de fechas y horas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

## Decisiones y alternativas

1. Define primero la invariante o el riesgo.
2. Compara una opción simple con otra más operable.
3. Registra el costo introducido: latencia, acoplamiento, complejidad o mantenimiento.
4. Elige: Preferir pipelines cortos, puros y legibles; volver a código imperativo cuando hace visible la intención.
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

La unidad enseña a convertir Java funcional, Streams y tiempo en decisiones verificables. La idea profesional no es acumular herramientas, sino conectar propósito, mecanismo, fallo, evidencia y costo.

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
