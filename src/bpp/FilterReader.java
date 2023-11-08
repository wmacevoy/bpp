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

public class FilterReader extends java.io.FilterReader {

    BufferedReader br;
    PrintWriter pw;
    PipedWriter pipew;
    PipedReader piper;
    Filter filter;
    public IOException e;
    Pump pump;

    class Pump extends Thread {
	Pump() { /* setDaemon(true); */ }
	public void run() {
	    try {
		filter.filter(br,pw);
	    } catch (IOException _e) {
                e.printStackTrace(System.out);
		e=_e;
	    } finally {
		try {
		    br.close();
		    pw.close();
		} catch (IOException _e) {
		    throw new RuntimeException(_e.getMessage());
		}
	    }
	}
    }

    public FilterReader(Filter _filter,Reader _in) {
      this(_filter,_in,new PipedWriter(),new PipedReader());
    }
    private FilterReader(Filter _filter,Reader _in,PipedWriter _pw,PipedReader _pr) {
	super(_pr);
        pipew=_pw;
        piper=_pr;
        try { piper.connect(pipew); }
	catch (IOException e) { throw new RuntimeException(e.getMessage()); }
	filter=_filter;
	br = (_in instanceof BufferedReader) ?
	    (BufferedReader)_in : new BufferedReader(_in);
	pw = new PrintWriter(pipew);
	pump = new Pump();
	pump.start();
    }
}
