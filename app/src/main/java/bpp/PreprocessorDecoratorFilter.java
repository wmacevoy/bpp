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

abstract public class PreprocessorDecoratorFilter extends DecoratorFilter {
  protected char echoCommand = '#';
  protected char exactCommand = '\'';
  protected char magicCommand = '\"';
  protected char defaultCommand = '\"';
  protected char magicIdentifier = '$';
  protected int depth;

  public char getEchoCommmand() {
    return echoCommand;
  }

  public char getExactCommmand() {
    return exactCommand;
  }

  public char getMagicCommmand() {
    return magicCommand;
  }

  public char getDefaultCommand() {
    return defaultCommand;
  }

  public int getDepth() {
    return depth;
  };

  public PreprocessorDecoratorFilter(int _depth) {
    depth = _depth;
  }

  public void decorate(String line, PrintStream out) throws IOException {
    int count = 0;
    while (count < line.length() && line.charAt(count) == echoCommand)
      ++count;
    if (count >= depth) {
      echo(line, out);
      return;
    }
    char cmd;
    String data;
    if (count == depth - 1 && line.length() > count) {
      cmd = line.charAt(count);
      if (cmd == exactCommand || cmd == magicCommand) {
        data = line.substring(0, count) + line.substring(count + 1);
      } else {
        if ((defaultCommand == magicCommand) && hasMagic(line)) {
          cmd = magicCommand;
        } else {
          cmd = exactCommand;
        }
        data = line;
      }
    } else {
      cmd = exactCommand;
      data = line;
    }
    // System.out.println("action(" + depth + "," + line + ")=" + cmd);
    if (cmd == exactCommand || !hasMagic(data)) {
      exact(data, out);
    } else {
      magic(data, out);
    }
  }

  public void echo(String line, PrintStream out) throws IOException {
    out.println(line.substring(depth));
    out.flush();
  }

  public boolean hasMagic(String line) {
    return line.indexOf(magicIdentifier) >= 0;
  }

  abstract public void exact(String line, PrintStream out) throws IOException;

  abstract public void magic(String line, PrintStream out) throws IOException;
}
