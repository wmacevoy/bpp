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

class OutputStreamToWriterConverter extends Thread {
    PrintWriter out;
    PipedOutputStream pout;
    PipedInputStream pin;
    InputStreamReader isr;
    BufferedReader br;

    OutputStreamToWriterConverter() {
	pout = new PipedOutputStream();
	try {
	    pin = new PipedInputStream(pout);
	} catch (IOException e) {
	    throw new RuntimeException(e.getMessage());
	}
	try {
	    isr = new InputStreamReader(pin,"UTF-8");
	} catch (UnsupportedEncodingException e) {
	    throw new RuntimeException(e.getMessage());
	}
        br = new BufferedReader(isr);

    }

    public void run() {
	for (;;) {
	    String line=null;
	    try {
		line = br.readLine();
	    } catch (IOException e) {
		exception(e);
	    }
	    if (line == null) break;
	    out.println(line);
	}
	out.close();
    }

    public void exception(Exception e) { e.printStackTrace(System.err); }
}
