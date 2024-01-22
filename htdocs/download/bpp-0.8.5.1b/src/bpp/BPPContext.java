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
    Context for the parser generated by JavaCC.  Internal bridge between
    a BeanshellDecoratorFilter and the BshPPParser magic-line parser.
    <hr/>
    <br/>The Beanshell Preprocessor
    <br/>Copyright (C) 2003-2004 Warren MacEvoy
*/
public class BPPContext {
  PrintWriter pw=null;
  String content=null;
  boolean bol=true;

  private void emit() {
    if (bol) {
      pw.print("{");
      bol=false;
    }
    if (content != null) {
      pw.print("out.print(");
      pw.print(content);
      pw.print(");");
      content=null;
    }
  }

  public void done() {
    pw.print("out.println(");
    if (content != null) pw.print(content);
    if (bol) {
      pw.println(");");
    } else {
      pw.println(");}");
    }
    content=null;
    bol=true;
  }

  public void magic(String s) {
    emit();
    content=s;
  }

  public void literal(String s) {
    emit();
    content=Format.literal(s);
  }
}
