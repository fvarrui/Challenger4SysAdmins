@echo off
title CMD monitorizado
setlocal EnableDelayedExpansion
echo.
echo Este interprete de comandos esta siendo monitorizado por Challenger4SysAdmins.
:loop
echo.
set OLDPWD=!CD!
set COMMAND=
set /P COMMAND=!CD!^>
if "!COMMAND!" == "" goto loop
call !COMMAND!
eventcreate /id 1 /L Application /T Information /SO Challenger4SysAdmins /D "cmd:!USERNAME!:'!CD!':'!OLDPWD!':!COMMAND!" > nul
goto loop

rem hay un problema si uso opciones que son consideradas validas para "call" / no puedo obviar call porque no se expanden las variables