# Profundización profesional — Transacciones, planes y persistencia

## Invariante monetaria

Una transferencia válida conserva la suma del dinero entre las cuentas involucradas, usa la misma moneda o una conversión explícita y nunca deja un débito sin su crédito. Comprobar saldo y actualizarlo en operaciones separadas abre una carrera: dos transacciones pueden leer el mismo saldo y aprobar débitos incompatibles.

La solución no es una receta única. Puedes bloquear filas en orden estable, usar versionado optimista con reintento consciente o formular un UPDATE condicional. La elección depende de contención, patrón de acceso y experiencia operativa. La restricción de idempotencia debe persistir en la misma base que el resultado de negocio.

## Persistence context

Hibernate mantiene identidad por clave dentro del contexto, detecta cambios al hacer flush y coordina SQL. Flush no equivale necesariamente a commit. Acceder a una relación lazy fuera del contexto falla; convertir todo a eager puede cargar grafos enormes. Diseña queries y proyecciones por caso de uso.

## Índices y evidencia

Un índice compuesto sirve según el prefijo de columnas que aprovecha la consulta. Compara cardinalidad estimada y real, filas examinadas y ordenamiento. Mide con datos representativos; un esquema vacío puede inducir una conclusión falsa.

## Laboratorio concurrente

Diseña dos sesiones que intenten debitar la misma cuenta. Predice bloqueos y resultado para dos niveles de aislamiento, ejecútalo en una base descartable y registra la diferencia. No se proporciona secuencia resuelta.
