# Unidad 5 — Antes, durante y después de la clase

## Cómo encaja con la diplomatura

Esta unidad es una ampliación previa. Su función es darte el Spring Boot básico que el proyecto original suponía: arranque, IoC, inyección, beans, controller/service/repository, CRUD y conexión a datos.

No necesitas esperar una clase específica para leerla. Si la diplomatura ya está en la unidad 6 o posterior, trabaja en paralelo hasta cumplir el criterio de salida y luego retoma la secuencia oficial.

## Antes de estudiar Spring Boot — guía previa

- Comprueba Java 21 con terminal y editor.
- Lee `00-objetivos-y-ruta.md`.
- Lee `00A-guia-previa-java-maven-y-proyectos.md` antes del capítulo `01`.
- Localiza el proyecto `codigo/fintechlab-inicio`, pero consulta únicamente POM, clase principal, configuración y carpetas.
- Distingue abrir, importar, compilar y ejecutar antes de pulsar Run.
- Deja controller, service, repository, entidades y pruebas para sus capítulos.
- Prepara dos preguntas: una sobre mecanismo y otra sobre una decisión.

## Antes de una clase que ya usa controllers — 60 a 90 minutos

Prioriza:

1. las partes esenciales de `00A-guia-previa-java-maven-y-proyectos.md`;
2. `01-spring-y-spring-boot-desde-cero.md` hasta el recorrido de una petición;
3. las secciones controller, service, repository, entity y DTO del capítulo 03;
4. etapas 0 a 2 del laboratorio;
5. U05-E01 o una explicación oral equivalente.

No intentes estudiar MySQL, testing y errores en la misma sesión si todavía no distingues importar de ejecutar.

## Antes de una clase que ya usa JPA o MySQL — 60 a 90 minutos

Prioriza:

1. el capítulo 02 para entender quién crea el repository;
2. el capítulo 04 hasta migraciones;
3. etapas 5 y 6 del laboratorio;
4. el recorrido repository → JPA → Hibernate → JDBC → MySQL.

Lleva el error completo si algo falla, no solo una captura de la última línea.

## Durante la clase

Divide tus notas en cuatro columnas:

| Lo que aparece | Quién lo procesa | Cuándo ocurre | Evidencia |
|---|---|---|---|
| `@RestController` | Spring MVC/escaneo | creación del contexto y mapping | endpoint registrado |
| `@Transactional` | proxy transaccional | llamada al bean | commit o rollback |
| `@Entity` | JPA/Hibernate | construcción del modelo persistente | mapping/SQL |
| propiedad YAML | Environment/autoconfiguración | arranque o binding | valor efectivo |

Registra también:

- versión exacta usada por el docente;
- perfil activo;
- comando de arranque;
- mensaje de error completo si existe;
- qué parte es Java, Spring, Boot, web o datos;
- una duda que puedas convertir en hipótesis.

Si el docente escribe muchas anotaciones seguidas, pregunta qué objeto crea o qué comportamiento activa cada una. Esa pregunta vale más que copiar la pantalla.

## Después de la clase — primera sesión

1. Reconstruye el ejemplo mínimo sin video.
2. Ejecuta una sola prueba.
3. Realiza una petición con `curl` y conserva request/response.
4. Provoca una entrada inválida.
5. Corrige tus notas de “quién/cuándo/evidencia”.
6. Escribe una diferencia entre la versión de clase y la baseline si existe.

## Después de la clase — consolidación

- Completa una etapa del laboratorio, no todas de una vez.
- Resuelve un ejercicio esencial por sesión.
- Explica en voz alta el recorrido antes de mirar el código.
- Alterna recuperación conceptual, implementación y debugging.
- Conserva commits pequeños con una conducta comprobada.
- Anota lo que H2 no demuestra y repítelo con MySQL cuando corresponda.

## Sesiones sugeridas

| Sesión | Foco | Evidencia de cierre |
|---:|---|---|
| 1 | Proyecto, compilación, Maven, POM y `target` | mapa desde `src` hasta JAR |
| 2 | IntelliJ, ejecución, Spring frente a Boot | health + diagrama de arranque |
| 3 | IoC, beans e inyección | grafo + fallo de bean reproducido |
| 4 | Controller, JSON y DTO | POST válido e inválido |
| 5 | Service, repository y CRUD | cinco operaciones y errores |
| 6 | JPA, H2 y transacción | recorrido hasta SQL |
| 7 | Pruebas | unit, web y contexto clasificadas |
| 8 | MySQL y Flyway | migración + persistencia tras reinicio |
| 9 | Diagnóstico y salida | ficha de fallo + explicación oral |

## Si estás atrasado respecto de la diplomatura

Usa este orden de rescate:

1. guía previa y capítulo 01;
2. capítulo 02;
3. capítulo 03 hasta flujo READ;
4. laboratorio con H2, etapas 0 a 3;
5. capítulo 04 y pruebas;
6. MySQL/Flyway;
7. ejercicios esenciales;
8. unidad 6.

Puedes leer conceptualmente una clase avanzada, pero no intentes implementar seguridad, microservicios o transacciones complejas sobre un grafo que todavía no entiendes.

## Preguntas útiles para el docente

- ¿Qué bean crea esta dependencia y bajo qué condición?
- ¿Esta anotación pertenece a Spring, Boot, JPA o Validation?
- ¿Qué parte del resultado es convención y cuál configuramos?
- ¿Qué ocurre si hay dos implementaciones?
- ¿El método sigue dentro de una transacción cuando se ejecuta esa línea?
- ¿Este test levanta contexto, servidor o base?
- ¿Qué evidencia muestra que usa el perfil esperado?
- ¿Cómo se comporta ante ausencia, duplicado o dependencia caída?

## Señales para detenerte y revisar

- Copias anotaciones que no puedes asociar a un procesador.
- Añades `@Autowired` hasta que deja de fallar.
- El controller conoce la entidad y el repository directamente.
- Cambias `ddl-auto` para ocultar un error de esquema.
- Solo pruebas con Postman y no puedes repetir por comando.
- Dices “funciona” sin guardar status, body o test.
- Una segunda ejecución falla y no sabes qué proceso conserva el puerto.

Detenerte en estas señales ahorra tiempo: vuelve a la primera frontera que no puedes explicar.

## Cierre de la unidad

No necesitas dominar todo Spring para continuar. Necesitas un modelo verificable: saber quién crea los objetos, cómo entra una petición, dónde vive cada responsabilidad, cómo persiste el estado y cómo distinguir un fallo. Cuando cumplas el checklist de `00-objetivos-y-ruta.md`, abre la unidad 6.
