Param(
    [switch]$Enable,
    [switch]$Disable,
    [switch]$Test = -not ($Enable -or $Disable)
)

$EventSourcePath = "HKLM:\SYSTEM\CurrentControlSet\Services\EventLog\Application\Challenger4SysAdmins"
$CmdAutorunPath = "HKLM:\Software\Microsoft\Command Processor"
$CmdAuditScriptPath = "$env:windir\System32\cmd-audit.bat"

##########################################################################
# Funciones auxiliares
##########################################################################

<#
    Comprueba si existe un valor determinado en el Registro de Windows
#>
function Test-RegistryValue($Path, $Value) {
    try {
        Get-ItemProperty -Path $Path | Select-Object -ExpandProperty $Value -ErrorAction Stop | Out-Null
        return $true
    } catch {
        return $false
    }
}

##########################################################################
# Habilitar monitorización
##########################################################################

<#
    Crea el EventSource Challenger4SysAdmins en el Registro de Windows donde se registrará
    un evento por cada comando ejecutado en los intérprete de comandos.

    Info: https://msdn.microsoft.com/es-es/library/windows/desktop/aa363661(v=vs.85).aspx
#>
function New-EventSource() {
    Write-Host "- Creando EventSource Challenger4SysAdmins en el Registro de Windows"
    New-Item -Path $EventSourcePath | Out-Null
    New-ItemProperty -Path $EventSourcePath -Name CustomSource -PropertyType DWord -Value 1 | Out-Null
    New-ItemProperty -Path $EventSourcePath -Name EventMessageFile -PropertyType ExpandString -Value "$env:windir\System32\EventCreate.exe" | Out-Null
    New-ItemProperty -Path $EventSourcePath -Name TypesSupported -PropertyType DWord -Value 7 | Out-Null
}

<#
    Habilita la monitorización de CMD (Símbolo del sistema):
    - Se configura para que registre un evento por cada comando ejecutado
#>
function Enable-CmdAudit() {
    # Copia el script para monitorizar CMD en System32
    Write-Host "- Copiando script para auditar CMD en $CmdAuditScriptPath"
    Copy-Item "$PSScriptRoot\resources\cmd-audit.bat" $CmdAuditScriptPath | Out-Null

    # Configura la ejecución automática del script al iniciar CMD para monitorizarlo
    Write-Host "- Configurando valor AutoRun en el registro para iniciar script de monitorización al abrir CMD"
    New-ItemProperty -Path $CmdAutorunPath -Name Autorun -PropertyType ExpandString -Value $CmdAuditScriptPath | Out-Null
}

<#
    Habilita la monitorización de PowerShell
#>
function Enable-PSAudit() {  
    # Crea el perfil PowerShell global (para todos los usuarios) que habilita su monitorización sobrescribiendo la función "prompt()"
    Write-Host "- Estableciendo el perfil global de PowerShell para monitorizarlo sobrescribiendo la funcion 'prompt'"
    Copy-Item "$PSScriptRoot\resources\powershell-profile.ps1" $PSHOME\Profile.ps1 | Out-Null
}

##########################################################################
# Comprobar monitorización
##########################################################################

<#
    Comprueba si existe la clave 'HKLM\SYSTEM\CurrentControlSet\Services\EventLog\Application\Challenger4SysAdmins',
    correspondiente al origen de eventos del sistema 'Challenger4SysAdmins'.
#>
function Test-EventSource() {
    return (Test-Path $EventSourcePath)
}

<#
    Comprueba si está habilitada la monitorización de CMD (Símbolo del sistema)
#>
function Test-CmdAudit() {
    # Comprueba si existe una copia del script de monitorización en "System32",
    # si existe el valor "Autorun" en "HKLM\Software\Microsoft\Command Processor"
    # y además ese valor hace referencia al script de monitorización.
    return  ((Test-Path $CmdAuditScriptPath) -and
            (Test-RegistryValue -Path $CmdAutorunPath -Value "Autorun") -and
            ((Get-ItemProperty -Path $CmdAutorunPath -Name "Autorun").Autorun -ieq $CmdAuditScriptPath))
}

<#
    Comprueba si está habilitada la monitorización de PowerShell
#>
function Test-PSAudit() {
    # Comprueba si existe el perfil global de PowerShell
    return (Test-Path "$PSHOME\Profile.ps1")
}

##########################################################################
# Deshabilitar monitorización
##########################################################################

<#
    Elimina el EventSource Challenger4SysAdmins del Registro de Windows
#>
function Remove-EventSource() {
    Write-Host "- Eliminando EventSource Challenger4SysAdmins del Registro de Windows"
    Remove-Item -Path $EventSourcePath | Out-Null
}

<#
    Deshabilita la monitorización de CMD (Símbolo del sistema)
#>
function Disable-CmdAudit() {
    # Elimina el script para monitorizar CMD en System32
    Write-Host "- Eliminando script para auditar CMD de $CmdAuditScriptPath"
    Remove-Item $CmdAuditScriptPath | Out-Null

    # Elimina el valor Autorun del Registro de Windows
    Write-Host "- Eliminando valor AutoRun en el registro para iniciar script de monitorización al abrir CMD"
    Remove-ItemProperty -Path $CmdAutorunPath -Name Autorun | Out-Null
}

<#
    Deshabilita la monitorización de PowerShell
#>
function Disable-PSAudit() {  
    # Elimina el perfil PowerShell global (para todos los usuarios) 
    Write-Host "- Elimina el perfil global de PowerShell"
    Remove-Item $PSHOME\Profile.ps1 | Out-Null
}

##########################################################################
# Funciones principales
##########################################################################

If ($Test) {
    $ReturnValue = 0

    Write-Host -NoNewline "- Comprobando si existe el origen de eventos Challenger4SysAdmins "
    if (-not (Test-EventSource)) {
        Write-Host "[NO]"
        $ReturnValue = 1
    } else {
        Write-Host "[SI]"
    }

    Write-Host -NoNewline "- Comprobando si está habilitada la monitorización de CMD "
    if (-not (Test-CmdAudit)) {
        Write-Host "[NO]"
        $ReturnValue = 1
    } else {
        Write-Host "[SI]"
    }
    
    Write-Host -NoNewline "- Comprobando si está habilitada la monitorización de PowerShell "
    if (-not (Test-PSAudit)) {
        Write-Host "[NO]"
        $ReturnValue = 1
    } else {
        Write-Host "[SI]"
    }

    exit $ReturnValue
}

If ($Disable) {
    Remove-EventSource
    Disable-CmdAudit
    Disable-PSAudit
}

If ($Enable) {
    New-EventSource
    Enable-CmdAudit
    Enable-PSAudit
}

