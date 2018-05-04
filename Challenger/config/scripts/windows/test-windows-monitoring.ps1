
Write-Host "Comprobando si la monitorización de intérpretes de comandos de Windows (CMD y Powershell) está habilitada..."

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

<#
    Comprueba si existe la clave 'HKLM\SYSTEM\CurrentControlSet\Services\EventLog\Application\Challenger4SysAdmins',
    correspondiente al origen de eventos del sistema 'Challenger4SysAdmins'.
#>
function Test-EventSourceRegistered() {
    Write-Host -NoNewline "- Test-EventSourceRegistered"
    If (Test-Path "HKLM:\SYSTEM\CurrentControlSet\Services\EventLog\Application\Challenger4SysAdmins") {
        Write-Host ("." * 4) "[OK]"
        return $true
    }
    Write-Host ("." * 4) "[ERROR]"
    return $false
}

<#
    Comprueba si existe una copia del script de monitorización en "System32"
#>
function Test-CmdAuditScriptExists() {
    Write-Host -NoNewline "- Test-CmdAuditScriptExists"
    If (Test-Path "$env:windir\System32\cmd-audit.bat") {
        Write-Host ("." * 5) "[OK]"
        return $true
    }
    Write-Host ("." * 5) "[ERROR]"
    return $false
}

<#
    Comprueba si existe el valor "Autorun" en "HKLM\Software\Microsoft\Command Processor" y
    hace referencia al script de monitorización.
#>
function Test-AutorunCmdEnabled() {
    Write-Host -NoNewline "- Test-AutorunCmdEnabled"
    If (Test-RegistryValue -Path "HKLM:\Software\Microsoft\Command Processor" -Value "Autorun") {
        $Value = (Get-ItemProperty -Path "HKLM:\Software\Microsoft\Command Processor" -Name "Autorun").Autorun
        If ($Value -ieq "$env:windir\System32\cmd-audit.bat") {
            Write-Host ("." * 8) "[OK]"
            return $true
        }
    }
    Write-Host ("." * 8) "[ERROR]"
    return $false
}

function Test-PSGlobalProfileEnabled() {
    Write-Host -NoNewline "- Test-PSGlobalProfileEnabled"
    If (Test-PAth "$PSHOME\Profile.ps1") {
        Write-Host ("." * 3) "[OK]"
        return $true
    }
    Write-Host ("." * 3) "[ERROR]"
    return $false
}

if ($(Test-EventSourceRegistered) -and $(Test-CmdAuditScriptExists) -and $(Test-AutorunCmdEnabled) -and $(Test-PSGlobalProfileEnabled)) {
    Write-Host "Monitorización habilitada"
    exit 0
} else {
    Write-Host "Monitorización deshabilitada"
    exit 1
}
