#!/bin/sh

# The settings below give Tomcat up to 1GB of memory - should be ok if there is nothing else on the server and you have
# more than 1Gb RAM. Adjust the figures proportionally, e.g. if you have only 1GB RAM in the machine and it is used for
# more than just Tomcat, halve all the numbers.
JAVA_OPTS='-XX:NewSize=192m -XX:MaxNewSize=256m -XX:MaxPermSize=256m -Xms768m -Xmx1024m'

# The following is necessary for Tomcat to shutdown properly using shutdown -force
CATALINA_PID=/tmp/tomcatpid.txt

# Limit the amount of RAM used by ImageMagick subprocesses
# uncomment if "convert" or "identify" take huge amounts of RAM and cause excessive swapping
#export MAGICK_AREA_LIMIT=1024mb
