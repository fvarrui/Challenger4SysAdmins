@echo off
rem Elimina la inyecci�n de Click al iniciar el Simbolo del sistema (cmd.exe)
reg delete "HKLM\Software\Microsoft\Command Processor" /v AutoRun /f 