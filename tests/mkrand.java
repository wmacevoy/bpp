//
// great UTF encoding summary @
// http://www.azillionmonkeys.com/qed/unicode.html
// thanks Paul Hsieh!
import java.io.*;

public class mkrand {
  public static void main(String[] args) throws Exception {
    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("rand.bpp"),"UTF-8"));
    BufferedWriter vfy = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("rand.verify"),"UTF-8"));

    for (int line=0; line<1000; ++line) {
      CharArrayWriter caos=new CharArrayWriter();
      int codes;
      if (Math.random()<0.5) {
        codes = (int)(10*Math.random());
      } else {
        codes = (int)(100*Math.random());
      }
      boolean quote=false;
 
      for (int i=0; i<codes; ++i) {
        for (;;) {
          int utf8= (int)(3*Math.random());
          int utfcode;
          switch(utf8) {
          case 0:
            utfcode=0x0+(int)((0x80-0x0)*Math.random());
            if (utfcode == '\n' || utfcode == '\r') continue;
            if (!Character.isDefined((char)utfcode)) continue;
            if (utfcode == '$' || (i==0 && (utfcode=='#'||utfcode=='\"'||utfcode=='\''))) quote=true;
            caos.write(utfcode);
            break;
          case 1:
            utfcode=0x80+(int)((0x800-0x80)*Math.random());
            if (!Character.isDefined((char)utfcode)) continue;
            caos.write(utfcode);
            break;
          case 2:	
            utfcode=0x800+(int)((0x10000-0x800)*Math.random());
            if (!Character.isDefined((char)utfcode)) continue;
            caos.write(utfcode);
            break;
          }
          break;
        }
      }
      if (quote) out.write('\'');
      char[] data = caos.toCharArray();
      out.write(data,0,data.length);
      out.newLine();
      vfy.write(data,0,data.length);
      vfy.newLine();
      System.out.print("line="+line+":");
      for (int k=0; k<data.length; ++k) {
        System.out.print(" c"+k+"->"+data[k]+"/o"+Integer.toOctalString(data[k])+"/h"+Integer.toHexString(data[k]));
      }
      System.out.println();
    }
    out.close();
    vfy.close();
  }
}
