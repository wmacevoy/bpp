package bpp;

import java.io.*;

/**
<p>Read the stream until a difference is noticed, then write the remainder.</p>

<p>This class acts like a FileOutputStream, but intead <i>reads</i> the file until a difference in what is written is noticed.  If a change is noticed, the file is written from the first point of difference onward.  If no change is noticed, then the file is only read.
</p>
*/
public class LazyFileOutputStream extends OutputStream {
  private RandomAccessFile ra;
  private OutputStream out;
  private File file;
  private boolean different;
  private boolean abandoned;

  public boolean isDifferent() { return different; }
  public boolean isAbandoned() { return abandoned; }

  private abstract class AbandonableOutputStream extends OutputStream {
    public void abandon() throws IOException { 
      ra.close();
    }

    public void close() throws IOException { 
      if (!abandoned) {
        long fp=ra.getFilePointer();
        if (ra.length() != fp) {
          ra.setLength(fp);
          different=true;
        }
        ra.close(); 
      }
    }

  }

  private class VerifyOutputStream extends AbandonableOutputStream {
    public void flush() throws IOException {};
    public void write(byte[] data,int offset,int length) throws IOException {
      if (offset != 0 || length != data.length) {
        byte[] writedata = new byte[length];
        System.arraycopy(data,offset,writedata,0,length);
        write(writedata);
      } else {
        write(data);
      }
    }
    public void write(byte[] data) throws IOException {
      byte[] rdata = new byte[data.length];
      long position=ra.getFilePointer();
      int readBytes=ra.read(rdata);
      if ((readBytes == data.length) && java.util.Arrays.equals(data,rdata)) {
        return;
      }
      ra.seek(position);
      different=true;
      out=new WriteOutputStream();
      out.write(data);
    }

    public void write(int b) throws IOException {
      byte[] data = new byte[1];
      data[0]=(byte)b;
      write(data);
    }
  }

  private class WriteOutputStream extends AbandonableOutputStream {
    public void flush() throws IOException {}
    
    public void write(byte[] data,int offset,int length) throws IOException {
      ra.write(data,offset,length);
    }
    
    public void write(byte[] data) throws IOException {
      ra.write(data);
    }

    public void write(int data) throws IOException {
      ra.write(data);
    }
  }

  public LazyFileOutputStream(String _fileName) throws IOException {
    this(new File(_fileName));
  }

  public LazyFileOutputStream(File _file) throws IOException {
    file=_file; 
    if (file.exists()) {
      ra = new RandomAccessFile(file,"rws");
      out = new VerifyOutputStream();
    } else {
      different=true;
      out = new FileOutputStream(file);
    }
  }

  /** close without truncating the file to the current write position */
  synchronized public void abandon() throws IOException {
    abandoned=true;
    if (out instanceof AbandonableOutputStream) {
      ((AbandonableOutputStream)out).abandon();
    } else {
      out.close();
    }
  }
    
  synchronized public void close() 
    throws IOException 
  { 
    out.close(); 
  }

  synchronized public void flush() 
    throws IOException 
  {
    out.flush();
  };

  synchronized public void write(byte[] data,int offset,int length)
    throws IOException 
  { 
    out.write(data,offset,length); 
  }

  synchronized public void write(byte[] data) 
    throws IOException 
  {
    out.write(data);
  }

  synchronized public void write(int data) 
    throws IOException 
  {
    out.write(data);
  }
}
