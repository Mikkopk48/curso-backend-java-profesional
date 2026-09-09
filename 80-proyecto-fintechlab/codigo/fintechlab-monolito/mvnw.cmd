@echo off
where mvn >nul 2>nul || (echo Maven 3.9+ no esta instalado. Instale Maven o use CI. & exit /b 127)
mvn %*
