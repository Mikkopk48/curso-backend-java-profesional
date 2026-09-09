# Unidad 8 — Testing de integración y E2E: teoría profunda

## Lugar dentro del recorrido

Esta unidad corresponde a la etapa **Flujo real con MySQL de prueba**. Parte de la API inicial de la unidad 5 y añade una capacidad profesional sin volver a explicar Java, POO o CRUD desde cero.

## Conocimientos previos

- Controladores, servicios y repositorios básicos.
- Lectura de Java y ejecución de una aplicación Spring Boot.
- Conceptos recuperados en <code>00-objetivos-y-ruta.md</code>.

## Objetivos observables

Al terminar, podrás explicar el riesgo que resuelve cada concepto, seguir su funcionamiento, aplicarlo a FintechLab, escribir evidencia y defender cuándo no conviene usarlo.

## Problema central

Cada nivel de prueba compra confianza distinta a un costo distinto. En un ejercicio pequeño puede parecer suficiente memorizar una API. En producción importan además concurrencia, datos inválidos, latencia, compatibilidad, trazabilidad y recuperación.

## Modelo mental y analogía

Probar piezas, subsistemas y el vehículo completo responde preguntas diferentes.

**Límite:** Las capas de la pirámide no son porcentajes universales; dependen del riesgo y la arquitectura.

## Funcionamiento técnico progresivo

El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. El recorrido útil siempre contiene cinco preguntas: quién inicia, qué cruza la frontera, qué estado participa, dónde puede fallar y qué evidencia queda.

~~~java
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransferFlowIT {
    @Container static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.4.11");
    // La prueba usa HTTP real, Spring real y MySQL desechable.
}
~~~

El ejemplo es deliberadamente pequeño. No es la solución de los ejercicios: muestra vocabulario y forma, mientras las consignas cambian datos, restricciones y estructura.

## Explicación conectada: elegir el alcance de integración

### Un vocabulario para no mockear todo

Un slice test carga una porción de Spring: <code>@WebMvcTest</code> se concentra en MVC y <code>@DataJpaTest</code> en persistencia. <code>@SpringBootTest</code> crea el contexto completo de la aplicación. Una prueba de componente puede incluir API, dominio y base, pero sustituir servicios externos. Una prueba de contrato verifica que productor y consumidor comparten formato y semántica. E2E atraviesa la interfaz y dependencias necesarias del recorrido.

MockMvc simula la interacción HTTP dentro del proceso: ejecuta filtros, mapping, conversión y controller sin abrir un puerto. RANDOM_PORT abre un servidor real y utiliza un cliente HTTP. Elige el menor alcance que todavía pueda reproducir el riesgo. Si el defecto está en una migración MySQL, un mock de repositorio no puede encontrarlo.

### Base de memoria o motor real

H2 puede acelerar casos, pero su dialecto, tipos, locks e índices no equivalen a MySQL. Una consulta que pasa en H2 puede fallar en MySQL. Testcontainers crea un MySQL de la versión declarada, ejecuta Flyway y destruye el entorno. Cuesta más tiempo, pero compra evidencia sobre el motor real.

<code>@DataJpaTest</code> suele envolver cada test en transacción y revertir. Eso ayuda al aislamiento, pero puede ocultar comportamiento de commit. Para outbox o concurrencia necesitas controlar transacciones explícitamente. Datos únicos y limpieza por test evitan dependencia de orden.

### Dependencias HTTP con WireMock

Mockear la interfaz Feign no comprueba serialización, path, headers ni ErrorDecoder. WireMock abre un servidor HTTP controlado: programa 200, 404, 503, demora o JSON inválido, y verifica la request. Así se prueba el límite sin ejecutar el servicio remoto completo.

Fixtures representan escenarios; builders permiten mostrar solo valores relevantes. Una fixture global enorme es difícil de entender. Evita timestamps y UUID impredecibles cuando forman parte de la afirmación; inyecta Clock o captura la respuesta.

### Flakiness y estrategia

Una prueba flaky alterna rojo y verde sin cambio de código. Reintentarla oculta el síntoma. Investiga reloj, puertos, procesos, datos, orden, carrera o recursos insuficientes. Registra seed y entorno cuando uses generación aleatoria.

La estrategia de FintechLab asigna: dominio monetario a unidades, MVC a slice, repositorios y migraciones a MySQL Testcontainers, Feign a WireMock y uno o dos flujos críticos a E2E. Cada nivel responde una pregunta y documenta lo que queda fuera.

## Atlas completo del temario oficial

Las subsecciones siguientes cubren cada punto del programa. La etiqueta **Diplomatura** identifica el núcleo oficial; las decisiones de producción y el vínculo con FintechLab constituyen profundización y ampliación profesional.

### 1. test unitario, slice test, integración, contrato, componente y E2E

**Problema e intuición.** test unitario, slice test, integración, contrato, componente y E2E aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing de integración y E2E, test unitario, slice test, integración, contrato, componente y E2E se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Usar la prueba más pequeña que incluya todos los componentes capaces de causar el fallo investigado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica test unitario, slice test, integración, contrato, componente y E2E con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 2. qué componentes participan en cada nivel

**Problema e intuición.** qué componentes participan en cada nivel aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing de integración y E2E, qué componentes participan en cada nivel se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Usar la prueba más pequeña que incluya todos los componentes capaces de causar el fallo investigado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica qué componentes participan en cada nivel con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 3. @SpringBootTest

**Problema e intuición.** @SpringBootTest aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing de integración y E2E, @SpringBootTest se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Usar la prueba más pequeña que incluya todos los componentes capaces de causar el fallo investigado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica @SpringBootTest con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 4. @WebMvcTest

**Problema e intuición.** @WebMvcTest aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing de integración y E2E, @WebMvcTest se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Usar la prueba más pequeña que incluya todos los componentes capaces de causar el fallo investigado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica @WebMvcTest con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 5. @DataJpaTest

**Problema e intuición.** @DataJpaTest aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing de integración y E2E, @DataJpaTest se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Usar la prueba más pequeña que incluya todos los componentes capaces de causar el fallo investigado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica @DataJpaTest con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 6. MockMvc

**Problema e intuición.** MockMvc aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Los dobles controlan colaboraciones: un stub devuelve datos preparados, un mock además verifica interacciones y un fake implementa una versión simplificada con comportamiento real. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Usar la prueba más pequeña que incluya todos los componentes capaces de causar el fallo investigado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica MockMvc con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 7. cliente HTTP de prueba compatible con la pila elegida

**Problema e intuición.** cliente HTTP de prueba compatible con la pila elegida aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing de integración y E2E, cliente HTTP de prueba compatible con la pila elegida se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Usar la prueba más pequeña que incluya todos los componentes capaces de causar el fallo investigado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica cliente HTTP de prueba compatible con la pila elegida con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 8. contexto de Spring

**Problema e intuición.** contexto de Spring aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing de integración y E2E, contexto de Spring se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Usar la prueba más pequeña que incluya todos los componentes capaces de causar el fallo investigado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica contexto de Spring con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 9. base de datos en memoria frente a la base real

**Problema e intuición.** base de datos en memoria frente a la base real aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing de integración y E2E, base de datos en memoria frente a la base real se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Usar la prueba más pequeña que incluya todos los componentes capaces de causar el fallo investigado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica base de datos en memoria frente a la base real con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 10. Testcontainers con MySQL

**Problema e intuición.** Testcontainers con MySQL aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Testcontainers inicia dependencias reales y desechables para reducir diferencias con producción, a cambio de más tiempo y necesidad de un runtime de contenedores. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Usar la prueba más pequeña que incluya todos los componentes capaces de causar el fallo investigado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica Testcontainers con MySQL con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 11. WireMock para dependencias HTTP

**Problema e intuición.** WireMock para dependencias HTTP aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Los dobles controlan colaboraciones: un stub devuelve datos preparados, un mock además verifica interacciones y un fake implementa una versión simplificada con comportamiento real. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Usar la prueba más pequeña que incluya todos los componentes capaces de causar el fallo investigado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica WireMock para dependencias HTTP con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 12. fixtures y builders de datos

**Problema e intuición.** fixtures y builders de datos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing de integración y E2E, fixtures y builders de datos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Usar la prueba más pequeña que incluya todos los componentes capaces de causar el fallo investigado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica fixtures y builders de datos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 13. limpieza y aislamiento entre pruebas

**Problema e intuición.** limpieza y aislamiento entre pruebas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing de integración y E2E, limpieza y aislamiento entre pruebas se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Usar la prueba más pequeña que incluya todos los componentes capaces de causar el fallo investigado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica limpieza y aislamiento entre pruebas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 14. transacciones en tests

**Problema e intuición.** transacciones en tests aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** La transacción debe rodear el caso de uso que preserva una invariante. Una anotación en un método no corrige llamadas que evitan el proxy ni efectos remotos no transaccionales. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Usar la prueba más pequeña que incluya todos los componentes capaces de causar el fallo investigado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica transacciones en tests con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 15. puertos aleatorios

**Problema e intuición.** puertos aleatorios aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing de integración y E2E, puertos aleatorios se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Usar la prueba más pequeña que incluya todos los componentes capaces de causar el fallo investigado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica puertos aleatorios con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 16. prueba de JSON

**Problema e intuición.** prueba de JSON aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing de integración y E2E, prueba de JSON se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Usar la prueba más pequeña que incluya todos los componentes capaces de causar el fallo investigado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica prueba de JSON con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 17. contratos entre servicios

**Problema e intuición.** contratos entre servicios aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing de integración y E2E, contratos entre servicios se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Usar la prueba más pequeña que incluya todos los componentes capaces de causar el fallo investigado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica contratos entre servicios con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 18. costo y velocidad de cada nivel

**Problema e intuición.** costo y velocidad de cada nivel aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing de integración y E2E, costo y velocidad de cada nivel se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Usar la prueba más pequeña que incluya todos los componentes capaces de causar el fallo investigado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica costo y velocidad de cada nivel con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 19. mocks en los límites correctos

**Problema e intuición.** mocks en los límites correctos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Los dobles controlan colaboraciones: un stub devuelve datos preparados, un mock además verifica interacciones y un fake implementa una versión simplificada con comportamiento real. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Usar la prueba más pequeña que incluya todos los componentes capaces de causar el fallo investigado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica mocks en los límites correctos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 20. falsos positivos

**Problema e intuición.** falsos positivos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing de integración y E2E, falsos positivos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Usar la prueba más pequeña que incluya todos los componentes capaces de causar el fallo investigado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica falsos positivos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 21. datos de prueba

**Problema e intuición.** datos de prueba aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing de integración y E2E, datos de prueba se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Usar la prueba más pequeña que incluya todos los componentes capaces de causar el fallo investigado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica datos de prueba con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 22. flakiness

**Problema e intuición.** flakiness aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing de integración y E2E, flakiness se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Usar la prueba más pequeña que incluya todos los componentes capaces de causar el fallo investigado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica flakiness con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 23. diseño de una estrategia de pruebas

**Problema e intuición.** diseño de una estrategia de pruebas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing de integración y E2E, diseño de una estrategia de pruebas se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Usar la prueba más pequeña que incluya todos los componentes capaces de causar el fallo investigado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica diseño de una estrategia de pruebas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

## Decisiones y alternativas

1. Define primero la invariante o el riesgo.
2. Compara una opción simple con otra más operable.
3. Registra el costo introducido: latencia, acoplamiento, complejidad o mantenimiento.
4. Elige: Usar la prueba más pequeña que incluya todos los componentes capaces de causar el fallo investigado.
5. Documenta qué dato futuro haría cambiar la decisión.

## Errores frecuentes

- Copiar una anotación sin conocer quién la interpreta ni cuándo.
- Confundir el caso feliz con el contrato completo.
- Añadir infraestructura sin una necesidad observable.
- Escribir un test que replica la implementación en vez de cubrir riesgo.
- Registrar datos sensibles para facilitar debugging.
- Afirmar una garantía sin una restricción, prueba o medición que la sostenga.

## Qué ocurre en producción

Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. Un profesional diseña para degradación, documenta límites, mide antes de optimizar y deja una ruta de recuperación. FintechLab usa datos ficticios y no debe conectarse con bancos ni procesar dinero real.

## Cómo reconocer que realmente lo entendiste

- Puedes explicarlo sin depender del nombre de una anotación.
- Puedes dibujar el recorrido y marcar al menos tres fallos.
- Puedes cambiar el ejemplo sin copiarlo.
- Puedes escribir una prueba que falla cuando se rompe la regla.
- Puedes nombrar una alternativa y justificar el trade-off.

## Resumen sencillo

La unidad enseña a convertir Testing de integración y E2E en decisiones verificables. La idea profesional no es acumular herramientas, sino conectar propósito, mecanismo, fallo, evidencia y costo.

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
