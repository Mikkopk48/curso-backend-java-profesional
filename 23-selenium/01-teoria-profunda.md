# Unidad 23 — Selenium y pruebas de navegador: teoría profunda

## Lugar dentro del recorrido

Esta unidad corresponde a la etapa **Interfaz mínima E2E**. Parte de la API inicial de la unidad 5 y añade una capacidad profesional sin volver a explicar Java, POO o CRUD desde cero.

## Conocimientos previos

- Controladores, servicios y repositorios básicos.
- Lectura de Java y ejecución de una aplicación Spring Boot.
- Conceptos recuperados en <code>00-objetivos-y-ruta.md</code>.

## Objetivos observables

Al terminar, podrás explicar el riesgo que resuelve cada concepto, seguir su funcionamiento, aplicarlo a FintechLab, escribir evidencia y defender cuándo no conviene usarlo.

## Problema central

Una prueba de navegador valida el flujo visto por el usuario y paga un costo alto de ejecución. En un ejercicio pequeño puede parecer suficiente memorizar una API. En producción importan además concurrencia, datos inválidos, latencia, compatibilidad, trazabilidad y recuperación.

## Modelo mental y analogía

Es un robot que usa la interfaz como una persona, no un atajo interno.

**Límite:** El robot no juzga usabilidad y una espera mal diseñada produce inestabilidad.

## Funcionamiento técnico progresivo

WebDriver envía comandos normalizados al driver del navegador, que localiza elementos y ejecuta interacciones en una página real. El recorrido útil siempre contiene cinco preguntas: quién inicia, qué cruza la frontera, qué estado participa, dónde puede fallar y qué evidencia queda.

~~~java
wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-testid='transfer-submit']"))).click();
wait.until(ExpectedConditions.textToBePresentInElementLocated(
    By.cssSelector("[role='status']"), "Transferencia registrada"));
~~~

El ejemplo es deliberadamente pequeño. No es la solución de los ejercicios: muestra vocabulario y forma, mientras las consignas cambian datos, restricciones y estructura.

## Explicación conectada: el navegador como frontera costosa

Selenium WebDriver envía comandos al driver, que controla un navegador real. Valida HTML, JavaScript, navegación y API juntos, pero es más lento y tiene más causas de fallo que una unidad. Por eso cubre pocos recorridos críticos.

Localiza primero por ID estable o atributo data-testid; name puede servir en formularios; CSS suele ser claro; XPath es útil para relaciones difíciles, pero expresiones ligadas al árbol visual son frágiles. No localices por texto traducible si no es parte del contrato.

Después de click, la página puede cambiar asincrónicamente. Implicit wait afecta toda búsqueda y puede combinarse de forma confusa. Explicit wait observa una condición: elemento visible, clickable o texto presente. <code>Thread.sleep</code> espera siempre y aun así falla si la máquina tarda más.

Page Object encapsula localizadores y acciones de una página; no debería contener assertions de negocio ni reproducir toda la UI. Component objects modelan widgets compartidos. Tests preparan datos por API o fixture, usan un usuario/tenant aislado y limpian. Crear todo por UI alarga y acopla.

Ante fallo, captura screenshot, DOM, console y correlation ID sin secretos. Headless facilita CI, pero conserva al menos una comparación con navegador visible. Paralelizar exige drivers y datos aislados. Reintentar en verde puede ocultar flakiness; primero clasifica causa.

Selenium no reemplaza tests de accesibilidad: integra herramientas que inspeccionen roles, nombre accesible y contraste. FintechLab usa una UI mínima para crear transferencia; saldos y reglas se cubren más abajo.

## Atlas completo del temario oficial

Las subsecciones siguientes cubren cada punto del programa. La etiqueta **Diplomatura** identifica el núcleo oficial; las decisiones de producción y el vínculo con FintechLab constituyen profundización y ampliación profesional.

### 1. WebDriver

**Problema e intuición.** WebDriver aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Selenium y pruebas de navegador, WebDriver se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** WebDriver envía comandos normalizados al driver del navegador, que localiza elementos y ejecuta interacciones en una página real. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** automatiza un flujo crítico sobre una interfaz didáctica mínima. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Selectores frágiles, estado compartido y esperas fijas provocan flakiness. La regla es: Reservar E2E para recorridos críticos; usar localizadores estables y esperas explícitas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica WebDriver con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 2. funcionamiento cliente-driver-navegador

**Problema e intuición.** funcionamiento cliente-driver-navegador aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Selenium y pruebas de navegador, funcionamiento cliente-driver-navegador se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** WebDriver envía comandos normalizados al driver del navegador, que localiza elementos y ejecuta interacciones en una página real. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** automatiza un flujo crítico sobre una interfaz didáctica mínima. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Selectores frágiles, estado compartido y esperas fijas provocan flakiness. La regla es: Reservar E2E para recorridos críticos; usar localizadores estables y esperas explícitas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica funcionamiento cliente-driver-navegador con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 3. configuración

**Problema e intuición.** configuración aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Selenium y pruebas de navegador, configuración se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** WebDriver envía comandos normalizados al driver del navegador, que localiza elementos y ejecuta interacciones en una página real. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** automatiza un flujo crítico sobre una interfaz didáctica mínima. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Selectores frágiles, estado compartido y esperas fijas provocan flakiness. La regla es: Reservar E2E para recorridos críticos; usar localizadores estables y esperas explícitas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica configuración con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 4. localizadores

**Problema e intuición.** localizadores aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Selenium y pruebas de navegador, localizadores se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** WebDriver envía comandos normalizados al driver del navegador, que localiza elementos y ejecuta interacciones en una página real. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** automatiza un flujo crítico sobre una interfaz didáctica mínima. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Selectores frágiles, estado compartido y esperas fijas provocan flakiness. La regla es: Reservar E2E para recorridos críticos; usar localizadores estables y esperas explícitas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica localizadores con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 5. id, name, CSS selector y XPath

**Problema e intuición.** id, name, CSS selector y XPath aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Selenium y pruebas de navegador, id, name, CSS selector y XPath se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** WebDriver envía comandos normalizados al driver del navegador, que localiza elementos y ejecuta interacciones en una página real. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** automatiza un flujo crítico sobre una interfaz didáctica mínima. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Selectores frágiles, estado compartido y esperas fijas provocan flakiness. La regla es: Reservar E2E para recorridos críticos; usar localizadores estables y esperas explícitas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica id, name, CSS selector y XPath con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 6. selección de localizadores estables

**Problema e intuición.** selección de localizadores estables aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Selenium y pruebas de navegador, selección de localizadores estables se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** WebDriver envía comandos normalizados al driver del navegador, que localiza elementos y ejecuta interacciones en una página real. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** automatiza un flujo crítico sobre una interfaz didáctica mínima. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Selectores frágiles, estado compartido y esperas fijas provocan flakiness. La regla es: Reservar E2E para recorridos críticos; usar localizadores estables y esperas explícitas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica selección de localizadores estables con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 7. interacción con formularios

**Problema e intuición.** interacción con formularios aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Selenium y pruebas de navegador, interacción con formularios se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** WebDriver envía comandos normalizados al driver del navegador, que localiza elementos y ejecuta interacciones en una página real. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** automatiza un flujo crítico sobre una interfaz didáctica mínima. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Selectores frágiles, estado compartido y esperas fijas provocan flakiness. La regla es: Reservar E2E para recorridos críticos; usar localizadores estables y esperas explícitas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica interacción con formularios con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 8. navegación

**Problema e intuición.** navegación aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Selenium y pruebas de navegador, navegación se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** WebDriver envía comandos normalizados al driver del navegador, que localiza elementos y ejecuta interacciones en una página real. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** automatiza un flujo crítico sobre una interfaz didáctica mínima. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Selectores frágiles, estado compartido y esperas fijas provocan flakiness. La regla es: Reservar E2E para recorridos críticos; usar localizadores estables y esperas explícitas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica navegación con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 9. ventanas y alertas

**Problema e intuición.** ventanas y alertas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Selenium y pruebas de navegador, ventanas y alertas se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** WebDriver envía comandos normalizados al driver del navegador, que localiza elementos y ejecuta interacciones en una página real. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** automatiza un flujo crítico sobre una interfaz didáctica mínima. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Selectores frágiles, estado compartido y esperas fijas provocan flakiness. La regla es: Reservar E2E para recorridos críticos; usar localizadores estables y esperas explícitas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica ventanas y alertas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 10. waits implícitos y explícitos

**Problema e intuición.** waits implícitos y explícitos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Una espera fija adivina cuánto tardará el sistema. Una espera explícita observa una condición concreta y finaliza tan pronto como se cumple. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** WebDriver envía comandos normalizados al driver del navegador, que localiza elementos y ejecuta interacciones en una página real. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** automatiza un flujo crítico sobre una interfaz didáctica mínima. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Selectores frágiles, estado compartido y esperas fijas provocan flakiness. La regla es: Reservar E2E para recorridos críticos; usar localizadores estables y esperas explícitas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica waits implícitos y explícitos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 11. condiciones de espera

**Problema e intuición.** condiciones de espera aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Selenium y pruebas de navegador, condiciones de espera se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** WebDriver envía comandos normalizados al driver del navegador, que localiza elementos y ejecuta interacciones en una página real. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** automatiza un flujo crítico sobre una interfaz didáctica mínima. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Selectores frágiles, estado compartido y esperas fijas provocan flakiness. La regla es: Reservar E2E para recorridos críticos; usar localizadores estables y esperas explícitas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica condiciones de espera con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 12. por qué Thread.sleep produce pruebas frágiles

**Problema e intuición.** por qué Thread.sleep produce pruebas frágiles aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Una espera fija adivina cuánto tardará el sistema. Una espera explícita observa una condición concreta y finaliza tan pronto como se cumple. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** WebDriver envía comandos normalizados al driver del navegador, que localiza elementos y ejecuta interacciones en una página real. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** automatiza un flujo crítico sobre una interfaz didáctica mínima. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Selectores frágiles, estado compartido y esperas fijas provocan flakiness. La regla es: Reservar E2E para recorridos críticos; usar localizadores estables y esperas explícitas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica por qué Thread.sleep produce pruebas frágiles con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 13. Page Object Model

**Problema e intuición.** Page Object Model aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Selenium y pruebas de navegador, Page Object Model se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** WebDriver envía comandos normalizados al driver del navegador, que localiza elementos y ejecuta interacciones en una página real. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** automatiza un flujo crítico sobre una interfaz didáctica mínima. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Selectores frágiles, estado compartido y esperas fijas provocan flakiness. La regla es: Reservar E2E para recorridos críticos; usar localizadores estables y esperas explícitas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica Page Object Model con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 14. componentes reutilizables

**Problema e intuición.** componentes reutilizables aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Selenium y pruebas de navegador, componentes reutilizables se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** WebDriver envía comandos normalizados al driver del navegador, que localiza elementos y ejecuta interacciones en una página real. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** automatiza un flujo crítico sobre una interfaz didáctica mínima. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Selectores frágiles, estado compartido y esperas fijas provocan flakiness. La regla es: Reservar E2E para recorridos críticos; usar localizadores estables y esperas explícitas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica componentes reutilizables con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 15. datos de prueba

**Problema e intuición.** datos de prueba aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Selenium y pruebas de navegador, datos de prueba se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** WebDriver envía comandos normalizados al driver del navegador, que localiza elementos y ejecuta interacciones en una página real. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** automatiza un flujo crítico sobre una interfaz didáctica mínima. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Selectores frágiles, estado compartido y esperas fijas provocan flakiness. La regla es: Reservar E2E para recorridos críticos; usar localizadores estables y esperas explícitas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica datos de prueba con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 16. preparación y limpieza

**Problema e intuición.** preparación y limpieza aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Selenium y pruebas de navegador, preparación y limpieza se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** WebDriver envía comandos normalizados al driver del navegador, que localiza elementos y ejecuta interacciones en una página real. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** automatiza un flujo crítico sobre una interfaz didáctica mínima. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Selectores frágiles, estado compartido y esperas fijas provocan flakiness. La regla es: Reservar E2E para recorridos críticos; usar localizadores estables y esperas explícitas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica preparación y limpieza con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 17. screenshots

**Problema e intuición.** screenshots aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Selenium y pruebas de navegador, screenshots se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** WebDriver envía comandos normalizados al driver del navegador, que localiza elementos y ejecuta interacciones en una página real. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** automatiza un flujo crítico sobre una interfaz didáctica mínima. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Selectores frágiles, estado compartido y esperas fijas provocan flakiness. La regla es: Reservar E2E para recorridos críticos; usar localizadores estables y esperas explícitas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica screenshots con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 18. logs

**Problema e intuición.** logs aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Selenium y pruebas de navegador, logs se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** WebDriver envía comandos normalizados al driver del navegador, que localiza elementos y ejecuta interacciones en una página real. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** automatiza un flujo crítico sobre una interfaz didáctica mínima. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Selectores frágiles, estado compartido y esperas fijas provocan flakiness. La regla es: Reservar E2E para recorridos críticos; usar localizadores estables y esperas explícitas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica logs con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 19. ejecución headless

**Problema e intuición.** ejecución headless aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Selenium y pruebas de navegador, ejecución headless se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** WebDriver envía comandos normalizados al driver del navegador, que localiza elementos y ejecuta interacciones en una página real. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** automatiza un flujo crítico sobre una interfaz didáctica mínima. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Selectores frágiles, estado compartido y esperas fijas provocan flakiness. La regla es: Reservar E2E para recorridos críticos; usar localizadores estables y esperas explícitas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica ejecución headless con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 20. ejecución en CI

**Problema e intuición.** ejecución en CI aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Selenium y pruebas de navegador, ejecución en CI se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** WebDriver envía comandos normalizados al driver del navegador, que localiza elementos y ejecuta interacciones en una página real. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** automatiza un flujo crítico sobre una interfaz didáctica mínima. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Selectores frágiles, estado compartido y esperas fijas provocan flakiness. La regla es: Reservar E2E para recorridos críticos; usar localizadores estables y esperas explícitas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica ejecución en CI con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 21. paralelización

**Problema e intuición.** paralelización aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Selenium y pruebas de navegador, paralelización se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** WebDriver envía comandos normalizados al driver del navegador, que localiza elementos y ejecuta interacciones en una página real. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** automatiza un flujo crítico sobre una interfaz didáctica mínima. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Selectores frágiles, estado compartido y esperas fijas provocan flakiness. La regla es: Reservar E2E para recorridos críticos; usar localizadores estables y esperas explícitas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica paralelización con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 22. aislamiento

**Problema e intuición.** aislamiento aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Selenium y pruebas de navegador, aislamiento se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** WebDriver envía comandos normalizados al driver del navegador, que localiza elementos y ejecuta interacciones en una página real. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** automatiza un flujo crítico sobre una interfaz didáctica mínima. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Selectores frágiles, estado compartido y esperas fijas provocan flakiness. La regla es: Reservar E2E para recorridos críticos; usar localizadores estables y esperas explícitas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica aislamiento con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 23. flakiness

**Problema e intuición.** flakiness aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Selenium y pruebas de navegador, flakiness se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** WebDriver envía comandos normalizados al driver del navegador, que localiza elementos y ejecuta interacciones en una página real. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** automatiza un flujo crítico sobre una interfaz didáctica mínima. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Selectores frágiles, estado compartido y esperas fijas provocan flakiness. La regla es: Reservar E2E para recorridos críticos; usar localizadores estables y esperas explícitas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica flakiness con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 24. reintentos y por qué pueden ocultar problemas

**Problema e intuición.** reintentos y por qué pueden ocultar problemas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Selenium y pruebas de navegador, reintentos y por qué pueden ocultar problemas se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** WebDriver envía comandos normalizados al driver del navegador, que localiza elementos y ejecuta interacciones en una página real. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** automatiza un flujo crítico sobre una interfaz didáctica mínima. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Selectores frágiles, estado compartido y esperas fijas provocan flakiness. La regla es: Reservar E2E para recorridos críticos; usar localizadores estables y esperas explícitas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica reintentos y por qué pueden ocultar problemas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 25. accesibilidad como ampliación

**Problema e intuición.** accesibilidad como ampliación aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Selenium y pruebas de navegador, accesibilidad como ampliación se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** WebDriver envía comandos normalizados al driver del navegador, que localiza elementos y ejecuta interacciones en una página real. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** automatiza un flujo crítico sobre una interfaz didáctica mínima. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Selectores frágiles, estado compartido y esperas fijas provocan flakiness. La regla es: Reservar E2E para recorridos críticos; usar localizadores estables y esperas explícitas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica accesibilidad como ampliación con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 26. límites de Selenium

**Problema e intuición.** límites de Selenium aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Selenium y pruebas de navegador, límites de Selenium se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** WebDriver envía comandos normalizados al driver del navegador, que localiza elementos y ejecuta interacciones en una página real. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** automatiza un flujo crítico sobre una interfaz didáctica mínima. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Selectores frágiles, estado compartido y esperas fijas provocan flakiness. La regla es: Reservar E2E para recorridos críticos; usar localizadores estables y esperas explícitas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica límites de Selenium con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 27. equilibrio entre tests de UI y tests más bajos

**Problema e intuición.** equilibrio entre tests de UI y tests más bajos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Selenium y pruebas de navegador, equilibrio entre tests de UI y tests más bajos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** WebDriver envía comandos normalizados al driver del navegador, que localiza elementos y ejecuta interacciones en una página real. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** automatiza un flujo crítico sobre una interfaz didáctica mínima. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Selectores frágiles, estado compartido y esperas fijas provocan flakiness. La regla es: Reservar E2E para recorridos críticos; usar localizadores estables y esperas explícitas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica equilibrio entre tests de UI y tests más bajos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

## Decisiones y alternativas

1. Define primero la invariante o el riesgo.
2. Compara una opción simple con otra más operable.
3. Registra el costo introducido: latencia, acoplamiento, complejidad o mantenimiento.
4. Elige: Reservar E2E para recorridos críticos; usar localizadores estables y esperas explícitas.
5. Documenta qué dato futuro haría cambiar la decisión.

## Errores frecuentes

- Copiar una anotación sin conocer quién la interpreta ni cuándo.
- Confundir el caso feliz con el contrato completo.
- Añadir infraestructura sin una necesidad observable.
- Escribir un test que replica la implementación en vez de cubrir riesgo.
- Registrar datos sensibles para facilitar debugging.
- Afirmar una garantía sin una restricción, prueba o medición que la sostenga.

## Qué ocurre en producción

Selectores frágiles, estado compartido y esperas fijas provocan flakiness. Un profesional diseña para degradación, documenta límites, mide antes de optimizar y deja una ruta de recuperación. FintechLab usa datos ficticios y no debe conectarse con bancos ni procesar dinero real.

## Cómo reconocer que realmente lo entendiste

- Puedes explicarlo sin depender del nombre de una anotación.
- Puedes dibujar el recorrido y marcar al menos tres fallos.
- Puedes cambiar el ejemplo sin copiarlo.
- Puedes escribir una prueba que falla cuando se rompe la regla.
- Puedes nombrar una alternativa y justificar el trade-off.

## Resumen sencillo

La unidad enseña a convertir Selenium y pruebas de navegador en decisiones verificables. La idea profesional no es acumular herramientas, sino conectar propósito, mecanismo, fallo, evidencia y costo.

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
