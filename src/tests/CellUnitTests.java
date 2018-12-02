package tests;

import static org.junit.Assert.*;

import org.junit.Test ;
import java.util.*;

public class CellUnitTest {
  @Test
  public void testGetContent1() {
    CellNumber cn = new CellNumber(2);
    Cell cell = new Cell(cn);
    assertEquals(cell.getContent(), "2");
  }

  @Test
  public void testGetContent2() {
    CellPointer cp = new CellPointer("A2");
    Cell cell = new Cell(cp);
    assertEquals(cell.getContent(), "A2");
  }

  @Test
  public void testGetContent3() {
    CellFormula cf = new CellFormula("=SUM 1 1");
    Cell cell = new Cell(cf);
    assertEquals(cell.getContent(), "=SUM 1 1");
  }


  @Test
  public void testGetValue1() {
      CellNumber cn = new CellNumber(2);
      Cell cell = new Cell(cn);
      assertEquals(cn.getValue(), 2);
  }

  @Test
  public void testGetValue2() {
      SpreadSheet ss = new SpreadSheet();
      Cell refCell = new Cell(new CellNumber(64));
      ss.addCell(refCell, "A2");

      CellPointer cp = new CellPointer("A2");
      Cell cell = new Cell(cp);

      assertEquals(cell.getValue(), 64);
  }

  @Test
  public void testGetValue3() {
      CellFormula cf = new CellFormula("=SUM 1 1");
      Cell cell = new Cell(cf);
      assertEquals(cell.getValue(), 2);
  }


  @Test
  public void testGetValue2() {
      SpreadSheet ss = new SpreadSheet();
      Cell refCell = new Cell(new CellNumber(64));
      ss.addCell(refCell, "A2");

      CellPointer cf = new CellFormula("=SUM A2 64");
      Cell cell = new Cell(cf);

      assertEquals(cell.getValue(), 128);
  }
}
