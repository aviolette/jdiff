@echo off

setlocal
set MMMPATH=c:\javaclasses\metamata\lib
set MMMPARSE=%MMMPATH%\metamata.jar;%MMMPATH%\JavaCC.zip

set CLASSPATH=jdiff.jar;%MMMPARSE%

java language.java.diff.JavaPrinter %1 %2 %3 %4 %5 %6 %7 %8 %9

endlocal