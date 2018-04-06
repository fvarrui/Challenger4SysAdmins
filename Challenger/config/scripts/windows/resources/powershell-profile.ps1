$global:OLDPWD = Get-Location

# registra un evento correspondiente al último Cmdlet ejecutado en PowerShell
function Write-LastPSCmdletEvent ($cmdlet) {
	$username = $env:USERNAME
	$pwd = ($PWD).Path
    $eventId = 1
    $type = 1 	# information event
    $id = New-Object System.Diagnostics.EventInstance($eventId, $type)
    $event = New-Object System.Diagnostics.EventLog
    $event.Log = "Application"
    $event.Source = "Challenger4SysAdmins"
    $event.WriteEvent($id, @($cmdlet, $username, $pwd, $global:OLDPWD))
}

# sobreescribe la función prompt para "inyectar" el registro de cmdlets ejecutados
function prompt {
	$lastCmdlet = (Get-History -Count 1).CommandLine
	if ($lastCmdlet -ne "") {
		Write-LastPSCmdletEvent $lastCmdlet
	}
	$global:OLDPWD = Get-Location
	return "PS $($executionContext.SessionState.Path.CurrentLocation)$('>' * ($nestedPromptLevel + 1)) "
}