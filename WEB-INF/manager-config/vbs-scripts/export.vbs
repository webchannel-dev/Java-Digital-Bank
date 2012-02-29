' Option Explicit
' On Error Resume Next just helps ensure we get to close and quit
On Error Resume Next

' Read arguments
Set objArgs = WScript.Arguments
if objArgs.Count <> 3 then
	WScript.Echo "Usage: export sourceFile destFile destType"
	WScript.Quit(1)
end if
sourceFile = objArgs(0)
destFile = objArgs(1)
destType = objArgs(2)


' Check file to open is valid ppt and exists
if Right(sourceFile, 4) <> ".ppt" then
	WScript.Echo "Error: sourceFile is not a .ppt file."
	WScript.Quit(1)
end if	

Dim objFSO
Set objFSO = CreateObject("Scripting.FileSystemObject")
if objFSO.FileExists(sourceFile) = False Then
	WScript.Echo "Error: sourceFile does not exist."
	WScript.Quit(1)
end if


' Create a PowerPoint application
' Needs to be a visible frame to work
Set ppt = WScript.CreateObject("Powerpoint.Application")
ppt.DisplayAlerts = ppAlertsNone
ppt.FeatureInstall = msoFeatureInstallNone 
ppt.Visible = True

' Open presentation and export slide 1 to JPG format
ppt.Presentations.Open sourceFile
Set pres = ppt.ActivePresentation
pres.Slides(1).Export destFile, destType


' Close presentation and destroy the application object
pres.Close
ppt.Quit

