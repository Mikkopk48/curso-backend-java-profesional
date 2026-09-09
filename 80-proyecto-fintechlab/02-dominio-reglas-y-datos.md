# FintechLab — Dominio, reglas y datos

## Reglas

Importe positivo con BigDecimal, moneda explícita, escala dos, cuentas distintas, misma moneda, saldo no negativo, movimiento por cada lado, instante UTC e Idempotency-Key vinculada al mismo payload.

La base refuerza reglas con NOT NULL, CHECK, UNIQUE y claves foráneas. El servicio protege reglas que necesitan contexto. La auditoría registra IDs técnicos, acción, resultado y correlation ID; nunca token, contraseña ni payload completo.

## Estados

Una transferencia puede quedar CREATED, COMPLETED o REJECTED dentro del modelo didáctico. Una notificación posee su propio estado; su fallo no cambia el resultado monetario. Cada transición debe estar permitida explícitamente y probada.
