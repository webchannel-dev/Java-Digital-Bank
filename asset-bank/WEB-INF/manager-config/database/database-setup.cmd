@echo off
rem ***** Bright-Interactive Asset Bank *************
rem ***** Trial install, Database setup *************

rem * This install script assumes that a recent version of MySQL is installed,
rem * and that the machine on which the script run is the MySQL server.
rem * If this is a client machine, then it must have access to the server via the mysql client using the root account.
rem * In the latter case change HOSTNAME to the address of the server.

rem * For the trial version, leave DATABASE_NAME, APPLICATION_USER, APPLICATION_PASSWORD as default.
rem * If alternate details are used, you must update the components.xconf file in [Tomcat]\webapps\asset-bank\WEB-INF.
rem * The elements that need changing are: <dburl>, <user> and <password>. 

rem * It is assumed that the Tomcat application is going to be installed on the same machine as MySQL.
rem * If not, then you will need to update the application user account to allow remote access.

rem * The root password can optionally be passed as a parameter to this script.
rem * Otherwise no root password will be used.

set HOSTNAME=localhost
set DATABASE_NAME=assetbank
set APPLICATION_USER=application
set APPLICATION_PASSWORD=password

if "%1" == "" goto noPassword
set PASSWORD=-p%1
goto run

:noPassword
echo Not using password. If a root password is required, then use: database-setup.cmd [root_password]
set PASSWORD=

:run
set CREATE_SQL="CREATE DATABASE %DATABASE_NAME%;"
echo Creating database using: %CREATE_SQL%
mysql -h %HOSTNAME% -u root %PASSWORD% -e %CREATE_SQL%
 
set USER_SQL="GRANT ALL ON %DATABASE_NAME%.* TO '%APPLICATION_USER%'@'localhost' IDENTIFIED BY '%APPLICATION_PASSWORD%';" 
echo Creating application user using: %USER_SQL%
mysql -h %HOSTNAME% -u root %PASSWORD% mysql -e %USER_SQL%

echo Creating schema and data
mysql -h %HOSTNAME% -u root %PASSWORD% %DATABASE_NAME% < install-mysql.sql

:end
