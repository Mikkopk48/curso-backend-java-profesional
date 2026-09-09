# Unidad 25 — Seguridad, roles y JWT: teoría profunda

## Lugar dentro del recorrido

Esta unidad corresponde a la etapa **Autenticación y autorización**. Parte de la API inicial de la unidad 5 y añade una capacidad profesional sin volver a explicar Java, POO o CRUD desde cero.

## Conocimientos previos

- Controladores, servicios y repositorios básicos.
- Lectura de Java y ejecución de una aplicación Spring Boot.
- Conceptos recuperados en <code>00-objetivos-y-ruta.md</code>.

## Objetivos observables

Al terminar, podrás explicar el riesgo que resuelve cada concepto, seguir su funcionamiento, aplicarlo a FintechLab, escribir evidencia y defender cuándo no conviene usarlo.

## Problema central

Seguridad administra riesgos sobre activos; una anotación aislada no crea un sistema seguro. En un ejercicio pequeño puede parecer suficiente memorizar una API. En producción importan además concurrencia, datos inválidos, latencia, compatibilidad, trazabilidad y recuperación.

## Modelo mental y analogía

Identidad, permiso y registro se parecen a credencial, puerta y libro de accesos.

**Límite:** En Internet la credencial puede copiarse, reproducirse y analizarse a gran escala.

## Funcionamiento técnico progresivo

SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. El recorrido útil siempre contiene cinco preguntas: quién inicia, qué cruza la frontera, qué estado participa, dónde puede fallar y qué evidencia queda.

~~~java
@Bean
SecurityFilterChain api(HttpSecurity http) throws Exception {
    return http.authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.GET, "/api/accounts/**").hasAuthority("accounts:read")
            .anyRequest().authenticated())
        .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults())).build();
}
~~~

El ejemplo es deliberadamente pequeño. No es la solución de los ejercicios: muestra vocabulario y forma, mientras las consignas cambian datos, restricciones y estructura.

## Explicación conectada: identidad, permiso y riesgo

### Threat model antes de anotaciones

Activo es algo valioso; amenaza un evento adverso; vulnerabilidad una debilidad y riesgo combina probabilidad e impacto. Dibuja fronteras de confianza y flujo de una transferencia. Pregunta quién controla cada entrada, qué pasaría si se modifica, repite, lee o bloquea.

Autenticación establece identidad; autorización decide acción. Roles agrupan permisos, pero los endpoints deberían razonar con authorities específicas. Mínimo privilegio reduce impacto. 401 significa autenticación necesaria/inválida; 403 identidad válida sin permiso.

### Spring Security y contraseñas

SecurityFilterChain corre antes del controller. Un filtro extrae credencial; AuthenticationManager/Provider valida; SecurityContext conserva Authentication durante la request; reglas web y method security autorizan. El orden de matchers importa.

Contraseñas no se cifran reversiblemente. Argon2 o BCrypt combinan hash lento y salt único; el costo se calibra y aumenta. Nunca registres contraseña ni la envíes fuera de TLS.

### JWT sin mitos

JWT contiene header, payload y signature. Base64url no cifra. HS256 comparte secreto; RS/ES separa clave privada de firma y pública de validación. Restringe algoritmos. Valida firma, issuer, audience, exp, nbf y reloj. Claims personalizados deben ser mínimos y versionados.

Access token es corto; refresh token renueva y requiere almacenamiento, rotación, detección de reutilización y revocación. Logout no borra un access token ya emitido salvo lista de revocación o vida corta. El robo permite replay hasta que expire o sea revocado.

En navegador, localStorage es accesible a XSS. Cookies HttpOnly reducen lectura por script, pero al enviarse automáticamente necesitan CSRF, SameSite y origen. CORS solo controla lectura cross-origin del navegador, no autentica. HTTPS protege tránsito; CSP y escaping reducen XSS.

### Protocolos y operación

OAuth 2.0 delega autorización; OIDC añade identidad. JWT es un formato posible. No implementes un authorization server casero. Entre servicios usa identidad de workload, TLS y audience/permissions propios, no propagues ciegamente un token de usuario.

Rate limiting reduce abuso pero debe diferenciar identidad y ruta. Auditoría registra quién, qué, cuándo y resultado sin datos sensibles. OWASP API Top 10 guía revisión de autorización por objeto, autenticación, consumo de recursos, SSRF, inventario y configuración. Prueba 401/403, claims inválidos, expiración, acceso cruzado y dependencias vulnerables. La configuración didáctica no equivale a producción.

## Atlas completo del temario oficial

Las subsecciones siguientes cubren cada punto del programa. La etiqueta **Diplomatura** identifica el núcleo oficial; las decisiones de producción y el vínculo con FintechLab constituyen profundización y ampliación profesional.

### 1. activo, amenaza, vulnerabilidad y riesgo

**Problema e intuición.** activo, amenaza, vulnerabilidad y riesgo aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, activo, amenaza, vulnerabilidad y riesgo se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica activo, amenaza, vulnerabilidad y riesgo con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 2. threat modeling básico

**Problema e intuición.** threat modeling básico aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, threat modeling básico se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica threat modeling básico con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 3. autenticación

**Problema e intuición.** autenticación aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, autenticación se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica autenticación con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 4. autorización

**Problema e intuición.** autorización aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, autorización se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica autorización con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 5. identidad

**Problema e intuición.** identidad aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, identidad se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica identidad con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 6. roles y permisos

**Problema e intuición.** roles y permisos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, roles y permisos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica roles y permisos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 7. principio de mínimo privilegio

**Problema e intuición.** principio de mínimo privilegio aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, principio de mínimo privilegio se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica principio de mínimo privilegio con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 8. Spring Security

**Problema e intuición.** Spring Security aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, Spring Security se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica Spring Security con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 9. SecurityFilterChain

**Problema e intuición.** SecurityFilterChain aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, SecurityFilterChain se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica SecurityFilterChain con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 10. filtros

**Problema e intuición.** filtros aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, filtros se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica filtros con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 11. contexto de seguridad

**Problema e intuición.** contexto de seguridad aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, contexto de seguridad se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica contexto de seguridad con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 12. login

**Problema e intuición.** login aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, login se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica login con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 13. almacenamiento de usuarios

**Problema e intuición.** almacenamiento de usuarios aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, almacenamiento de usuarios se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica almacenamiento de usuarios con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 14. hash de contraseñas

**Problema e intuición.** hash de contraseñas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Las contraseñas se verifican con funciones lentas de hash y salt único; no se almacenan reversiblemente. El costo debe poder elevarse con el tiempo. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica hash de contraseñas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 15. salt

**Problema e intuición.** salt aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Las contraseñas se verifican con funciones lentas de hash y salt único; no se almacenan reversiblemente. El costo debe poder elevarse con el tiempo. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica salt con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 16. BCrypt o Argon2

**Problema e intuición.** BCrypt o Argon2 aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Las contraseñas se verifican con funciones lentas de hash y salt único; no se almacenan reversiblemente. El costo debe poder elevarse con el tiempo. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica BCrypt o Argon2 con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 17. por qué las contraseñas no se cifran reversiblemente

**Problema e intuición.** por qué las contraseñas no se cifran reversiblemente aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Las contraseñas se verifican con funciones lentas de hash y salt único; no se almacenan reversiblemente. El costo debe poder elevarse con el tiempo. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica por qué las contraseñas no se cifran reversiblemente con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 18. JWT

**Problema e intuición.** JWT aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** JWT es un formato de claims que puede firmarse; firmar demuestra integridad y origen según la clave, pero no cifra el payload. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica JWT con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 19. header, payload y signature

**Problema e intuición.** header, payload y signature aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, header, payload y signature se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica header, payload y signature con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 20. claims registrados y personalizados

**Problema e intuición.** claims registrados y personalizados aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, claims registrados y personalizados se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica claims registrados y personalizados con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 21. firma simétrica y asimétrica

**Problema e intuición.** firma simétrica y asimétrica aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** JWT es un formato de claims que puede firmarse; firmar demuestra integridad y origen según la clave, pero no cifra el payload. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica firma simétrica y asimétrica con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 22. diferencia entre firmar y cifrar

**Problema e intuición.** diferencia entre firmar y cifrar aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, diferencia entre firmar y cifrar se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica diferencia entre firmar y cifrar con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 23. validación de issuer, audience, expiración y not-before

**Problema e intuición.** validación de issuer, audience, expiración y not-before aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Validar firma no basta: issuer limita quién emitió, audience para quién, exp y nbf la ventana temporal. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica validación de issuer, audience, expiración y not-before con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 24. access token

**Problema e intuición.** access token aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, access token se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica access token con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 25. refresh token

**Problema e intuición.** refresh token aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, refresh token se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica refresh token con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 26. rotación

**Problema e intuición.** rotación aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, rotación se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica rotación con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 27. revocación

**Problema e intuición.** revocación aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, revocación se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica revocación con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 28. cierre de sesión

**Problema e intuición.** cierre de sesión aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, cierre de sesión se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica cierre de sesión con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 29. robo y replay de tokens

**Problema e intuición.** robo y replay de tokens aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, robo y replay de tokens se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica robo y replay de tokens con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 30. almacenamiento de tokens

**Problema e intuición.** almacenamiento de tokens aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, almacenamiento de tokens se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica almacenamiento de tokens con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 31. cookies seguras frente a almacenamiento del navegador

**Problema e intuición.** cookies seguras frente a almacenamiento del navegador aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, cookies seguras frente a almacenamiento del navegador se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica cookies seguras frente a almacenamiento del navegador con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 32. CSRF

**Problema e intuición.** CSRF aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** CORS controla qué orígenes del navegador pueden leer respuestas, CSRF explota credenciales enviadas automáticamente y XSS ejecuta script en el origen. Son amenazas distintas. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica CSRF con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 33. CORS

**Problema e intuición.** CORS aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** CORS controla qué orígenes del navegador pueden leer respuestas, CSRF explota credenciales enviadas automáticamente y XSS ejecuta script en el origen. Son amenazas distintas. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica CORS con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 34. XSS en relación con tokens

**Problema e intuición.** XSS en relación con tokens aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** CORS controla qué orígenes del navegador pueden leer respuestas, CSRF explota credenciales enviadas automáticamente y XSS ejecuta script en el origen. Son amenazas distintas. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica XSS en relación con tokens con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 35. HTTPS

**Problema e intuición.** HTTPS aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, HTTPS se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica HTTPS con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 36. secretos y rotación de claves

**Problema e intuición.** secretos y rotación de claves aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, secretos y rotación de claves se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica secretos y rotación de claves con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 37. 401 frente a 403

**Problema e intuición.** 401 frente a 403 aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** 401 indica ausencia o invalidez de autenticación adecuada; 403 indica que la identidad autenticada no posee permiso para la acción. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica 401 frente a 403 con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 38. method security

**Problema e intuición.** method security aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, method security se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica method security con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 39. autorización por rol y por permiso

**Problema e intuición.** autorización por rol y por permiso aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, autorización por rol y por permiso se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica autorización por rol y por permiso con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 40. seguridad entre microservicios

**Problema e intuición.** seguridad entre microservicios aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, seguridad entre microservicios se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica seguridad entre microservicios con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 41. OAuth 2.0 y OpenID Connect como mapa conceptual, sin confundirlos con JWT

**Problema e intuición.** OAuth 2.0 y OpenID Connect como mapa conceptual, sin confundirlos con JWT aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** JWT es un formato de claims que puede firmarse; firmar demuestra integridad y origen según la clave, pero no cifra el payload. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica OAuth 2.0 y OpenID Connect como mapa conceptual, sin confundirlos con JWT con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 42. rate limiting

**Problema e intuición.** rate limiting aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, rate limiting se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica rate limiting con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 43. auditoría

**Problema e intuición.** auditoría aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, auditoría se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica auditoría con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 44. protección de datos sensibles

**Problema e intuición.** protección de datos sensibles aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, protección de datos sensibles se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica protección de datos sensibles con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 45. OWASP API Security Top 10

**Problema e intuición.** OWASP API Security Top 10 aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, OWASP API Security Top 10 se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica OWASP API Security Top 10 con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 46. pruebas de seguridad

**Problema e intuición.** pruebas de seguridad aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, pruebas de seguridad se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica pruebas de seguridad con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 47. dependencias vulnerables

**Problema e intuición.** dependencias vulnerables aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, dependencias vulnerables se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica dependencias vulnerables con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 48. errores comunes de implementaciones caseras

**Problema e intuición.** errores comunes de implementaciones caseras aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Seguridad, roles y JWT, errores comunes de implementaciones caseras se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** SecurityFilterChain procesa la petición antes del controller, construye Authentication y aplica reglas; el resource server valida firma y claims del token. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** protege endpoints por permisos y registra acciones sin guardar tokens ni datos sensibles. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. La regla es: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica errores comunes de implementaciones caseras con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

## Decisiones y alternativas

1. Define primero la invariante o el riesgo.
2. Compara una opción simple con otra más operable.
3. Registra el costo introducido: latencia, acoplamiento, complejidad o mantenimiento.
4. Elige: Aplicar mínimo privilegio, validar cada claim y delegar identidad en componentes mantenidos.
5. Documenta qué dato futuro haría cambiar la decisión.

## Errores frecuentes

- Copiar una anotación sin conocer quién la interpreta ni cuándo.
- Confundir el caso feliz con el contrato completo.
- Añadir infraestructura sin una necesidad observable.
- Escribir un test que replica la implementación en vez de cubrir riesgo.
- Registrar datos sensibles para facilitar debugging.
- Afirmar una garantía sin una restricción, prueba o medición que la sostenga.

## Qué ocurre en producción

Un token firmado puede ser robado; omitir issuer, audience o expiración acepta credenciales fuera de contexto. Un profesional diseña para degradación, documenta límites, mide antes de optimizar y deja una ruta de recuperación. FintechLab usa datos ficticios y no debe conectarse con bancos ni procesar dinero real.

## Cómo reconocer que realmente lo entendiste

- Puedes explicarlo sin depender del nombre de una anotación.
- Puedes dibujar el recorrido y marcar al menos tres fallos.
- Puedes cambiar el ejemplo sin copiarlo.
- Puedes escribir una prueba que falla cuando se rompe la regla.
- Puedes nombrar una alternativa y justificar el trade-off.

## Resumen sencillo

La unidad enseña a convertir Seguridad, roles y JWT en decisiones verificables. La idea profesional no es acumular herramientas, sino conectar propósito, mecanismo, fallo, evidencia y costo.

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
