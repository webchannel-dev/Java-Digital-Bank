' -------------------- Settings ---------------------------------

' Backup directory root - where you want the backups to be created.
' Please ensure that this directory exists before running the backup.
BACKUP_DIR = "C:\temp\database_backup\"

' Whether to copy files as well as the database dump.
' If this is set to 0, then you should ensure that your backup process includes the image bank files directory.
' If this is set to 1, then the files directory will be copied by this script, 
' however the time taken will be proportional to the file data size. 
BACKUP_FILES_INCLUDED = 0

' Location of the image bank files directory
FILE_STORE_DIR = "C:\tomcat\webapps\image-bank\files"

' Location of MySQL bin
MYSQL_BIN = "C:\Program Files\MySQL\MySQL Server 4.1\bin\"

' Database details
USER = "root"
PASSWORD = "password"
DATABASE = "assetbank"
SQL_DUMP_NAME = "backup.sql"

' -------------------- End of settings --------------------------


' Create the dump command
MYSQLDUMP_CMD = MYSQL_BIN & "mysqldump --user " & USER & " --password=" & PASSWORD & " --result-file=" & SQL_DUMP_NAME & " " & DATABASE

' Execute mysqldump
WScript.Echo Now & ": Running: " & MYSQLDUMP_CMD

Dim WshShell, oExec
Set WshShell = CreateObject("WScript.Shell")
Set oExec = WshShell.Exec(MYSQLDUMP_CMD)

Do While oExec.Status = 0
     WScript.Sleep 100
Loop
WScript.Echo Now & ": SQL Dump Result: " & oExec.Status

' Create a file system object
Dim oFSO
Set oFSO = CreateObject("Scripting.FileSystemObject")

' Create a folder with the name of today
Dim dirTodaysBackup
dirTodaysBackup = BACKUP_DIR & WeekdayName(Weekday(Now))
WScript.Echo Now & ": Backing up to: " & dirTodaysBackup

If (oFSO.FolderExists(dirTodaysBackup)) Then
	WScript.Echo Now & ": Deleting backup directory:" & dirTodaysBackup
	oFSO.DeleteFolder(dirTodaysBackup)
End If

WScript.Echo Now & ": Creating backup directory:" & dirTodaysBackup
Set oFolder = oFSO.CreateFolder(dirTodaysBackup)

' Copy data dump 
oFSO.CopyFile SQL_DUMP_NAME, dirTodaysBackup & "\"

' Copy web app files if required
If (BACKUP_FILES_INCLUDED) Then
	WScript.Echo Now & ": Copying files"
	oFSO.CopyFolder FILE_STORE_DIR, dirTodaysBackup & "\files"
End If

WScript.Echo Now & ": Finished"