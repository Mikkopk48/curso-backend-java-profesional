# FintechLab — Arquitectura y recorrido

FintechLab es un sistema didáctico con datos ficticios. Comienza como monolito modular porque cuentas, movimientos y transferencias comparten invariantes y una transacción local. Notificaciones se separa después como laboratorio de fallos parciales.

## Módulos

- customer: identidad ficticia del cliente;
- account: moneda y saldo;
- transfer: caso de uso e idempotencia;
- movement: historial inmutable;
- identity: usuarios y permisos;
- notification: entrega simulada;
- shared: errores y observabilidad, sin convertirse en depósito indiscriminado.

## Recorrido

HTTP entra por filtro de correlation ID y seguridad, llega al DTO validado, ejecuta el caso de uso transaccional, persiste transferencia y outbox, confirma y responde. El publicador procesa la notificación fuera de la transacción monetaria.
