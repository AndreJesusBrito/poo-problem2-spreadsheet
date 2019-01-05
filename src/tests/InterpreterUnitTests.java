<<<<<<< HEAD
package Unit_Tests__DELETE_ME_PLEASE;
=======
package tests;
>>>>>>> alt
import static org.junit.Assert.*;

import org.junit.Test;

import UnitTest_Dep.Interpreter;
import UnitTest_Dep.Spreadsheet;

public class InterpreterUnitTests {
    @Test
    public void test1() {
        Spreadsheet ss = new Spreadsheet();
        Interpreter interp = new Interpreter(ss);

        interp.readInput("A1 =SUM 1 2");
        interp.readInput("p*");
        
        interp.run();
        assertEquals(interp.getOutput(), "A1 =SUM 1 2 3");
    }

    @Test
    public void test2() {
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
    public void test3() {
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
    public void test4() {
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
    public void test5() {
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
    public void test6() {
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
    public void test7() {
        Spreadsheet ss = new Spreadsheet();
        Interpreter interp = new Interpreter(ss);

        interp.readInput("A1 10.1");
        interp.readInput("AA2 =SUM 2 =SUM 2 =SUM A");
        interp.readInput("AA2");


        interp.run();
        assertEquals(interp.getOutput(), "AA2 =SUM 2 =SUM 2 =SUM A 14.1");
    }
}
