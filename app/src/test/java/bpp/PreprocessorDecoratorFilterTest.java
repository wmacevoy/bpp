package bpp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

import org.junit.Test;

class MockPreprocessorDecoratorFilter extends PreprocessorDecoratorFilter {
    public MockPreprocessorDecoratorFilter(int _depth) {
        super(_depth);
    }

    @Override
    public void exact(String line, PrintStream out) throws IOException {
        out.println("exact " + line);
    }

    @Override
    public void magic(String line, PrintStream out) throws IOException {
        out.println("magic " + line);
    }

}

public class PreprocessorDecoratorFilterTest {
    class MockOut {
        StringBuilder sb = new StringBuilder();
        AppendableOutputStream aos = new AppendableOutputStream(sb, false);
        PrintStream ps = new PrintStream(aos);

        public String toString() {
            ps.flush();
            return sb.toString();
        }
    }

    @Test
    public void testDecorateDefault() throws Exception {
        MockPreprocessorDecoratorFilter mock = new MockPreprocessorDecoratorFilter(1);
        MockOut mockOut = new MockOut();
        mock.decorate("test", mockOut.ps);
        assertEquals("exact test" + System.lineSeparator(), mockOut.toString());
    }

    @Test
    public void testDecorateEcho() throws Exception {
        MockPreprocessorDecoratorFilter mock = new MockPreprocessorDecoratorFilter(2);
        MockOut mockOut = new MockOut();
        mock.decorate("line 0", mockOut.ps);
        mock.decorate("#line 1", mockOut.ps);
        mock.decorate("##line 2", mockOut.ps);
        mock.decorate("###line 3", mockOut.ps);
        String eol = System.lineSeparator();
        String expect = "exact line 0" + eol
                + "exact #line 1" + eol
                + "line 2" + eol
                + "#line 3" + eol;
        assertEquals(expect, mockOut.toString());
    }

    @Test
    public void testEcho() throws Exception {
        MockPreprocessorDecoratorFilter mock = new MockPreprocessorDecoratorFilter(2);
        MockOut mockOut = new MockOut();

        mock.echo("##test", mockOut.ps);

        String eol = System.lineSeparator();
        String expect = "test" + eol;

        assertEquals(expect,mockOut.toString());
    }

    @Test
    public void testExact() throws Exception {
        MockPreprocessorDecoratorFilter mock = new MockPreprocessorDecoratorFilter(2);
        MockOut mockOut = new MockOut();

        mock.exact("test", mockOut.ps);

        String eol = System.lineSeparator();
        String expect = "exact test" + eol;

        assertEquals(expect,mockOut.toString());
    }

    @Test
    public void testGetDefaultCommand() {
        MockPreprocessorDecoratorFilter mock = new MockPreprocessorDecoratorFilter(2);
        assertEquals('\"',mock.getDefaultCommand());

    }

    @Test
    public void testGetDepth() {
        MockPreprocessorDecoratorFilter mock = new MockPreprocessorDecoratorFilter(2);
        assertEquals(2, mock.getDepth());

    }

    @Test
    public void testGetEchoCommmand() {
        MockPreprocessorDecoratorFilter mock = new MockPreprocessorDecoratorFilter(2);
        assertEquals('#', mock.getEchoCommand());

    }

    @Test
    public void testGetExactCommmand() {
        MockPreprocessorDecoratorFilter mock = new MockPreprocessorDecoratorFilter(2);
        assertEquals('\'', mock.getExactCommand());

    }

    @Test
    public void testGetMagicCommmand() {
        MockPreprocessorDecoratorFilter mock = new MockPreprocessorDecoratorFilter(2);
        assertEquals('\"', mock.getMagicCommand());

    }

    @Test
    public void testHasMagic() {
        MockPreprocessorDecoratorFilter mock = new MockPreprocessorDecoratorFilter(2);
        assertTrue(mock.hasMagic("this is $magic"));
    }

    @Test
    public void testMagic() throws Exception {
        MockPreprocessorDecoratorFilter mock = new MockPreprocessorDecoratorFilter(2);
        MockOut mockOut = new MockOut();

        mock.magic("test", mockOut.ps);

        String eol = System.lineSeparator();
        String expect = "magic test" + eol;

        assertEquals(expect,mockOut.toString());

    }
}
