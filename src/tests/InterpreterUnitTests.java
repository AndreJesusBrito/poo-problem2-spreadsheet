package tests;
import static org.junit.Assert.*;

import org.junit.Test;

public class InterpreterUnitTests {
    @Test
    public void test01() {
        Spreadsheet ss = new Spreadsheet();
        Interpreter interp = new Interpreter(ss);

        interp.readInput("A1 =SUM 1 2");
        interp.readInput("p*");
        
        interp.run();
        assertEquals(interp.getOutput(), "A1 =SUM 1 2 3");
    }

    @Test
    public void test02() {
        Spreadsheet ss = new Spreadsheet();
        Interpreter interp = new Interpreter(ss);

        interp.readInput("A1 =SUM 1 2");
        interp.readInput("A2 =SUM A1 3");
        interp.readInput("C1 =SUM A1 A2");
        interp.readInput("C1");
        interp.readInput("p*");

        interp.run();
        assertEquals(interp.getOutput(), "C1 =SUM A1 A2 9\nA1 =SUM 1 2 3 C1 =SUM A1 A2 9\nA2 =SUM A1 3 6");
    }

    @Test
    public void test03() {
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
    public void test04() {
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
    
    @Test
    public void test05() {
        Spreadsheet ss = new Spreadsheet();
        Interpreter interp = new Interpreter(ss);

        interp.readInput("A1 10.1");
        interp.readInput("B1 10.5");
        interp.readInput("A2 =SUM =SUM 1 =SUM 2 2");
        interp.readInput("A2");


        interp.run();
        assertEquals(interp.getOutput(), "A2 =SUM =SUM 1 =SUM 2 2 24.6");
    }
    
    @Test
    public void test06() {
        Spreadsheet ss = new Spreadsheet();
        Interpreter interp = new Interpreter(ss);

        interp.readInput("A1 10.1");
        interp.readInput("B1 10.5");
        interp.readInput("AA2 =SUM =SUM A =SUM 2 =SUM B");
        interp.readInput("AA2");


        interp.run();
        assertEquals(interp.getOutput(), "AA2 =SUM =SUM A =SUM 2 =SUM B 22.6");
    }
    
    @Test
    public void test07() {
        Spreadsheet ss = new Spreadsheet();
        Interpreter interp = new Interpreter(ss);

        interp.readInput("A1 10.1");
        interp.readInput("AA2 =SUM 2 =SUM 2 =SUM A");
        interp.readInput("AA2");


        interp.run();
        assertEquals(interp.getOutput(), "AA2 =SUM 2 =SUM 2 =SUM A 14.1");
    }
    
    @Test
    public void test08() {
        Spreadsheet ss = new Spreadsheet();
        Interpreter interp = new Interpreter(ss);

        interp.readInput("A1 =SUM 1 2");
        interp.readInput("A2 =SUM A1 3");
        interp.readInput("C1 =SUM A1 A2");
        interp.readInput("A1 =SUM 1 2");
        interp.readInput("A2 =SUM A1 3");
        interp.readInput("C1 =SUM A1 A2");
        interp.readInput("d* 2");
        interp.readInput("A1 =SUM 1.0 2.0");
        interp.readInput("A2 =SUM A1 3.0");
        interp.readInput("A3 =SUM A1 A2");
        interp.readInput("A4 =SUM =SUM 1.0 A1 =SUM A2 A3");
        interp.readInput("A1 1");
        interp.readInput("IC1 1");
        interp.readInput("B4 IC1");
        interp.readInput("N23 1");
        interp.readInput("A1 1");
        interp.readInput("C3 2.1");
        interp.readInput("AG1 1");
        interp.readInput("Q1 4.5");
        interp.readInput("G2 =SUM A1 AG1");
        interp.readInput("F2 =SUM A1 G2");
        interp.readInput("J3 =SUM 2");
        interp.readInput("L1 =SUM C3 Q1");
        interp.readInput("C1 5");
        interp.readInput("N23 1");
        interp.readInput("A1 1");
        interp.readInput("C3 2.1");
        interp.readInput("AG1 1");
        interp.readInput("Q1 4.5");
        interp.readInput("G2 =SUM A1 AG1");
        interp.readInput("F2 =SUM A1 G2");
        interp.readInput("J3 =SUM 2");
        interp.readInput("L1 =SUM C3 Q1");
        interp.readInput("C1 5");
        interp.readInput("N23 1");
        interp.readInput("A1 1");
        interp.readInput("C3 2.1");
        interp.readInput("AG1 1");
        interp.readInput("Q1 4.5");
        interp.readInput("G2 =SUM A2 AG1");
        interp.readInput("F2 =SUM A4 G2");
        interp.readInput("J3 =SUM 2");
        interp.readInput("L1 =SUM C3 Q1");
        interp.readInput("C1 5");
        interp.readInput("d* C");
        interp.readInput("N23 1");
        interp.readInput("A1 1");
        interp.readInput("C3 2.1");
        interp.readInput("AG1 1");
        interp.readInput("Q1 4.5");
        interp.readInput("C1 5");
        interp.readInput("N23 3.5");
        interp.readInput("A1 1");
        interp.readInput("C3 2");
        interp.readInput("Q1 4.5");
        interp.readInput("BA1 1");
        interp.readInput("A1 BA1");
        interp.readInput("B3999 10");
        interp.readInput("A1 =SUM BA1 B3999");
        interp.readInput("A4");

        interp.run();
        assertEquals(interp.getOutput(), "A4 =SUM =SUM 1.0 A1 =SUM A2 A3 51.0");
    }
    
    @Test
    public void test09() {
        Spreadsheet ss = new Spreadsheet();
        Interpreter interp = new Interpreter(ss);

        interp.readInput("N23 1");
        interp.readInput("A1 1");
        interp.readInput("C3 2.1");
        interp.readInput("AG1 1");
        interp.readInput("Q1 4.5");
        interp.readInput("G2 =SUM A1 AG1");
        interp.readInput("F2 =SUM A1 G2");
        interp.readInput("J3 =SUM 2");
        interp.readInput("L1 =SUM C3 Q1");
        interp.readInput("C1 5");
        interp.readInput("d* 1");
        interp.readInput("d* C");
        interp.readInput("N23 3.5");
        interp.readInput("A1 1");
        interp.readInput("C3 2");
        interp.readInput("Q1 4.5");
        interp.readInput("p*");

        interp.run();
        assertEquals(interp.getOutput(), "A1 1 1 Q1 4.5 4.5 AG1 0 0\nF2 =SUM A1 G2 2 G2 =SUM A1 AG1 1\nC3 2 2 J3 =SUM 2 3\nN23 3.5 3.5");
    }
    
    @Test
    public void test10() {
        Spreadsheet ss = new Spreadsheet();
        Interpreter interp = new Interpreter(ss);

        interp.readInput("A1 =SUM A2 A3");
        interp.readInput("A2 2");
        interp.readInput("A3 1000");
        interp.readInput("T1 =SUM A");

        interp.readInput("B1 2");
        interp.readInput("B2 30");
        interp.readInput("B3 100.1");
        interp.readInput("T2 =SUM B");

        interp.readInput("T1");
        interp.readInput("T2");

        interp.run();
        assertEquals(interp.getOutput(), "T1 =SUM A 2004\nT2 =SUM B 132.1");
    }
}
