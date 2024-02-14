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
    PrintStream untracedOut = new PrintStream(pipeOut, true);
    PipedInputStream pipeIn = new PipedInputStream();
    BufferedReader untracedIn = new BufferedReader(new InputStreamReader(pipeIn, "UTF-8"));
    pipeOut.connect(pipeIn);

    PrintStream psOut; // maybe traced
    if (in instanceof TracedBufferedReader) {
      TracedBufferedReader tbr = (TracedBufferedReader) in;
      String prefix = tbr.prefix + ">" + this.getClass(); 

      psOut = new TracedPrintStream(untracedOut,prefix,tbr.log);
    } else {
      psOut = untracedOut;
    }

    BufferedReader brIn; // maybe traced
    if (out instanceof TracedPrintStream) {
      TracedPrintStream tps = (TracedPrintStream) out;
      String prefix = this.getClass() + ">" + tps.prefix;
      brIn = new TracedBufferedReader(untracedIn,prefix,tps.log);
    } else {
      brIn = untracedIn;
    }

    IOExceptions exceptions = new IOExceptions();
    Thread beforeThread = new Thread(() -> {
      try {
        before.filter(in, psOut);
      } catch (IOException ex) {
        exceptions.before = ex;
      } finally {
        psOut.flush();
        psOut.close();
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
