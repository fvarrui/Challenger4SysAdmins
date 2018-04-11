#!/bin/bash

patron="^export PROMPT_COMMAND=.*$"
bashrc=/etc/bash.bashrc

# elimina la variable PROMPT_COMMAND a la configuración global de la BASH (afecta a todos los usuarios) para que se registre en la tubería el último comando ejecutado
if grep -q $patron $bashrc
then
	echo "Eliminando la variabel PROMPT_COMMAND de $bashrc"
	grep -v $patron $bashrc > $bashrc
fi

