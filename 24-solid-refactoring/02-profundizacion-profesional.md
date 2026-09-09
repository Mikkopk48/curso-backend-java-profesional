# Profundización profesional — Refactorización guiada por cambio

## Del smell a la fuerza de diseño

Una clase grande no es mala por su cantidad de líneas aislada. Investiga cuántos motivos de cambio mezcla, qué reglas se repiten y qué dependencias obligan a probar infraestructura. Formula una hipótesis de mejora antes de extraer interfaces.

## Secuencia segura

Caracteriza comportamiento con pruebas, renombra para revelar intención, extrae una regla pura, mueve dependencias al constructor y ejecuta pruebas después de cada paso. Si una extracción no reduce acoplamiento o mejora claridad, revísala.

## Liskov más allá de herencia

Una implementación sustituible conserva precondiciones, postcondiciones e invariantes que espera el cliente. Lanzar UnsupportedOperationException en un subtipo que prometía la operación viola la expectativa aunque compile.

## Dependency Inversion

El dominio no debe conocer detalles de HTTP o JPA. Esto no obliga a crear una interfaz para cada clase: una frontera es útil cuando protege una política estable de un mecanismo variable o facilita una prueba relevante.

## Taller

Escoge un servicio que mezcle mapping, regla monetaria y envío remoto. Propón tres pasos reversibles, riesgo cubierto por cada test y criterio para detener la refactorización.
