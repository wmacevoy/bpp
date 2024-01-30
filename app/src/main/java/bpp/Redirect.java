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
import java.lang.reflect.*;
import java.util.*;

/** <p>This class simplifies redirecting standard output (System.out) to different streams.  The old output stream is maintained on a stack and is restored with end() </p> 

<p><b>WARNING</b>This Redirect works with access through System.out.  Beanshell's <code>print()</code> statement apparently caches its PrintStream, so this redirect <i>will not work</i> with beanshell. Use the <code>redirect.bsh</code> scripts instead.</p>

<br/>The Beanshell Preprocessor
<br/>Copyright (C) 2003 Warren MacEvoy
*/
public class Redirect {
  public static final LinkedList redirects = new LinkedList();
  public static final PrintStream STDOUT=System.out;

  /** Clear and close all redirects.  
      Sets System.out back to original value. */
  public void clear() throws IOException {
    try {
      while (!redirects.isEmpty()) {
        ((OutputStream)redirects.removeLast()).close();
      }
    } finally {
      redirects.clear();
      System.setOut(STDOUT);
    }
  }

  public static void begin(PrintStream out) {
    redirects.addLast(out);
    System.setOut(out);
  }

  public static void begin(String filename,boolean append) throws IOException {
    begin(new PrintStream(new BufferedOutputStream(new FileOutputStream(filename,append)),true,"UTF-8"));

  }

  public static void begin(String filename) throws IOException {
    begin(filename,false);
  }

  public static void append(String filename) throws IOException {
    begin(filename,true);
  }

  public static void end() throws IOException {
    PrintStream last = (PrintStream) redirects.removeLast();
    System.setOut(redirects.isEmpty() ? STDOUT : (PrintStream) redirects.getLast());
    if (last != STDOUT) last.close();
  }

  public static void main(String [] args) throws Exception {
    System.out.println("testing redirect.");
    Redirect.begin("redirect.test1");
    System.out.println("one");
    Redirect.end();
    System.out.println("finished test 1.");
    Redirect.begin("redirect.test2");
    System.out.println("one");
    Redirect.end();
    System.out.println("finished test 2, part 1");
    Redirect.append("redirect.test2");
    System.out.println("two");
    Redirect.end();
    System.out.println("finished test 2, part 2");
    Redirect.begin("redirect.test3");
    System.out.println("here\'s some stuff.");
    Redirect.begin("redirect.test4");
    System.out.println("oh, and this.");
    Redirect.end();
    Redirect.begin(Redirect.STDOUT);
    System.out.println("finished test 4");
    Redirect.end();
    System.out.println("and some more stuff.");
    Redirect.end();
    System.out.println("finished test 3");
    System.out.println("done.");
  }
}
