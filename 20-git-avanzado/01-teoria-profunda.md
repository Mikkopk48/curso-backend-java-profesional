# Unidad 20 — Git avanzado y colaboración: teoría profunda

## Lugar dentro del recorrido

Esta unidad corresponde a la etapa **Historial mantenible**. Parte de la API inicial de la unidad 5 y añade una capacidad profesional sin volver a explicar Java, POO o CRUD desde cero.

## Conocimientos previos

- Controladores, servicios y repositorios básicos.
- Lectura de Java y ejecución de una aplicación Spring Boot.
- Conceptos recuperados en <code>00-objetivos-y-ruta.md</code>.

## Objetivos observables

Al terminar, podrás explicar el riesgo que resuelve cada concepto, seguir su funcionamiento, aplicarlo a FintechLab, escribir evidencia y defender cuándo no conviene usarlo.

## Problema central

Git administra un grafo de estados y referencias; comprenderlo permite recuperar errores. En un ejercicio pequeño puede parecer suficiente memorizar una API. En producción importan además concurrencia, datos inválidos, latencia, compatibilidad, trazabilidad y recuperación.

## Modelo mental y analogía

Los commits son fotografías enlazadas y las ramas son etiquetas móviles.

**Límite:** Una historia compartida afecta a otras personas; reescribirla no es una operación privada.

## Funcionamiento técnico progresivo

Cada commit identifica contenido y padres; HEAD y las ramas son referencias. Merge, rebase, cherry-pick y revert construyen grafos distintos. El recorrido útil siempre contiene cinco preguntas: quién inicia, qué cruza la frontera, qué estado participa, dónde puede fallar y qué evidencia queda.

~~~bash
git switch -c practice/recover-commit
git log --graph --decorate --oneline --all
git reflog
# La práctica se realiza en un repositorio descartable.
~~~

El ejemplo es deliberadamente pequeño. No es la solución de los ejercicios: muestra vocabulario y forma, mientras las consignas cambian datos, restricciones y estructura.

## Explicación conectada: razonar sobre el grafo de Git

Un commit contiene un tree y padres; su hash identifica contenido e historia. Una rama es una referencia móvil y HEAD indica la referencia o commit actual. Comprender esto elimina la idea de que las ramas son carpetas copiadas.

Fast-forward mueve la referencia si no hubo divergencia. Un merge commit conserva dos padres. Rebase copia commits sobre otra base y crea hashes nuevos: útil en una rama local, riesgoso sobre historia compartida. Cherry-pick copia un cambio concreto. Revert añade un commit inverso y es seguro para historia publicada.

Restore modifica working tree/index según opciones; reset mueve referencias y puede además alterar index/working tree. Antes de reset, inspecciona estado y usa un repositorio de práctica. Reflog conserva movimientos locales de referencias y permite recuperar commits todavía alcanzables. No uses force push sobre ramas compartidas salvo acuerdo y protección.

Un conflicto representa dos cambios que Git no puede combinar semánticamente. Lee base y lados, decide intención, compila y prueba; no elijas “ours” por comodidad. Rebase interactivo permite reordenar, squash y reword antes de publicar. Bisect busca el primer commit defectuoso con búsqueda binaria; automatizar el test mejora evidencia.

Stash guarda temporalmente cambios, tags nombran versiones y commits atómicos facilitan revert/review. Git Flow usa ramas de release/hotfix y puede ser pesado. Trunk-based prefiere ramas cortas, integración frecuente y flags. Elige según frecuencia y regulación, protegiendo main con review y CI.

## Atlas completo del temario oficial

Las subsecciones siguientes cubren cada punto del programa. La etiqueta **Diplomatura** identifica el núcleo oficial; las decisiones de producción y el vínculo con FintechLab constituyen profundización y ampliación profesional.

### 1. grafo de commits

**Problema e intuición.** grafo de commits aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Git avanzado y colaboración, grafo de commits se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Cada commit identifica contenido y padres; HEAD y las ramas son referencias. Merge, rebase, cherry-pick y revert construyen grafos distintos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** conserva decisiones y cambios revisables en commits pequeños. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Reescribir historia publicada o usar reset sin comprender alcance puede perder trabajo accesible. La regla es: Preferir operaciones reversibles y commits atómicos; proteger ramas compartidas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica grafo de commits con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 2. HEAD

**Problema e intuición.** HEAD aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Git avanzado y colaboración, HEAD se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Cada commit identifica contenido y padres; HEAD y las ramas son referencias. Merge, rebase, cherry-pick y revert construyen grafos distintos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** conserva decisiones y cambios revisables en commits pequeños. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Reescribir historia publicada o usar reset sin comprender alcance puede perder trabajo accesible. La regla es: Preferir operaciones reversibles y commits atómicos; proteger ramas compartidas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica HEAD con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 3. referencias y ramas

**Problema e intuición.** referencias y ramas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Git avanzado y colaboración, referencias y ramas se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Cada commit identifica contenido y padres; HEAD y las ramas son referencias. Merge, rebase, cherry-pick y revert construyen grafos distintos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** conserva decisiones y cambios revisables en commits pequeños. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Reescribir historia publicada o usar reset sin comprender alcance puede perder trabajo accesible. La regla es: Preferir operaciones reversibles y commits atómicos; proteger ramas compartidas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica referencias y ramas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 4. merge

**Problema e intuición.** merge aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Estas operaciones modifican o recorren el grafo de commits de formas distintas. Revert añade un commit inverso; reset mueve una referencia y reflog ayuda a recuperar referencias locales. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Cada commit identifica contenido y padres; HEAD y las ramas son referencias. Merge, rebase, cherry-pick y revert construyen grafos distintos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** conserva decisiones y cambios revisables en commits pequeños. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Reescribir historia publicada o usar reset sin comprender alcance puede perder trabajo accesible. La regla es: Preferir operaciones reversibles y commits atómicos; proteger ramas compartidas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica merge con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 5. fast-forward

**Problema e intuición.** fast-forward aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Git avanzado y colaboración, fast-forward se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Cada commit identifica contenido y padres; HEAD y las ramas son referencias. Merge, rebase, cherry-pick y revert construyen grafos distintos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** conserva decisiones y cambios revisables en commits pequeños. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Reescribir historia publicada o usar reset sin comprender alcance puede perder trabajo accesible. La regla es: Preferir operaciones reversibles y commits atómicos; proteger ramas compartidas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica fast-forward con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 6. merge commit

**Problema e intuición.** merge commit aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Estas operaciones modifican o recorren el grafo de commits de formas distintas. Revert añade un commit inverso; reset mueve una referencia y reflog ayuda a recuperar referencias locales. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Cada commit identifica contenido y padres; HEAD y las ramas son referencias. Merge, rebase, cherry-pick y revert construyen grafos distintos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** conserva decisiones y cambios revisables en commits pequeños. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Reescribir historia publicada o usar reset sin comprender alcance puede perder trabajo accesible. La regla es: Preferir operaciones reversibles y commits atómicos; proteger ramas compartidas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica merge commit con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 7. rebase

**Problema e intuición.** rebase aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Estas operaciones modifican o recorren el grafo de commits de formas distintas. Revert añade un commit inverso; reset mueve una referencia y reflog ayuda a recuperar referencias locales. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Cada commit identifica contenido y padres; HEAD y las ramas son referencias. Merge, rebase, cherry-pick y revert construyen grafos distintos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** conserva decisiones y cambios revisables en commits pequeños. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Reescribir historia publicada o usar reset sin comprender alcance puede perder trabajo accesible. La regla es: Preferir operaciones reversibles y commits atómicos; proteger ramas compartidas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica rebase con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 8. rebase interactivo

**Problema e intuición.** rebase interactivo aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Estas operaciones modifican o recorren el grafo de commits de formas distintas. Revert añade un commit inverso; reset mueve una referencia y reflog ayuda a recuperar referencias locales. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Cada commit identifica contenido y padres; HEAD y las ramas son referencias. Merge, rebase, cherry-pick y revert construyen grafos distintos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** conserva decisiones y cambios revisables en commits pequeños. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Reescribir historia publicada o usar reset sin comprender alcance puede perder trabajo accesible. La regla es: Preferir operaciones reversibles y commits atómicos; proteger ramas compartidas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica rebase interactivo con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 9. cherry-pick

**Problema e intuición.** cherry-pick aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Estas operaciones modifican o recorren el grafo de commits de formas distintas. Revert añade un commit inverso; reset mueve una referencia y reflog ayuda a recuperar referencias locales. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Cada commit identifica contenido y padres; HEAD y las ramas son referencias. Merge, rebase, cherry-pick y revert construyen grafos distintos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** conserva decisiones y cambios revisables en commits pequeños. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Reescribir historia publicada o usar reset sin comprender alcance puede perder trabajo accesible. La regla es: Preferir operaciones reversibles y commits atómicos; proteger ramas compartidas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica cherry-pick con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 10. revert

**Problema e intuición.** revert aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Estas operaciones modifican o recorren el grafo de commits de formas distintas. Revert añade un commit inverso; reset mueve una referencia y reflog ayuda a recuperar referencias locales. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Cada commit identifica contenido y padres; HEAD y las ramas son referencias. Merge, rebase, cherry-pick y revert construyen grafos distintos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** conserva decisiones y cambios revisables en commits pequeños. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Reescribir historia publicada o usar reset sin comprender alcance puede perder trabajo accesible. La regla es: Preferir operaciones reversibles y commits atómicos; proteger ramas compartidas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica revert con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 11. restore

**Problema e intuición.** restore aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Git avanzado y colaboración, restore se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Cada commit identifica contenido y padres; HEAD y las ramas son referencias. Merge, rebase, cherry-pick y revert construyen grafos distintos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** conserva decisiones y cambios revisables en commits pequeños. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Reescribir historia publicada o usar reset sin comprender alcance puede perder trabajo accesible. La regla es: Preferir operaciones reversibles y commits atómicos; proteger ramas compartidas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica restore con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 12. reset y sus riesgos

**Problema e intuición.** reset y sus riesgos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Estas operaciones modifican o recorren el grafo de commits de formas distintas. Revert añade un commit inverso; reset mueve una referencia y reflog ayuda a recuperar referencias locales. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Cada commit identifica contenido y padres; HEAD y las ramas son referencias. Merge, rebase, cherry-pick y revert construyen grafos distintos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** conserva decisiones y cambios revisables en commits pequeños. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Reescribir historia publicada o usar reset sin comprender alcance puede perder trabajo accesible. La regla es: Preferir operaciones reversibles y commits atómicos; proteger ramas compartidas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica reset y sus riesgos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 13. reflog

**Problema e intuición.** reflog aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Estas operaciones modifican o recorren el grafo de commits de formas distintas. Revert añade un commit inverso; reset mueve una referencia y reflog ayuda a recuperar referencias locales. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Cada commit identifica contenido y padres; HEAD y las ramas son referencias. Merge, rebase, cherry-pick y revert construyen grafos distintos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** conserva decisiones y cambios revisables en commits pequeños. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Reescribir historia publicada o usar reset sin comprender alcance puede perder trabajo accesible. La regla es: Preferir operaciones reversibles y commits atómicos; proteger ramas compartidas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica reflog con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 14. resolución de conflictos

**Problema e intuición.** resolución de conflictos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Git avanzado y colaboración, resolución de conflictos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Cada commit identifica contenido y padres; HEAD y las ramas son referencias. Merge, rebase, cherry-pick y revert construyen grafos distintos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** conserva decisiones y cambios revisables en commits pequeños. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Reescribir historia publicada o usar reset sin comprender alcance puede perder trabajo accesible. La regla es: Preferir operaciones reversibles y commits atómicos; proteger ramas compartidas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica resolución de conflictos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 15. bisect

**Problema e intuición.** bisect aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Git avanzado y colaboración, bisect se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Cada commit identifica contenido y padres; HEAD y las ramas son referencias. Merge, rebase, cherry-pick y revert construyen grafos distintos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** conserva decisiones y cambios revisables en commits pequeños. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Reescribir historia publicada o usar reset sin comprender alcance puede perder trabajo accesible. La regla es: Preferir operaciones reversibles y commits atómicos; proteger ramas compartidas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica bisect con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 16. tags

**Problema e intuición.** tags aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Git avanzado y colaboración, tags se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Cada commit identifica contenido y padres; HEAD y las ramas son referencias. Merge, rebase, cherry-pick y revert construyen grafos distintos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** conserva decisiones y cambios revisables en commits pequeños. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Reescribir historia publicada o usar reset sin comprender alcance puede perder trabajo accesible. La regla es: Preferir operaciones reversibles y commits atómicos; proteger ramas compartidas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica tags con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 17. stash

**Problema e intuición.** stash aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Git avanzado y colaboración, stash se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Cada commit identifica contenido y padres; HEAD y las ramas son referencias. Merge, rebase, cherry-pick y revert construyen grafos distintos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** conserva decisiones y cambios revisables en commits pequeños. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Reescribir historia publicada o usar reset sin comprender alcance puede perder trabajo accesible. La regla es: Preferir operaciones reversibles y commits atómicos; proteger ramas compartidas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica stash con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 18. limpieza responsable del historial

**Problema e intuición.** limpieza responsable del historial aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Git avanzado y colaboración, limpieza responsable del historial se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Cada commit identifica contenido y padres; HEAD y las ramas son referencias. Merge, rebase, cherry-pick y revert construyen grafos distintos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** conserva decisiones y cambios revisables en commits pequeños. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Reescribir historia publicada o usar reset sin comprender alcance puede perder trabajo accesible. La regla es: Preferir operaciones reversibles y commits atómicos; proteger ramas compartidas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica limpieza responsable del historial con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 19. commits atómicos

**Problema e intuición.** commits atómicos aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Git avanzado y colaboración, commits atómicos se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Cada commit identifica contenido y padres; HEAD y las ramas son referencias. Merge, rebase, cherry-pick y revert construyen grafos distintos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** conserva decisiones y cambios revisables en commits pequeños. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Reescribir historia publicada o usar reset sin comprender alcance puede perder trabajo accesible. La regla es: Preferir operaciones reversibles y commits atómicos; proteger ramas compartidas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica commits atómicos con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 20. mensajes útiles

**Problema e intuición.** mensajes útiles aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Git avanzado y colaboración, mensajes útiles se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Cada commit identifica contenido y padres; HEAD y las ramas son referencias. Merge, rebase, cherry-pick y revert construyen grafos distintos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** conserva decisiones y cambios revisables en commits pequeños. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Reescribir historia publicada o usar reset sin comprender alcance puede perder trabajo accesible. La regla es: Preferir operaciones reversibles y commits atómicos; proteger ramas compartidas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica mensajes útiles con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 21. revisión de código

**Problema e intuición.** revisión de código aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Git avanzado y colaboración, revisión de código se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Cada commit identifica contenido y padres; HEAD y las ramas son referencias. Merge, rebase, cherry-pick y revert construyen grafos distintos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** conserva decisiones y cambios revisables en commits pequeños. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Reescribir historia publicada o usar reset sin comprender alcance puede perder trabajo accesible. La regla es: Preferir operaciones reversibles y commits atómicos; proteger ramas compartidas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica revisión de código con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 22. pull request y merge request

**Problema e intuición.** pull request y merge request aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Estas operaciones modifican o recorren el grafo de commits de formas distintas. Revert añade un commit inverso; reset mueve una referencia y reflog ayuda a recuperar referencias locales. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Cada commit identifica contenido y padres; HEAD y las ramas son referencias. Merge, rebase, cherry-pick y revert construyen grafos distintos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** conserva decisiones y cambios revisables en commits pequeños. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Reescribir historia publicada o usar reset sin comprender alcance puede perder trabajo accesible. La regla es: Preferir operaciones reversibles y commits atómicos; proteger ramas compartidas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica pull request y merge request con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 23. actualización de ramas

**Problema e intuición.** actualización de ramas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Git avanzado y colaboración, actualización de ramas se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Cada commit identifica contenido y padres; HEAD y las ramas son referencias. Merge, rebase, cherry-pick y revert construyen grafos distintos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** conserva decisiones y cambios revisables en commits pequeños. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Reescribir historia publicada o usar reset sin comprender alcance puede perder trabajo accesible. La regla es: Preferir operaciones reversibles y commits atómicos; proteger ramas compartidas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica actualización de ramas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 24. Git Flow

**Problema e intuición.** Git Flow aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Git avanzado y colaboración, Git Flow se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Cada commit identifica contenido y padres; HEAD y las ramas son referencias. Merge, rebase, cherry-pick y revert construyen grafos distintos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** conserva decisiones y cambios revisables en commits pequeños. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Reescribir historia publicada o usar reset sin comprender alcance puede perder trabajo accesible. La regla es: Preferir operaciones reversibles y commits atómicos; proteger ramas compartidas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica Git Flow con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 25. trunk-based development

**Problema e intuición.** trunk-based development aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Git avanzado y colaboración, trunk-based development se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Cada commit identifica contenido y padres; HEAD y las ramas son referencias. Merge, rebase, cherry-pick y revert construyen grafos distintos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** conserva decisiones y cambios revisables en commits pequeños. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Reescribir historia publicada o usar reset sin comprender alcance puede perder trabajo accesible. La regla es: Preferir operaciones reversibles y commits atómicos; proteger ramas compartidas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica trunk-based development con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 26. ramas de corta duración

**Problema e intuición.** ramas de corta duración aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Git avanzado y colaboración, ramas de corta duración se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Cada commit identifica contenido y padres; HEAD y las ramas son referencias. Merge, rebase, cherry-pick y revert construyen grafos distintos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** conserva decisiones y cambios revisables en commits pequeños. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Reescribir historia publicada o usar reset sin comprender alcance puede perder trabajo accesible. La regla es: Preferir operaciones reversibles y commits atómicos; proteger ramas compartidas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica ramas de corta duración con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 27. protección de ramas

**Problema e intuición.** protección de ramas aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Git avanzado y colaboración, protección de ramas se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Cada commit identifica contenido y padres; HEAD y las ramas son referencias. Merge, rebase, cherry-pick y revert construyen grafos distintos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** conserva decisiones y cambios revisables en commits pequeños. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Reescribir historia publicada o usar reset sin comprender alcance puede perder trabajo accesible. La regla es: Preferir operaciones reversibles y commits atómicos; proteger ramas compartidas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica protección de ramas con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 28. estrategias para releases y hotfixes

**Problema e intuición.** estrategias para releases y hotfixes aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** Eureka mantiene registros mediante renovaciones y cachés. La ausencia de heartbeat tarda en reflejarse; self-preservation evita expulsiones masivas ante pérdida de comunicación. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Cada commit identifica contenido y padres; HEAD y las ramas son referencias. Merge, rebase, cherry-pick y revert construyen grafos distintos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** conserva decisiones y cambios revisables en commits pequeños. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Reescribir historia publicada o usar reset sin comprender alcance puede perder trabajo accesible. La regla es: Preferir operaciones reversibles y commits atómicos; proteger ramas compartidas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica estrategias para releases y hotfixes con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 29. colaboración sin force push destructivo

**Problema e intuición.** colaboración sin force push destructivo aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Git avanzado y colaboración, colaboración sin force push destructivo se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Cada commit identifica contenido y padres; HEAD y las ramas son referencias. Merge, rebase, cherry-pick y revert construyen grafos distintos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** conserva decisiones y cambios revisables en commits pequeños. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Reescribir historia publicada o usar reset sin comprender alcance puede perder trabajo accesible. La regla es: Preferir operaciones reversibles y commits atómicos; proteger ramas compartidas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica colaboración sin force push destructivo con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

### 30. recuperación de errores

**Problema e intuición.** recuperación de errores aparece porque una aplicación real debe conservar significado cuando intervienen clientes, infraestructura, datos y fallos. La analogía central de la unidad ayuda a ubicarlo, pero no reemplaza el mecanismo técnico.

**Definición de trabajo.** En Git avanzado y colaboración, recuperación de errores se considera una capacidad observable: Mikko debe poder describir su propósito, reconocer sus límites y justificar una decisión concreta, no solo repetir el nombre. Esta definición se comprueba preguntando qué entrada recibe, qué estado observa o modifica, qué salida ofrece y qué garantía no ofrece.

**Qué ocurre internamente.** Cada commit identifica contenido y padres; HEAD y las ramas son referencias. Merge, rebase, cherry-pick y revert construyen grafos distintos. Para estudiar este punto, sigue el recorrido desde la entrada hasta el efecto y marca la frontera donde cambia la responsabilidad.

**Aplicación en FintechLab.** conserva decisiones y cambios revisables en commits pequeños. La evidencia esperada puede ser una prueba, una consulta, un contrato, un log seguro o una decisión arquitectónica, según el punto.

**Fallo y decisión profesional.** Reescribir historia publicada o usar reset sin comprender alcance puede perder trabajo accesible. La regla es: Preferir operaciones reversibles y commits atómicos; proteger ramas compartidas. Antes de adoptar una herramienta, escribe la invariante o riesgo que intenta proteger.

**Comprobación breve, sin respuesta:** explica recuperación de errores con un ejemplo diferente de FintechLab, señala un límite y propone una evidencia que permitiría verificar tu afirmación.

## Decisiones y alternativas

1. Define primero la invariante o el riesgo.
2. Compara una opción simple con otra más operable.
3. Registra el costo introducido: latencia, acoplamiento, complejidad o mantenimiento.
4. Elige: Preferir operaciones reversibles y commits atómicos; proteger ramas compartidas.
5. Documenta qué dato futuro haría cambiar la decisión.

## Errores frecuentes

- Copiar una anotación sin conocer quién la interpreta ni cuándo.
- Confundir el caso feliz con el contrato completo.
- Añadir infraestructura sin una necesidad observable.
- Escribir un test que replica la implementación en vez de cubrir riesgo.
- Registrar datos sensibles para facilitar debugging.
- Afirmar una garantía sin una restricción, prueba o medición que la sostenga.

## Qué ocurre en producción

Reescribir historia publicada o usar reset sin comprender alcance puede perder trabajo accesible. Un profesional diseña para degradación, documenta límites, mide antes de optimizar y deja una ruta de recuperación. FintechLab usa datos ficticios y no debe conectarse con bancos ni procesar dinero real.

## Cómo reconocer que realmente lo entendiste

- Puedes explicarlo sin depender del nombre de una anotación.
- Puedes dibujar el recorrido y marcar al menos tres fallos.
- Puedes cambiar el ejemplo sin copiarlo.
- Puedes escribir una prueba que falla cuando se rompe la regla.
- Puedes nombrar una alternativa y justificar el trade-off.

## Resumen sencillo

La unidad enseña a convertir Git avanzado y colaboración en decisiones verificables. La idea profesional no es acumular herramientas, sino conectar propósito, mecanismo, fallo, evidencia y costo.

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
