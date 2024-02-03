package bpp;

import java.io.*;

public class BeanshellInterpreterFilter implements Filter {
  protected BeanshellInterpreterFactory factory;

  public BeanshellInterpreterFilter() {
    this(new DefaultBeanshellInterpreterFactory());
  }

  public BeanshellInterpreterFilter(BeanshellInterpreterFactory _factory) {
    factory = _factory;
  }

  public void filter(BufferedReader in, PrintStream out) {
    factory.setIn(in);
    factory.setOut(out);
    try {
      bsh.Interpreter i = factory.interpreter();
      i.run();
    } finally {
      out.flush();
    }
  }

}
