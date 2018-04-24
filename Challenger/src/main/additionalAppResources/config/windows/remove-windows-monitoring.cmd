@echo off

echo Eliminando la monitorizacion de interpretes de comandos de Windows (CMD y PowerShell):
echo.

echo 1) Eliminando EventSource Challenger4SysAdmins del Registro de Windows
rem Info: https://msdn.microsoft.com/es-es/library/windows/desktop/aa363661(v=vs.85).aspx
reg delete "HKLM\SYSTEM\CurrentControlSet\Services\EventLog\Application\Challenger4SysAdmins" /f > nul 

echo 2) Eliminando AutoRun para CMD del registro
reg delete "HKLM\Software\Microsoft\Command Processor" /v AutoRun /f > nul

echo 3) Eliminando script cmd-audit.cmd  
set AUTORUN=%windir%\System32\cmd-audit.bat
del %AUTORUN% > nul
  
echo 4) Eliminadno el perfil global de PowerShell  
set PSHOME=%WinDir%\System32\WindowsPowerShell\v1.0
del %PSHOME%\Profile.ps1 > nul

echo.
echo Proceso completado
