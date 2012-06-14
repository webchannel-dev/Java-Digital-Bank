@echo off
setlocal
set PATH=%MINGW_HOME%\bin;%PATH%
mingw32-make %*
endlocal
