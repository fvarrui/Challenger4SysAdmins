@echo off
rem Elimina la inyección de Click al iniciar el Simbolo del sistema (cmd.exe)
reg delete "HKLM\Software\Microsoft\Command Processor" /v AutoRun /f 