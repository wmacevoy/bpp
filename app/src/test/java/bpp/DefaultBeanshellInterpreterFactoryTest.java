package bpp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

public class DefaultBeanshellInterpreterFactoryTest {

    DefaultBeanshellInterpreterFactory factory;

    @Before
    public void before() {
        factory = new DefaultBeanshellInterpreterFactory();

    }

    @Test
    public void testGetClassPath() throws Exception {
        URL[] cp = new URL[] { new URL("http://test.url") };
        factory.setClassPath(cp);
        assertTrue(cp == factory.getClassPath());

    }

    @Test
    public void testGetErr() {
        MockOut mockErr = new MockOut();
        factory.setErr(mockErr.out);
        assertTrue(mockErr.out == factory.getErr());

    }

    @Test
    public void testGetIn() {
        MockReader mockReader = new MockReader("");
        factory.setIn(mockReader.bufferedReader);
        assertTrue(mockReader.bufferedReader == factory.getIn());

    }

    @Test
    public void testGetInteractive() {
        boolean interactive = true;
        factory.setInteractive(interactive);
        assertTrue(interactive == factory.getInteractive());
        interactive = false;
        factory.setInteractive(interactive);
        assertTrue(interactive == factory.getInteractive());
    }

    @Test
    public void testGetNameSpace() {
        bsh.NameSpace ns = new bsh.ExternalNameSpace();
        factory.setNameSpace(ns);
        assertTrue(ns == factory.getNameSpace());

    }

    @Test
    public void testGetOut() {
        MockOut mockOut = new MockOut();
        factory.setOut(mockOut.out);
        assertTrue(mockOut.out == factory.getOut());

    }

    @Test
    public void testGetParent() {
        MockOut mockOut = new MockOut();
        MockOut mockErr = new MockOut();
        MockReader mockReader = new MockReader("");
        factory.setIn(mockReader.bufferedReader);
        factory.setOut(mockOut.out);
        factory.setErr(mockErr.out);
        bsh.Interpreter parent = factory.interpreter();
        factory.setParent(parent);
        assertTrue(parent == factory.getParent());
    }

    @Test
    public void testGetSource() {
        factory.setSource("source.bpp");
        assertEquals("source.bpp",factory.getSource());

    }

    @Test
    public void testInterpreter() {
        MockOut mockOut = new MockOut();
        MockOut mockErr = new MockOut();
        String eol = System.lineSeparator();
        String input = "out.println(\"hi\");" + eol
        + "err.println(\"oops\");" + eol;
        MockReader mockReader = new MockReader(input);
        factory.setIn(mockReader.bufferedReader);
        factory.setOut(mockOut.out);
        factory.setErr(mockErr.out);
        bsh.Interpreter interpreter = factory.interpreter();
        interpreter.run();
        assertEquals("hi"+eol,mockOut.toString());
        assertEquals("oops"+eol,mockErr.toString());
    }

}
