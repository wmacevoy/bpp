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
  A java.io.StringWriter with a few new features:
   <ul>
   <li>You can pass it an existing StringBuffer to append to.</li>
   <li>The toString() method returns the current contents of
       the string buffer</li>
   <li>The toStringBuffer() method returns the underlying string buffer.</li>
   </ul>
 */
public class StringBufferWriter extends Writer {
  public StringBuffer sb;
  StringBufferWriter() { this(new StringBuffer()); }
  StringBufferWriter(int capacity) { this(new StringBuffer(capacity)); }
  StringBufferWriter(StringBuffer _sb) { sb=_sb; }
    
  public void close() {};
  public void flush() {};
  public void write(char[] cbuf) { sb.append(cbuf); }
  public void write(char[] cbuf, int off, int len) { 
    sb.append(cbuf,off,len); 
  }
  public void write(int c) { sb.append((char)c); }
  public void write(String str) { sb.append(str); }
  public void write(String str, int off, int len) { sb.append(str.substring(off,off+len)); }
  public String toString() { return sb.toString(); }
  public StringBuffer toStringBuffer() { return sb; }
}
