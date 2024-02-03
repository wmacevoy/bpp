package bpp;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.*;
import java.util.*;

class MockAppendable implements Appendable, Closeable, Flushable {
    StringBuilder data = new StringBuilder();

    @Override
    public Appendable append(CharSequence csq) throws IOException {
        data.append(csq);
        return this;
    }

    @Override
    public Appendable append(CharSequence csq, int start, int end) throws IOException {
        data.append(csq, start, end);
        return this;
    }

    @Override
    public Appendable append(char c) throws IOException {
        data.append(c);
        return this;
    }

    boolean closed = false;

    @Override
    public void close() throws IOException {
        closed = true;
    }

    boolean flushed = false;

    @Override
    public void flush() throws IOException {
        flushed = true;
    }
}

public class AppendableOutputStreamTest {
    int[] utf8boundaries = new int[] { 0x7F, 0x7FF, 0xFFFF, 0x10FFFF };
    int[] utf16boundaries = new int[] { 0xFFFF, 0x10FFFF };

    ArrayList<Integer> getBoundaries() {
        ArrayList<Integer> codepoints = new ArrayList<>();
        for (int i = 0; i < utf8boundaries.length; ++i) {
            codepoints.add((i > 0) ? utf8boundaries[i - 1] + 1 : 0);
            codepoints.add(utf8boundaries[i]);
        }
        for (int i = 0; i < utf16boundaries.length; ++i) {
            codepoints.add((i > 0) ? utf16boundaries[i - 1] + 1 : 0);
            codepoints.add(utf16boundaries[i]);
        }
        return codepoints;
    }

    ArrayList<ArrayList<Integer>> getStrings(int length) {
        if (length == 0) {
            ArrayList<ArrayList<Integer>> ans = new ArrayList<ArrayList<Integer>>(1);
            ans.add(new ArrayList<Integer>());
            return ans;
        } else if (length == 1) {
            ArrayList<Integer> boundaries = getBoundaries();
            ArrayList<ArrayList<Integer>> ans = new ArrayList<ArrayList<Integer>>(boundaries.size());
            for (Integer codepoint : getBoundaries()) {
                ArrayList<Integer> sample = new ArrayList<Integer>(1);
                sample.add(codepoint);
                ans.add(sample);
            }
            return ans;
        } else {
            ArrayList<ArrayList<Integer>> prefixes = getStrings(length / 2);
            ArrayList<ArrayList<Integer>> postfixes = getStrings(length - length / 2);
            ArrayList<ArrayList<Integer>> ans = new ArrayList<ArrayList<Integer>>(prefixes.size() * postfixes.size());

            for (ArrayList<Integer> prefix : prefixes) {
                for (ArrayList<Integer> postfix : postfixes) {
                    ArrayList<Integer> join = new ArrayList<Integer>(prefix);
                    join.addAll(postfix);
                    ans.add(join);
                }
            }
            return ans;
        }
    }

    byte[] codePointToUtf8(int codePoint) throws Exception {
        return (new String(Character.toChars(codePoint))).getBytes("UTF-8");
    }

    @Test
    public void testWrite() throws Exception {
        for (int length = 0; length <= 4; ++length) {
            ArrayList<ArrayList<Integer>> samples = getStrings(length);
            for (ArrayList<Integer> sample : samples) {
                MockAppendable mock = new MockAppendable();
                AppendableOutputStream out = new AppendableOutputStream(mock, false);
                StringBuilder sb = new StringBuilder();
                for (Integer codepoint : sample) {
                    out.write(codePointToUtf8(codepoint));
                    sb.appendCodePoint(codepoint);
                }
                out.close();
                assertEquals(sb.toString(), mock.data.toString());

            }
        }
    }

    @Test
    public void testAppendCodePoint() throws Exception {
        for (int length = 0; length <= 4; ++length) {
            ArrayList < ArrayList < Integer > > samples = getStrings(length);
            for (ArrayList < Integer > sample : samples) {
                StringBuilder sb = new StringBuilder();
                MockAppendable mock = new MockAppendable();
                AppendableOutputStream out = new AppendableOutputStream(mock, false);
                for (Integer codepoint : sample) {
                    out.appendCodePoint(codepoint);
                    sb.appendCodePoint(codepoint);
                }
                out.close();
                assertEquals(sb.toString(), mock.data.toString());
            }
        }
    }

    @Test
    public void testClose() throws Exception {
        MockAppendable mock = new MockAppendable();
        AppendableOutputStream out = new AppendableOutputStream(mock, false);
        assertFalse(mock.closed);
        out.close();
        assertTrue(mock.closed);

    }

    @Test
    public void testFlush() throws Exception {
        MockAppendable mock = new MockAppendable();
        AppendableOutputStream out = new AppendableOutputStream(mock, false);
        assertFalse(mock.flushed);
        out.flush();
        assertTrue(mock.flushed);
        out.close();
    }

    @Test
    public void testAutoFlush() throws Exception {
        MockAppendable mock = new MockAppendable();
        AppendableOutputStream out = new AppendableOutputStream(mock, true);
        assertFalse(mock.flushed);
        out.write("Hello".getBytes("UTF-8"));
        assertFalse(mock.flushed);
        out.write(", World!\n".getBytes("UTF-8"));
        assertTrue(mock.flushed);
        out.close();
    }
}
