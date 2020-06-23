@echo off
if "%1" == "" goto :end-no-param

set TDIR=gen

if not exist %TDIR% mkdir %TDIR%
echo y | del %TDIR%\*
copy %1.xml %TDIR%
if not exist %TDIR%\images mkdir %TDIR%\images
copy images\* %TDIR%\images
copy *.css %TDIR%
pushd .

@echo on
java -classpath bin fi.tuska.cvgen.Main %TDIR%\%1.xml fi finnish
java -classpath bin fi.tuska.cvgen.Main %TDIR%\%1.xml en english
@echo off
if errorlevel 1 goto :end-error

cd %TDIR%

pdflatex %1-fi.tex
pdflatex %1-fi.tex

pdflatex %1-fi-detailed.tex
pdflatex %1-fi-detailed.tex

pdflatex %1-en.tex
pdflatex %1-en.tex

pdflatex %1-en-detailed.tex
pdflatex %1-en-detailed.tex

popd

goto :end

:end-no-param
echo Please give the base name of the xml file as parameter
goto :end

:end-error
echo Error running generator, terminating
popd
goto :end

:end
