@echo off

setlocal

set MMMPATH=c:\javaclasses\metamata\lib
set MMMPARSE=%MMMPATH%\metamata.jar;%MMMPATH%\JavaCC.zip

set JAXPPATH=C:\Program Files\JavaSoft\Jaxp1.0
set JAXP=%JAXPPATH%\parser.jar;%JAXPPATH%\jaxp.jar

set CLASSPATH=jdiff.jar;%MMMPARSE%;%JAXP%

java language.java.diff.JavaPatch %1 %2 %3 %4 %5 %6 %7 %8 %9

endlocal