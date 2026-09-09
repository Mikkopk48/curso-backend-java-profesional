# Unidad 22 — Mapeo estructurado con MapStruct: teoría profunda

## Lugar dentro del recorrido

Esta unidad corresponde a la etapa **Contratos separados de JPA**. Parte de la API inicial de la unidad 5 y añade una capacidad profesional sin volver a explicar Java, POO o CRUD desde cero.

## Conocimientos previos

- Controladores, servicios y repositorios básicos.
- Lectura de Java y ejecución de una aplicación Spring Boot.
- Conceptos recuperados en <code>00-objetivos-y-ruta.md</code>.

## Objetivos observables

Al terminar, podrás explicar el riesgo que resuelve cada concepto, seguir su funcionamiento, aplicarlo a FintechLab, escribir evidencia y defender cuándo no conviene usarlo.

## Problema central

Mapear crea una frontera explícita entre modelos con responsabilidades diferentes. En un ejercicio pequeño puede parecer suficiente memorizar una API. En producción importan además concurrencia, datos inválidos, latencia, compatibilidad, trazabilidad y recuperación.

## Modelo mental y analogía

Un traductor conserva significado sin entregar el documento interno original.

**Límite:** El mapeo no arregla un contrato malo y puede activar carga diferida si recorre entidades sin control.

## Funcionamiento técnico progresivo

MapStruct ejecuta un annotation processor durante compilación y genera Java explícito que el compilador vuelve a verificar. El recorrido útil siempre contiene cinco preguntas: quién inicia, qué cruza la frontera, qué estado participa, dónde puede fallar y qué evidencia queda.

~~~java
@Mapper(componentModel = "spring")
interface AccountMapper {
    @Mapping(target = "availableBalance", source = "balance")
    AccountResponse toResponse(AccountEntity source);
}
~~~

El ejemplo es deliberadamente pequeño. No es la solución de los ejercicios: muestra vocabulario y forma, mientras las consignas cambian datos, restricciones y estructura.

## Explicación conectada: modelos distintos en fronteras distintas

Una entidad representa persistencia y ciclo de vida. Un objeto de dominio expresa reglas. Un comando expresa intención. Un request/response DTO expresa contrato externo. Pueden compartir campos y seguir siendo conceptos diferentes. Exponer entidad permite que una relación, columna o lazy proxy cambie el JSON.

El mapeo manual es explícito y fácil de depurar, pero repetitivo. Mapeadores por reflexión reducen código visible y trasladan errores al runtime. MapStruct ejecuta un annotation processor: lee interfaces anotadas y genera Java que luego compila. No realiza magia en cada request.

<code>@Mapper(componentModel="spring")</code> produce un bean. <code>@Mapping</code> conecta nombres distintos; <code>uses</code> compone mappers. Objetos anidados, colecciones, enums y fechas requieren reglas conscientes. Las expressions permiten Java arbitrario, pero pueden ocultar lógica sin chequeo de nombres: prefiere métodos auxiliares.

Para updates, <code>@MappingTarget</code> modifica instancia existente. Debes decidir si null significa “borrar”, “ignorar” o “inválido”; las null strategies vuelven esa decisión explícita. Los ciclos bidireccionales de JPA pueden recursar; DTO debe modelar lo necesario en lugar de serializar el grafo.

Mapear una relación lazy puede disparar N+1. Obtén una proyección o fetch plan antes del mapper. Prueba conversiones con reglas, nombres distintos y null; los campos idénticos triviales pueden confiar en compilación, pero revisa el código generado al actualizar versión. No automatices un mapping que contiene una decisión de negocio importante.

## Atlas completo del temario oficial

Las subsecciones siguientes cubren cada punto del programa. La etiqueta **Diplomatura** identifica el núcleo oficial; las decisiones de producción y el vínculo con FintechLab constituyen profundización y ampliación profesional.

### 1. entidad, modelo de dominio, comando, respuesta y DTO

**Problema e intuición.** entidad, modelo de dominio, comando, respuesta y DTO aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapeo estructurado con MapStruct, entidad, modelo de dominio, comando, respuesta y DTO se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MapStruct ejecuta un annotation processor durante compilación y genera Java explícito que el compilador vuelve a verificar. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa requests y responses de entidades persistentes. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un mapper puede esconder reglas, acceder a relaciones lazy o confundir ausencia con valor nulo. La regla es: Mapear en los límites, probar reglas no triviales y revisar el código generado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica entidad, modelo de dominio, comando, respuesta y DTO con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 2. por qué no exponer entidades

**Problema e intuición.** por qué no exponer entidades aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapeo estructurado con MapStruct, por qué no exponer entidades se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MapStruct ejecuta un annotation processor durante compilación y genera Java explícito que el compilador vuelve a verificar. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa requests y responses de entidades persistentes. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un mapper puede esconder reglas, acceder a relaciones lazy o confundir ausencia con valor nulo. La regla es: Mapear en los límites, probar reglas no triviales y revisar el código generado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica por qué no exponer entidades con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 3. mapeo manual

**Problema e intuición.** mapeo manual aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapeo estructurado con MapStruct, mapeo manual se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MapStruct ejecuta un annotation processor durante compilación y genera Java explícito que el compilador vuelve a verificar. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa requests y responses de entidades persistentes. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un mapper puede esconder reglas, acceder a relaciones lazy o confundir ausencia con valor nulo. La regla es: Mapear en los límites, probar reglas no triviales y revisar el código generado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica mapeo manual con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 4. ventajas y costos

**Problema e intuición.** ventajas y costos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapeo estructurado con MapStruct, ventajas y costos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MapStruct ejecuta un annotation processor durante compilación y genera Java explícito que el compilador vuelve a verificar. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa requests y responses de entidades persistentes. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un mapper puede esconder reglas, acceder a relaciones lazy o confundir ausencia con valor nulo. La regla es: Mapear en los límites, probar reglas no triviales y revisar el código generado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica ventajas y costos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 5. MapStruct

**Problema e intuición.** MapStruct aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** MapStruct genera implementaciones en compilación, sin reflexión durante cada request. La generación visible facilita depuración, pero las reglas siguen necesitando revisión. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MapStruct ejecuta un annotation processor durante compilación y genera Java explícito que el compilador vuelve a verificar. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa requests y responses de entidades persistentes. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un mapper puede esconder reglas, acceder a relaciones lazy o confundir ausencia con valor nulo. La regla es: Mapear en los límites, probar reglas no triviales y revisar el código generado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica MapStruct con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 6. generación de código en compilación

**Problema e intuición.** generación de código en compilación aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapeo estructurado con MapStruct, generación de código en compilación se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MapStruct ejecuta un annotation processor durante compilación y genera Java explícito que el compilador vuelve a verificar. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa requests y responses de entidades persistentes. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un mapper puede esconder reglas, acceder a relaciones lazy o confundir ausencia con valor nulo. La regla es: Mapear en los límites, probar reglas no triviales y revisar el código generado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica generación de código en compilación con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 7. configuración del annotation processor

**Problema e intuición.** configuración del annotation processor aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** MapStruct genera implementaciones en compilación, sin reflexión durante cada request. La generación visible facilita depuración, pero las reglas siguen necesitando revisión. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MapStruct ejecuta un annotation processor durante compilación y genera Java explícito que el compilador vuelve a verificar. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa requests y responses de entidades persistentes. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un mapper puede esconder reglas, acceder a relaciones lazy o confundir ausencia con valor nulo. La regla es: Mapear en los límites, probar reglas no triviales y revisar el código generado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica configuración del annotation processor con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 8. @Mapper

**Problema e intuición.** @Mapper aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapeo estructurado con MapStruct, @Mapper se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MapStruct ejecuta un annotation processor durante compilación y genera Java explícito que el compilador vuelve a verificar. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa requests y responses de entidades persistentes. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un mapper puede esconder reglas, acceder a relaciones lazy o confundir ausencia con valor nulo. La regla es: Mapear en los límites, probar reglas no triviales y revisar el código generado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica @Mapper con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 9. componentModel de Spring

**Problema e intuición.** componentModel de Spring aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapeo estructurado con MapStruct, componentModel de Spring se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MapStruct ejecuta un annotation processor durante compilación y genera Java explícito que el compilador vuelve a verificar. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa requests y responses de entidades persistentes. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un mapper puede esconder reglas, acceder a relaciones lazy o confundir ausencia con valor nulo. La regla es: Mapear en los límites, probar reglas no triviales y revisar el código generado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica componentModel de Spring con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 10. @Mapping

**Problema e intuición.** @Mapping aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapeo estructurado con MapStruct, @Mapping se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MapStruct ejecuta un annotation processor durante compilación y genera Java explícito que el compilador vuelve a verificar. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa requests y responses de entidades persistentes. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un mapper puede esconder reglas, acceder a relaciones lazy o confundir ausencia con valor nulo. La regla es: Mapear en los límites, probar reglas no triviales y revisar el código generado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica @Mapping con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 11. campos con nombres distintos

**Problema e intuición.** campos con nombres distintos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapeo estructurado con MapStruct, campos con nombres distintos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MapStruct ejecuta un annotation processor durante compilación y genera Java explícito que el compilador vuelve a verificar. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa requests y responses de entidades persistentes. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un mapper puede esconder reglas, acceder a relaciones lazy o confundir ausencia con valor nulo. La regla es: Mapear en los límites, probar reglas no triviales y revisar el código generado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica campos con nombres distintos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 12. objetos anidados

**Problema e intuición.** objetos anidados aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapeo estructurado con MapStruct, objetos anidados se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MapStruct ejecuta un annotation processor durante compilación y genera Java explícito que el compilador vuelve a verificar. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa requests y responses de entidades persistentes. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un mapper puede esconder reglas, acceder a relaciones lazy o confundir ausencia con valor nulo. La regla es: Mapear en los límites, probar reglas no triviales y revisar el código generado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica objetos anidados con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 13. expresiones y métodos auxiliares

**Problema e intuición.** expresiones y métodos auxiliares aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapeo estructurado con MapStruct, expresiones y métodos auxiliares se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MapStruct ejecuta un annotation processor durante compilación y genera Java explícito que el compilador vuelve a verificar. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa requests y responses de entidades persistentes. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un mapper puede esconder reglas, acceder a relaciones lazy o confundir ausencia con valor nulo. La regla es: Mapear en los límites, probar reglas no triviales y revisar el código generado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica expresiones y métodos auxiliares con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 14. enums

**Problema e intuición.** enums aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapeo estructurado con MapStruct, enums se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MapStruct ejecuta un annotation processor durante compilación y genera Java explícito que el compilador vuelve a verificar. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa requests y responses de entidades persistentes. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un mapper puede esconder reglas, acceder a relaciones lazy o confundir ausencia con valor nulo. La regla es: Mapear en los límites, probar reglas no triviales y revisar el código generado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica enums con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 15. fechas

**Problema e intuición.** fechas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapeo estructurado con MapStruct, fechas se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MapStruct ejecuta un annotation processor durante compilación y genera Java explícito que el compilador vuelve a verificar. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa requests y responses de entidades persistentes. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un mapper puede esconder reglas, acceder a relaciones lazy o confundir ausencia con valor nulo. La regla es: Mapear en los límites, probar reglas no triviales y revisar el código generado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica fechas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 16. colecciones

**Problema e intuición.** colecciones aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapeo estructurado con MapStruct, colecciones se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MapStruct ejecuta un annotation processor durante compilación y genera Java explícito que el compilador vuelve a verificar. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa requests y responses de entidades persistentes. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un mapper puede esconder reglas, acceder a relaciones lazy o confundir ausencia con valor nulo. La regla es: Mapear en los límites, probar reglas no triviales y revisar el código generado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica colecciones con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 17. valores nulos

**Problema e intuición.** valores nulos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapeo estructurado con MapStruct, valores nulos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MapStruct ejecuta un annotation processor durante compilación y genera Java explícito que el compilador vuelve a verificar. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa requests y responses de entidades persistentes. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un mapper puede esconder reglas, acceder a relaciones lazy o confundir ausencia con valor nulo. La regla es: Mapear en los límites, probar reglas no triviales y revisar el código generado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica valores nulos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 18. estrategias de null

**Problema e intuición.** estrategias de null aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapeo estructurado con MapStruct, estrategias de null se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MapStruct ejecuta un annotation processor durante compilación y genera Java explícito que el compilador vuelve a verificar. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa requests y responses de entidades persistentes. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un mapper puede esconder reglas, acceder a relaciones lazy o confundir ausencia con valor nulo. La regla es: Mapear en los límites, probar reglas no triviales y revisar el código generado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica estrategias de null con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 19. actualización de un objeto existente

**Problema e intuición.** actualización de un objeto existente aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapeo estructurado con MapStruct, actualización de un objeto existente se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MapStruct ejecuta un annotation processor durante compilación y genera Java explícito que el compilador vuelve a verificar. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa requests y responses de entidades persistentes. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un mapper puede esconder reglas, acceder a relaciones lazy o confundir ausencia con valor nulo. La regla es: Mapear en los límites, probar reglas no triviales y revisar el código generado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica actualización de un objeto existente con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 20. @MappingTarget

**Problema e intuición.** @MappingTarget aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapeo estructurado con MapStruct, @MappingTarget se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MapStruct ejecuta un annotation processor durante compilación y genera Java explícito que el compilador vuelve a verificar. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa requests y responses de entidades persistentes. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un mapper puede esconder reglas, acceder a relaciones lazy o confundir ausencia con valor nulo. La regla es: Mapear en los límites, probar reglas no triviales y revisar el código generado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica @MappingTarget con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 21. mappers reutilizables

**Problema e intuición.** mappers reutilizables aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapeo estructurado con MapStruct, mappers reutilizables se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MapStruct ejecuta un annotation processor durante compilación y genera Java explícito que el compilador vuelve a verificar. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa requests y responses de entidades persistentes. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un mapper puede esconder reglas, acceder a relaciones lazy o confundir ausencia con valor nulo. La regla es: Mapear en los límites, probar reglas no triviales y revisar el código generado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica mappers reutilizables con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 22. ciclos

**Problema e intuición.** ciclos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapeo estructurado con MapStruct, ciclos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MapStruct ejecuta un annotation processor durante compilación y genera Java explícito que el compilador vuelve a verificar. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa requests y responses de entidades persistentes. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un mapper puede esconder reglas, acceder a relaciones lazy o confundir ausencia con valor nulo. La regla es: Mapear en los límites, probar reglas no triviales y revisar el código generado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica ciclos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 23. relaciones JPA

**Problema e intuición.** relaciones JPA aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapeo estructurado con MapStruct, relaciones JPA se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MapStruct ejecuta un annotation processor durante compilación y genera Java explícito que el compilador vuelve a verificar. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa requests y responses de entidades persistentes. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un mapper puede esconder reglas, acceder a relaciones lazy o confundir ausencia con valor nulo. La regla es: Mapear en los límites, probar reglas no triviales y revisar el código generado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica relaciones JPA con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 24. mapeos que disparan lazy loading

**Problema e intuición.** mapeos que disparan lazy loading aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** N+1 aparece cuando una consulta inicial dispara otra por cada fila relacionada. Cambiar todo a eager suele trasladar el problema; se diseñan consultas por caso de uso. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MapStruct ejecuta un annotation processor durante compilación y genera Java explícito que el compilador vuelve a verificar. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa requests y responses de entidades persistentes. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un mapper puede esconder reglas, acceder a relaciones lazy o confundir ausencia con valor nulo. La regla es: Mapear en los límites, probar reglas no triviales y revisar el código generado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica mapeos que disparan lazy loading con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 25. pruebas unitarias de mappers

**Problema e intuición.** pruebas unitarias de mappers aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapeo estructurado con MapStruct, pruebas unitarias de mappers se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MapStruct ejecuta un annotation processor durante compilación y genera Java explícito que el compilador vuelve a verificar. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa requests y responses de entidades persistentes. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un mapper puede esconder reglas, acceder a relaciones lazy o confundir ausencia con valor nulo. La regla es: Mapear en los límites, probar reglas no triviales y revisar el código generado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica pruebas unitarias de mappers con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 26. comparación con mapeadores basados en reflexión

**Problema e intuición.** comparación con mapeadores basados en reflexión aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapeo estructurado con MapStruct, comparación con mapeadores basados en reflexión se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MapStruct ejecuta un annotation processor durante compilación y genera Java explícito que el compilador vuelve a verificar. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa requests y responses de entidades persistentes. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un mapper puede esconder reglas, acceder a relaciones lazy o confundir ausencia con valor nulo. La regla es: Mapear en los límites, probar reglas no triviales y revisar el código generado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica comparación con mapeadores basados en reflexión con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 27. cuándo el mapeo automático oculta demasiado

**Problema e intuición.** cuándo el mapeo automático oculta demasiado aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Mapeo estructurado con MapStruct, cuándo el mapeo automático oculta demasiado se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** MapStruct ejecuta un annotation processor durante compilación y genera Java explícito que el compilador vuelve a verificar. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** separa requests y responses de entidades persistentes. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un mapper puede esconder reglas, acceder a relaciones lazy o confundir ausencia con valor nulo. La regla es: Mapear en los límites, probar reglas no triviales y revisar el código generado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica cuándo el mapeo automático oculta demasiado con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

## Decisiones y alternativas

1. Define primero la invariante o el riesgo.
2. Compara una opción simple con otra más operable.
3. Registra el costo introducido: latencia, acoplamiento, complejidad o mantenimiento.
4. Elige: Mapear en los límites, probar reglas no triviales y revisar el código generado.
5. Documenta qué dato futuro haría cambiar la decisión.

## Errores frecuentes

- Copiar una anotación sin conocer quién la interpreta ni cuándo.
- Confundir el caso feliz con el contrato completo.
- Añadir infraestructura sin una necesidad observable.
- Escribir un test que replica la implementación en vez de cubrir riesgo.
- Registrar datos sensibles para facilitar debugging.
- Afirmar una garantía sin una restricción, prueba o medición que la sostenga.

## Qué ocurre en producción

Un mapper puede esconder reglas, acceder a relaciones lazy o confundir ausencia con valor nulo. Un profesional diseña para degradación, documenta límites, mide antes de optimizar y deja una ruta de recuperación. FintechLab usa datos ficticios y no debe conectarse con bancos ni procesar dinero real.

## Cómo reconocer que realmente lo entendiste

- Puedes explicarlo sin depender del nombre de una anotación.
- Puedes dibujar el recorrido y marcar al menos tres fallos.
- Puedes cambiar el ejemplo sin copiarlo.
- Puedes escribir una prueba que falla cuando se rompe la regla.
- Puedes nombrar una alternativa y justificar el trade-off.

## Resumen sencillo

La unidad enseña a convertir Mapeo estructurado con MapStruct en decisiones verificables. La idea profesional no es acumular herramientas, sino conectar propósito, mecanismo, fallo, evidencia y costo.

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
