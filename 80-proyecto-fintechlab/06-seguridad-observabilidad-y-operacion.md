# FintechLab — Seguridad, observabilidad y operación

El perfil dev permite ejecución local ficticia; el perfil secure usa un issuer externo y permisos accounts:read y transfers:write. La configuración de ejemplo no es autenticación lista para producción.

Cada petición recibe correlation ID. Logs estructurados contienen evento, IDs técnicos y resultado; excluyen Authorization, cookies, contraseñas y datos personales. Métricas observan latencia, errores, rechazos e intentos de outbox; las etiquetas no contienen IDs de alta cardinalidad.

Operación documenta health, migraciones, rollback del artefacto, recuperación de base conceptual y rotación de claves.
