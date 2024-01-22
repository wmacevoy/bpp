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

abstract public class StageFilter implements Filter {
  protected int stage;
  protected int maxLineLength;
  protected int lineNumber;
  public int getLineNumber() { return lineNumber; }
  public StageFilter(int _stage,int _maxLineLength) {
     stage=_stage;
     maxLineLength=_maxLineLength;
 }

  public void filter(BufferedReader in, PrintWriter out) throws IOException {
    for (;;) {
      String line = null;
      in.mark(maxLineLength);
      line=in.readLine();
      ++lineNumber;
      if (line == null) return;
      int lineStage = getLineStage(line);
      if (lineStage > stage) {
        try {
          in.reset();
          --lineNumber;
        } catch (IOException e) {
          throw new IOException("Max line limit exceeded.  Rerun with -maxline "
                                + 3*line.length()/2 + " option");
        }
        break;
      }
      out.println(line);
      out.flush();
    }
    ++stage;
    Filter f2 = getStageFilter();
    Filter f12=new ComposedFilter(this,f2);
    f12.filter(in,out);
    out.flush();
  }
  
 public abstract int getLineStage(String line);
 public abstract Filter getStageFilter();
}
