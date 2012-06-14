@echo off
START "nice composite" /B /WAIT /LOW "C:\Program Files\ImageMagick\composite" %*
EXIT %ERRORLEVEL%
