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

    public static PrintWriter getPrintWriter(Writer w) {
      return (w instanceof PrintWriter) ?
          (PrintWriter) w : new PrintWriter(w);
    }

    public static PrintWriter getPrintWriter(OutputStream out) {
      try {
        return new PrintWriter(new OutputStreamWriter(out,"UTF-8"));
      } catch (IOException e) {
        throw new RuntimeException(e.getMessage());
      }
    }

    public static PrintWriter getPrintWriter(File file) {
      try {
        return new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8")));
      } catch (IOException e) {
        throw new RuntimeException(e.getMessage());
      }
    }

    public static PrintWriter getPrintWriter(String file) {
      if (file == null || file.equals("") || file.equals("-")) return getPrintWriter(System.out);
      return getPrintWriter(new File(file));
    }

    public static BufferedReader getBufferedReader(Reader r) {
	return (r instanceof BufferedReader) ?
	    (BufferedReader) r : new BufferedReader(r);
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
