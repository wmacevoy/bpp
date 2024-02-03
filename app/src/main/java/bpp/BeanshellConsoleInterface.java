package bpp;

import java.io.*;

public class BeanshellConsoleInterface implements bsh.ConsoleInterface {
  protected Reader in;
  protected PrintStream out;
  protected PrintStream err;

  public BeanshellConsoleInterface(Reader _in, PrintStream _out, PrintStream _err) {
    in = _in;
    out = _out;
    err = _err;
  }

  public Reader getIn() {
    return in;
  }

  public void setIn(Reader _in) {
    in = _in;
  }

  public PrintStream getOut() {
    return out;
  }

  public void setOut(PrintStream _out) {
    out = _out;
  }

  public PrintStream getErr() {
    return err;
  }

  public void setErr(PrintStream _err) {
    err = _err;
  }

  public void println(Object o) {
    out.println(o);
    out.flush();
  }

  public void print(Object o) {
    out.print(o);
  }

  public void error(Object o) {
    err.println(o);
    out.flush();
  }
}
