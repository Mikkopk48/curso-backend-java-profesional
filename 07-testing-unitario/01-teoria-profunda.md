# Unidad 7 — Testing unitario: teoría profunda

## Lugar dentro del recorrido

Esta unidad corresponde a la etapa **Servicios de dominio comprobables**. Parte de la API inicial de la unidad 5 y añade una capacidad profesional sin volver a explicar Java, POO o CRUD desde cero.

## Conocimientos previos

- Controladores, servicios y repositorios básicos.
- Lectura de Java y ejecución de una aplicación Spring Boot.
- Conceptos recuperados en <code>00-objetivos-y-ruta.md</code>.

## Objetivos observables

Al terminar, podrás explicar el riesgo que resuelve cada concepto, seguir su funcionamiento, aplicarlo a FintechLab, escribir evidencia y defender cuándo no conviene usarlo.

## Problema central

Un test valioso reduce un riesgo concreto y documenta comportamiento observable. En un ejercicio pequeño puede parecer suficiente memorizar una API. En producción importan además concurrencia, datos inválidos, latencia, compatibilidad, trazabilidad y recuperación.

## Modelo mental y analogía

Es una alarma de humo: no evita el incendio ni garantiza todo el edificio, pero detecta una clase de peligro con rapidez.

**Límite:** El software no tiene un único sensor; un test aislado no demuestra integración, rendimiento ni seguridad.

## Funcionamiento técnico progresivo

El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. El recorrido útil siempre contiene cinco preguntas: quién inicia, qué cruza la frontera, qué estado participa, dónde puede fallar y qué evidencia queda.

~~~java
@Test
void rejectsTransferWhenAvailableBalanceIsInsufficient() {
    var account = Account.open("ARS", new BigDecimal("100.00"));
    assertThatThrownBy(() -> account.debit(new BigDecimal("100.01")))
        .isInstanceOf(InsufficientFundsException.class);
}
~~~

El ejemplo es deliberadamente pequeño. No es la solución de los ejercicios: muestra vocabulario y forma, mientras las consignas cambian datos, restricciones y estructura.

## Explicación conectada: pruebas que reducen riesgo

### Qué demuestra un test

Un test ejecuta un escenario y compara observaciones con una expectativa. Si pasa, aumenta confianza únicamente sobre ese escenario, esa configuración y esas afirmaciones. No prueba ausencia total de defectos. Empieza por el riesgo: en FintechLab interesa que una cuenta no quede negativa, que monedas incompatibles se rechacen y que repetir una intención no duplique movimientos.

Una prueba unitaria mantiene el sujeto pequeño y controla colaboraciones. Una integración comprueba piezas reales juntas. E2E cruza el recorrido del usuario. La pirámide recuerda que las pruebas amplias suelen ser más lentas y frágiles; no impone porcentajes universales.

### Arrange, Act, Assert

Arrange prepara datos y dobles; Act ejecuta una conducta; Assert observa resultado o estado. Un test debería fallar por una razón reconocible. Nombres como <code>rejectsTransferWhenAvailableBalanceIsInsufficient</code> explican contexto y consecuencia. Given–When–Then expresa la misma estructura con vocabulario de comportamiento.

JUnit Jupiter descubre métodos anotados, crea instancias, ejecuta callbacks y reporta resultados. <code>@BeforeEach</code> prepara aislamiento; <code>@BeforeAll</code> comparte recursos costosos y exige cuidado. <code>@ParameterizedTest</code> permite verificar una regla con varios bordes sin copiar tests. <code>assertAll</code> agrupa observaciones independientes; <code>assertThrows</code> exige además revisar tipo y mensaje/código estable cuando forma parte del contrato.

### Dobles y Mockito

Un dummy completa una firma; un stub entrega respuestas; un fake posee una implementación simplificada; un spy envuelve comportamiento real y un mock verifica mensajes. Mockito crea mocks y configura stubbing con <code>when</code>; <code>verify</code> comprueba interacción cuando esa interacción es parte del comportamiento. Verificar cada getter acopla el test a la implementación.

No mockees BigDecimal, colecciones ni objetos de valor. No mockees la clase bajo prueba. Un repositorio o cliente remoto sí es un límite útil en una unidad de servicio. Si necesitas muchos mocks y stubs para crear el sujeto, quizá la clase mezcla responsabilidades.

### Fragilidad, determinismo y cobertura

Reloj real, aleatoriedad, orden, red y estado compartido vuelven inestable un test. Inyecta Clock, fija datos y construye un objeto nuevo por escenario. Evita sleeps. Una prueba que solo afirma <code>notNull</code> puede pasar aunque la regla esencial esté rota; una prueba sin assertions puede no demostrar nada.

Cobertura de líneas muestra ejecución; cobertura de branches distingue caminos, pero ninguna evalúa la calidad de la afirmación. Mutation testing altera código y comprueba si la suite lo detecta: sirve para descubrir assertions débiles, no para convertir un porcentaje en objetivo absoluto.

Código testeable tiene reglas puras, dependencias explícitas, tiempo controlable y fronteras pequeñas. La IA puede proponer casos, pero debes revisar si inventó APIs, replicó implementación o ignoró concurrencia.

## Atlas completo del temario oficial

Las subsecciones siguientes cubren cada punto del programa. La etiqueta **Diplomatura** identifica el núcleo oficial; las decisiones de producción y el vínculo con FintechLab constituyen profundización y ampliación profesional.

### 1. propósito del testing

**Problema e intuición.** propósito del testing aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing unitario, propósito del testing se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Probar decisiones de negocio y bordes; usar dobles solo donde controlan una colaboración relevante. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica propósito del testing con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 2. riesgo, comportamiento y especificación

**Problema e intuición.** riesgo, comportamiento y especificación aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing unitario, riesgo, comportamiento y especificación se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Probar decisiones de negocio y bordes; usar dobles solo donde controlan una colaboración relevante. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica riesgo, comportamiento y especificación con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 3. pirámide de pruebas y límites de la metáfora

**Problema e intuición.** pirámide de pruebas y límites de la metáfora aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing unitario, pirámide de pruebas y límites de la metáfora se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Probar decisiones de negocio y bordes; usar dobles solo donde controlan una colaboración relevante. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica pirámide de pruebas y límites de la metáfora con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 4. prueba unitaria frente a integración y E2E

**Problema e intuición.** prueba unitaria frente a integración y E2E aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing unitario, prueba unitaria frente a integración y E2E se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Probar decisiones de negocio y bordes; usar dobles solo donde controlan una colaboración relevante. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica prueba unitaria frente a integración y E2E con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 5. estructura Arrange, Act, Assert

**Problema e intuición.** estructura Arrange, Act, Assert aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing unitario, estructura Arrange, Act, Assert se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Probar decisiones de negocio y bordes; usar dobles solo donde controlan una colaboración relevante. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica estructura Arrange, Act, Assert con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 6. JUnit 5

**Problema e intuición.** JUnit 5 aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing unitario, JUnit 5 se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Probar decisiones de negocio y bordes; usar dobles solo donde controlan una colaboración relevante. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica JUnit 5 con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 7. ciclo de vida de pruebas

**Problema e intuición.** ciclo de vida de pruebas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing unitario, ciclo de vida de pruebas se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Probar decisiones de negocio y bordes; usar dobles solo donde controlan una colaboración relevante. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica ciclo de vida de pruebas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 8. assertions

**Problema e intuición.** assertions aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing unitario, assertions se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Probar decisiones de negocio y bordes; usar dobles solo donde controlan una colaboración relevante. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica assertions con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 9. pruebas parametrizadas

**Problema e intuición.** pruebas parametrizadas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing unitario, pruebas parametrizadas se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Probar decisiones de negocio y bordes; usar dobles solo donde controlan una colaboración relevante. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica pruebas parametrizadas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 10. nombres expresivos

**Problema e intuición.** nombres expresivos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing unitario, nombres expresivos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Probar decisiones de negocio y bordes; usar dobles solo donde controlan una colaboración relevante. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica nombres expresivos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 11. Given, When, Then

**Problema e intuición.** Given, When, Then aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing unitario, Given, When, Then se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Probar decisiones de negocio y bordes; usar dobles solo donde controlan una colaboración relevante. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica Given, When, Then con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 12. dobles de prueba: dummy, stub, fake, spy y mock

**Problema e intuición.** dobles de prueba: dummy, stub, fake, spy y mock aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Los dobles controlan colaboraciones: un stub devuelve datos preparados, un mock además verifica interacciones y un fake implementa una versión simplificada con comportamiento real. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Probar decisiones de negocio y bordes; usar dobles solo donde controlan una colaboración relevante. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica dobles de prueba: dummy, stub, fake, spy y mock con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 13. Mockito

**Problema e intuición.** Mockito aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Los dobles controlan colaboraciones: un stub devuelve datos preparados, un mock además verifica interacciones y un fake implementa una versión simplificada con comportamiento real. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Probar decisiones de negocio y bordes; usar dobles solo donde controlan una colaboración relevante. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica Mockito con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 14. stubbing y verification

**Problema e intuición.** stubbing y verification aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Los dobles controlan colaboraciones: un stub devuelve datos preparados, un mock además verifica interacciones y un fake implementa una versión simplificada con comportamiento real. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Probar decisiones de negocio y bordes; usar dobles solo donde controlan una colaboración relevante. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica stubbing y verification con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 15. prueba de servicios aislados

**Problema e intuición.** prueba de servicios aislados aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing unitario, prueba de servicios aislados se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Probar decisiones de negocio y bordes; usar dobles solo donde controlan una colaboración relevante. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica prueba de servicios aislados con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 16. excepciones y casos límite

**Problema e intuición.** excepciones y casos límite aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing unitario, excepciones y casos límite se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Probar decisiones de negocio y bordes; usar dobles solo donde controlan una colaboración relevante. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica excepciones y casos límite con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 17. determinismo

**Problema e intuición.** determinismo aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing unitario, determinismo se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Probar decisiones de negocio y bordes; usar dobles solo donde controlan una colaboración relevante. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica determinismo con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 18. pruebas frágiles

**Problema e intuición.** pruebas frágiles aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing unitario, pruebas frágiles se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Probar decisiones de negocio y bordes; usar dobles solo donde controlan una colaboración relevante. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica pruebas frágiles con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 19. qué no conviene mockear

**Problema e intuición.** qué no conviene mockear aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Los dobles controlan colaboraciones: un stub devuelve datos preparados, un mock además verifica interacciones y un fake implementa una versión simplificada con comportamiento real. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Probar decisiones de negocio y bordes; usar dobles solo donde controlan una colaboración relevante. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica qué no conviene mockear con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 20. cobertura de líneas y ramas

**Problema e intuición.** cobertura de líneas y ramas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Cobertura indica qué código se ejecutó, no si las afirmaciones fueron buenas. Debe interpretarse junto con riesgos, branches y calidad de assertions. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Probar decisiones de negocio y bordes; usar dobles solo donde controlan una colaboración relevante. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica cobertura de líneas y ramas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 21. por qué 100 % de cobertura no garantiza calidad

**Problema e intuición.** por qué 100 % de cobertura no garantiza calidad aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Cobertura indica qué código se ejecutó, no si las afirmaciones fueron buenas. Debe interpretarse junto con riesgos, branches y calidad de assertions. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Probar decisiones de negocio y bordes; usar dobles solo donde controlan una colaboración relevante. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica por qué 100 % de cobertura no garantiza calidad con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 22. introducción opcional a mutation testing

**Problema e intuición.** introducción opcional a mutation testing aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing unitario, introducción opcional a mutation testing se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Probar decisiones de negocio y bordes; usar dobles solo donde controlan una colaboración relevante. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica introducción opcional a mutation testing con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 23. pruebas generadas con IA y revisión crítica

**Problema e intuición.** pruebas generadas con IA y revisión crítica aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing unitario, pruebas generadas con IA y revisión crítica se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Probar decisiones de negocio y bordes; usar dobles solo donde controlan una colaboración relevante. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica pruebas generadas con IA y revisión crítica con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 24. diseño de código testeable

**Problema e intuición.** diseño de código testeable aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Testing unitario, diseño de código testeable se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** El motor de JUnit descubre pruebas, prepara instancias y extensiones, ejecuta cada caso y recopila resultados; Spring añade contexto cuando el alcance deja de ser puramente unitario. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege reglas de saldo, moneda, idempotencia y traducción de errores antes de integrar infraestructura. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un test puede pasar sin cubrir el riesgo, compartir estado o depender del orden y del tiempo. La regla es: Probar decisiones de negocio y bordes; usar dobles solo donde controlan una colaboración relevante. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica diseño de código testeable con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

## Decisiones y alternativas

1. Define primero la invariante o el riesgo.
2. Compara una opción simple con otra más operable.
3. Registra el costo introducido: latencia, acoplamiento, complejidad o mantenimiento.
4. Elige: Probar decisiones de negocio y bordes; usar dobles solo donde controlan una colaboración relevante.
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

La unidad enseña a convertir Testing unitario en decisiones verificables. La idea profesional no es acumular herramientas, sino conectar propósito, mecanismo, fallo, evidencia y costo.

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
