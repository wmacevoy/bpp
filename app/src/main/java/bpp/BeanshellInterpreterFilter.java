/**A
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
import java.util.*;

public class BeanshellInterpreterFilter implements Filter {
  protected BeanshellInterpreterFactory factory;

  public BeanshellInterpreterFilter() {
    this(new DefaultBeanshellInterpreterFactory());
  }

  public BeanshellInterpreterFilter(BeanshellInterpreterFactory _factory) {
    factory = _factory;
  }

  public void filter(BufferedReader in, PrintStream out) {
    factory.setIn(in);
    factory.setOut(out);
    bsh.Interpreter i = factory.interpreter();
    i.run();
    out.flush();
  }

}
