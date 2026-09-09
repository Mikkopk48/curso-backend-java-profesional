# Unidad 18 — Spring Cloud Config Server: teoría profunda

## Lugar dentro del recorrido

Esta unidad corresponde a la etapa **Configuración centralizada**. Parte de la API inicial de la unidad 5 y añade una capacidad profesional sin volver a explicar Java, POO o CRUD desde cero.

## Conocimientos previos

- Controladores, servicios y repositorios básicos.
- Lectura de Java y ejecución de una aplicación Spring Boot.
- Conceptos recuperados en <code>00-objetivos-y-ruta.md</code>.

## Objetivos observables

Al terminar, podrás explicar el riesgo que resuelve cada concepto, seguir su funcionamiento, aplicarlo a FintechLab, escribir evidencia y defender cuándo no conviene usarlo.

## Problema central

Separar configuración del binario permite promover el mismo artefacto entre ambientes. En un ejercicio pequeño puede parecer suficiente memorizar una API. En producción importan además concurrencia, datos inválidos, latencia, compatibilidad, trazabilidad y recuperación.

## Modelo mental y analogía

Es un tablero de parámetros compartido, con historial y responsables.

**Límite:** Centralizar crea una dependencia y un repositorio Git no se convierte por ello en un gestor de secretos.

## Funcionamiento técnico progresivo

Spring construye un Environment combinando property sources con precedencia; Config Client consulta una fuente remota antes o durante el arranque según la versión. El recorrido útil siempre contiene cinco preguntas: quién inicia, qué cruza la frontera, qué estado participa, dónde puede fallar y qué evidencia queda.

~~~yaml
spring:
  config:
    import: optional:configserver:http://localhost:8888
  application:
    name: notification-service
~~~

El ejemplo es deliberadamente pequeño. No es la solución de los ejercicios: muestra vocabulario y forma, mientras las consignas cambian datos, restricciones y estructura.

## Explicación conectada: configuración como entrada versionada

El binario debería ser el mismo en dev, test y producción; cambian parámetros: URL, timeouts, flags y niveles. Spring construye Environment desde property sources con precedencia. Un argumento de línea, variable, archivo de perfil y default pueden competir. Diagnosticar exige conocer el valor efectivo y su origen, no solo encontrar una línea en YAML.

Profiles activan conjuntos de beans y propiedades. Deben representar ambientes o modos coherentes, no crear una explosión de combinaciones. YAML expresa jerarquía por indentación y listas; un espacio incorrecto cambia estructura aunque el archivo siga siendo texto válido.

Config Server expone configuración por application, profile y label. El backend Git aporta commits, revisión y rollback de valores no sensibles. El cliente usa <code>spring.config.import=configserver:</code> en la línea moderna; no copies recetas bootstrap de versiones antiguas sin comprobar documentación.

Fail-fast decide si una aplicación debe abortar cuando la configuración no llega. Retry puede ayudar durante arranque simultáneo, con límites. Refresh actualiza únicamente beans preparados y puede producir un proceso con valores mezclados; muchos cambios críticos requieren reinicio controlado.

Git no es un secret manager. Quitar una contraseña del último commit no la elimina del historial. Secretos necesitan cifrado en reposo, control de acceso, auditoría y rotación; se inyectan mediante Vault o el mecanismo de la plataforma. Config Server también necesita disponibilidad y autenticación. En contenedores, ConfigMaps/Secrets y variables son alternativas; elige una fuente de verdad clara.

## Atlas completo del temario oficial

Las subsecciones siguientes cubren cada punto del programa. La etiqueta **Diplomatura** identifica el núcleo oficial; las decisiones de producción y el vínculo con FintechLab constituyen profundización y ampliación profesional.

### 1. configuración embebida frente a externa

**Problema e intuición.** configuración embebida frente a externa aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Spring Cloud Config Server, configuración embebida frente a externa se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring construye un Environment combinando property sources con precedencia; Config Client consulta una fuente remota antes o durante el arranque según la versión. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** diferencia parámetros versionados de credenciales inyectadas por ambiente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una precedencia inesperada o la indisponibilidad central puede impedir arrancar; los secretos en Git permanecen en el historial. La regla es: Versionar configuración no sensible; inyectar credenciales por un canal especializado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica configuración embebida frente a externa con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 2. configuración por ambiente

**Problema e intuición.** configuración por ambiente aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Spring Cloud Config Server, configuración por ambiente se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring construye un Environment combinando property sources con precedencia; Config Client consulta una fuente remota antes o durante el arranque según la versión. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** diferencia parámetros versionados de credenciales inyectadas por ambiente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una precedencia inesperada o la indisponibilidad central puede impedir arrancar; los secretos en Git permanecen en el historial. La regla es: Versionar configuración no sensible; inyectar credenciales por un canal especializado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica configuración por ambiente con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 3. perfiles de Spring

**Problema e intuición.** perfiles de Spring aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Spring combina fuentes con una precedencia definida. Saber qué fuente ganó es parte del diagnóstico; una configuración remota también debe versionarse y auditarse. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring construye un Environment combinando property sources con precedencia; Config Client consulta una fuente remota antes o durante el arranque según la versión. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** diferencia parámetros versionados de credenciales inyectadas por ambiente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una precedencia inesperada o la indisponibilidad central puede impedir arrancar; los secretos en Git permanecen en el historial. La regla es: Versionar configuración no sensible; inyectar credenciales por un canal especializado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica perfiles de Spring con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 4. jerarquía y precedencia de propiedades

**Problema e intuición.** jerarquía y precedencia de propiedades aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Spring combina fuentes con una precedencia definida. Saber qué fuente ganó es parte del diagnóstico; una configuración remota también debe versionarse y auditarse. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring construye un Environment combinando property sources con precedencia; Config Client consulta una fuente remota antes o durante el arranque según la versión. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** diferencia parámetros versionados de credenciales inyectadas por ambiente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una precedencia inesperada o la indisponibilidad central puede impedir arrancar; los secretos en Git permanecen en el historial. La regla es: Versionar configuración no sensible; inyectar credenciales por un canal especializado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica jerarquía y precedencia de propiedades con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 5. YAML

**Problema e intuición.** YAML aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Spring Cloud Config Server, YAML se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring construye un Environment combinando property sources con precedencia; Config Client consulta una fuente remota antes o durante el arranque según la versión. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** diferencia parámetros versionados de credenciales inyectadas por ambiente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una precedencia inesperada o la indisponibilidad central puede impedir arrancar; los secretos en Git permanecen en el historial. La regla es: Versionar configuración no sensible; inyectar credenciales por un canal especializado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica YAML con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 6. Config Server y Config Client

**Problema e intuición.** Config Server y Config Client aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Spring combina fuentes con una precedencia definida. Saber qué fuente ganó es parte del diagnóstico; una configuración remota también debe versionarse y auditarse. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring construye un Environment combinando property sources con precedencia; Config Client consulta una fuente remota antes o durante el arranque según la versión. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** diferencia parámetros versionados de credenciales inyectadas por ambiente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una precedencia inesperada o la indisponibilidad central puede impedir arrancar; los secretos en Git permanecen en el historial. La regla es: Versionar configuración no sensible; inyectar credenciales por un canal especializado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica Config Server y Config Client con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 7. backend Git

**Problema e intuición.** backend Git aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Spring Cloud Config Server, backend Git se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring construye un Environment combinando property sources con precedencia; Config Client consulta una fuente remota antes o durante el arranque según la versión. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** diferencia parámetros versionados de credenciales inyectadas por ambiente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una precedencia inesperada o la indisponibilidad central puede impedir arrancar; los secretos en Git permanecen en el historial. La regla es: Versionar configuración no sensible; inyectar credenciales por un canal especializado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica backend Git con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 8. nombres de aplicación, perfiles y labels

**Problema e intuición.** nombres de aplicación, perfiles y labels aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Spring combina fuentes con una precedencia definida. Saber qué fuente ganó es parte del diagnóstico; una configuración remota también debe versionarse y auditarse. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring construye un Environment combinando property sources con precedencia; Config Client consulta una fuente remota antes o durante el arranque según la versión. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** diferencia parámetros versionados de credenciales inyectadas por ambiente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una precedencia inesperada o la indisponibilidad central puede impedir arrancar; los secretos en Git permanecen en el historial. La regla es: Versionar configuración no sensible; inyectar credenciales por un canal especializado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica nombres de aplicación, perfiles y labels con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 9. bootstrap según la versión elegida

**Problema e intuición.** bootstrap según la versión elegida aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Spring Cloud Config Server, bootstrap según la versión elegida se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring construye un Environment combinando property sources con precedencia; Config Client consulta una fuente remota antes o durante el arranque según la versión. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** diferencia parámetros versionados de credenciales inyectadas por ambiente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una precedencia inesperada o la indisponibilidad central puede impedir arrancar; los secretos en Git permanecen en el historial. La regla es: Versionar configuración no sensible; inyectar credenciales por un canal especializado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica bootstrap según la versión elegida con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 10. fail-fast y retry

**Problema e intuición.** fail-fast y retry aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Timeout limita espera, retry repite, circuit breaker deja de insistir temporalmente y bulkhead aísla recursos. Combinarlos sin presupuesto puede amplificar una caída. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring construye un Environment combinando property sources con precedencia; Config Client consulta una fuente remota antes o durante el arranque según la versión. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** diferencia parámetros versionados de credenciales inyectadas por ambiente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una precedencia inesperada o la indisponibilidad central puede impedir arrancar; los secretos en Git permanecen en el historial. La regla es: Versionar configuración no sensible; inyectar credenciales por un canal especializado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica fail-fast y retry con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 11. actualización de configuración

**Problema e intuición.** actualización de configuración aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Spring Cloud Config Server, actualización de configuración se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring construye un Environment combinando property sources con precedencia; Config Client consulta una fuente remota antes o durante el arranque según la versión. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** diferencia parámetros versionados de credenciales inyectadas por ambiente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una precedencia inesperada o la indisponibilidad central puede impedir arrancar; los secretos en Git permanecen en el historial. La regla es: Versionar configuración no sensible; inyectar credenciales por un canal especializado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica actualización de configuración con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 12. refresh y sus límites

**Problema e intuición.** refresh y sus límites aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Spring Cloud Config Server, refresh y sus límites se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring construye un Environment combinando property sources con precedencia; Config Client consulta una fuente remota antes o durante el arranque según la versión. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** diferencia parámetros versionados de credenciales inyectadas por ambiente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una precedencia inesperada o la indisponibilidad central puede impedir arrancar; los secretos en Git permanecen en el historial. La regla es: Versionar configuración no sensible; inyectar credenciales por un canal especializado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica refresh y sus límites con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 13. configuración mutable frente a reinicio

**Problema e intuición.** configuración mutable frente a reinicio aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Spring Cloud Config Server, configuración mutable frente a reinicio se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring construye un Environment combinando property sources con precedencia; Config Client consulta una fuente remota antes o durante el arranque según la versión. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** diferencia parámetros versionados de credenciales inyectadas por ambiente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una precedencia inesperada o la indisponibilidad central puede impedir arrancar; los secretos en Git permanecen en el historial. La regla es: Versionar configuración no sensible; inyectar credenciales por un canal especializado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica configuración mutable frente a reinicio con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 14. secretos

**Problema e intuición.** secretos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Spring Cloud Config Server, secretos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring construye un Environment combinando property sources con precedencia; Config Client consulta una fuente remota antes o durante el arranque según la versión. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** diferencia parámetros versionados de credenciales inyectadas por ambiente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una precedencia inesperada o la indisponibilidad central puede impedir arrancar; los secretos en Git permanecen en el historial. La regla es: Versionar configuración no sensible; inyectar credenciales por un canal especializado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica secretos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 15. por qué un repositorio de configuración no es automáticamente un gestor de secretos

**Problema e intuición.** por qué un repositorio de configuración no es automáticamente un gestor de secretos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Spring Cloud Config Server, por qué un repositorio de configuración no es automáticamente un gestor de secretos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring construye un Environment combinando property sources con precedencia; Config Client consulta una fuente remota antes o durante el arranque según la versión. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** diferencia parámetros versionados de credenciales inyectadas por ambiente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una precedencia inesperada o la indisponibilidad central puede impedir arrancar; los secretos en Git permanecen en el historial. La regla es: Versionar configuración no sensible; inyectar credenciales por un canal especializado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica por qué un repositorio de configuración no es automáticamente un gestor de secretos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 16. cifrado cuando sea compatible

**Problema e intuición.** cifrado cuando sea compatible aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Spring Cloud Config Server, cifrado cuando sea compatible se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring construye un Environment combinando property sources con precedencia; Config Client consulta una fuente remota antes o durante el arranque según la versión. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** diferencia parámetros versionados de credenciales inyectadas por ambiente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una precedencia inesperada o la indisponibilidad central puede impedir arrancar; los secretos en Git permanecen en el historial. La regla es: Versionar configuración no sensible; inyectar credenciales por un canal especializado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica cifrado cuando sea compatible con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 17. separación entre configuración y credenciales

**Problema e intuición.** separación entre configuración y credenciales aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Spring Cloud Config Server, separación entre configuración y credenciales se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring construye un Environment combinando property sources con precedencia; Config Client consulta una fuente remota antes o durante el arranque según la versión. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** diferencia parámetros versionados de credenciales inyectadas por ambiente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una precedencia inesperada o la indisponibilidad central puede impedir arrancar; los secretos en Git permanecen en el historial. La regla es: Versionar configuración no sensible; inyectar credenciales por un canal especializado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica separación entre configuración y credenciales con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 18. auditoría

**Problema e intuición.** auditoría aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Spring Cloud Config Server, auditoría se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring construye un Environment combinando property sources con precedencia; Config Client consulta una fuente remota antes o durante el arranque según la versión. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** diferencia parámetros versionados de credenciales inyectadas por ambiente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una precedencia inesperada o la indisponibilidad central puede impedir arrancar; los secretos en Git permanecen en el historial. La regla es: Versionar configuración no sensible; inyectar credenciales por un canal especializado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica auditoría con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 19. disponibilidad del Config Server

**Problema e intuición.** disponibilidad del Config Server aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Spring combina fuentes con una precedencia definida. Saber qué fuente ganó es parte del diagnóstico; una configuración remota también debe versionarse y auditarse. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring construye un Environment combinando property sources con precedencia; Config Client consulta una fuente remota antes o durante el arranque según la versión. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** diferencia parámetros versionados de credenciales inyectadas por ambiente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una precedencia inesperada o la indisponibilidad central puede impedir arrancar; los secretos en Git permanecen en el historial. La regla es: Versionar configuración no sensible; inyectar credenciales por un canal especializado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica disponibilidad del Config Server con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 20. pruebas

**Problema e intuición.** pruebas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Spring Cloud Config Server, pruebas se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring construye un Environment combinando property sources con precedencia; Config Client consulta una fuente remota antes o durante el arranque según la versión. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** diferencia parámetros versionados de credenciales inyectadas por ambiente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una precedencia inesperada o la indisponibilidad central puede impedir arrancar; los secretos en Git permanecen en el historial. La regla es: Versionar configuración no sensible; inyectar credenciales por un canal especializado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica pruebas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 21. alternativas en contenedores y Kubernetes

**Problema e intuición.** alternativas en contenedores y Kubernetes aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Spring Cloud Config Server, alternativas en contenedores y Kubernetes se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Spring construye un Environment combinando property sources con precedencia; Config Client consulta una fuente remota antes o durante el arranque según la versión. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** diferencia parámetros versionados de credenciales inyectadas por ambiente. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Una precedencia inesperada o la indisponibilidad central puede impedir arrancar; los secretos en Git permanecen en el historial. La regla es: Versionar configuración no sensible; inyectar credenciales por un canal especializado. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica alternativas en contenedores y Kubernetes con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

## Decisiones y alternativas

1. Define primero la invariante o el riesgo.
2. Compara una opción simple con otra más operable.
3. Registra el costo introducido: latencia, acoplamiento, complejidad o mantenimiento.
4. Elige: Versionar configuración no sensible; inyectar credenciales por un canal especializado.
5. Documenta qué dato futuro haría cambiar la decisión.

## Errores frecuentes

- Copiar una anotación sin conocer quién la interpreta ni cuándo.
- Confundir el caso feliz con el contrato completo.
- Añadir infraestructura sin una necesidad observable.
- Escribir un test que replica la implementación en vez de cubrir riesgo.
- Registrar datos sensibles para facilitar debugging.
- Afirmar una garantía sin una restricción, prueba o medición que la sostenga.

## Qué ocurre en producción

Una precedencia inesperada o la indisponibilidad central puede impedir arrancar; los secretos en Git permanecen en el historial. Un profesional diseña para degradación, documenta límites, mide antes de optimizar y deja una ruta de recuperación. FintechLab usa datos ficticios y no debe conectarse con bancos ni procesar dinero real.

## Cómo reconocer que realmente lo entendiste

- Puedes explicarlo sin depender del nombre de una anotación.
- Puedes dibujar el recorrido y marcar al menos tres fallos.
- Puedes cambiar el ejemplo sin copiarlo.
- Puedes escribir una prueba que falla cuando se rompe la regla.
- Puedes nombrar una alternativa y justificar el trade-off.

## Resumen sencillo

La unidad enseña a convertir Spring Cloud Config Server en decisiones verificables. La idea profesional no es acumular herramientas, sino conectar propósito, mecanismo, fallo, evidencia y costo.

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
