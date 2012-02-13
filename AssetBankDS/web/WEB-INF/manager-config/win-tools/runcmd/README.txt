Readme for runcmd - Francis Devereux 15-Apr-2009
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Introduction
~~~~~~~~~~~~

runcmd.exe is a Windows executable that runs a .CMD file with the same name
as the .EXE that it is run as, passing it the same arguments.  It can be used
to allow ImageMagick to be run with a different priority, by creating copies
of runcmd.exe as convert.exe, identify.exe etc. and batch files called
convert.cmd, identify.cmd etc. that launch the real ImageMagick exes with a
different priority.


Developer Information
~~~~~~~~~~~~~~~~~~~~~

runcmd was developed using MinGW 5.1.4 on Windows Vista.  It is written in
C++ and the build system uses make, so you need to make sure that you choose
to install g++ and make when you install MinGW.

MinGW is free and can be downloaded from http://www.mingw.org/

To compile runcmd, set the environment variable MINGW_HOME to the directory
where you installed MinGW (e.g. C:\MinGW) and then run build.cmd.
