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
import java.net.URL;

public interface BeanshellInterpreterFactory {
    public void setClassPath(URL[] classPath);
    public URL[] getClassPath();
    public void setIn(Reader _in);
    public Reader getIn();
    public void setOut(PrintStream _out);
    public PrintStream getOut();
    public void setErr(PrintStream _err);
    public PrintStream getErr();
    public void setInteractive(boolean _interactive); 
    public boolean getInteractive();
    public void setNameSpace(bsh.NameSpace _nameSpace); 
    public bsh.NameSpace getNameSpace();
    public void setParent(bsh.Interpreter _parent);
    public bsh.Interpreter getParent();
    public void setSource(String _source);
    public String getSource();
    public bsh.Interpreter interpreter();
}
