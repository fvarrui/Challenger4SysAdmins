@echo off
rem A�ade la inyecci�n de Clink al iniciar el Simbolo del sistema (cmd.exe)
set CLINK_HOME=C:\Users\Fran\Desktop\clink_0.4.9
reg add "HKLM\Software\Microsoft\Command Processor" /v AutoRun /t REG_EXPAND_SZ /d "%CLINK_HOME%\clink.bat inject -q" /f 