#!/bin/bash

echo "Comprobando si la monitorización de intérpretes de comandos de GNU/Linux (bash) está habilitada..."

patron="^export PROMPT_COMMAND=.*$"
bashrc=/etc/bash.bashrc

error=0

# comprueba si existe la variable PROMPT_COMMAND en el fichero /etc/bash.bashrc
echo -n "- Test-PromptCommand ...... "
if grep -q "$patron" $bashrc
then	
	echo "[OK]"
else
	echo "[ERROR]"
	error=1
fi

if [ $error -eq "0" ]
then	
	echo "Monitorización habilitada"
else
	echo "Monitorización deshabilitada"
fi	

exit $error
