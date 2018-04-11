#!/bin/bash

echo -e "Eliminando la monitorizacion de interpretes de comandos de Linux (BASH):\n"

patron="^export PROMPT_COMMAND=.*$"
bashrc=/etc/bash.bashrc

# elimina la variable PROMPT_COMMAND a la configuración global de la BASH (afecta a todos los usuarios) para que se registre en la tubería el último comando ejecutado
if grep -q $patron $bashrc
then
	echo "1) Eliminando la variable PROMPT_COMMAND de $bashrc"
	sed -i "/$patron/d" $bashrc
else
	echo "1) La variable PROMPT_COMMAND no existe en $bashrc"
fi

echo -e "\nProceso completado"

