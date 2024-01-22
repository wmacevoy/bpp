/**
   BPP: The Beanshell Preprocessor, version 0.4
   Copyright (C) 2003-2004  Warren D. MacEvoy jr.

   This program is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 2 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program; if not, write to the Free Software
   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/

package bpp;

import java.io.*;
import java.net.*;

public class DefaultBeanshellInterpreterFactory 
  implements BeanshellInterpreterFactory {


  protected Reader in;
  protected PrintStream out;
  protected PrintStream err;
  protected boolean interactive;
  protected bsh.NameSpace nameSpace;
  protected bsh.Interpreter parent;
  protected String source;
  protected URL[] classPath;

  public void setIn(Reader _in) { in=_in; }
  public Reader getIn() { return in; }
  public void setOut(PrintStream _out) { out=_out; }
  public PrintStream getOut() { return out; }
  public void setErr(PrintStream _err) { err=_err; }
  public PrintStream getErr() { return err; }
  public void setInteractive(boolean _interactive) { 
    interactive=_interactive; 
  }
  public boolean getInteractive() { return interactive; }
  public void setNameSpace(bsh.NameSpace _nameSpace) { 
    nameSpace=_nameSpace; 
  }
  public bsh.NameSpace getNameSpace() { return nameSpace; }
  public void setParent(bsh.Interpreter _parent) { parent=_parent; }
  public URL[] getClassPath() { return classPath; }

  public void setClassPath(URL[] _classPath) { 
    classPath = _classPath;
  }

  public bsh.Interpreter getParent() { return parent; }
  public void setSource(String _source) { source=_source; }
  public String getSource() { return source; }

  public bsh.Interpreter interpreter() {
    if (in == null) {
      try {
        in=new InputStreamReader(System.in,"UTF-8");
      } catch (UnsupportedEncodingException e) {
        throw new RuntimeException(e.getMessage());
      }
    }
    if (out == null) out=System.out;
    if (err == null) err=System.err;
    bsh.Interpreter interpreter = null;
    interpreter = new bsh.Interpreter(in,System.out,System.err,interactive,nameSpace,parent,source);
    try {
      interpreter.set("out",out);
      interpreter.set("err",err);
    } catch (bsh.EvalError e) {
      throw new RuntimeException(e.getMessage());
    }

    bsh.BshClassManager classManager = interpreter.getClassManager();
    if (classPath != null) for (int i=0; i<classPath.length; ++i) {
      try {
        classManager.addClassPath(classPath[i]);
      } catch (IOException e) {
        throw new RuntimeException(e.getMessage());
      }
    }
    //  interpreter.setConsole(new BeanshellConsoleInterface(in,out,err));
    return interpreter;
  }
}


