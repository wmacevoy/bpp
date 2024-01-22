import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;

import bpp.BPPFilter;

public class BppErrorRedirectTest {
	public static void main(String a[]) throws Exception {
		
		 FileInputStream fis = new FileInputStream("error-redirect-test.bpp");
	     BufferedReader in = new BufferedReader(new InputStreamReader(fis));
         OutputStream os = new ByteArrayOutputStream();
         PrintWriter out = new PrintWriter(os);

         BPPFilter filter = new BPPFilter();
         
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         filter.setErr(new java.io.PrintStream(baos));
         
         // context 
         //filter.setStageMap(new HashMap() {{put("a","b"); }});
                  
         filter.filter(in, out);

         if(baos.size() > 0) {
           System.out.println("Redirected error while rendering: " + baos);
         }
         
         System.out.println(os.toString());		
	}
}
