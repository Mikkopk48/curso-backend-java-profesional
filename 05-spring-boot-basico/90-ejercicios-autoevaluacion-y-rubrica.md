# Unidad 5 — Ejercicios, autoevaluación y rúbrica

## Política

Estos ejercicios no contienen soluciones. No tienes que resolverlos ni compartir respuestas mientras el curso se completa. Cuando estudies la unidad, trabaja por ID y conserva código, comandos, resultados e hipótesis.

Los ejercicios usan problemas distintos del CRUD de clientes resuelto. Puedes consultar los capítulos para recordar mecanismos, pero no copies y renombres archivos: cada consigna añade una decisión que debes justificar.

## Forma de entrega sugerida

Para cada ejercicio conserva:

- ID y versión del entorno;
- interpretación del comportamiento esperado;
- archivos modificados;
- comando mínimo de comprobación;
- salida relevante, sin secretos;
- un caso feliz, un borde y, cuando corresponda, un fallo;
- una limitación y una siguiente mejora.

## Ruta esencial

Resuelve U05-E01 a U05-E08 y la prueba de salida. La ruta profesional añade U05-E09 a U05-E16.

## U05-E01 — Explicar el arranque sin decir “magia”

- **Tipo:** explicación y diagrama.
- **Dificultad:** 1/5.
- **Tiempo:** 25 minutos.

### Consigna

Explica qué ocurre desde que ejecutas `main` hasta que health responde. Distingue JVM, Maven, Spring Framework, Spring Boot, contexto y servidor embebido.

### Criterios de aceptación

- El diagrama tiene entradas y salidas, no solo nombres.
- `@SpringBootApplication` se descompone en sus tres funciones.
- Se distingue abrir/importar el proyecto de ejecutarlo.
- Se identifica un fallo posible antes y otro después de iniciar el servidor.
- La explicación cabe en tres minutos y usa un ejemplo propio.

### Pistas graduadas

1. Empieza en `public static void main`.
2. Pregunta cuándo existe el `ApplicationContext`.
3. Busca qué evidencia confirma que el puerto quedó escuchando.

## U05-E02 — Auditar un POM pequeño

- **Tipo:** lectura técnica.
- **Dificultad:** 1/5.
- **Tiempo:** 35 minutos.

### Consigna

Toma el POM del proyecto inicial y crea una tabla con cada dependencia directa: propósito, fase donde se usa, una capacidad que activa y un riesgo de eliminarla. Después identifica qué versiones administra el parent y cuáles aparecen explícitas.

### Restricciones

- No ejecutes una actualización de versiones.
- No confundas dependencia directa con transitiva.
- No definas un starter como “paquete que hace todo”.

### Criterios de aceptación

- Se explican Web, Validation, Data JPA, Actuator, H2, MySQL, Flyway y Test.
- Los scopes `runtime` y `test` se interpretan correctamente.
- `dependency:tree` se usa como evidencia.
- Se señala por qué añadir versiones aisladas puede romper compatibilidad.

### Pistas graduadas

1. Lee `<dependencies>` y `<parent>` por separado.
2. Ejecuta `./mvnw dependency:tree`.
3. Pregunta qué clases necesita tu código al compilar frente a la aplicación al arrancar.

## U05-E03 — Endpoint de información de aprendizaje

- **Tipo:** implementación.
- **Dificultad:** 2/5.
- **Tiempo:** 45 minutos.

### Escenario

La API necesita `GET /api/learning/info` con nombre de aplicación, entorno de aprendizaje y versión declarada. Los valores deben provenir de configuración tipada, no estar escritos dentro del controller.

### Requisitos

- Define propiedades con prefijo propio.
- Inyecta por constructor.
- Devuelve un DTO, no un `Map<String, Object>`.
- Permite cambiar el nombre del entorno mediante variable externa.
- No expongas todas las propiedades del proceso.

### Criterios de aceptación

- El contexto arranca cuando existen los valores requeridos.
- Una prueba web verifica status, media type y tres campos.
- El controller no usa `Environment` para buscar cadenas arbitrarias.
- La documentación indica cómo sobrescribir solo el valor permitido.

### Pistas graduadas

1. Agrupa valores relacionados en un record de configuración.
2. Revisa cómo se registra `@ConfigurationProperties`.
3. Una prueba puede suministrar propiedades sin depender de tu terminal.

## U05-E04 — Dibujar y construir un grafo de beans

- **Tipo:** diseño y Java.
- **Dificultad:** 2/5.
- **Tiempo:** 50 minutos.

### Escenario

Un caso de uso `RegisterBeneficiaryService` necesita un repository, un reloj y un generador de identificadores. El reloj y el generador pertenecen a la biblioteca estándar y deben ser configurables para pruebas.

### Requisitos

- Dibuja el grafo con tipos e implementaciones.
- Declara beans explícitos cuando no puedas anotar la clase externa.
- Usa constructor y campos `final`.
- Construye el service manualmente en una prueba Java sin contexto.
- Evita crear interfaces que no representen una frontera útil.

### Criterios de aceptación

- El contexto resuelve exactamente un candidato por dependencia.
- La prueba usa tiempo e ID deterministas.
- Ningún campo usa inyección directa.
- Se explica qué objeto crea Spring y cuál crea la prueba.

### Pistas graduadas

1. `Clock` puede ser un bean.
2. Una interfaz funcional pequeña puede representar la generación de ID.
3. La prueba no necesita `@SpringBootTest` si construye el service.

## U05-E05 — Diagnosticar tres fallos de contenedor

- **Tipo:** debugging.
- **Dificultad:** 2/5.
- **Tiempo:** 60 minutos.

### Escenario

Recibes tres ramas rotas: una clase está fuera del escaneo, dos implementaciones compiten y dos services dependen entre sí.

### Requisitos

- Reproduce cada fallo de forma aislada.
- Conserva el bloque de error mínimo útil.
- Dibuja el grafo real.
- Corrige el diseño, no solo el síntoma.
- Restaura un test de contexto verde después de cada caso.

### Restricciones

- No habilites referencias circulares.
- No uses `@Lazy` como primera corrección.
- No marques candidatos al azar con `@Primary`.

### Criterios de aceptación

- Cada informe distingue cero, varios y ciclo de candidatos.
- La solución de ambigüedad expresa una decisión verificable.
- La solución del ciclo reduce acoplamiento conceptual.
- Los tres cambios pueden explicarse desde el constructor que fallaba.

### Pistas graduadas

1. Sigue las flechas de constructor.
2. Lee tipo requerido y candidatos encontrados.
3. Para el ciclo, busca una responsabilidad que coordine a ambos.

## U05-E06 — Configuración por ambientes sin secretos

- **Tipo:** configuración y seguridad básica.
- **Dificultad:** 2/5.
- **Tiempo:** 55 minutos.

### Escenario

El equipo necesita un perfil `demo` con puerto diferente y un perfil `mysql` con datasource externo. Un compañero agregó credenciales reales al YAML.

### Requisitos

- Sustituye credenciales por variables obligatorias o valores locales ficticios explícitos.
- Documenta fuentes y sobrescrituras.
- Comprueba el perfil activo en el arranque.
- Añade un ejemplo de variables sin incluir secretos.
- Evita duplicar configuración común.

### Criterios de aceptación

- La configuración base funciona con H2.
- `demo` cambia solo lo necesario.
- MySQL no necesita editar archivos versionados.
- Una variable de mayor precedencia se demuestra con evidencia.
- El historial final no contiene el secreto recibido.

### Pistas graduadas

1. Usa `application-{perfil}.yml` para diferencias.
2. Las variables `SPRING_*` siguen vinculación relajada.
3. Un secreto filtrado requiere rotación; borrarlo del último commit no basta en un repositorio publicado.

## U05-E07 — Asignar responsabilidades por capa

- **Tipo:** revisión de diseño.
- **Dificultad:** 2/5.
- **Tiempo:** 40 minutos.

### Consigna

Clasifica estas acciones y justifica el límite: leer `Content-Type`, normalizar un alias, comprobar unicidad, iniciar transacción, buscar por ID, elegir status, mapear entidad a salida, generar SQL, validar `@Email`, impedir borrar un beneficiario usado y registrar un evento seguro.

Después recibe un método de controller que hace todas las acciones y propone un plan de refactorización en commits pequeños.

### Criterios de aceptación

- No se confunde validación de forma con regla de negocio.
- El repository no conoce status HTTP.
- El controller no abre transacciones de negocio.
- La base conserva restricciones de integridad.
- El plan mantiene comportamiento verificable entre commits.

### Pistas graduadas

1. Pregunta qué frontera conoce cada dato.
2. Ubica primero HTTP y persistencia; después el caso de uso.
3. Una responsabilidad transversal puede necesitar un adaptador, no una capa nueva por nombre.

## U05-E08 — Primer recurso de beneficiarios

- **Tipo:** implementación integral inicial.
- **Dificultad:** 3/5.
- **Tiempo:** 2 horas.

### Escenario

FintechLab necesita registrar beneficiarios ficticios con `id`, `displayName` y `alias`. Implementa creación, listado y consulta individual con H2.

### Requisitos

- Package por capacidad `beneficiary`.
- DTO diferentes para entrada y salida.
- Alias obligatorio, normalizado y único sin distinguir mayúsculas.
- ID generado por el servidor.
- Controller, service, repository y entity con responsabilidades separadas.
- `201` con `Location`, `200` y `404` controlado.
- Una prueba de service y una web.

### Restricciones

- No copies la entidad de cliente cambiando nombres sin revisar invariantes.
- No devuelvas la entidad.
- No uses datos personales reales.
- No implementes todavía update/delete.

### Criterios de aceptación

- El flujo feliz es reproducible con `curl`.
- Alias duplicado produce conflicto y la base tiene restricción.
- La lista vacía es una respuesta válida.
- Las pruebas fallan si se elimina la regla principal.
- Puedes recorrer la petición en un diagrama.

### Pistas graduadas

1. Define primero los mensajes HTTP y el esquema de tabla.

2. Diseña el constructor de la entidad para impedir campos obligatorios ausentes.

3. Haz visible la normalización en una sola función y protege además con índice único.

## U05-E09 — Completar UPDATE y DELETE con decisiones explícitas

- **Tipo:** implementación y contrato.
- **Dificultad:** 3/5.
- **Tiempo:** 90 minutos.

### Escenario

Extiende beneficiarios con modificación completa y desactivación. El historial exige conservar el registro; por tanto, DELETE no puede borrar físicamente.

### Requisitos

- PUT recibe todos los campos editables.
- DELETE cambia a estado inactivo de forma repetible.
- Consultas normales excluyen inactivos o documentan claramente cómo incluirlos.
- Un alias de otro registro sigue siendo conflicto.
- La entidad ofrece métodos de comportamiento, no setters públicos generales.

### Criterios de aceptación

- Dos DELETE consecutivos no corrompen estado.
- PUT sobre ID ausente se distingue de conflicto de alias.
- Una prueba verifica dirty checking dentro de la transacción o el resultado persistente.
- La decisión de borrado lógico incluye un costo.

### Pistas graduadas

1. Añade un enum estable, no un booleano ambiguo si prevés más estados.
2. La consulta de alias para update debe excluir el mismo ID.
3. Decide el resultado observable de repetir DELETE antes de escribir código.

## U05-E10 — Diseñar validación y contrato de errores inicial

- **Tipo:** diseño e implementación.
- **Dificultad:** 3/5.
- **Tiempo:** 75 minutos.

### Escenario

Clientes reciben hoy tres cuerpos incompatibles para JSON inválido, campos inválidos y alias duplicado. Define una forma básica consistente sin anticipar toda la unidad 10.

### Requisitos

- Código estable legible por máquinas.
- Mensaje legible sin stack trace.
- Violaciones por campo para Bean Validation.
- Timestamp y correlation ID opcional si ya existe.
- Status HTTP adecuado por categoría.

### Restricciones

- No devuelvas nombres de tablas, SQL ni clases internas.
- No captures `Throwable`.
- No conviertas todo a `400`.

### Criterios de aceptación

- Pruebas web cubren al menos cuatro categorías.
- El caso feliz no cambia.
- El formato no incluye el valor sensible rechazado.
- Se documenta qué queda pendiente para la unidad 10.

### Pistas graduadas

1. Centraliza traducción con advice.
2. Ordena violaciones si la prueba necesita determinismo.
3. Separa identificador de error de la frase humana.

## U05-E11 — Migrar de H2 a MySQL de forma demostrable

- **Tipo:** datos y operación local.
- **Dificultad:** 3/5.
- **Tiempo:** 90 minutos.

### Escenario

El recurso beneficiario funciona en H2. Debe iniciar en MySQL 8.4 con Flyway, conservar datos al reiniciar y fallar si entidad y esquema divergen.

### Requisitos

- Compose aislado con healthcheck y volumen nombrado.
- Perfil MySQL separado.
- `V1` crea tabla, claves y restricciones.
- JPA usa `validate` con MySQL.
- Variables externas para conexión.
- Evidencia de persistencia tras reinicio.

### Criterios de aceptación

- El log demuestra migración aplicada una vez.
- Un segundo arranque no recrea ni altera la migración.
- La API conserva un dato ficticio entre reinicios.
- Una divergencia controlada provoca fallo de validación.
- El informe distingue limitaciones de H2.

### Pistas graduadas

1. No ejecutes Hibernate `create` y Flyway como autoridades simultáneas.
2. Inspecciona la tabla de historial de Flyway.
3. Detener la aplicación no elimina el volumen.

## U05-E12 — Pruebas unitarias que expresan reglas

- **Tipo:** testing unitario.
- **Dificultad:** 3/5.
- **Tiempo:** 70 minutos.

### Escenario

Escribe pruebas del service de beneficiarios para creación, alias duplicado, consulta ausente y desactivación repetida.

### Requisitos

- JUnit Jupiter, AssertJ y Mockito o un fake justificado.
- Sin contexto Spring.
- Nombres de prueba basados en comportamiento.
- Datos deterministas.
- Verificación de efectos solo cuando forma parte del contrato del colaborador.

### Criterios de aceptación

- Cada test posee una razón de fallo clara.
- No se mockean DTO ni entidad sin necesidad.
- La ruta de duplicado no guarda.
- La ausencia conserva tipo de excepción.
- Un refactor interno razonable no rompe los asserts.

### Pistas graduadas

1. Construye el service con constructor.
2. Controla generador de ID o evita afirmar un valor aleatorio exacto.
3. Verifica salida y efecto relevante, no cada llamada privada imaginada.

## U05-E13 — Prueba web del contrato básico

- **Tipo:** slice test.
- **Dificultad:** 3/5.
- **Tiempo:** 75 minutos.

### Escenario

Prueba mappings, serialización, validación, `Location` y errores del controller de beneficiarios sin conectar base.

### Requisitos

- `@WebMvcTest` enfocado en el controller.
- Service sustituido de forma explícita.
- JSON y media type comprobados.
- Casos POST válido, body inválido, GET ausente y DELETE.
- Sin arrancar puerto real.

### Criterios de aceptación

- El test detecta eliminar `@Valid`.
- El test detecta un status o header incorrecto.
- No afirma el JSON entero como cadena frágil.
- Se explica qué no demuestra el slice.

### Pistas graduadas

1. MockMvc envía peticiones sin servidor externo.
2. Configura el mock solo para el caso que lo necesita.
3. Usa `jsonPath` para propiedades significativas.

## U05-E14 — Diario de diagnóstico reproducible

- **Tipo:** debugging.
- **Dificultad:** 4/5.
- **Tiempo:** 90 minutos.

### Escenario

Diagnostica, uno por vez: puerto ocupado, perfil incorrecto, bean ausente, credenciales rechazadas y migración con error de sintaxis.

### Requisitos

- No arreglar antes de capturar evidencia.
- Clasificar fase y frontera.
- Citar la causa mínima relevante.
- Formular una hipótesis refutable.
- Aplicar un cambio por intento.
- Agregar una prevención proporcionada.

### Criterios de aceptación

- Cinco fichas reproducibles.
- No hay secretos en logs compartidos.
- Se distingue conexión rechazada de acceso denegado.
- La prevención no consiste solo en “tener cuidado”.
- El proyecto termina verde y sin configuración rota.

### Pistas graduadas

1. El mensaje superior suele describir la consecuencia; busca `Caused by`.
2. Compara entorno efectivo con entorno supuesto.
3. Una prueba, healthcheck o validación temprana puede prevenir repetición.

## U05-E15 — Revisión de código por severidad

- **Tipo:** code review.
- **Dificultad:** 4/5.
- **Tiempo:** 60 minutos.

### Escenario

Revisa un cambio que usa inyección por campo, devuelve entidades, guarda contraseñas en YAML, llama repository desde controller, usa `ddl-auto: update`, captura `Exception`, registra el body y no tiene pruebas.

### Requisitos

- Comentarios clasificados como bloqueo, importante o mejora.
- Cada comentario explica riesgo, evidencia y dirección.
- No reescribas toda la solución en el review.
- Separa correcciones necesarias para integrar de mejoras posteriores.

### Criterios de aceptación

- Secretos y exposición de datos reciben prioridad apropiada.
- No todo se marca como bloqueo.
- Las sugerencias conservan un cambio abordable.
- Se propone una secuencia de commits verificables.
- El tono critica el código y el riesgo, no a la persona.

### Pistas graduadas

1. Prioriza pérdida de datos, seguridad y contrato antes que estilo.
2. Una observación sin consecuencia es difícil de evaluar.
3. Propón dirección y prueba, no una clase completa en el comentario.

## U05-E16 — Integración y defensa de salida

- **Tipo:** integración y explicación oral.
- **Dificultad:** 5/5.
- **Tiempo:** 2 horas.

### Escenario

Una persona nueva debe ejecutar tu recurso de beneficiarios, entenderlo y comprobarlo sin preguntarte. Prepara una entrega local reproducible y una defensa de diez minutos.

### Evidencias obligatorias

- instrucciones desde clon limpio;
- versión y comandos;
- health y CRUD con datos ficticios;
- diagrama de beans y de petición;
- H2 y MySQL con diferencias declaradas;
- migración e integridad;
- pruebas unitarias, web y contexto;
- un caso de diagnóstico;
- tres decisiones con trade-offs;
- lista de riesgos que se profundizan en unidades 6 a 13.

### Criterios de aceptación

- Otra persona puede reproducir sin editar secretos en Git.
- El build no depende del estado accidental del editor.
- La defensa diferencia framework, Boot y código propio.
- Se explica una petición desde bytes/JSON hasta SQL y regreso.
- Se reconoce qué evidencia no es suficiente para producción.
- No se presenta el CRUD como arquitectura universal.

### Pistas graduadas

1. Ensaya primero con una terminal nueva.
2. Explica desde las fronteras y no archivo por archivo.
3. Si no puedes justificar una anotación, identifica quién la procesa.

## Prueba de salida — sin código nuevo

Realízala cuando termines U05-E01 a U05-E08. Sin consultar notas, responde oralmente y luego verifica:

1. ¿Qué inicia `main` y qué confirma que el arranque terminó?
2. ¿Qué diferencia existe entre bean y objeto Java?
3. ¿Por qué el constructor revela el grafo?
4. ¿Cómo selecciona Spring MVC un método del controller?
5. ¿Dónde se transforma JSON y dónde se valida negocio?
6. ¿Por qué request, entity y response son tipos distintos?
7. ¿Qué crea la implementación del repository?
8. ¿Qué significa que una entidad esté administrada?
9. ¿Qué aporta una transacción al caso de uso?
10. ¿Por qué H2 no demuestra compatibilidad con MySQL?
11. ¿Qué diferencias hay entre prueba unitaria, web y de contexto?
12. ¿Cómo investigas un fallo de arranque sin cambiar cinco cosas?

## Rúbrica de la Unidad 5 — 100 puntos

| Dimensión | Puntos | Evidencia excelente |
|---|---:|---|
| Modelo mental de Spring y Boot | 15 | Explica arranque, autoconfiguración y límites con evidencia |
| IoC, DI y beans | 15 | Diseña grafo explícito, constructor y configuración sin ciclos |
| API y capas | 20 | Separa HTTP, caso de uso, persistencia, entidad y DTO |
| CRUD y errores | 15 | Contrato observable, validación y categorías de fallo coherentes |
| Datos y migraciones | 15 | H2 consciente, MySQL reproducible, Flyway e integridad |
| Pruebas | 10 | Elige alcance según riesgo y mantiene determinismo |
| Diagnóstico | 5 | Parte de evidencia, causa e hipótesis única |
| Seguridad y comunicación | 5 | Datos ficticios, sin secretos y decisiones defendibles |

### Interpretación

- **85–100:** preparado para la unidad 6 y ruta profesional.
- **70–84:** preparado para la unidad 6; refuerza dimensiones inferiores a 60 % de su puntaje.
- **55–69:** completa el laboratorio y repite prueba de salida antes de avanzar.
- **0–54:** reconstruye primero arranque, grafo y petición; no memorices más anotaciones.

La rúbrica sirve para decidir qué revisar, no para medir valor personal.

## Escalera general de ayuda

Si te bloqueas cuando empieces a estudiar:

1. **Pista conceptual:** identifica frontera y comportamiento esperado.
2. **Pista de ubicación:** señala package, clase o configuración relevante.
3. **Pista de mecanismo:** describe el siguiente paso sin dar código final.
4. **Revisión de intento:** comparte ID, diff mínimo, comando, resultado e hipótesis.
5. **Solución explícita:** solo cuando la pidas inequívocamente después de intentar.

No necesitas activar esta escalera ahora. El curso permanece completo y los ejercicios quedan listos para cuando decidas resolverlos.
