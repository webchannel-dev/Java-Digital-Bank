' Option Explicit
' On Error Resume Next just helps ensure we get to close and quit
On Error Resume Next

' Read arguments
Set files = WScript.Arguments
if files.Count < 3 then
	WScript.Echo "Usage: insert destFile file1 file2 [file3 ...]"
	WScript.Quit(1)
end if
destFile = files(0)


' Check files to open are valid ppt and exist
Dim objFSO
Set objFSO = CreateObject("Scripting.FileSystemObject")

for i = 1 to files.count - 1
	Dim sourceFile
	sourceFile = files(i)
	if Right(sourceFile, 4) <> ".ppt" then
		WScript.Echo "Error: a sourceFile is not a .ppt file."
		WScript.Quit(1)
	end if	
	
	if objFSO.FileExists(sourceFile) = False Then
		WScript.Echo "Error: a sourceFile does not exist."
		WScript.Quit(1)
	end if
next


' Create a PowerPoint application
Set ppt = WScript.CreateObject("Powerpoint.Application")
ppt.DisplayAlerts = ppAlertsNone
ppt.FeatureInstall = msoFeatureInstallNone 
ppt.Visible = True


' Open presentation 1 and insert slides from the others
ppt.Presentations.Open files(1)
Set pres = ppt.ActivePresentation
Set slides = pres.Slides

for i = 2 to files.count - 1
	numSlides1 = slides.Count
	slides.InsertFromFile files(i), numSlides1
next

' Save a copy of the merged presentation
pres.SaveAs destFile


' Close presentation and destroy the application object
pres.Close
ppt.Quit

