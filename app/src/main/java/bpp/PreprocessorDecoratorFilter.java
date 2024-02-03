package bpp;

import java.io.*;

abstract public class PreprocessorDecoratorFilter extends DecoratorFilter {
  protected char echoCommand = '#';
  protected char exactCommand = '\'';
  protected char magicCommand = '\"';
  protected char defaultCommand = '\"';
  protected char magicIdentifier = '$';
  protected int depth;

  public char getEchoCommand() {
    return echoCommand;
  }

  public char getExactCommand() {
    return exactCommand;
  }

  public char getMagicCommand() {
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
