# FintechLab — Contrato API e idempotencia

## Recursos

<code>/api/accounts</code>, <code>/api/accounts/{id}</code>, <code>/api/transfers</code> y <code>/api/movements</code>. Requests y responses son DTO; entidades no cruzan la API.

POST de transferencia requiere Idempotency-Key. Primera petición crea resultado; repetición con mismo payload devuelve el resultado asociado; misma clave con payload diferente produce conflicto. La restricción UNIQUE resuelve carreras que una comprobación previa no puede cerrar.

Errores usan Problem Details con type, title, status, detail, instance, code y correlationId. Detail es seguro y no revela SQL ni stack trace.
