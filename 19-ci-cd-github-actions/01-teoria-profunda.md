# Unidad 19 — CI/CD con GitHub Actions: teoría profunda

## Lugar dentro del recorrido

Esta unidad corresponde a la etapa **Pipeline verificable**. Parte de la API inicial de la unidad 5 y añade una capacidad profesional sin volver a explicar Java, POO o CRUD desde cero.

## Conocimientos previos

- Controladores, servicios y repositorios básicos.
- Lectura de Java y ejecución de una aplicación Spring Boot.
- Conceptos recuperados en <code>00-objetivos-y-ruta.md</code>.

## Objetivos observables

Al terminar, podrás explicar el riesgo que resuelve cada concepto, seguir su funcionamiento, aplicarlo a FintechLab, escribir evidencia y defender cuándo no conviene usarlo.

## Problema central

Un pipeline convierte la definición de terminado en controles repetibles. En un ejercicio pequeño puede parecer suficiente memorizar una API. En producción importan además concurrencia, datos inválidos, latencia, compatibilidad, trazabilidad y recuperación.

## Modelo mental y analogía

Es una línea de ensamblaje que detiene la entrega cuando una estación detecta una falla.

**Límite:** Automatizar un control pobre solo reproduce el error con mayor velocidad.

## Funcionamiento técnico progresivo

GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. El recorrido útil siempre contiene cinco preguntas: quién inicia, qué cruza la frontera, qué estado participa, dónde puede fallar y qué evidencia queda.

~~~yaml
- uses: actions/setup-java@v5
  with: { distribution: temurin, java-version: '21', cache: maven }
- run: ./mvnw --batch-mode verify
~~~

El ejemplo es deliberadamente pequeño. No es la solución de los ejercicios: muestra vocabulario y forma, mientras las consignas cambian datos, restricciones y estructura.

## Explicación conectada: convertir “terminado” en un pipeline

Continuous Integration integra cambios frecuentes y verifica automáticamente. Continuous Delivery produce un artefacto listo para promover; Continuous Deployment además promueve sin intervención manual cuando pasa controles. Un equipo puede practicar CI sin desplegar.

En GitHub Actions, un evento dispara workflow; jobs corren en runners y contienen steps. Jobs son aislados salvo artefactos/cache. Actions reutilizan lógica, pero son dependencias de la cadena de suministro: fija versiones mantenidas y otorga permisos mínimos.

Un pipeline FintechLab hace checkout, instala Java 21, restaura cache Maven, compila, ejecuta unidad e integración, valida OpenAPI/migraciones, analiza calidad, empaqueta y guarda artefacto. Cache acelera dependencias; un artefacto es el resultado identificado que se promueve. No reconstruyas para cada ambiente porque producirías bytes diferentes.

Quality gates pueden incluir tests, cobertura interpretada, análisis estático y vulnerabilidades. Un gate numérico sin revisión incentiva tests vacíos. Un fallo se diagnostica desde el primer error y se reproduce localmente con versión y comando del runner.

Al construir imagen, usa base identificada, usuario no root y tags inmutables ligados a commit. Escanea dependencias, genera SBOM cuando sea útil y protege secretos mediante environments y OIDC; nunca los imprimas. Matrices prueban versiones justificadas. Workflows reutilizables evitan copiar políticas.

Rolling reemplaza instancias gradualmente; blue-green cambia entre dos entornos; canary expone una fracción. Cada una exige health, métricas y rollback. Rollback del binario no siempre revierte una migración, por lo que los cambios de esquema deben ser compatibles por etapas.

## Atlas completo del temario oficial

Las subsecciones siguientes cubren cada punto del programa. La etiqueta **Diplomatura** identifica el núcleo oficial; las decisiones de producción y el vínculo con FintechLab constituyen profundización y ampliación profesional.

### 1. integración continua

**Problema e intuición.** integración continua aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** CI integra y verifica cambios con frecuencia; delivery mantiene un artefacto desplegable y deployment automatiza además su llegada a producción. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** compila, prueba, verifica el contrato y construye una imagen identificable sin desplegar dinero real. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. La regla es: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica integración continua con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 2. continuous delivery

**Problema e intuición.** continuous delivery aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** CI integra y verifica cambios con frecuencia; delivery mantiene un artefacto desplegable y deployment automatiza además su llegada a producción. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** compila, prueba, verifica el contrato y construye una imagen identificable sin desplegar dinero real. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. La regla es: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica continuous delivery con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 3. continuous deployment

**Problema e intuición.** continuous deployment aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** CI integra y verifica cambios con frecuencia; delivery mantiene un artefacto desplegable y deployment automatiza además su llegada a producción. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** compila, prueba, verifica el contrato y construye una imagen identificable sin desplegar dinero real. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. La regla es: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica continuous deployment con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 4. diferencia entre los tres términos

**Problema e intuición.** diferencia entre los tres términos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En CI/CD con GitHub Actions, diferencia entre los tres términos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** compila, prueba, verifica el contrato y construye una imagen identificable sin desplegar dinero real. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. La regla es: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica diferencia entre los tres términos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 5. pipeline

**Problema e intuición.** pipeline aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En CI/CD con GitHub Actions, pipeline se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** compila, prueba, verifica el contrato y construye una imagen identificable sin desplegar dinero real. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. La regla es: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica pipeline con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 6. evento, workflow, job, step, runner y action

**Problema e intuición.** evento, workflow, job, step, runner y action aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En CI/CD con GitHub Actions, evento, workflow, job, step, runner y action se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** compila, prueba, verifica el contrato y construye una imagen identificable sin desplegar dinero real. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. La regla es: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica evento, workflow, job, step, runner y action con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 7. sintaxis YAML

**Problema e intuición.** sintaxis YAML aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En CI/CD con GitHub Actions, sintaxis YAML se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** compila, prueba, verifica el contrato y construye una imagen identificable sin desplegar dinero real. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. La regla es: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica sintaxis YAML con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 8. triggers

**Problema e intuición.** triggers aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En CI/CD con GitHub Actions, triggers se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** compila, prueba, verifica el contrato y construye una imagen identificable sin desplegar dinero real. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. La regla es: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica triggers con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 9. checkout

**Problema e intuición.** checkout aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En CI/CD con GitHub Actions, checkout se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** compila, prueba, verifica el contrato y construye una imagen identificable sin desplegar dinero real. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. La regla es: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica checkout con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 10. instalación de Java

**Problema e intuición.** instalación de Java aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En CI/CD con GitHub Actions, instalación de Java se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** compila, prueba, verifica el contrato y construye una imagen identificable sin desplegar dinero real. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. La regla es: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica instalación de Java con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 11. caché de Maven

**Problema e intuición.** caché de Maven aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En CI/CD con GitHub Actions, caché de Maven se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** compila, prueba, verifica el contrato y construye una imagen identificable sin desplegar dinero real. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. La regla es: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica caché de Maven con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 12. compilación

**Problema e intuición.** compilación aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En CI/CD con GitHub Actions, compilación se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** compila, prueba, verifica el contrato y construye una imagen identificable sin desplegar dinero real. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. La regla es: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica compilación con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 13. tests unitarios

**Problema e intuición.** tests unitarios aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En CI/CD con GitHub Actions, tests unitarios se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** compila, prueba, verifica el contrato y construye una imagen identificable sin desplegar dinero real. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. La regla es: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica tests unitarios con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 14. tests de integración

**Problema e intuición.** tests de integración aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En CI/CD con GitHub Actions, tests de integración se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** compila, prueba, verifica el contrato y construye una imagen identificable sin desplegar dinero real. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. La regla es: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica tests de integración con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 15. análisis estático

**Problema e intuición.** análisis estático aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En CI/CD con GitHub Actions, análisis estático se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** compila, prueba, verifica el contrato y construye una imagen identificable sin desplegar dinero real. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. La regla es: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica análisis estático con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 16. cobertura

**Problema e intuición.** cobertura aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Cobertura indica qué código se ejecutó, no si las afirmaciones fueron buenas. Debe interpretarse junto con riesgos, branches y calidad de assertions. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** compila, prueba, verifica el contrato y construye una imagen identificable sin desplegar dinero real. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. La regla es: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica cobertura con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 17. quality gates

**Problema e intuición.** quality gates aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En CI/CD con GitHub Actions, quality gates se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** compila, prueba, verifica el contrato y construye una imagen identificable sin desplegar dinero real. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. La regla es: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica quality gates con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 18. empaquetado

**Problema e intuición.** empaquetado aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En CI/CD con GitHub Actions, empaquetado se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** compila, prueba, verifica el contrato y construye una imagen identificable sin desplegar dinero real. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. La regla es: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica empaquetado con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 19. artefactos

**Problema e intuición.** artefactos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En CI/CD con GitHub Actions, artefactos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** compila, prueba, verifica el contrato y construye una imagen identificable sin desplegar dinero real. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. La regla es: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica artefactos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 20. creación de imagen de contenedor

**Problema e intuición.** creación de imagen de contenedor aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En CI/CD con GitHub Actions, creación de imagen de contenedor se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** compila, prueba, verifica el contrato y construye una imagen identificable sin desplegar dinero real. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. La regla es: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica creación de imagen de contenedor con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 21. tags reproducibles

**Problema e intuición.** tags reproducibles aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En CI/CD con GitHub Actions, tags reproducibles se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** compila, prueba, verifica el contrato y construye una imagen identificable sin desplegar dinero real. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. La regla es: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica tags reproducibles con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 22. secretos

**Problema e intuición.** secretos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En CI/CD con GitHub Actions, secretos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** compila, prueba, verifica el contrato y construye una imagen identificable sin desplegar dinero real. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. La regla es: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica secretos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 23. permisos mínimos

**Problema e intuición.** permisos mínimos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En CI/CD con GitHub Actions, permisos mínimos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** compila, prueba, verifica el contrato y construye una imagen identificable sin desplegar dinero real. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. La regla es: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica permisos mínimos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 24. environments

**Problema e intuición.** environments aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En CI/CD con GitHub Actions, environments se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** compila, prueba, verifica el contrato y construye una imagen identificable sin desplegar dinero real. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. La regla es: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica environments con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 25. aprobaciones

**Problema e intuición.** aprobaciones aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En CI/CD con GitHub Actions, aprobaciones se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** compila, prueba, verifica el contrato y construye una imagen identificable sin desplegar dinero real. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. La regla es: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica aprobaciones con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 26. matrices

**Problema e intuición.** matrices aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En CI/CD con GitHub Actions, matrices se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** compila, prueba, verifica el contrato y construye una imagen identificable sin desplegar dinero real. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. La regla es: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica matrices con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 27. reutilización de workflows

**Problema e intuición.** reutilización de workflows aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En CI/CD con GitHub Actions, reutilización de workflows se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** compila, prueba, verifica el contrato y construye una imagen identificable sin desplegar dinero real. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. La regla es: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica reutilización de workflows con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 28. dependencias entre jobs

**Problema e intuición.** dependencias entre jobs aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En CI/CD con GitHub Actions, dependencias entre jobs se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** compila, prueba, verifica el contrato y construye una imagen identificable sin desplegar dinero real. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. La regla es: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica dependencias entre jobs con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 29. diagnóstico de pipelines fallidos

**Problema e intuición.** diagnóstico de pipelines fallidos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En CI/CD con GitHub Actions, diagnóstico de pipelines fallidos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** compila, prueba, verifica el contrato y construye una imagen identificable sin desplegar dinero real. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. La regla es: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica diagnóstico de pipelines fallidos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 30. seguridad de la cadena de suministro

**Problema e intuición.** seguridad de la cadena de suministro aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En CI/CD con GitHub Actions, seguridad de la cadena de suministro se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** compila, prueba, verifica el contrato y construye una imagen identificable sin desplegar dinero real. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. La regla es: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica seguridad de la cadena de suministro con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 31. escaneo de dependencias

**Problema e intuición.** escaneo de dependencias aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En CI/CD con GitHub Actions, escaneo de dependencias se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** compila, prueba, verifica el contrato y construye una imagen identificable sin desplegar dinero real. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. La regla es: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica escaneo de dependencias con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 32. SBOM como ampliación

**Problema e intuición.** SBOM como ampliación aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En CI/CD con GitHub Actions, SBOM como ampliación se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** compila, prueba, verifica el contrato y construye una imagen identificable sin desplegar dinero real. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. La regla es: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica SBOM como ampliación con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 33. estrategias rolling, blue-green y canary a nivel conceptual

**Problema e intuición.** estrategias rolling, blue-green y canary a nivel conceptual aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En CI/CD con GitHub Actions, estrategias rolling, blue-green y canary a nivel conceptual se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** compila, prueba, verifica el contrato y construye una imagen identificable sin desplegar dinero real. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. La regla es: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica estrategias rolling, blue-green y canary a nivel conceptual con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 34. rollback

**Problema e intuición.** rollback aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En CI/CD con GitHub Actions, rollback se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** compila, prueba, verifica el contrato y construye una imagen identificable sin desplegar dinero real. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. La regla es: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica rollback con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 35. aplicación en un proyecto fintech

**Problema e intuición.** aplicación en un proyecto fintech aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En CI/CD con GitHub Actions, aplicación en un proyecto fintech se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** GitHub Actions crea runners efímeros que ejecutan jobs y steps declarados; cada job recibe permisos y artefactos explícitos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** compila, prueba, verifica el contrato y construye una imagen identificable sin desplegar dinero real. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. La regla es: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica aplicación en un proyecto fintech con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

## Decisiones y alternativas

1. Define primero la invariante o el riesgo.
2. Compara una opción simple con otra más operable.
3. Registra el costo introducido: latencia, acoplamiento, complejidad o mantenimiento.
4. Elige: Empezar por build y tests deterministas; añadir seguridad, artefactos y despliegue con permisos mínimos.
5. Documenta qué dato futuro haría cambiar la decisión.

## Errores frecuentes

- Copiar una anotación sin conocer quién la interpreta ni cuándo.
- Confundir el caso feliz con el contrato completo.
- Añadir infraestructura sin una necesidad observable.
- Escribir un test que replica la implementación en vez de cubrir riesgo.
- Registrar datos sensibles para facilitar debugging.
- Afirmar una garantía sin una restricción, prueba o medición que la sostenga.

## Qué ocurre en producción

Dependencias no fijadas, permisos amplios y pruebas no deterministas debilitan la cadena de suministro. Un profesional diseña para degradación, documenta límites, mide antes de optimizar y deja una ruta de recuperación. FintechLab usa datos ficticios y no debe conectarse con bancos ni procesar dinero real.

## Cómo reconocer que realmente lo entendiste

- Puedes explicarlo sin depender del nombre de una anotación.
- Puedes dibujar el recorrido y marcar al menos tres fallos.
- Puedes cambiar el ejemplo sin copiarlo.
- Puedes escribir una prueba que falla cuando se rompe la regla.
- Puedes nombrar una alternativa y justificar el trade-off.

## Resumen sencillo

La unidad enseña a convertir CI/CD con GitHub Actions en decisiones verificables. La idea profesional no es acumular herramientas, sino conectar propósito, mecanismo, fallo, evidencia y costo.

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
