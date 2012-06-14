@echo off
START "nice convert" /B /WAIT /LOW "C:\Program Files\ImageMagick\convert" %*
EXIT %ERRORLEVEL%
