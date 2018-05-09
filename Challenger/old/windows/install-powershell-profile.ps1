Param(
    [switch]$Uninstall = $false,
    [switch]$Install = !$Uninstall
)

$logname = "Application"
$source = "Challenger4SysAdmins"

function InstallPSProfile() {
    Write-Host "Instalando PowerShell Profile"

	# registra el origen de eventos, si no est� ya registrado
	if (-not [System.Diagnostics.EventLog]::SourceExists($source)) {
		New-EventLog -LogName $logname -Source $source
	}

	# crea el perfil de powershell para todos los usuarios y hosts, sobreescribiendo la funci�n prompt
    Copy-Item resources\powershell-profile.ps1 $PSHOME\Profile.ps1
}

function RemovePSProfile() {
    Write-Host "Desinstalando PowerShell Profile "
    
    # elimina el origen de eventos, si est� registrado
	if ([System.Diagnostics.EventLog]::SourceExists($source)) {
		Remove-EventLog -Source $source
	}  
    
    # elimina el perfil de powershell
   	Remove-Item $PSHOME\Profile.ps1
}

if ($Install) {
    InstallPSProfile
} else {
    RemovePSProfile
}
