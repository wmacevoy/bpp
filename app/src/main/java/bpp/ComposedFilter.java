package bpp;

import java.io.*;

public class ComposedFilter implements Filter {
  Filter before;
  Filter after;

  public ComposedFilter(Filter _before, Filter _after) {
    before = _before;
    after = _after;
  }

  static class IOExceptions {
    IOException before;
    IOException after;
  }

  public void filter(BufferedReader in, PrintStream out) throws IOException {
    PipedOutputStream pipeOut = new PipedOutputStream();
    PrintStream psOut = new PrintStream(pipeOut, true);
    PipedInputStream pipeIn = new PipedInputStream();
    BufferedReader brIn = new BufferedReader(new InputStreamReader(pipeIn, "UTF-8"));
    pipeOut.connect(pipeIn);
    IOExceptions exceptions = new IOExceptions();
    Thread beforeThread = new Thread(() -> {
      try {
        before.filter(in, psOut);
      } catch (IOException ex) {
        exceptions.before = ex;
      } finally {
        psOut.flush();
      }
    });
    Thread afterThread = new Thread(() -> {
      try {
        after.filter(brIn, out);
      } catch (IOException ex) {
        exceptions.after = ex;
      } finally {
        out.flush();
      }
    });
    beforeThread.start();
    afterThread.start();
    try {
      beforeThread.join();
    } catch (InterruptedException e) {
    }
    if (exceptions.before != null) {
      afterThread.interrupt();
      throw exceptions.before;
    }
    try {
      afterThread.join();
    } catch (InterruptedException e) {
    }
    if (exceptions.after != null) {
      throw exceptions.after;
    }
  }
}
