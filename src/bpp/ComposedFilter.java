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

import java.util.*;
import java.io.*;

public class ComposedFilter implements Filter {
  Filter first;
  Filter after;
  class Connect {
    PrintWriter out;
    PipedWriter pout;
    PipedReader pin;
    BufferedReader in;
    Connect() {
      pout = new PipedWriter();
      out = new PrintWriter(pout);
      try {
        pin = new PipedReader(pout);
      } catch (IOException e) {
        throw new RuntimeException(e.getMessage());
      }
      in = new BufferedReader(pin);
    }
  }
  class Pump extends Thread {
    Connect c;
    BufferedReader in;
    PrintWriter out;
    IOException e;
    Pump(BufferedReader _in, PrintWriter _out) {
      c=new Connect();
      in=_in;
      out=_out;
      e=null;
    }

    public void run() {
      try {
        after.filter(c.in,out);
      } catch (IOException _e) {
        e=_e;
      }
    }
  }

  public ComposedFilter(Filter _first,Filter _after) {
    first=_first;
    after=_after;
  }

  public void filter(BufferedReader in,PrintWriter out) throws IOException {
    Pump p = new Pump(in,out);
    p.start();
    first.filter(in,p.c.out);
    p.c.out.close();
    try { p.join(); } catch (InterruptedException e) {}
    if (p.e != null) throw p.e;
  }
}
