@echo off
echo [INFO] Run dev and api-doc is http://localhost:8080/api/swagger-ui.html
cd ..\
call mvn spring-boot:run -Dspring.profiles.active=dev,api-doc
pause
