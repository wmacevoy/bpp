import java.io.*;

public class copy {
  public static void main(String []args) throws Exception {
    BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]),"UTF-8"));
    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]),"UTF-8"));

    for (;;) {
      String line = in.readLine();
      if (line == null) break;
      out.write(line,0,line.length());
      out.newLine();
    }
    in.close();
    out.close();
  }
}
