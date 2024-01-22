BPP: The Beanshell Preprocessor 
     --- The fully caffeinated preprocessor.
         
Copyright (C) 2003-2004 Warren D. MacEvoy jr.
Licensed under the Gnu General Public License, version 2.

OUTLINE

1. INTRODUCTION
2. INSTALLATION
3. USE
4. TUTORIAL
5. REFERENCE
6. LICENSE

1. INTRODUCTION

BPP is a tool for Java developers to use Java as a preprocessor. It is a
single executable jar file that works with beanshell to create a flexible
and powerful preprocessing environment.  Here is a hello-world style example:

    #name="World";
    #greet="Hello";
    class HW {
      public static void main(String[] args) {
          System.out.println("$greet, $name!");
      }
    }

Save this as "HW.java.bpp" and execute bpp (supposing bpp is properly
configured) with

    bpp HW.java.bpp

This will generate the HW.java source file:

    class HW {
      public static void main(String[] args) {
          System.out.println("Hello, World!");
      }
    }

Executive summary: #'ed lines are executed at preprocess time, and values
of the form $ID and $(EXPRESION) are translated from the preprocessor 
definitions to the goal source file.

BPP is a Symmetric Prepsrocessor. This means the preprocessor is essentially
the same as the host language, including the fact that it has a preprocessor,
ad. infinitum. For developers who know Java, they can now benefit from using
Java as a preprocessor.

Here is a hello-world style example that uses the second-level preprocessor:

    #// english
    #en_name="World"; 
    #en_greet="Hello";
    #
    #// italiano
    #it_name="Mondo";
    #it_greet="Caio";
    #
    #// default language
    ##lang="it";
    #greet=$(lang)_greet;
    #name=$(lang)_name;
    #
    class HW {
      public static void main(String[] args) {
          System.out.println("$greet, $name!");
      }
    }

Preprocessing this file will produce an Italian Hello World:

    class HW {
      public static void main(String[] args) {
          System.out.println("Caio, Mondo!");
      }
    }

See the tutorial in doc/tutorial/index.html for more details.

2. INSTALLATION

BPP works with java (JDK1.4), and Beanshell (Bsh-2.0).  BPP is a small jar
file.  Building the BPP also requires JAVACC and examples in the tutorial
and tests folder use JDOM.  Building the tutorial requires the docbook XSL
and xsltproc.  Jar files for Beanshell, JavaCC, and JDOM are in the bin
folder of the BPP distribution.  Move the bpp folder to where-ever you
would like to have it, maybe:

Unix/Cygwin:

  dstdir=/usr/local # or whereever you want it
  tar xjCf $dstdir /path/to/bpp-X.XX.tar.bz2
  pushd $dstdir; ln -s bpp-X.XX bpp; popd
  
Win32:

  extract bpp-X.XX.zip to C:\Program Files\BPP

3. USE

See the javadoc for bpp.BPP.main(String[] args) for all the command line
options, but the setup before use is:

Unix/Cygwin (bash):

  pushd /usr/local/bpp; . bin/profile; popd

Win32

  3.1 Set BPP_HOME and BPP_CLASSPATH environment variables:

  set BPP_HOME="C:\Program Files\BPP";
  set BPP_CLASSPATH="%BPP_HOME%\bin\bpp.jar;%BPP_HOME%\bin\bsh-2.0b1.jar;%BPP_HOME%\bin\jdom.jar"

  3.2 Add BPP_HOME to your PATH:

  PATH "%BPP_HOME%\bin;%PATH%"

In either case, after this setup, you should be able to run BPP with

    bpp [args]

See javadocs for bpp.BPP.Main for all the command line arguments.

4. TUTORIAL

See doc/tutorial/index.html for a fairly complete tutorial.

5. REFERENCE

None yet.

6. LICENSE

Gnu GPL.  See license.txt for details.
