#!/bin/bash

patron="^export PROMPT_COMMAND=.*$"
bashrc=/etc/bash.bashrc

# Añade la variable PROMPT_COMMAND a la configuración global de la BASH (afecta a todos los usuarios) 
# para que se registre en "syslog" el último comando ejecutado
function enable-monitoring() {
	echo -e "Configurando la monitorizacion de interpretes de comandos de Linux (BASH):\n"
	if grep -q "$patron" $bashrc
	then
		echo "- La variable PROMPT_COMMAND ya existe en $bashrc"
	else
		echo "- Añadiendo variable PROMPT_COMMAND a $bashrc"
		echo "export PROMPT_COMMAND='history -a && logger --tag Challenger4SysAdmins \"bash:\$USER:\$PWD:\$OLDPWD:\$(tail -n 1 \$HISTFILE)\"'" >> $bashrc
		echo "echo -e \"Este interprete de comandos esta siendo monitorizado por Challenger4SysAdmins\n\"" >> $bashrc
	fi
	echo -e "\nProceso completado"
	exit 0
}

# Elimina la variable PROMPT_COMMAND de la configuración global de la BASH (afectando a todos los usuarios) 
# para que se registre en "syslog" el último comando ejecutado
function disable-monitoring() {
	echo -e "Eliminando la monitorizacion de interpretes de comandos de Linux (BASH):\n"

	if grep -q "$patron" $bashrc
	then
		echo "- Eliminando la variable PROMPT_COMMAND de $bashrc"
		sed -i "/$patron/d" $bashrc
		set -i "/^echo.*Challenger4SysAdmins.*$/d" $bashrc
	else
		echo "- La variable PROMPT_COMMAND no existe en $bashrc"
	fi
	echo -e "\nProceso completado"
	exit 0
}

# Comprueba si existe la variable PROMPT_COMMAND en el fichero /etc/bash.bashrc
function test-monitoring() {
	echo "Comprobando si la monitorización de intérpretes de comandos de GNU/Linux (bash) está habilitada..."
	error=0
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
}

if [ "$1" == "--test" ]
then
	test-monitoring
elif [ "$1" == "--enable" ]
then
	enable-monitoring
elif [ "$1" == "--disable" ]
then
	disable-monitoring
else
	echo "Script de configuración de la monitorización de la BASH para Challenger4SysAdmins"
	echo "Uso: $0 [--test|--enable|--disable]"
	echo
fi
