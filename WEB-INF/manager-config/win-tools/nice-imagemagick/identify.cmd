@echo off
START "nice identify" /B /WAIT /LOW "C:\Program Files\ImageMagick\identify" %*
EXIT %ERRORLEVEL%
