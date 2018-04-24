$global:OLDPWD = Get-Location
$global:FIRSTTIME = $true

# registra un evento correspondiente al último Cmdlet ejecutado en PowerShell
function Write-LastPSCmdletEvent ($cmdlet) {
	$username = $env:USERNAME
	$pwd = ($PWD).Path
	$message = "powershell:" + $username + ":'" + $pwd + "':'" + $global:OLDPWD + "':" + $cmdlet 
    $eventId = 1
    $type = 1 	# information event
    $id = New-Object System.Diagnostics.EventInstance($eventId, $type)
    $event = New-Object System.Diagnostics.EventLog
    $event.Log = "Application"
    $event.Source = "Challenger4SysAdmins"
    $event.WriteEvent($id, $message)
}

# sobreescribe la función prompt para "inyectar" el registro de cmdlets ejecutados
function prompt {
	$lastCmdlet = (Get-History -Count 1).CommandLine
	if ($lastCmdlet -ne "") {
		Write-LastPSCmdletEvent $lastCmdlet
	}
	$global:OLDPWD = Get-Location
	$prompt = "PS $($executionContext.SessionState.Path.CurrentLocation)$('>' * ($nestedPromptLevel + 1)) ";
	if ($global:FIRSTTIME) {
		$prompt = "`nEste interprete de comandos esta siendo monitorizado por Challenger4SysAdmins.`n`n" + $prompt 
		$global:FIRSTTIME = $false
	}
	return $prompt
}