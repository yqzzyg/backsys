@echo off
echo [INFO] build and install modules.
cd ..\
call mvn spring-boot:run -Dspring.profiles.active=oracle,testdb,api-doc
pause
