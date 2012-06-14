#!/bin/sh
cd "`dirname $0`"
svn rm ../nice-imagemagick/*.exe
for O in ../nice-imagemagick/composite.exe ../nice-imagemagick/convert.exe ../nice-imagemagick/identify.exe ../nice-imagemagick/mogrify.exe; do svn cp runcmd.exe $O; done
