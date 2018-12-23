package tests;

import static org.junit.Assert.*;

import org.junit.Test;

public class InterpreterUnitTests {
    @Test
    public void testConstructor() {
        Spreadsheet ss = new Spreadsheet();
        Interpreter interp = new Interpreter(ss);

        interp.readInput("A1 =SUM 1 2");
        interp.readInput("p*");
        
        interp.run();
        assertEquals(interp.getOutput(), "A1 =SUM 1 2 3");
    }

    @Test
    public void testConstructor2() {
        Spreadsheet ss = new Spreadsheet();
        Interpreter interp = new Interpreter(ss);

        interp.readInput("A1 =SUM 1 2");
        interp.readInput("A2 =SUM A1 3");
        interp.readInput("C1 =SUM A1 A2");
        interp.readInput("C1");
        interp.readInput("p*");

        interp.run();
        assertEquals(interp.getOutput(), "C1 =SUM A1 A2 9\nA1 =SUM 1 2 3 C1\n=SUM A1 A2 9\nA2 =SUM A1 3 6");
    }

    @Test
    public void testConstructor3() {
        Spreadsheet ss = new Spreadsheet();
        Interpreter interp = new Interpreter(ss);

        interp.readInput("A1 =SUM 1 2");
        interp.readInput("A2 =SUM A1 3");
        interp.readInput("C1 =SUM A1 A2");
        interp.readInput("d* 2");
        interp.readInput("C1");
        interp.readInput("p*");
        
        interp.run();
        assertEquals(interp.getOutput(), "C1 =SUM A1 A2 3\nA1 =SUM 1 2 3 C1 =SUM A1 A2 3\nA2 0 0");
    }

    @Test
    public void testConstructor4() {
        Spreadsheet ss = new Spreadsheet();
        Interpreter interp = new Interpreter(ss);

        interp.readInput("A1 =SUM 1.0 2.0");
        interp.readInput("A2 =SUM A1 3.0");
        interp.readInput("A3 =SUM A1 A2");
        interp.readInput("A4 =SUM =SUM 1.0 A1 =SUM A2 A3");
        interp.readInput("A4");

        interp.run();
        assertEquals(interp.getOutput(), "A4 =SUM =SUM 1.0 A1 =SUM A2 A3 19.0");
    }
}
