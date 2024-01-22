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

  // in -> (before) -> printwriter -> pipedwriter -> pipedreader -> bufferedreader -> (after) -> out
  //
  Filter before;
  Filter after;

  class Pump extends Thread {
    PrintWriter printWriter;
    PipedReader pipedReader;
    PipedWriter pipedWriter;
    BufferedReader in;
    BufferedReader bufferedReader;
    PrintWriter out;
    
    IOException exception;

    Pump(BufferedReader _in,PrintWriter _out) {
      in=_in;
      out=_out;
      pipedWriter = new PipedWriter();
      printWriter = new PrintWriter(pipedWriter);
      try {
        pipedReader = new PipedReader(pipedWriter);
      } catch (IOException e) {
        throw new RuntimeException(e.getMessage());
      }
      bufferedReader = new BufferedReader(pipedReader);
      exception=null;
      start();
    }

    public void run() {
      try {
        before.filter(in,printWriter);
      } catch (IOException _exception) {
        exception=_exception;
      } finally {
        printWriter.close();
      }
    }
  }

  public ComposedFilter(Filter _before,Filter _after) {
    before=_before;
    after=_after;
  }

  public void filter(BufferedReader in,PrintWriter out) throws IOException {
    Pump p = new Pump(in,out);
    after.filter(p.bufferedReader,out);
    try {
      p.bufferedReader.close();
    } catch (IOException e) {}
    try {
      p.join();
    } catch (InterruptedException e) {}
    if (p.exception != null) throw p.exception;
  }
}
