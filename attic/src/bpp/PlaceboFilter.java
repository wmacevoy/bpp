package bpp;

import java.io.*;

public class PlaceboFilter extends DecoratorFilter {
  public String mark;
  public PlaceboFilter(String _mark) { mark=_mark; }

  public void begin(PrintWriter out) throws IOException {
    out.print("begin ");
    out.println(mark);
  }

  public void end(PrintWriter out) throws IOException {
    out.print("end ");
    out.println(mark);
  }

  public void decorate(String line,PrintWriter out) throws IOException {
    out.print(mark);
    out.print("(");
    out.print(line);
    out.println(")");
  }
}
