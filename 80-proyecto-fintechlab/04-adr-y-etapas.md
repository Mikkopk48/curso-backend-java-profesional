# FintechLab — ADR y etapas

## ADR-001 Monolito modular primero

**Decisión:** mantener la operación monetaria en un proceso y base. **Razón:** atomicidad y menor carga operativa. **Consecuencia:** despliegue conjunto; límites internos preparados para evolución.

## ADR-002 BigDecimal, moneda e Instant

**Decisión:** BigDecimal escala dos, código de moneda explícito e Instant en persistencia. **Razón:** evitar error binario y ambigüedad temporal. **Consecuencia:** redondeo y presentación requieren política.

## ADR-003 Outbox para notificación

**Decisión:** transferencia y evento se guardan juntos; la entrega es posterior e idempotente. **Consecuencia:** consistencia eventual y necesidad de reintento observable.

## Etapas

HTTP, unit testing, integración, escrituras, errores, OpenAPI, análisis funcional, persistencia, parcial, decisión distribuida, Feign, Eureka, Config, CI/CD, Git, agregaciones, mapping, UI E2E, refactor, seguridad, cierre y defensa.
