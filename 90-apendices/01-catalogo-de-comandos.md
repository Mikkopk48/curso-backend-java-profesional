# Catálogo de comandos seguros

~~~bash
./mvnw clean verify
docker compose up -d mysql
docker compose logs --no-log-prefix mysql
curl -i http://localhost:8080/actuator/health
git status --short
git log --graph --decorate --oneline --all
~~~

Antes de reset, clean agresivo, force push o eliminación, trabaja en repositorio descartable y comprende recuperación. Nunca pegues secretos en la terminal compartida ni en logs de CI.
