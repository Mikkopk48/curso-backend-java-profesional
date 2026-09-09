# Profundización profesional — Threat model de FintechLab

## Activos y fronteras

Activos: identidad, autorización, saldos ficticios, historial, claves y disponibilidad. Fronteras: navegador–API, API–servicio, servicio–base y pipeline–registro. Para cada frontera pregunta quién controla la entrada y qué confianza se está asumiendo.

## Token no equivale a sesión segura

Un JWT firmado puede leerse y copiarse. El resource server debe validar algoritmo permitido, firma, issuer, audience, expiración y not-before. Los permisos se derivan con una regla explícita. La rotación de claves requiere convivencia temporal y observabilidad.

## Navegador

Guardar tokens accesibles a JavaScript amplía el impacto de XSS; usar cookies enviadas automáticamente exige defensa CSRF. SameSite, Secure y HttpOnly ayudan, pero el diseño depende del tipo de cliente y del flujo de identidad. CORS no es autenticación.

## Contraseñas y secretos

Las contraseñas se verifican con Argon2 o BCrypt y salt; las claves privadas se inyectan y rotan. Un valor retirado de la última versión de Git puede seguir en el historial y debe considerarse comprometido.

## Actividad STRIDE ligera

Elige una transferencia y clasifica escenarios de suplantación, manipulación, repudio, divulgación, denegación y elevación. Para cada uno escribe mitigación, evidencia y riesgo residual, sin diseñar exploits contra sistemas reales.
