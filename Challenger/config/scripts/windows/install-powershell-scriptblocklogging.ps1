Param(
    [switch]$Uninstall = $false,
    [switch]$Install = !$Uninstall
)

$ErrorActionPreference = "Stop"

function Get-HiveValues($hive) {
    return Get-ItemProperty -Path $hive;
}

function Test-Hive($path) {
    try {
        Get-Item -Path $path | Out-Null
	    return $true
    } catch [System.Management.Automation.ItemNotFoundException] {
	    return $false
    }
}

function Test-Value($path, $name) {
    try {
        Get-ItemProperty -Path $path -Name $name | Out-Null
	    return $true
    } catch [System.Management.Automation.PSArgumentException] {
	    return $false
    }
}

function Get-Value($path, $name) {
    try {
        return (Get-ItemProperty -Path $path -Name $name).$name
    } catch [System.Management.Automation.PSArgumentException] {
	    return $null
    }
}

function New-Hive($path, $name) {
    $newHive = "$path\$name"
    If (!(Test-Hive $newHive)) {
        Write-Host "Creando clave $newHive"
        New-Item -Path $path -Name $name | Out-Null
    } else {
        Write-Host "Ya existe la clave" $newHive
    }
}

function New-Value($path, $name, $type, $value) {
    If (!(Test-Value $path $name)) {
        Write-Host "Creando valor" $name "en" $path "de tipo" $type "con valor" $value
        New-ItemProperty -Path $path -Name $name -PropertyType $tyle -Value $value | Out-Null
    } else {
        Write-Host "Actualizando valor" $name "en" $path "con valor" $value
        Set-ItemProperty -Path $path -Name $name -Value $value | Out-Null
    }
}

function Remove-Hive($path) {
    If (Test-Hive $path) {
        Write-Host "Eliminando $path"
        Remove-Item -Path $path
    }
}

$psHive = "HKLM:\SOFTWARE\Policies\Microsoft\Windows\PowerShell"

function EnableScriptBlockLogging() {
    New-Hive $psHive "ScriptBlockLogging"
    New-Value "$psHive\ScriptBlockLogging" "EnableScriptBlockInvocationLogging" "DWord" 1
    New-Value "$psHive\ScriptBlockLogging" "EnableScriptBlockLogging" "DWord" 1
}

function DisableScriptBlockLogging() {
    Remove-Hive "$psHive\ScriptBlockLogging"
}

if ($Install) {
    Write-Host "Habilitando"
    EnableScriptBlockLogging
} else {
    Write-Host "Deshabilitando"
    DisableScriptBlockLogging
}
