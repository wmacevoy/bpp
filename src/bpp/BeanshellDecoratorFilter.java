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

/** 
<p>This is used by the BPFilter for the decorating stages of filtering. </p>
<hr/>
<br/>The Beanshell Preprocessor
<br/>Copyright (C) 2003-2004 Warren D. MacEvoy jr.
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
    context.pw = out;
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
