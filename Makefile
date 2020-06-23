CV=cv
TDIR=gen

all: build run

build:
	javac -sourcepath src -d bin src/fi/tuska/cvgen/Main.java

init-gen: 
	rm -rf $(TDIR)
	mkdir -p $(TDIR)
	cp $(CV).xml $(TDIR)
	mkdir -p $(TDIR)/images
	cp -rf images/* $(TDIR)/images
	cp *.css $(TDIR)

run-java: 
	java -classpath bin fi.tuska.cvgen.Main $(TDIR)\$(CV).xml fi finnish
	java -classpath bin fi.tuska.cvgen.Main $(TDIR)\$(CV).xml en english

run-latex: 
	cd $(TDIR) && pdflatex $(CV)-fi.tex 	
	cd $(TDIR) && pdflatex $(CV)-fi.tex 	
	cd $(TDIR) && pdflatex $(CV)-fi-detailed.tex 	
	cd $(TDIR) && pdflatex $(CV)-fi-detailed.tex 	
	cd $(TDIR) && pdflatex $(CV)-en.tex 	
	cd $(TDIR) && pdflatex $(CV)-en.tex 	
	cd $(TDIR) && pdflatex $(CV)-en-detailed.tex 	
	cd $(TDIR) && pdflatex $(CV)-en-detailed.tex 	

run: init-gen run-java run-latex

clean:
	rm -rf $(TDIR)
