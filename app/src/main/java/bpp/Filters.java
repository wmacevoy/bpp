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
		try {
			return new PrintStream(new ByteArrayOutputStream(), autoFlush, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static PrintStream getPrintStream(int capacity) {
		boolean autoFlush = true;
		try {
			return new PrintStream(new ByteArrayOutputStream(capacity), autoFlush, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static PrintStream getPrintStream(Appendable appendable) {
		if (appendable instanceof PrintStream) {
			return (PrintStream) appendable;
		}
		if (appendable instanceof OutputStream) {
			boolean autoFlush = true;
			try {
				return new PrintStream((OutputStream) appendable, autoFlush, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		boolean autoFlush = true;
		try {
			return new PrintStream(new AppendableOutputStream(appendable, autoFlush), autoFlush, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static PrintStream getPrintStream(File file) throws IOException {
		boolean autoFlush = true;
		return new PrintStream(new FileOutputStream(file), autoFlush, "UTF-8");
	}

	public static PrintStream getPrintStream(String file) throws IOException {
		if (file == null || file.equals("") || file.equals("-") || file.equals("stdout")) {
			return System.out;
		}
		if (file.equals("stderr")) {
			return System.err;
		}
		return getPrintStream(new File(file));
	}

	public static BufferedReader getBufferedReader(Reader reader) {
		return (reader instanceof BufferedReader) ? (BufferedReader) reader : new BufferedReader(reader);
	}

	public static BufferedReader getBufferedReader(InputStream in) {
		try {
			return new BufferedReader(new InputStreamReader(in, "UTF-8"));
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static BufferedReader getBufferedReader(File file) {
		try {
			return new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
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
