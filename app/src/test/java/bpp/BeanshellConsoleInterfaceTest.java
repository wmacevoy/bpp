package bpp;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.*;

public class BeanshellConsoleInterfaceTest {
    class MockOut {
        StringBuilder sb = new StringBuilder();
        AppendableOutputStream aos = new AppendableOutputStream(sb, false);
        PrintStream ps = new PrintStream(aos);

        public String toString() {
            ps.flush();
            return sb.toString();
        }
    }

    class MockReader {
        String s;
        StringReader sr;
        BufferedReader br;

        MockReader(String _s) {
            s = _s;
            sr = new StringReader(s);
            br = new BufferedReader(sr);
        }
    }

    class MockBCI {
        MockOut mockOut = new MockOut();
        MockOut mockErr = new MockOut();
        MockReader mockIn = new MockReader("");
        BeanshellConsoleInterface bci = new BeanshellConsoleInterface(mockIn.br, mockOut.ps, mockErr.ps);
    }

    @Test
    public void testError() {
        MockBCI mock = new MockBCI();
        mock.bci.error("test");
        assertEquals("test" + System.lineSeparator(), mock.mockErr.toString());
    }

    @Test
    public void testGetErr() {
        MockBCI mock = new MockBCI();
        MockOut mockErr2 = new MockOut();
        mock.bci.setErr(mockErr2.ps);
        assertTrue(mock.bci.getErr() == mockErr2.ps);
    }

    @Test
    public void testGetIn() {
        MockBCI mock = new MockBCI();
        MockReader mockIn2 = new MockReader("");
        mock.bci.setIn(mockIn2.br);
        assertTrue(mock.bci.getIn() == mockIn2.br);

    }

    @Test
    public void testGetOut() {
        MockBCI mock = new MockBCI();
        MockOut mockOut2 = new MockOut();
        mock.bci.setOut(mockOut2.ps);
        assertTrue(mock.bci.getOut() == mockOut2.ps);

    }

    @Test
    public void testPrint() {
        MockBCI mock = new MockBCI();
        mock.bci.print("test");
        assertEquals("test", mock.mockOut.toString());

    }

    @Test
    public void testPrintln() {
        MockBCI mock = new MockBCI();
        mock.bci.println("test");
        assertEquals("test" + System.lineSeparator(), mock.mockOut.toString());

    }

    @Test
    public void testSetErr() {

    }

    @Test
    public void testSetIn() {

    }

    @Test
    public void testSetOut() {

    }
}
