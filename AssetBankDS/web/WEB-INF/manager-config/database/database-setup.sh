#!/bin/sh

# ***** Bright-Interactive Asset Bank *************
# ***** Trial install, Database setup *************

# * This install script assumes that a recent version of MySQL is installed,
# * and that the machine on which the script run is the MySQL server.
# * If this is a client machine, then it must have access to the server via the mysql client using the root account.
# * In the latter case change HOSTNAME to the address of the server.

# * For the trial version, leave DATABASE_NAME, APPLICATION_USER, APPLICATION_PASSWORD as default.
# * If alternate details are used, you must update the components.xconf file in [Tomcat]\webapps\asset-bank\WEB-INF.
# * The elements that need changing are: <dburl>, <user> and <password>. 

# * It is assumed that the Tomcat application is going to be installed on the same machine as MySQL.
# * If not, then you will need to update the application user account to allow remote access.

# * The root password can optionally be passed as a parameter to this script.
# * Otherwise no root password will be used.

HOSTNAME=localhost
DATABASE_NAME=assetbank
APPLICATION_USER=application
APPLICATION_PASSWORD=djs9d12a
MYSQL=mysql

if [ -n "$1" ] 
then
	PASSWORD="-p$1"
else
	echo Not using password. If a root password is required, then use: database-setup.cmd [root_password]
	PASSWORD=
fi

CREATE_SQL="CREATE DATABASE $DATABASE_NAME;"
echo Creating database using $CREATE_SQL
"$MYSQL" -h $HOSTNAME -u root "$PASSWORD" -e "$CREATE_SQL"
 
USER_SQL="GRANT ALL ON $DATABASE_NAME.* TO '$APPLICATION_USER'@'localhost' IDENTIFIED BY '$APPLICATION_PASSWORD';" 
echo Creating application user using: $USER_SQL
"$MYSQL" -h $HOSTNAME -u root "$PASSWORD" mysql -e "$USER_SQL"

echo Creating schema and data
"$MYSQL" -h $HOSTNAME -u root "$PASSWORD" $DATABASE_NAME < install-mysql.sql
