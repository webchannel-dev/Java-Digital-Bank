
REM The settings below give Tomcat up to 1GB of memory - should be ok if there is nothing else on the server and you have
REM more than 1Gb RAM. Adjust the figures proportionally, e.g. if you have only 1GB RAM in the machine and it is used for
REM more than just Tomcat, halve all the numbers.
SET JAVA_OPTS=-XX:NewSize=192m -XX:MaxNewSize=256m -XX:MaxPermSize=256m -Xms768m -Xmx1024m


REM Limit the amount of RAM used by ImageMagick subprocesses
REM uncomment if "convert" or "identify" take huge amounts of RAM and cause excessive swapping
REM SET MAGICK_AREA_LIMIT 1024mb
