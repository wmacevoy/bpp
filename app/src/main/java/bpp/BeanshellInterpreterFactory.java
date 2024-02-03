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
