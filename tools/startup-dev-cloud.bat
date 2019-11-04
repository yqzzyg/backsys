::======================
::以后台方式运行
::======================
@echo off
echo [INFO] Run dev_cloud.
cd ..\
call mvn spring-boot:run -Dspring.profiles.active=dev_cloud
pause
