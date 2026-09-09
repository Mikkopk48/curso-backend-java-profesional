# Unidad 27 — Evaluación integradora final: teoría profunda

## Lugar dentro del recorrido

Esta unidad corresponde a la etapa **Evaluación y defensa**. Parte de la API inicial de la unidad 5 y añade una capacidad profesional sin volver a explicar Java, POO o CRUD desde cero.

## Conocimientos previos

- Controladores, servicios y repositorios básicos.
- Lectura de Java y ejecución de una aplicación Spring Boot.
- Conceptos recuperados en <code>00-objetivos-y-ruta.md</code>.

## Objetivos observables

Al terminar, podrás explicar el riesgo que resuelve cada concepto, seguir su funcionamiento, aplicarlo a FintechLab, escribir evidencia y defender cuándo no conviene usarlo.

## Problema central

La evaluación mide transferencia: explicar, diagnosticar, construir y justificar bajo restricciones. En un ejercicio pequeño puede parecer suficiente memorizar una API. En producción importan además concurrencia, datos inválidos, latencia, compatibilidad, trazabilidad y recuperación.

## Modelo mental y analogía

Una guardia supervisada combina conocimiento, procedimiento y toma de decisiones.

**Límite:** Una prueba temporal no captura toda la capacidad profesional; por eso incluye evidencias y defensa.

## Funcionamiento técnico progresivo

Las consignas combinan competencias y la rúbrica observa resultados, razonamiento y evidencia sin prescribir una única implementación. El recorrido útil siempre contiene cinco preguntas: quién inicia, qué cruza la frontera, qué estado participa, dónde puede fallar y qué evidencia queda.

~~~text
La evaluación no pide memorizar anotaciones aisladas: pide explicar el riesgo,
diagnosticar evidencia y defender una decisión con límites explícitos.
~~~

El ejemplo es deliberadamente pequeño. No es la solución de los ejercicios: muestra vocabulario y forma, mientras las consignas cambian datos, restricciones y estructura.

## Explicación conectada: demostrar transferencia de conocimiento

La evaluación final no premia repetir definiciones. Combina explicación, predicción, debugging, diseño e implementación para comprobar si puedes trasladar principios a un caso nuevo. Antes de programar, lee todas las restricciones y crea una matriz requisito–evidencia.

En lectura de código, separa lo que Java garantiza de lo que supones sobre Spring, base o red. En debugging, ordena hipótesis por evidencia y cambia una variable. En diseño HTTP, expresa recurso, método, idempotencia, status y errores. En SQL, declara invariante, aislamiento, índice y plan. En testing, vincula cada prueba con riesgo.

La sección distribuida evalúa que no reintentes ciegamente, que reconozcas fallos parciales y que separes datos por ownership. CI/CD exige artefactos reproducibles y permisos. Git se resuelve en repositorio seguro. Map/Streams/MapStruct/SOLID se valoran por legibilidad y límites, no por usar la mayor cantidad de APIs. Selenium se reserva al flujo visible. Seguridad exige distinguir autenticación, autorización, firma, cifrado y claims.

Durante la defensa, una restricción puede cambiar. Explica el impacto antes de improvisar una tecnología. Reconocer que falta evidencia es mejor que inventar una garantía. La recuperación se organiza por competencia y usa una consigna nueva; la rúbrica no contiene respuestas ni código esperado.

## Atlas completo del temario oficial

Las subsecciones siguientes cubren cada punto del programa. La etiqueta **Diplomatura** identifica el núcleo oficial; las decisiones de producción y el vínculo con FintechLab constituyen profundización y ampliación profesional.

### 1. preguntas conceptuales

**Problema e intuición.** preguntas conceptuales aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Evaluación integradora final, preguntas conceptuales se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Las consignas combinan competencias y la rúbrica observa resultados, razonamiento y evidencia sin prescribir una única implementación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** obliga a defender decisiones y reconocer límites del sistema ficticio. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una respuesta convincente pero falsa puede ocultar una laguna crítica. La regla es: Valorar corrección, razonamiento, seguridad y capacidad de reconocer límites. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica preguntas conceptuales con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 2. lectura de código

**Problema e intuición.** lectura de código aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Evaluación integradora final, lectura de código se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Las consignas combinan competencias y la rúbrica observa resultados, razonamiento y evidencia sin prescribir una única implementación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** obliga a defender decisiones y reconocer límites del sistema ficticio. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una respuesta convincente pero falsa puede ocultar una laguna crítica. La regla es: Valorar corrección, razonamiento, seguridad y capacidad de reconocer límites. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica lectura de código con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 3. predicción de comportamiento

**Problema e intuición.** predicción de comportamiento aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Evaluación integradora final, predicción de comportamiento se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Las consignas combinan competencias y la rúbrica observa resultados, razonamiento y evidencia sin prescribir una única implementación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** obliga a defender decisiones y reconocer límites del sistema ficticio. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una respuesta convincente pero falsa puede ocultar una laguna crítica. La regla es: Valorar corrección, razonamiento, seguridad y capacidad de reconocer límites. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica predicción de comportamiento con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 4. debugging

**Problema e intuición.** debugging aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Evaluación integradora final, debugging se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Las consignas combinan competencias y la rúbrica observa resultados, razonamiento y evidencia sin prescribir una única implementación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** obliga a defender decisiones y reconocer límites del sistema ficticio. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una respuesta convincente pero falsa puede ocultar una laguna crítica. La regla es: Valorar corrección, razonamiento, seguridad y capacidad de reconocer límites. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica debugging con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 5. diseño de endpoints

**Problema e intuición.** diseño de endpoints aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Evaluación integradora final, diseño de endpoints se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Las consignas combinan competencias y la rúbrica observa resultados, razonamiento y evidencia sin prescribir una única implementación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** obliga a defender decisiones y reconocer límites del sistema ficticio. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una respuesta convincente pero falsa puede ocultar una laguna crítica. La regla es: Valorar corrección, razonamiento, seguridad y capacidad de reconocer límites. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica diseño de endpoints con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 6. SQL

**Problema e intuición.** SQL aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Evaluación integradora final, SQL se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Las consignas combinan competencias y la rúbrica observa resultados, razonamiento y evidencia sin prescribir una única implementación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** obliga a defender decisiones y reconocer límites del sistema ficticio. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una respuesta convincente pero falsa puede ocultar una laguna crítica. La regla es: Valorar corrección, razonamiento, seguridad y capacidad de reconocer límites. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica SQL con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 7. transacciones

**Problema e intuición.** transacciones aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** La transacción debe rodear el caso de uso que preserva una invariante. Una anotación en un método no corrige llamadas que evitan el proxy ni efectos remotos no transaccionales. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Las consignas combinan competencias y la rúbrica observa resultados, razonamiento y evidencia sin prescribir una única implementación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** obliga a defender decisiones y reconocer límites del sistema ficticio. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una respuesta convincente pero falsa puede ocultar una laguna crítica. La regla es: Valorar corrección, razonamiento, seguridad y capacidad de reconocer límites. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica transacciones con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 8. testing

**Problema e intuición.** testing aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Evaluación integradora final, testing se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Las consignas combinan competencias y la rúbrica observa resultados, razonamiento y evidencia sin prescribir una única implementación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** obliga a defender decisiones y reconocer límites del sistema ficticio. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una respuesta convincente pero falsa puede ocultar una laguna crítica. La regla es: Valorar corrección, razonamiento, seguridad y capacidad de reconocer límites. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica testing con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 9. microservicios

**Problema e intuición.** microservicios aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Evaluación integradora final, microservicios se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Las consignas combinan competencias y la rúbrica observa resultados, razonamiento y evidencia sin prescribir una única implementación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** obliga a defender decisiones y reconocer límites del sistema ficticio. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una respuesta convincente pero falsa puede ocultar una laguna crítica. La regla es: Valorar corrección, razonamiento, seguridad y capacidad de reconocer límites. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica microservicios con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 10. Feign

**Problema e intuición.** Feign aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Evaluación integradora final, Feign se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Las consignas combinan competencias y la rúbrica observa resultados, razonamiento y evidencia sin prescribir una única implementación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** obliga a defender decisiones y reconocer límites del sistema ficticio. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una respuesta convincente pero falsa puede ocultar una laguna crítica. La regla es: Valorar corrección, razonamiento, seguridad y capacidad de reconocer límites. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica Feign con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 11. Eureka

**Problema e intuición.** Eureka aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Eureka mantiene registros mediante renovaciones y cachés. La ausencia de heartbeat tarda en reflejarse; self-preservation evita expulsiones masivas ante pérdida de comunicación. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Las consignas combinan competencias y la rúbrica observa resultados, razonamiento y evidencia sin prescribir una única implementación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** obliga a defender decisiones y reconocer límites del sistema ficticio. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una respuesta convincente pero falsa puede ocultar una laguna crítica. La regla es: Valorar corrección, razonamiento, seguridad y capacidad de reconocer límites. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica Eureka con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 12. Config Server

**Problema e intuición.** Config Server aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Spring combina fuentes con una precedencia definida. Saber qué fuente ganó es parte del diagnóstico; una configuración remota también debe versionarse y auditarse. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Las consignas combinan competencias y la rúbrica observa resultados, razonamiento y evidencia sin prescribir una única implementación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** obliga a defender decisiones y reconocer límites del sistema ficticio. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una respuesta convincente pero falsa puede ocultar una laguna crítica. La regla es: Valorar corrección, razonamiento, seguridad y capacidad de reconocer límites. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica Config Server con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 13. CI/CD

**Problema e intuición.** CI/CD aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Evaluación integradora final, CI/CD se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Las consignas combinan competencias y la rúbrica observa resultados, razonamiento y evidencia sin prescribir una única implementación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** obliga a defender decisiones y reconocer límites del sistema ficticio. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una respuesta convincente pero falsa puede ocultar una laguna crítica. La regla es: Valorar corrección, razonamiento, seguridad y capacidad de reconocer límites. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica CI/CD con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 14. Git

**Problema e intuición.** Git aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Evaluación integradora final, Git se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Las consignas combinan competencias y la rúbrica observa resultados, razonamiento y evidencia sin prescribir una única implementación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** obliga a defender decisiones y reconocer límites del sistema ficticio. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una respuesta convincente pero falsa puede ocultar una laguna crítica. La regla es: Valorar corrección, razonamiento, seguridad y capacidad de reconocer límites. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica Git con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 15. MapStruct

**Problema e intuición.** MapStruct aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** MapStruct genera implementaciones en compilación, sin reflexión durante cada request. La generación visible facilita depuración, pero las reglas siguen necesitando revisión. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Las consignas combinan competencias y la rúbrica observa resultados, razonamiento y evidencia sin prescribir una única implementación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** obliga a defender decisiones y reconocer límites del sistema ficticio. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una respuesta convincente pero falsa puede ocultar una laguna crítica. La regla es: Valorar corrección, razonamiento, seguridad y capacidad de reconocer límites. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica MapStruct con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 16. Selenium

**Problema e intuición.** Selenium aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Evaluación integradora final, Selenium se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Las consignas combinan competencias y la rúbrica observa resultados, razonamiento y evidencia sin prescribir una única implementación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** obliga a defender decisiones y reconocer límites del sistema ficticio. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una respuesta convincente pero falsa puede ocultar una laguna crítica. La regla es: Valorar corrección, razonamiento, seguridad y capacidad de reconocer límites. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica Selenium con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 17. SOLID

**Problema e intuición.** SOLID aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** SOLID describe fuerzas de cambio, sustitución y dependencias. No exige una interfaz por clase ni una proliferación de capas. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Las consignas combinan competencias y la rúbrica observa resultados, razonamiento y evidencia sin prescribir una única implementación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** obliga a defender decisiones y reconocer límites del sistema ficticio. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una respuesta convincente pero falsa puede ocultar una laguna crítica. La regla es: Valorar corrección, razonamiento, seguridad y capacidad de reconocer límites. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica SOLID con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 18. seguridad y JWT

**Problema e intuición.** seguridad y JWT aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** JWT es un formato de claims que puede firmarse; firmar demuestra integridad y origen según la clave, pero no cifra el payload. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Las consignas combinan competencias y la rúbrica observa resultados, razonamiento y evidencia sin prescribir una única implementación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** obliga a defender decisiones y reconocer límites del sistema ficticio. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una respuesta convincente pero falsa puede ocultar una laguna crítica. La regla es: Valorar corrección, razonamiento, seguridad y capacidad de reconocer límites. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica seguridad y JWT con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 19. implementación práctica

**Problema e intuición.** implementación práctica aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Evaluación integradora final, implementación práctica se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Las consignas combinan competencias y la rúbrica observa resultados, razonamiento y evidencia sin prescribir una única implementación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** obliga a defender decisiones y reconocer límites del sistema ficticio. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una respuesta convincente pero falsa puede ocultar una laguna crítica. La regla es: Valorar corrección, razonamiento, seguridad y capacidad de reconocer límites. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica implementación práctica con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 20. defensa de decisiones

**Problema e intuición.** defensa de decisiones aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Evaluación integradora final, defensa de decisiones se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Las consignas combinan competencias y la rúbrica observa resultados, razonamiento y evidencia sin prescribir una única implementación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** obliga a defender decisiones y reconocer límites del sistema ficticio. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una respuesta convincente pero falsa puede ocultar una laguna crítica. La regla es: Valorar corrección, razonamiento, seguridad y capacidad de reconocer límites. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica defensa de decisiones con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 21. duración recomendada

**Problema e intuición.** duración recomendada aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Evaluación integradora final, duración recomendada se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Las consignas combinan competencias y la rúbrica observa resultados, razonamiento y evidencia sin prescribir una única implementación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** obliga a defender decisiones y reconocer límites del sistema ficticio. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una respuesta convincente pero falsa puede ocultar una laguna crítica. La regla es: Valorar corrección, razonamiento, seguridad y capacidad de reconocer límites. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica duración recomendada con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 22. reglas

**Problema e intuición.** reglas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Evaluación integradora final, reglas se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Las consignas combinan competencias y la rúbrica observa resultados, razonamiento y evidencia sin prescribir una única implementación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** obliga a defender decisiones y reconocer límites del sistema ficticio. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una respuesta convincente pero falsa puede ocultar una laguna crítica. La regla es: Valorar corrección, razonamiento, seguridad y capacidad de reconocer límites. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica reglas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 23. recursos permitidos

**Problema e intuición.** recursos permitidos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Evaluación integradora final, recursos permitidos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Las consignas combinan competencias y la rúbrica observa resultados, razonamiento y evidencia sin prescribir una única implementación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** obliga a defender decisiones y reconocer límites del sistema ficticio. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una respuesta convincente pero falsa puede ocultar una laguna crítica. La regla es: Valorar corrección, razonamiento, seguridad y capacidad de reconocer límites. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica recursos permitidos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 24. criterios de aprobación

**Problema e intuición.** criterios de aprobación aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Evaluación integradora final, criterios de aprobación se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Las consignas combinan competencias y la rúbrica observa resultados, razonamiento y evidencia sin prescribir una única implementación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** obliga a defender decisiones y reconocer límites del sistema ficticio. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una respuesta convincente pero falsa puede ocultar una laguna crítica. La regla es: Valorar corrección, razonamiento, seguridad y capacidad de reconocer límites. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica criterios de aprobación con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 25. rúbrica de 100 puntos

**Problema e intuición.** rúbrica de 100 puntos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Evaluación integradora final, rúbrica de 100 puntos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Las consignas combinan competencias y la rúbrica observa resultados, razonamiento y evidencia sin prescribir una única implementación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** obliga a defender decisiones y reconocer límites del sistema ficticio. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una respuesta convincente pero falsa puede ocultar una laguna crítica. La regla es: Valorar corrección, razonamiento, seguridad y capacidad de reconocer límites. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica rúbrica de 100 puntos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 26. niveles de desempeño

**Problema e intuición.** niveles de desempeño aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Evaluación integradora final, niveles de desempeño se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Las consignas combinan competencias y la rúbrica observa resultados, razonamiento y evidencia sin prescribir una única implementación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** obliga a defender decisiones y reconocer límites del sistema ficticio. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una respuesta convincente pero falsa puede ocultar una laguna crítica. La regla es: Valorar corrección, razonamiento, seguridad y capacidad de reconocer límites. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica niveles de desempeño con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 27. errores críticos

**Problema e intuición.** errores críticos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Evaluación integradora final, errores críticos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Las consignas combinan competencias y la rúbrica observa resultados, razonamiento y evidencia sin prescribir una única implementación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** obliga a defender decisiones y reconocer límites del sistema ficticio. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una respuesta convincente pero falsa puede ocultar una laguna crítica. La regla es: Valorar corrección, razonamiento, seguridad y capacidad de reconocer límites. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica errores críticos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 28. instancia de defensa oral

**Problema e intuición.** instancia de defensa oral aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Evaluación integradora final, instancia de defensa oral se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Las consignas combinan competencias y la rúbrica observa resultados, razonamiento y evidencia sin prescribir una única implementación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** obliga a defender decisiones y reconocer límites del sistema ficticio. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una respuesta convincente pero falsa puede ocultar una laguna crítica. La regla es: Valorar corrección, razonamiento, seguridad y capacidad de reconocer límites. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica instancia de defensa oral con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 29. recuperación pedagógica por competencias

**Problema e intuición.** recuperación pedagógica por competencias aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Evaluación integradora final, recuperación pedagógica por competencias se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Las consignas combinan competencias y la rúbrica observa resultados, razonamiento y evidencia sin prescribir una única implementación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** obliga a defender decisiones y reconocer límites del sistema ficticio. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una respuesta convincente pero falsa puede ocultar una laguna crítica. La regla es: Valorar corrección, razonamiento, seguridad y capacidad de reconocer límites. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica recuperación pedagógica por competencias con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

## Decisiones y alternativas

1. Define primero la invariante o el riesgo.
2. Compara una opción simple con otra más operable.
3. Registra el costo introducido: latencia, acoplamiento, complejidad o mantenimiento.
4. Elige: Valorar corrección, razonamiento, seguridad y capacidad de reconocer límites.
5. Documenta qué dato futuro haría cambiar la decisión.

## Errores frecuentes

- Copiar una anotación sin conocer quién la interpreta ni cuándo.
- Confundir el caso feliz con el contrato completo.
- Añadir infraestructura sin una necesidad observable.
- Escribir un test que replica la implementación en vez de cubrir riesgo.
- Registrar datos sensibles para facilitar debugging.
- Afirmar una garantía sin una restricción, prueba o medición que la sostenga.

## Qué ocurre en producción

Una respuesta convincente pero falsa puede ocultar una laguna crítica. Un profesional diseña para degradación, documenta límites, mide antes de optimizar y deja una ruta de recuperación. FintechLab usa datos ficticios y no debe conectarse con bancos ni procesar dinero real.

## Cómo reconocer que realmente lo entendiste

- Puedes explicarlo sin depender del nombre de una anotación.
- Puedes dibujar el recorrido y marcar al menos tres fallos.
- Puedes cambiar el ejemplo sin copiarlo.
- Puedes escribir una prueba que falla cuando se rompe la regla.
- Puedes nombrar una alternativa y justificar el trade-off.

## Resumen sencillo

La unidad enseña a convertir Evaluación integradora final en decisiones verificables. La idea profesional no es acumular herramientas, sino conectar propósito, mecanismo, fallo, evidencia y costo.

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
