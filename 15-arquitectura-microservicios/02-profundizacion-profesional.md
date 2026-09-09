# Profundización profesional — Consistencia distribuida sin eslóganes

## El resultado desconocido

Si el cliente agota su timeout, no sabe si el servidor nunca recibió, ejecutó o confirmó la orden. Un retry automático de una transferencia puede duplicarla. La clave de idempotencia transforma la repetición en una consulta al mismo resultado, pero exige almacenamiento, identidad del payload y política de expiración.

## Límite de FintechLab

Cuentas, saldos, movimientos y la decisión de aceptar una transferencia permanecen juntos. Notificaciones pueden separarse porque una demora no debe revertir dinero ya registrado. Un outbox local conserva evento y transferencia en una transacción; un publicador y consumidor idempotentes gestionan entrega posterior.

## CAP con precisión

Durante una partición no siempre puedes ofrecer respuesta consistente y disponibilidad para cada operación. La decisión puede variar por operación: consultar una preferencia tolera antigüedad; autorizar un débito requiere una regla más estricta. Latencia y consistencia siguen siendo trade-offs aun sin una partición total.

## Ejercicio de arquitectura

Evalúa tres propuestas: monolito modular, notificaciones separadas y un servicio por tabla. Para cada una registra beneficio, costo operativo, propiedad de datos, modo de fallo y señal que justificaría migrar.
