#!/bin/bash

if [ "$(uname -o)" = "Cygwin" ] ; then
  if [ -z "$BPP_HOME" ] ; then
    export BPP_HOME=$(cygpath -w $(pwd))
  fi

  if [ -z "$BPP_CLASSPATH" ] ; then
    for p in $(find "$BPP_HOME\bin" -name '*.jar')
    do
      if [ -z "$BPP_CLASSPATH" ] ; then
	  export BPP_CLASSPATH="$$(cygpath -w $p)"
      else
	  export BPP_CLASSPATH="${BPP_CLASSPATH};$(cygpath -w $p)"
      fi
    done
  fi
  
  export PATH="$(cygpath $BPP_HOME)/bin:$PATH"
else
  if [ -z "$BPP_HOME" ] ; then
    export BPP_HOME=`pwd`
  fi

  if [ -z "$BPP_CLASSPATH" ] ; then
    for p in $(find "$BPP_HOME/bin" -name '*.jar')
    do
      if [ -z "$BPP_CLASSPATH" ] ; then
	  export BPP_CLASSPATH="$p"
      else
	  export BPP_CLASSPATH="${BPP_CLASSPATH}:$p"
      fi
    done
  fi

  export PATH="$BPP_HOME/bin:$PATH"
fi
