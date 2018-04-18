;This file will be executed next to the application bundle image
;I.e. current directory will contain folder Challenger with application files

#define APP_VERSION "0.0.1"

[Setup]
AppId={{fvarrui.sysadmin.challenger.monitoring.app}}
AppName=Challenger
AppVersion={#APP_VERSION}
AppVerName=Challenger {#APP_VERSION}
AppPublisher=Fran Vargas
AppComments=Challenger4SysAdmins
AppCopyright=Copyright (C) 2018
AppPublisherURL=https://github.com/fvarrui/
;AppSupportURL=http://java.com/
;AppUpdatesURL=http://java.com/
DefaultDirName={pf}\Challenger4SysAdmins\Challenger
DisableStartupPrompt=Yes
DisableDirPage=Yes
DisableProgramGroupPage=Yes
DisableReadyPage=No
DisableFinishedPage=No
DisableWelcomePage=No
DefaultGroupName=Challenger4SysAdmins
;Optional License
LicenseFile=LICENSE.txt
;windows vista o superior
MinVersion=6.0
OutputBaseFilename=Challenger-setup-{#APP_VERSION}
Compression=lzma
SolidCompression=yes
PrivilegesRequired=admin
SetupIconFile=Challenger\Challenger.ico
UninstallDisplayIcon={app}\Challenger.ico
UninstallDisplayName=Challenger
WizardImageStretch=Yes
WizardSmallImageFile=Challenger-setup-icon.bmp   
ArchitecturesInstallIn64BitMode=x64
LanguageDetectionMethod=locale

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"
Name: "spanish"; MessagesFile: "compiler:Languages\Spanish.isl"

[Files]
Source: "Challenger\Challenger.exe"; DestDir: "{app}"; Flags: ignoreversion
Source: "Challenger\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs

[Icons]
Name: "{group}\Challenger"; Filename: "{app}\Challenger.exe"; IconFilename: "{app}\Challenger.ico"; Check: returnTrue()
Name: "{commondesktop}\Challenger"; Filename: "{app}\Challenger.exe";  IconFilename: "{app}\Challenger.ico"; Check: returnTrue()


[Run]
Filename: "{app}\Challenger.exe"; Parameters: "-Xappcds:generatecache"; Check: returnFalse()
Filename: "{app}\Challenger.exe"; Description: "{cm:LaunchProgram,Challenger}"; Flags: nowait postinstall skipifsilent; Check: returnTrue()
Filename: "{app}\Challenger.exe"; Parameters: "-install -svcName ""Challenger"" -svcDesc ""Retador de administradores de sistemas"" -mainExe ""Challenger.exe""  "; Check: returnFalse()

[UninstallRun]
Filename: "{app}\Challenger.exe "; Parameters: "-uninstall -svcName Challenger -stopOnUninstall"; Check: returnFalse()

[Code]
function returnTrue(): Boolean;
begin
  Result := True;
end;

function returnFalse(): Boolean;
begin
  Result := False;
end;

function InitializeSetup(): Boolean;
begin
// Possible future improvements:
//   if version less or same => just launch app
//   if upgrade => check if same app is running and wait for it to exit
//   Add pack200/unpack200 support? 
  Result := True;
end;  
