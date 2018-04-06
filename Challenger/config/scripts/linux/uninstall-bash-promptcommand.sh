#!/bin/bash

tuberia=/tmp/challenger
bashrc=/etc/bash.bashrc

# elimina la tubería
if [ -p $tuberia ]
then
	echo "Eliminando la tubería $tuberia"
	rm -fr $tuberia
fi

# elimina la variable PROMPT_COMMAND a la configuración global de la BASH (afecta a todos los usuarios) para que se registre en la tubería el último comando ejecutado
if grep -q "^export PROMPT_COMMAND=.*$" $bashrc
then
	echo "Eliminando la variabel PROMPT_COMMAND de $bashrc"
	grep -v "^export PROMPT_COMMAND=.*$" $bashrc > $bashrc
fi

