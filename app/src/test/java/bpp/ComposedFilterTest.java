package bpp;

import static org.junit.Assert.assertEquals;

import java.io.*;
import java.util.*;

import org.junit.Test;


class MockFilter implements Filter {
    ArrayList < String > log = new ArrayList <String> ();
    String start = "start";
    String end = "end";
    String prefix = "(";
    String postfix = ")";
    MockFilter(String id) {
        start = "start " + id;
        end = "end " + id;
        prefix = id + "(";
        postfix = ")" + id;
    }
    @Override
    public void filter(BufferedReader in, PrintStream out) throws IOException  {
        out.println(start);
        log.add("println(" + start +")");
        for (;;) {
            String line = in.readLine();
            log.add("readLine(" + line + ")");
            if (line == null) break;
            String outLine = prefix + line + postfix;
            out.println(outLine);
            log.add("println("+outLine+")");
        }
        out.println(end);
        log.add("println(" + end + ")");
        out.flush();
        log.add("flush()");
    }
}

public class ComposedFilterTest {

    @Test
    public void testFilter() throws Exception {
        StringBuffer sb = new StringBuffer();
        PrintStream out = Filters.getPrintStream(sb);
        MockFilter mfa = new MockFilter("a");
        MockFilter mfb = new MockFilter("b");
        String eol = System.lineSeparator();
        StringReader sr = new StringReader("line 1"+eol+"line 2");
        BufferedReader in = new BufferedReader(sr);
        ComposedFilter cf = new ComposedFilter(mfa, mfb);

        cf.filter(in,out);
        in.close();
        out.close();
        String expect = "start b" + eol + "b(start a)b" + eol + "b(a(line 1)a)b" + eol + "b(a(line 2)a)b"+eol+"b(end a)b"+eol+"end b"+eol;
        String result = sb.toString();
        assertEquals(expect,result);

    }
}
