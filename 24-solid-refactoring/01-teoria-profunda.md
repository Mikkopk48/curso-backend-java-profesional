# Unidad 24 — SOLID y refactorización: teoría profunda

## Lugar dentro del recorrido

Esta unidad corresponde a la etapa **Mejora segura del diseño**. Parte de la API inicial de la unidad 5 y añade una capacidad profesional sin volver a explicar Java, POO o CRUD desde cero.

## Conocimientos previos

- Controladores, servicios y repositorios básicos.
- Lectura de Java y ejecución de una aplicación Spring Boot.
- Conceptos recuperados en <code>00-objetivos-y-ruta.md</code>.

## Objetivos observables

Al terminar, podrás explicar el riesgo que resuelve cada concepto, seguir su funcionamiento, aplicarlo a FintechLab, escribir evidencia y defender cuándo no conviene usarlo.

## Problema central

Refactorizar cambia estructura sin cambiar comportamiento observable. En un ejercicio pequeño puede parecer suficiente memorizar una API. En producción importan además concurrencia, datos inválidos, latencia, compatibilidad, trazabilidad y recuperación.

## Modelo mental y analogía

Reorganizar un taller reduce cruces y dependencias sin cambiar el producto.

**Límite:** Los principios orientan; aplicarlos mecánicamente puede multiplicar abstracciones inútiles.

## Funcionamiento técnico progresivo

La inyección por constructor ensambla dependencias en el composition root; interfaces y módulos controlan la dirección de compilación. El recorrido útil siempre contiene cinco preguntas: quién inicia, qué cruza la frontera, qué estado participa, dónde puede fallar y qué evidencia queda.

~~~java
final class TransferApplicationService {
    private final TransferPolicy policy;
    private final TransferRepository transfers;
    // El caso de uso coordina; las reglas viven en objetos con nombres de dominio.
}
~~~

El ejemplo es deliberadamente pequeño. No es la solución de los ejercicios: muestra vocabulario y forma, mientras las consignas cambian datos, restricciones y estructura.

## Explicación conectada: principios como fuerzas, no mandamientos

Refactorizar cambia estructura manteniendo comportamiento. Un test de caracterización crea red de seguridad. Code smells —método largo, feature envy, duplicación, parámetros excesivos— son señales para investigar, no sentencias automáticas.

SRP dice que un módulo debe responder a un actor o razón coherente de cambio. Un TransferService que valida HTTP, calcula saldo, ejecuta SQL y envía email cambia por cuatro motivos. Separar controller, caso de uso, dominio, repository y notification boundary concentra fuerzas.

OCP busca extender comportamiento sin modificar zonas estables. No significa diseñar plugins para todo: una estrategia de fees es útil si existen variantes reales. LSP exige que un subtipo preserve precondiciones, postcondiciones e invariantes esperadas; lanzar “no soportado” donde el contrato prometía operar rompe sustitución.

ISP evita clientes dependientes de métodos que no usan. Interfaces pequeñas nacen de necesidades del consumidor, no de dividir cada clase. DIP hace que políticas de alto nivel no dependan de detalles; ambas dependen de abstracciones estables. Inyección de dependencias es un mecanismo de ensamblaje, no el principio completo.

Composición suele ser más flexible que herencia porque delega capacidades sin heredar contrato accidental. Patrones de diseño dan vocabulario, pero aplicarlos antes de una fuerza real crea sobreingeniería.

Secuencia: identifica smell y riesgo, añade prueba, realiza cambio pequeño, ejecuta, revisa nombre y commit. Una aparente violación puede ser un compromiso razonable en código local estable. La IA puede sugerir extracción, pero revisa semántica, APIs inventadas, cambios de rendimiento y cobertura; nunca aceptes un refactor masivo sin pasos.

## Atlas completo del temario oficial

Las subsecciones siguientes cubren cada punto del programa. La etiqueta **Diplomatura** identifica el núcleo oficial; las decisiones de producción y el vínculo con FintechLab constituyen profundización y ampliación profesional.

### 1. cohesión

**Problema e intuición.** cohesión aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En SOLID y refactorización, cohesión se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La inyección por constructor ensambla dependencias en el composition root; interfaces y módulos controlan la dirección de compilación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** refactoriza servicios sin alterar las reglas de transferencia cubiertas por pruebas. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Abstraer sin necesidad dispersa comportamiento y eleva el costo de navegar el código. La regla es: Partir de un smell y un cambio probable, respaldar con pruebas y mejorar en pasos pequeños. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica cohesión con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 2. acoplamiento

**Problema e intuición.** acoplamiento aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En SOLID y refactorización, acoplamiento se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La inyección por constructor ensambla dependencias en el composition root; interfaces y módulos controlan la dirección de compilación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** refactoriza servicios sin alterar las reglas de transferencia cubiertas por pruebas. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Abstraer sin necesidad dispersa comportamiento y eleva el costo de navegar el código. La regla es: Partir de un smell y un cambio probable, respaldar con pruebas y mejorar en pasos pequeños. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica acoplamiento con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 3. code smells

**Problema e intuición.** code smells aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En SOLID y refactorización, code smells se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La inyección por constructor ensambla dependencias en el composition root; interfaces y módulos controlan la dirección de compilación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** refactoriza servicios sin alterar las reglas de transferencia cubiertas por pruebas. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Abstraer sin necesidad dispersa comportamiento y eleva el costo de navegar el código. La regla es: Partir de un smell y un cambio probable, respaldar con pruebas y mejorar en pasos pequeños. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica code smells con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 4. refactorización segura

**Problema e intuición.** refactorización segura aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En SOLID y refactorización, refactorización segura se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La inyección por constructor ensambla dependencias en el composition root; interfaces y módulos controlan la dirección de compilación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** refactoriza servicios sin alterar las reglas de transferencia cubiertas por pruebas. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Abstraer sin necesidad dispersa comportamiento y eleva el costo de navegar el código. La regla es: Partir de un smell y un cambio probable, respaldar con pruebas y mejorar en pasos pequeños. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica refactorización segura con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 5. Single Responsibility Principle

**Problema e intuición.** Single Responsibility Principle aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** SOLID describe fuerzas de cambio, sustitución y dependencias. No exige una interfaz por clase ni una proliferación de capas. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La inyección por constructor ensambla dependencias en el composition root; interfaces y módulos controlan la dirección de compilación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** refactoriza servicios sin alterar las reglas de transferencia cubiertas por pruebas. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Abstraer sin necesidad dispersa comportamiento y eleva el costo de navegar el código. La regla es: Partir de un smell y un cambio probable, respaldar con pruebas y mejorar en pasos pequeños. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica Single Responsibility Principle con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 6. Open/Closed Principle

**Problema e intuición.** Open/Closed Principle aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** SOLID describe fuerzas de cambio, sustitución y dependencias. No exige una interfaz por clase ni una proliferación de capas. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La inyección por constructor ensambla dependencias en el composition root; interfaces y módulos controlan la dirección de compilación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** refactoriza servicios sin alterar las reglas de transferencia cubiertas por pruebas. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Abstraer sin necesidad dispersa comportamiento y eleva el costo de navegar el código. La regla es: Partir de un smell y un cambio probable, respaldar con pruebas y mejorar en pasos pequeños. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica Open/Closed Principle con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 7. Liskov Substitution Principle

**Problema e intuición.** Liskov Substitution Principle aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** SOLID describe fuerzas de cambio, sustitución y dependencias. No exige una interfaz por clase ni una proliferación de capas. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La inyección por constructor ensambla dependencias en el composition root; interfaces y módulos controlan la dirección de compilación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** refactoriza servicios sin alterar las reglas de transferencia cubiertas por pruebas. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Abstraer sin necesidad dispersa comportamiento y eleva el costo de navegar el código. La regla es: Partir de un smell y un cambio probable, respaldar con pruebas y mejorar en pasos pequeños. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica Liskov Substitution Principle con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 8. Interface Segregation Principle

**Problema e intuición.** Interface Segregation Principle aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** SOLID describe fuerzas de cambio, sustitución y dependencias. No exige una interfaz por clase ni una proliferación de capas. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La inyección por constructor ensambla dependencias en el composition root; interfaces y módulos controlan la dirección de compilación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** refactoriza servicios sin alterar las reglas de transferencia cubiertas por pruebas. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Abstraer sin necesidad dispersa comportamiento y eleva el costo de navegar el código. La regla es: Partir de un smell y un cambio probable, respaldar con pruebas y mejorar en pasos pequeños. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica Interface Segregation Principle con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 9. Dependency Inversion Principle

**Problema e intuición.** Dependency Inversion Principle aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** SOLID describe fuerzas de cambio, sustitución y dependencias. No exige una interfaz por clase ni una proliferación de capas. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La inyección por constructor ensambla dependencias en el composition root; interfaces y módulos controlan la dirección de compilación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** refactoriza servicios sin alterar las reglas de transferencia cubiertas por pruebas. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Abstraer sin necesidad dispersa comportamiento y eleva el costo de navegar el código. La regla es: Partir de un smell y un cambio probable, respaldar con pruebas y mejorar en pasos pequeños. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica Dependency Inversion Principle con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 10. diferencias entre inversión e inyección de dependencias

**Problema e intuición.** diferencias entre inversión e inyección de dependencias aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En SOLID y refactorización, diferencias entre inversión e inyección de dependencias se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La inyección por constructor ensambla dependencias en el composition root; interfaces y módulos controlan la dirección de compilación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** refactoriza servicios sin alterar las reglas de transferencia cubiertas por pruebas. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Abstraer sin necesidad dispersa comportamiento y eleva el costo de navegar el código. La regla es: Partir de un smell y un cambio probable, respaldar con pruebas y mejorar en pasos pequeños. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica diferencias entre inversión e inyección de dependencias con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 11. composición frente a herencia

**Problema e intuición.** composición frente a herencia aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En SOLID y refactorización, composición frente a herencia se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La inyección por constructor ensambla dependencias en el composition root; interfaces y módulos controlan la dirección de compilación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** refactoriza servicios sin alterar las reglas de transferencia cubiertas por pruebas. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Abstraer sin necesidad dispersa comportamiento y eleva el costo de navegar el código. La regla es: Partir de un smell y un cambio probable, respaldar con pruebas y mejorar en pasos pequeños. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica composición frente a herencia con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 12. diseño orientado a comportamiento

**Problema e intuición.** diseño orientado a comportamiento aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En SOLID y refactorización, diseño orientado a comportamiento se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La inyección por constructor ensambla dependencias en el composition root; interfaces y módulos controlan la dirección de compilación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** refactoriza servicios sin alterar las reglas de transferencia cubiertas por pruebas. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Abstraer sin necesidad dispersa comportamiento y eleva el costo de navegar el código. La regla es: Partir de un smell y un cambio probable, respaldar con pruebas y mejorar en pasos pequeños. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica diseño orientado a comportamiento con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 13. interfaces pequeñas

**Problema e intuición.** interfaces pequeñas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En SOLID y refactorización, interfaces pequeñas se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La inyección por constructor ensambla dependencias en el composition root; interfaces y módulos controlan la dirección de compilación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** refactoriza servicios sin alterar las reglas de transferencia cubiertas por pruebas. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Abstraer sin necesidad dispersa comportamiento y eleva el costo de navegar el código. La regla es: Partir de un smell y un cambio probable, respaldar con pruebas y mejorar en pasos pequeños. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica interfaces pequeñas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 14. límites de módulos

**Problema e intuición.** límites de módulos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En SOLID y refactorización, límites de módulos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La inyección por constructor ensambla dependencias en el composition root; interfaces y módulos controlan la dirección de compilación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** refactoriza servicios sin alterar las reglas de transferencia cubiertas por pruebas. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Abstraer sin necesidad dispersa comportamiento y eleva el costo de navegar el código. La regla es: Partir de un smell y un cambio probable, respaldar con pruebas y mejorar en pasos pequeños. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica límites de módulos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 15. pruebas como red de seguridad

**Problema e intuición.** pruebas como red de seguridad aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En SOLID y refactorización, pruebas como red de seguridad se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La inyección por constructor ensambla dependencias en el composition root; interfaces y módulos controlan la dirección de compilación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** refactoriza servicios sin alterar las reglas de transferencia cubiertas por pruebas. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Abstraer sin necesidad dispersa comportamiento y eleva el costo de navegar el código. La regla es: Partir de un smell y un cambio probable, respaldar con pruebas y mejorar en pasos pequeños. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica pruebas como red de seguridad con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 16. aplicación a controller, service, repository y clientes externos

**Problema e intuición.** aplicación a controller, service, repository y clientes externos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En SOLID y refactorización, aplicación a controller, service, repository y clientes externos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La inyección por constructor ensambla dependencias en el composition root; interfaces y módulos controlan la dirección de compilación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** refactoriza servicios sin alterar las reglas de transferencia cubiertas por pruebas. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Abstraer sin necesidad dispersa comportamiento y eleva el costo de navegar el código. La regla es: Partir de un smell y un cambio probable, respaldar con pruebas y mejorar en pasos pequeños. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica aplicación a controller, service, repository y clientes externos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 17. relación con patrones de diseño

**Problema e intuición.** relación con patrones de diseño aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En SOLID y refactorización, relación con patrones de diseño se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La inyección por constructor ensambla dependencias en el composition root; interfaces y módulos controlan la dirección de compilación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** refactoriza servicios sin alterar las reglas de transferencia cubiertas por pruebas. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Abstraer sin necesidad dispersa comportamiento y eleva el costo de navegar el código. La regla es: Partir de un smell y un cambio probable, respaldar con pruebas y mejorar en pasos pequeños. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica relación con patrones de diseño con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 18. sobreingeniería

**Problema e intuición.** sobreingeniería aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En SOLID y refactorización, sobreingeniería se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La inyección por constructor ensambla dependencias en el composition root; interfaces y módulos controlan la dirección de compilación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** refactoriza servicios sin alterar las reglas de transferencia cubiertas por pruebas. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Abstraer sin necesidad dispersa comportamiento y eleva el costo de navegar el código. La regla es: Partir de un smell y un cambio probable, respaldar con pruebas y mejorar en pasos pequeños. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica sobreingeniería con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 19. abstracciones prematuras

**Problema e intuición.** abstracciones prematuras aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En SOLID y refactorización, abstracciones prematuras se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La inyección por constructor ensambla dependencias en el composition root; interfaces y módulos controlan la dirección de compilación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** refactoriza servicios sin alterar las reglas de transferencia cubiertas por pruebas. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Abstraer sin necesidad dispersa comportamiento y eleva el costo de navegar el código. La regla es: Partir de un smell y un cambio probable, respaldar con pruebas y mejorar en pasos pequeños. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica abstracciones prematuras con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 20. cuándo una violación aparente puede ser un compromiso aceptable

**Problema e intuición.** cuándo una violación aparente puede ser un compromiso aceptable aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En SOLID y refactorización, cuándo una violación aparente puede ser un compromiso aceptable se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La inyección por constructor ensambla dependencias en el composition root; interfaces y módulos controlan la dirección de compilación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** refactoriza servicios sin alterar las reglas de transferencia cubiertas por pruebas. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Abstraer sin necesidad dispersa comportamiento y eleva el costo de navegar el código. La regla es: Partir de un smell y un cambio probable, respaldar con pruebas y mejorar en pasos pequeños. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica cuándo una violación aparente puede ser un compromiso aceptable con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 21. uso de IA para sugerir refactorizaciones

**Problema e intuición.** uso de IA para sugerir refactorizaciones aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En SOLID y refactorización, uso de IA para sugerir refactorizaciones se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La inyección por constructor ensambla dependencias en el composition root; interfaces y módulos controlan la dirección de compilación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** refactoriza servicios sin alterar las reglas de transferencia cubiertas por pruebas. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Abstraer sin necesidad dispersa comportamiento y eleva el costo de navegar el código. La regla es: Partir de un smell y un cambio probable, respaldar con pruebas y mejorar en pasos pequeños. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica uso de IA para sugerir refactorizaciones con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 22. revisión crítica de cambios generados por IA

**Problema e intuición.** revisión crítica de cambios generados por IA aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En SOLID y refactorización, revisión crítica de cambios generados por IA se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La inyección por constructor ensambla dependencias en el composition root; interfaces y módulos controlan la dirección de compilación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** refactoriza servicios sin alterar las reglas de transferencia cubiertas por pruebas. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Abstraer sin necesidad dispersa comportamiento y eleva el costo de navegar el código. La regla es: Partir de un smell y un cambio probable, respaldar con pruebas y mejorar en pasos pequeños. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica revisión crítica de cambios generados por IA con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 23. definición sencilla

**Problema e intuición.** definición sencilla aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En SOLID y refactorización, definición sencilla se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La inyección por constructor ensambla dependencias en el composition root; interfaces y módulos controlan la dirección de compilación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** refactoriza servicios sin alterar las reglas de transferencia cubiertas por pruebas. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Abstraer sin necesidad dispersa comportamiento y eleva el costo de navegar el código. La regla es: Partir de un smell y un cambio probable, respaldar con pruebas y mejorar en pasos pequeños. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica definición sencilla con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 24. definición precisa

**Problema e intuición.** definición precisa aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En SOLID y refactorización, definición precisa se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La inyección por constructor ensambla dependencias en el composition root; interfaces y módulos controlan la dirección de compilación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** refactoriza servicios sin alterar las reglas de transferencia cubiertas por pruebas. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Abstraer sin necesidad dispersa comportamiento y eleva el costo de navegar el código. La regla es: Partir de un smell y un cambio probable, respaldar con pruebas y mejorar en pasos pequeños. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica definición precisa con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 25. ejemplo que lo viola

**Problema e intuición.** ejemplo que lo viola aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En SOLID y refactorización, ejemplo que lo viola se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La inyección por constructor ensambla dependencias en el composition root; interfaces y módulos controlan la dirección de compilación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** refactoriza servicios sin alterar las reglas de transferencia cubiertas por pruebas. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Abstraer sin necesidad dispersa comportamiento y eleva el costo de navegar el código. La regla es: Partir de un smell y un cambio probable, respaldar con pruebas y mejorar en pasos pequeños. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica ejemplo que lo viola con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 26. consecuencias

**Problema e intuición.** consecuencias aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En SOLID y refactorización, consecuencias se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La inyección por constructor ensambla dependencias en el composition root; interfaces y módulos controlan la dirección de compilación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** refactoriza servicios sin alterar las reglas de transferencia cubiertas por pruebas. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Abstraer sin necesidad dispersa comportamiento y eleva el costo de navegar el código. La regla es: Partir de un smell y un cambio probable, respaldar con pruebas y mejorar en pasos pequeños. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica consecuencias con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 27. proceso de refactorización

**Problema e intuición.** proceso de refactorización aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En SOLID y refactorización, proceso de refactorización se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La inyección por constructor ensambla dependencias en el composition root; interfaces y módulos controlan la dirección de compilación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** refactoriza servicios sin alterar las reglas de transferencia cubiertas por pruebas. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Abstraer sin necesidad dispersa comportamiento y eleva el costo de navegar el código. La regla es: Partir de un smell y un cambio probable, respaldar con pruebas y mejorar en pasos pequeños. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica proceso de refactorización con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 28. versión mejorada

**Problema e intuición.** versión mejorada aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En SOLID y refactorización, versión mejorada se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La inyección por constructor ensambla dependencias en el composition root; interfaces y módulos controlan la dirección de compilación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** refactoriza servicios sin alterar las reglas de transferencia cubiertas por pruebas. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Abstraer sin necesidad dispersa comportamiento y eleva el costo de navegar el código. La regla es: Partir de un smell y un cambio probable, respaldar con pruebas y mejorar en pasos pequeños. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica versión mejorada con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 29. límites y malinterpretaciones

**Problema e intuición.** límites y malinterpretaciones aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En SOLID y refactorización, límites y malinterpretaciones se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** La inyección por constructor ensambla dependencias en el composition root; interfaces y módulos controlan la dirección de compilación. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** refactoriza servicios sin alterar las reglas de transferencia cubiertas por pruebas. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Abstraer sin necesidad dispersa comportamiento y eleva el costo de navegar el código. La regla es: Partir de un smell y un cambio probable, respaldar con pruebas y mejorar en pasos pequeños. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica límites y malinterpretaciones con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

## Decisiones y alternativas

1. Define primero la invariante o el riesgo.
2. Compara una opción simple con otra más operable.
3. Registra el costo introducido: latencia, acoplamiento, complejidad o mantenimiento.
4. Elige: Partir de un smell y un cambio probable, respaldar con pruebas y mejorar en pasos pequeños.
5. Documenta qué dato futuro haría cambiar la decisión.

## Errores frecuentes

- Copiar una anotación sin conocer quién la interpreta ni cuándo.
- Confundir el caso feliz con el contrato completo.
- Añadir infraestructura sin una necesidad observable.
- Escribir un test que replica la implementación en vez de cubrir riesgo.
- Registrar datos sensibles para facilitar debugging.
- Afirmar una garantía sin una restricción, prueba o medición que la sostenga.

## Qué ocurre en producción

Abstraer sin necesidad dispersa comportamiento y eleva el costo de navegar el código. Un profesional diseña para degradación, documenta límites, mide antes de optimizar y deja una ruta de recuperación. FintechLab usa datos ficticios y no debe conectarse con bancos ni procesar dinero real.

## Cómo reconocer que realmente lo entendiste

- Puedes explicarlo sin depender del nombre de una anotación.
- Puedes dibujar el recorrido y marcar al menos tres fallos.
- Puedes cambiar el ejemplo sin copiarlo.
- Puedes escribir una prueba que falla cuando se rompe la regla.
- Puedes nombrar una alternativa y justificar el trade-off.

## Resumen sencillo

La unidad enseña a convertir SOLID y refactorización en decisiones verificables. La idea profesional no es acumular herramientas, sino conectar propósito, mecanismo, fallo, evidencia y costo.

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
