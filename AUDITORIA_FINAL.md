# Auditoría final

## Resultado

El curso ampliado queda **validado estructuralmente con limitaciones de ejecución declaradas**. La Unidad 5 elimina la antigua suposición de Spring Boot previo y ahora comienza con una guía lenta de Java, proyectos y Maven antes de entrar en Spring. Después añade teoría desde cero, práctica sin respuestas y un proyecto de referencia independiente. La auditoría no inventa un build completo: distingue lo comprobado aquí de lo que requiere JDK 21, acceso a Maven Central y Docker.

## Métricas reales

| Métrica | Resultado |
|---|---:|
| Archivos Markdown | 129 |
| Archivos funcionales sin metadatos Git | 232 |
| Metadatos internos del repositorio de configuración | 27 |
| Archivos totales dentro de la carpeta | 259 |
| Palabras aproximadas en Markdown | 319.141 |
| Tamaño sin comprimir | 2,24 MiB |
| Unidades | 23, desde la 5 hasta la 27 |
| Ejercicios únicos | 192 |
| Ejercicios nuevos de Unidad 5 | 16 |
| Ruta esencial | 243 horas |
| Ruta profesional | 391 horas |
| Correspondencias curriculares y fundacionales | 658 |

## Controles realizados

- **Aprobado — Expansión Markdown solicitada:** 129 archivos. La entrega anterior cumplía el rango original de 80–120; los diez Markdown adicionales corresponden a la Unidad 5, su guía previa y su proyecto ejecutable pedidos posteriormente, por lo que se conserva contenido útil en vez de recortarlo para mantener un límite anterior.
- **Aprobado — Archivos funcionales:** 232 archivos sin metadatos Git.
- **Aprobado — Archivos vacíos:** todos poseen contenido.
- **Aprobado — Estructura de Unidad 5:** objetivos, guía previa de Java/proyectos/Maven, cuatro capítulos progresivos, laboratorio, ejercicios/rúbrica, guía de clase y proyecto ejecutable.
- **Aprobado — Estructura de unidades 6–27:** 22 unidades con teoría, práctica y guía de clase.
- **Aprobado — Ejercicios identificados:** 192 encabezados y 192 IDs únicos; U05-E01 a U05-E16 están completos y no se duplican.
- **Aprobado — Soluciones bloqueadas:** directorio `99-soluciones-bloqueadas/README.md`; filtraciones detectadas=0.
- **Aprobado — Marcadores de contenido incompleto:** ninguno.
- **Aprobado — Enlaces internos:** todos los destinos locales existen.
- **Aprobado — Cercas de código:** Markdown sin bloques desbalanceados.
- **Aprobado — Manifiesto de estado:** 129 esperados; ausentes=0; inexistentes=0.
- **Aprobado — Mapa curricular:** 608 correspondencias del temario original más 50 correspondencias fundacionales de la Unidad 5.
- **Aprobado — Sintaxis YAML:** 15 archivos; errores=0.
- **Aprobado — Sintaxis XML/POM:** 7 archivos; errores=0.
- **Aprobado — Contrato OpenAPI:** versión 3.1.0; 4 paths; 7 schemas.
- **Aprobado — Migraciones estructurales:** 2 archivos, 6 tablas, 12 restricciones nombradas; paréntesis desbalanceados=0.
- **Aprobado — Sintaxis Java:** 66 fuentes analizadas con el parser de `jdk.compiler`; errores sintácticos=0.
- **Aprobado — Scripts POSIX:** 3 Maven Wrappers; `bash -n` sin errores.
- **Aprobado — Coherencia de versiones:** política, proyecto inicial, monolito y laboratorio comparados.
- **Aprobado — Escaneo de secretos:** sin firmas de claves privadas, tokens de proveedores ni JWT incrustados; solo valores `local_dev_only` explícitos para Compose.
- **Aprobado — Backend Git de Config Server:** commit `81be87c361fe8ad7f3d7bb404454132f4abb6af6`.
- **Corregido — Código preexistente:** el literal JSON de `OutboxEvent.transferCompleted` tenía comillas sin escapar; quedó sintácticamente válido y fue incluido en el control de las 66 fuentes.

## Compilación y pruebas

Se intentó `./mvnw -q test` en `05-spring-boot-basico/codigo/fintechlab-inicio`. El Wrapper pudo preparar Maven, pero el entorno no pudo resolver el parent de Spring Boot desde Maven Central por restricción de red/DNS. Además, el runtime disponible es Java 17 y la baseline del curso exige JDK 21. Por estas razones no se declara exitoso ningún build Spring ni una ejecución de H2/MySQL.

Sí se realizaron estas comprobaciones independientes de dependencias remotas:

- parseo sintáctico real de las 66 fuentes mediante `JavacTask.parse` del módulo `jdk.compiler`;
- parseo de todos los POM/XML y YAML;
- validación estructural de migraciones y OpenAPI;
- validación de enlaces, IDs, manifiesto, cercas y scripts.

Queda pendiente en una máquina compatible: resolución semántica completa, compilación con JDK 21, pruebas Spring, Flyway contra MySQL, Testcontainers y Selenium.

## Seguridad básica

El escaneo no encontró firmas de claves privadas, tokens de proveedores ni JWT incrustados. Las contraseñas `local_dev_only` y `local_root_only` son valores ficticios exclusivos de Compose y deben sustituirse por variables fuera de un entorno local. El perfil seguro del monolito exige issuer externo, valida audience y no se presenta como autenticación lista para producción.

El proyecto inicial usa correos `.test`, no expone entidades, valida entrada y no registra bodies. Actuator limita la exposición a health e info. Estas decisiones reducen riesgo de aprendizaje, pero no reemplazan la Unidad 25 ni una revisión profesional.

## Cobertura y continuidad

La Unidad 5 comienza explicando qué son un proyecto, el código fuente, compilar, ejecutar, probar y empaquetar; luego cubre JDK/JVM, Maven, `pom.xml`, dependencias, repositorios, `src`, `target`, JAR, classpath, IntelliJ y la inspección guiada del laboratorio. Desde esa base aporta Spring frente a Spring Boot, arranque, autoconfiguración, IoC/DI, beans, configuración, perfiles, REST básico, capas, CRUD, DTO, validación, errores, JPA, H2, MySQL, Flyway, transacciones, pruebas y diagnóstico. Su proyecto `fintechlab-inicio` permite aprender sin enfrentarse todavía al monolito avanzado.

El mapa conserva las 608 correspondencias del temario oficial y añade 50 fundacionales. Las unidades 13, 15, 24 y 25 mantienen profundización; 14 y 27 incluyen evaluaciones de 100 puntos; 26 contiene checklists de cierre.

## Soluciones

Las respuestas continúan bloqueadas. El directorio reservado contiene únicamente su política y ningún archivo evaluable incluye una clave de corrección. El CRUD de clientes es un ejemplo didáctico resuelto; los ejercicios cambian a beneficiarios, configuración, diagnóstico y revisión para impedir una copia nominal.

## Reproducción externa

En una máquina con JDK 21 y acceso a dependencias:

1. Ejecuta `./mvnw clean verify` en `05-spring-boot-basico/codigo/fintechlab-inicio` con H2.
2. Inicia su MySQL con Compose y repite usando el perfil `mysql`.
3. Ejecuta `./mvnw clean verify` en el monolito.
4. Ejecuta `./mvnw clean verify` en el laboratorio de microservicios.

La CI incluida cubre los proyectos avanzados en un runner compatible. Si cualquier comando falla, conserva el log y actualiza esta auditoría con el resultado real; no sustituyas la ejecución por una afirmación manual.
