@echo off

echo Configurando la monitorizacion de interpretes de comandos de Windows (CMD y PowerShell):
echo.

echo 1) Creando EventSource Challenger4SysAdmins en el Registro de Windows
rem Info: https://msdn.microsoft.com/es-es/library/windows/desktop/aa363661(v=vs.85).aspx
reg add "HKLM\SYSTEM\CurrentControlSet\Services\EventLog\Application\Challenger4SysAdmins" /v CustomSource /t REG_DWORD /d 1 /f > nul 
reg add "HKLM\SYSTEM\CurrentControlSet\Services\EventLog\Application\Challenger4SysAdmins" /v EventMessageFile /t REG_EXPAND_SZ /d "%SystemRoot%\System32\EventCreate.exe" /f > nul 
reg add "HKLM\SYSTEM\CurrentControlSet\Services\EventLog\Application\Challenger4SysAdmins" /v TypesSupported /t REG_DWORD /d 7 /f > nul

echo 2) Copiando script cmd-audit.cmd para auditar CMD en %windir%\System32 
set AUTORUN=%windir%\System32\cmd-audit.bat
copy resources\cmd-audit.bat %AUTORUN% > nul
 
echo 3) Configurando AutoRun en el registro para iniciar cmd-audit.cmd automaticamente al abrir CMD
reg add "HKLM\Software\Microsoft\Command Processor" /v AutoRun /t REG_EXPAND_SZ /d "%AUTORUN%" /f > nul
 
echo 4) Estableciendo el perfil global de PowerShell para monitorizarlo sobrescribiendo la funcion "prompt" 
set PSHOME=%WinDir%\System32\WindowsPowerShell\v1.0
copy resources\powershell-profile.ps1 %PSHOME%\Profile.ps1 > nul

echo.
echo Proceso completado