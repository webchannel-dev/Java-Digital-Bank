@echo off
START "nice mogrify" /B /WAIT /LOW "C:\Program Files\ImageMagick\mogrify" %*
EXIT %ERRORLEVEL%
