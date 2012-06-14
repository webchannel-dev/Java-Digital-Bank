Backup procedure for Windows installations.
For other operating systems, an equivalent procedure should be carried out or scripted.


The scripts included in this directory perform a backup of the MySQL database used by Asset Bank (and optionally the file data store). The backups are created in directories with names corresponding to the day of backup run (ie Monday, Tuesday etc).

Your backup procedure should ensure that MySQL data and file data are backed up at the same time, ideally when the system is not in use (eg nightly). This will ensure that the backup data is synchronised and consistent. If files are being added or deleted during the backup, some manual updates may be needed on restored data to recover consistency.

We recommend the following backup procedure:

1. Schedule a backup of the Asset Bank files directory. This is located under the web application root, eg c:\tomcat\webapps\asset-bank\files.

If the total size of your file data is small or moderate (eg a few hundred MB) then you can configure the backup script to carry out this file backup along with the database backup - see step 2.  


2. Open the file backup.vbs in a text editor. The top part of the file contains settings that you can change according to your environment (directory paths and database access details). Comments within the file describe these settings. 

Use BACKUP_FILES_INCLUDED = 1 if you want the script to include file backup along with data backup, otherwise set it to 0 and manually set up a directory backup as described in step 1.


3. To run the script manually, run the batch file backup.bat within a command prompt. Output will be written to a file out.txt.


4. To schedule the data backup, use Windows Scheduled Tasks.
- Control Panel > Scheduled Tasks > Add Scheduled Task
- In the wizard, click Browse, find and select backup.bat
- Set frequency and time of task
- Enter account details of an account with appropriate permissions (eg administrator).
- Check the file backup.bat as you may need to add full paths - this seems to be necessary on newer win servers

Remember to update the task properties if the password for the account used changes.



Restoring data

1. File data
The file directory just needs to be replaced with the backup of the directory.

2. MySQL data
The dump file created is a SQL script that can be run to recreate the schema and data.
a) Create an empty database for the restoration.

b) Run the data script using the mysql command: eg
mysql -uroot -ppassword -hlocalhost assetbank < backup.sql
