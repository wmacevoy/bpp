#
#    BPP: The Beanshell Preprocessor
#    Copyright (C) 2003-2004  Warren D. MacEvoy jr.
#
#    This program is free software; you can redistribute it and/or modify
#    it under the terms of the GNU General Public License as published by
#    the Free Software Foundation; either version 2 of the License, or
#    (at your option) any later version.
#
#    This program is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#    GNU General Public License for more details.
#
#    You should have received a copy of the GNU General Public License
#    along with this program; if not, write to the Free Software
#    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
#
SHELL=/bin/bash
JAVA=java -cp "./lib/*"
JAVAC=javac -classpath "./lib/*" -d classes -sourcepath src
DIST=bpp-0.8.5.2b
DOCBOOK=/usr/local/docbook

SRC=$(wildcard src/bpp/*.java)

all : bpp.jar doc

src/bpp/BshPPParser.java: src/bpp/BshPPParser.jj
	cd src/bpp; java -cp "../../lib/*" javacc BshPPParser.jj

classes : $(SRC)
	if [ ! -d classes ] ; then mkdir classes ; fi
	$(JAVAC) $(SRC)
	touch classes

bpp.jar : classes src/MANIFEST.MF
	jar  -cmf src/MANIFEST.MF bpp.jar -C classes .

.PHONY: doc
doc : doc/javadoc doc/tutorial

doc/javadoc : classes
	javadoc -cp "./lib/*" -d doc/javadoc -sourcepath src bpp || true
	touch doc/javadoc

doc/tutorial : doc/tutorial/tutorial.docbook
	xsltproc docbook/html/docbook.xsl doc/tutorial/tutorial.docbook > doc/tutorial/index.html
	touch doc/tutorial

doc/quickstart : doc/quickstart/quickstart.docbook
	xsltproc docbook/html/docbook.xsl doc/quickstart/quickstart.docbook > doc/quickstart/index.html
	touch doc/quickstart

clean :
	rm -rf dist
	rm -rf doc/javadoc/* classes bpp.jar tests/*.java tests/*.bsh
	rm -rf `find . -name '*~'`
	rm -rf `find . -name '*#'`

.PHONY: tests
tests : bpp.jar
	cd tests; ./check.sh

dist :
	rm -rf dist $(DIST) $(DIST).zip $(DIST).tar.bz2
	mkdir dist
	cp Makefile bpp.jar license.txt readme.txt dist
	mkdir dist/bin
	tar cf - `find bin -regex '.*/\(.*.jar\|profile\|bpp\|bpp.bat\)'` | tar xvCf dist -
	cp bpp.jar dist/bin
	tar cf - `find src -type f -a \( -name '*.java' -o -name '*.jj' \)` | tar xvCf dist -
	cp src/MANIFEST.MF dist/src
	tar cf - `find contrib -! -regex '.*\(-\|~\|#\|class\)'` | tar xvCf dist -
	tar cf - `find tests -type f -a \( -name '*.bpp' -o -name '*.verify' -o -name '*.xml' -o -name '*.run' \) ` | tar xvCf dist -
	tar cf - `find tests -regex '.*/\(mkrand.java\|mkrand.class\|check\|checkall\|TestComposedFilter.bsh\)'` | tar xvCf dist -
	mkdir dist/doc
	cp -r doc/javadoc dist/doc/javadoc
	mkdir dist/doc/tutorial
	cp doc/tutorial/index.html dist/doc/tutorial
	cp doc/tutorial/tutorial.docbook dist/doc/tutorial
	mkdir dist/doc/quickstart
	cp doc/quickstart/index.html dist/doc/quickstart
	cp doc/quickstart/quickstart.docbook dist/doc/quickstart
	mv dist $(DIST)
	zip -r $(DIST).zip $(DIST)
	tar jcf $(DIST).tar.bz2 $(DIST)
