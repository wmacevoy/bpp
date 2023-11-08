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

public class DecoratorFilter implements Filter {
    public void filter(BufferedReader in,PrintWriter out) throws IOException {
	begin(out);
	for (;;) {
	    String line = in.readLine();
	    if (line == null) break;
	    decorate(line,out);
	}
	end(out);
    }
    public void begin(PrintWriter out) throws IOException {}
    public void end(PrintWriter out) throws IOException {}
    public void decorate(String line,PrintWriter out) throws IOException {
	out.println(line); 
    }
}
