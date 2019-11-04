::======================
::以后台方式运行
::======================
@echo off
echo [INFO] Run int_cloud.
cd ..\
call mvn spring-boot:run -Dspring.profiles.active=int_cloud
pause
