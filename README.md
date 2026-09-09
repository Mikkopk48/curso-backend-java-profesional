# Curso profesional de Backend con Java, Spring y Microservicios

## Propósito

Este curso acompaña las unidades 5 a 27 de la diplomatura de Mikko y profundiza las competencias necesarias para diseñar, probar, depurar y mantener backend. La nueva unidad 5 enseña Spring Boot desde cero; no presupone controller/service/repository, CRUD ni conexión a datos. Sí parte de Java, POO y manejo básico de terminal.

Leer no convierte automáticamente a nadie en profesional. El progreso aparece al combinar comprensión, práctica deliberada, errores, corrección, proyectos y feedback. Aquí se ofrece teoría autocontenida, práctica sin respuestas y un sistema ficticio para producir evidencia.

## Alcance

- Proyecto Java, compilación, ejecución, Maven, `pom.xml`, `src`, `target`, JAR, classpath e IntelliJ.
- HTTP y diseño de contratos.
- Spring frente a Spring Boot, IoC, inyección, beans, configuración y arranque.
- API por capas, CRUD, DTO, validación, JPA y conexión inicial con H2 y MySQL.
- Testing unitario, integración, contrato y E2E.
- Errores, OpenAPI, Java funcional y tiempo.
- MySQL, SQL, JPA, transacciones, concurrencia e índices.
- Monolito modular, microservicios, Feign, Eureka y Config Server.
- CI/CD, Git avanzado, Map, Streams, MapStruct y Selenium.
- SOLID, refactorización, seguridad, roles, JWT y cierre profesional.

## Conocimientos previos

Se presupone Java y POO inicial, pero no Spring Boot. Lee el [puente hacia la unidad 5](00-orientacion/06-prerrequisitos-unidad-5.md). Si ya dominas Spring Boot puedes realizar el diagnóstico de salida; si empiezas desde cero, estudia la unidad 5 completa.

## Dos rutas

| Ruta | Tiempo | Uso |
|---|---:|---|
| Esencial | 243 horas | Guía previa, fundamentos Spring Boot, teoría central, ejercicios esenciales y laboratorio mínimo. |
| Profesional | 391 horas | Guía previa ampliada, Unidad 5 completa, atlas, ejercicios, debugging, review, proyecto y defensa. |

Consulta [rutas de estudio](00-orientacion/01-rutas-de-estudio.md) y el [mapa diplomatura–curso](MAPA_DIPLOMATURA_CURSO.md).

## Orden recomendado

Si no conoces Spring Boot, avanza desde la unidad 5 hasta la 27. Si ya puedes demostrar el checklist de salida de la unidad 5, comienza en la 6. La unidad 14 integra la primera mitad; la 26 prepara la entrega y la 27 evalúa transferencia. El proyecto empieza como monolito modular y separa únicamente notificaciones como laboratorio distribuido.

## Ejercicios y corrección

No hay respuestas dentro del curso. Trabaja por ID, conserva tu intento y comparte: ejercicio, código mínimo, comando, resultado, hipótesis y duda concreta. La rúbrica valora corrección, comprensión, diseño, errores, pruebas, seguridad y justificación.

## Acompañamiento de clases

Cada unidad incluye un archivo 94 con actividades antes, durante y después. El orden puede adaptarse sin romper dependencias.

## Proyecto

FintechLab usa exclusivamente datos ficticios y no procesa dinero real. La Unidad 5 incluye `fintechlab-inicio`, un CRUD aislado para aprender sin la complejidad del proyecto final. El código base avanzado incluye contrato, migraciones, tests, pipeline y un laboratorio de microservicios. Lee [arquitectura y recorrido](80-proyecto-fintechlab/01-arquitectura-y-recorrido.md) después de completar el puente básico.

## Garantías y límites

El curso ofrece cobertura curricular, ejemplos y práctica. No sustituye revisión profesional de seguridad, experiencia en producción ni feedback. La auditoría registra qué pudo comprobarse en este entorno y qué comandos deben ejecutarse localmente.

## Primer paso

Cuando tengas la próxima clase identificada, abre su archivo 94. Si comienzas sin Spring Boot, lee [cómo usar el curso](00-orientacion/00-como-usar-el-curso.md), después [Unidad 5 — objetivos y ruta](05-spring-boot-basico/00-objetivos-y-ruta.md) y comienza por `00A-guia-previa-java-maven-y-proyectos.md`.
