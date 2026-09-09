# Puente hacia la unidad 5

## Lo único que se da por dominado

Java y POO inicial, uso básico de terminal y lectura de archivos. Git básico es recomendable para conservar intentos. No se presupone Spring Boot, arquitectura web por capas, controller/service/repository, CRUD, HTTP, JPA ni conexión a datos.

## Si partes desde cero

Empieza en `05-spring-boot-basico/00-objetivos-y-ruta.md` y luego lee `00A-guia-previa-java-maven-y-proyectos.md`. Esa guía no presupone Maven, POM, JAR, classpath, `target`, IntelliJ ni Spring Boot. La unidad incluye después un proyecto separado que arranca primero con H2 y luego conecta MySQL. No abras el monolito avanzado como primer ejercicio.

## Si ya conoces Spring Boot

Realiza la prueba de salida de `05-spring-boot-basico/90-ejercicios-autoevaluacion-y-rubrica.md`. Puedes pasar a la unidad 6 si construyes y explicas una API por capas, validación, errores, persistencia y pruebas básicas sin depender de copiar una plantilla.

## Checklist

- [ ] Java 21 y Maven Wrapper funcionan.
- [ ] Puedo leer clases, interfaces, constructores, records y excepciones Java.
- [ ] Puedo ejecutar comandos y distinguir carpeta actual de archivo.
- [ ] Tengo un editor configurado con el mismo JDK que Maven.
- [ ] Docker está disponible para la etapa MySQL, o sé que puedo posponerla.
- [ ] El repositorio está limpio antes de cada laboratorio.
- [ ] Ninguna credencial real está versionada.

## Diagnóstico opcional — sin respuestas

Sin consultar ejemplos, explica el arranque y el recorrido controller–service–repository, añade un endpoint validado, identifica dependencias en el POM, persiste una entidad y reproduce una prueba unitaria y otra web. Si una parte no sale, estudia el bloque correspondiente de la unidad 5 antes de la unidad 6.
