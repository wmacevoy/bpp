package bpp;

import java.io.*;

/** 
<p>This is used by the BPFilter for the decorating stages of filtering. </p>
*/
public class BeanshellDecoratorFilter extends PreprocessorDecoratorFilter {
  private int blankLines;
  private int lineNumber;
  public int getLine() { return lineNumber; }
  
  public BeanshellDecoratorFilter(int _depth,int _blankLines) { 
    super(_depth); 
    blankLines=_blankLines;
    lineNumber=blankLines;
  }
  
  public void begin(PrintStream out) throws IOException {
    for (int i=blankLines-1; i>=0; --i) out.println();
    out.flush();
  }
  
  public void exact(String line,PrintStream out) throws IOException {
    out.print("out.println(");
    out.print(Format.literal(line));
    out.println(");");
    out.flush();
  }
  
  public void magic(String line,PrintStream out) throws IOException {
    BPPContext context = new BPPContext();
    context.out = out;
    try {
	    BshPPParser.parse(new StringReader(line),context);
    } catch (Exception e) {
	    exception(e);
    }
    out.flush();
  }
  
  public void echo(String line,PrintStream out) throws IOException {
    String output = line.substring(depth);
    out.println(output);
    out.flush();
  }
  
  public void exception(Exception e) {
    e.printStackTrace(System.err);
  }
}
