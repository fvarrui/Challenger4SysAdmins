#!/bin/bash

tuberia=/tmp/challenger
bashrc=/etc/bash.bashrc

# crea una tubería en el directorio temporal para comunicar BASHMonitor con la shell BASH
if [ ! -e $tuberia ]
then
	echo "Creando la tubería $tuberia"
	mkfifo -m 666 $tuberia
else
	echo "La tubería $tuberia ya existe"
fi

# añade la variable PROMPT_COMMAND a la configuración global de la BASH (afecta a todos los usuarios) para que se registre en la tubería el último comando ejecutado
if grep -q "^export PROMPT_COMMAND=.*$" $bashrc
then
	echo "La variable PROMPT_COMMAND ya existe en $bashrc"
else
	echo "Añadiendo variable PROMPT_COMMAND a $bashrc"
	echo "export PROMPT_COMMAND='history -a && echo \$(date +%FT%T):\$USER:\$PWD:\$OLDPWD:\$(tail -n 1 \$HISTFILE) > $tuberia'" >> $bashrc
fi

date +%FT%T