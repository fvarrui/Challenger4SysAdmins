#!/bin/bash

echo -e "Configurando la monitorizacion de interpretes de comandos de Linux (BASH):\n"

patron="^export PROMPT_COMMAND=.*$"
bashrc=/etc/bash.bashrc

# añade la variable PROMPT_COMMAND a la configuración global de la BASH (afecta a todos los usuarios) para que se registre en syslog el último comando ejecutado
if grep -q "$patron" $bashrc
then
	echo "La variable PROMPT_COMMAND ya existe en $bashrc"
else
	echo "Añadiendo variable PROMPT_COMMAND a $bashrc"
	echo "export PROMPT_COMMAND='history -a && logger --tag Challenger4SysAdmins \"bash:\$USER:\$PWD:\$OLDPWD:\$(tail -n 1 \$HISTFILE)\"'" >> $bashrc
	echo "echo -e \"Este interprete de comandos esta siendo monitorizado por Challenger4SysAdmins\n\"" >> $bashrc
fi

echo -e "\nProceso completado"