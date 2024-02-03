package bpp;

import java.io.*;

public class DecoratorFilter implements Filter {
  public void filter(BufferedReader in, PrintStream out) throws IOException {
    try {
      begin(out);
      for (;;) {
        String line = in.readLine();
        if (line == null)
          break;
        decorate(line, out);
      }
      end(out);
    } finally {
      out.flush();
    }
  }

  public void begin(PrintStream out) throws IOException {
  }

  public void end(PrintStream out) throws IOException {
  }

  public void decorate(String line, PrintStream out) throws IOException {
    out.println(line);
  }
}
