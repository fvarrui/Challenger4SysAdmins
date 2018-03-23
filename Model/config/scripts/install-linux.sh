#!/bin/bash

# instala sysdig: herramienta de exploración y monitorización del sistema
apt-get install sysdig

# activa el bit SUID en el binario de sysdig, para poder ejecutarlo con usuario normal
chmod u+x $(which sysdig)