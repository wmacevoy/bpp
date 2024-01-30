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
import java.net.*;

public class Filters {
    public static PrintStream getPrintStream() {
	boolean autoFlush = true;
	return new PrintStream(new ByteArrayOutputStream(),autoFlush);
    }
    public static PrintStream getPrintStream(int capacity) {
	boolean autoFlush = true;
	return new PrintStream(new ByteArrayOutputStream(capacity),autoFlush);
    }
    public static PrintStream getPrintStream(Appendable appendable) {
	if (appendable instanceof PrintStream) {
	    return (PrintWriter) appendable;
	}
	if (appendable instanceof OutputStream) {
	    boolean autoFlush = true;
	    return new PrintWriter((OutputStream) appendable, autoFlush);
	}
	return new PrintWriter(new AppendableOutputStream(appendable),autoFlush);
    }
    
    public static PrintStream getPrintStream(File file) {
	boolean autoFlush = true;
	OutputStream out = new BufferedOutputStream(new LazyFileOutputStream(file));
	return new PrintStream(out,autoFlush,"UTF-8");
    }

    public static PrintStream getPrintStream(String file) {
	if (file == null || file.equals("") || file.equals("-") || file.equals("stdout")) {
	    return System.out;
	}
	if (file.equals("stderr")) {
	    return System.err;
	}
	return getPrintStream(new File(file));
    }
    
    public static BufferedReader getBufferedReader(Reader reader) {
	return (reader instanceof BufferedReader) ?
	    (BufferedReader) reader : new BufferedReader(reader);
    }

    public static BufferedReader getBufferedReader(InputStream in) {
	try {
	    return new BufferedReader(new InputStreamReader(in,"UTF-8"));
	} catch (IOException e) {
	    throw new RuntimeException(e.getMessage());
	}
    }

  public static BufferedReader getBufferedReader(File file) {
    try {
	    return new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
    } catch (IOException e) {
	    throw new RuntimeException(e.getMessage());
    }
  }

    public static BufferedReader getBufferedReader(String file) {
	if (file == null || file.equals("") || file.equals("-")) {
	    return getBufferedReader(System.in);
	} else if (file.startsWith("http:") || file.startsWith("jar:")) {
	    try {
		return getBufferedReader(new URL(file).openStream());
	    } catch (Exception e) {
		throw new RuntimeException(e.getMessage());
	    }
	} else {
	    return getBufferedReader(new File(file));
	}
    }

}

