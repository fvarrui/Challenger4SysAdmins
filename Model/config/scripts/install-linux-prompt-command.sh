#!/bin/bash

# crea una tubería para comunicar BASHMonitor con la bash)
mkfifo -m 666 /tmp/challenger

# activa el bit SUID en el binario de sysdig, para poder ejecutarlo con usuario normal

echo "export PROMPT_COMMAND='history -a && echo $(date +""%Y%m%d%H%M%S""):$USER:$PWD:$OLDPWD:$(tail -n 1 $HISTFILE) > /tmp/challenger" > /etc/bash.bashrc

