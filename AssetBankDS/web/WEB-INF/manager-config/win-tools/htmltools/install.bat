copy /y GdiPlus.dll %windir%\system32
copy /y msvcp60.dll %windir%\system32
regsvr32 /s "%CD%\htmlshell.dll"
