tests;

import static org.junit.Assert.*;

import org.junit.Test;
import java.util.*;

public class InterpreterUnitTests {
    @Test
    public void testConstructor() {
        SpreadSheet ss = new SpreadSheet();
        Interpreter interp = new Interpreter(ss);

        interp.readLine("A1 =SUM 1 2");
        interp.readLine("p*");

        assertEquals(interp.run(), "A1 =SUM 1 2 3");
    }

    @Test
    public void testConstructor2() {
        SpreadSheet ss = new SpreadSheet();
        Interpreter interp = new Interpreter(ss);

        interp.readLine("A1 =SUM 1 2");
        interp.readLine("A2 =SUM A1 3");
        interp.readLine("C1 =SUM A1 A2");
        interp.readLine("C1");
        interp.readLine("p*");

        assertEquals(interp.run(), "C1 =SUM A1 A2 9\nA1 =SUM 1 2 3 C1\n=SUM A1 A2 9\nA2 =SUM A1 3 6");
    }

    @Test
    public void testConstructor3() {
        SpreadSheet ss = new SpreadSheet();
        Interpreter interp = new Interpreter(ss);

        interp.readLine("A1 =SUM 1 2");
        interp.readLine("A2 =SUM A1 3");
        interp.readLine("C1 =SUM A1 A2");
        interp.readLine("d* 2");
        interp.readLine("C1");
        interp.readLine("p*");

        assertEquals(interp.run(), "C1 =SUM A1 A2 3\nA1 =SUM 1 2 3 C1 =SUM A1 A2 3\nA2 0 0");
    }

    @Test
    public void testConstructor4() {
        SpreadSheet ss = new SpreadSheet();
        Interpreter interp = new Interpreter(ss);

        interp.readLine("A1 =SUM 1.0 2.0");
        interp.readLine("A2 =SUM A1 3.0");
        interp.readLine("A3 =SUM A1 A2");
        interp.readLine("A4 =SUM =SUM 1.0 A1 =SUM A2 A3");
        interp.readLine("A4");

        assertEquals(interp.run(), "A4 =SUM =SUM 1.0 A1 =SUM A2 A3 19.0");
    }
}
