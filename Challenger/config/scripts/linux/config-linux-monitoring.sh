#!/bin/bash

echo -e "Configurando la monitorizacion de interpretes de comandos de Linux (BASH):\n"

bashrc=/etc/bash.bashrc

# añade la variable PROMPT_COMMAND a la configuración global de la BASH (afecta a todos los usuarios) para que se registre en syslog el último comando ejecutado
if grep -q "^export PROMPT_COMMAND=.*$" $bashrc
then
	echo "La variable PROMPT_COMMAND ya existe en $bashrc"
else
	echo "Añadiendo variable PROMPT_COMMAND a $bashrc"
	echo "export PROMPT_COMMAND='history -a && logger --tag Challenger4SysAdmins \"bash:\$USER:\$PWD:\$OLDPWD:\$(tail -n 1 \$HISTFILE)\"'" >> $bashrc
fi

echo -e "\nProceso completado"