@echo off
setlocal EnableExtensions EnableDelayedExpansion

REM Load .env.dev variables (ignores lines starting with #)
if not exist ".env.dev" (
  echo [WARN] .env.dev not found. Skipping env load.
) else (
  for /f "usebackq eol=# tokens=1,* delims==" %%A in (".env.dev") do (
    set "key=%%~A"
    set "val=%%~B"
    if /i "!key:~0,6!"=="export" (
      set "key=!key:~7!"
    )
    for /f "tokens=* delims= " %%K in ("!key!") do set "key=%%~K"
    for /f "tokens=* delims= " %%V in ("!val!") do set "val=%%~V"
    set "!key!=!val!"
  )
)

REM Run app with dev profile
mvn -Dspring-boot.run.profiles=dev spring-boot:run
set "EXITCODE=%ERRORLEVEL%"

echo.
if "%EXITCODE%"=="0" (
  echo [INFO] Spring Boot exited successfully.
) else (
  echo [ERROR] Spring Boot failed with exit code %EXITCODE%.
  echo [ERROR] Check the error messages above for details.
  echo.
)

echo Press any key to close this window...
pause
exit /b %EXITCODE%
