import java.util.HashMap;
import java.util.Map;

public class SpreadSheet {
    private Map<String, Cell> cells;

    public SpreadSheet() {
        cells = new HashMap<String, Cell>();
    };

    public void print() {}
    public void deleteRow(int rowID) {}
    public void deleteColumn(String colId) {}

    public void deleteCell(String pos) {
        Cell cell = cells.get(pos);
        if(cell.isReferenced()) {
            cell.setContent(new CellNumber(0, "stuff here"));
        } else {
            cell.onDelete();
            cells.remove(pos);
        }
    }
    public Cell getCellAt(String pos) {
        return cells.get(pos);
    }

    public void createCell(String pos, Object value) {
        Cell cell = new Cell(value);
        if(cells.containsKey(pos)) throw new Exception("The pos " + pos + "contains a cell!");
        cells.put(pos, cell);
    }
}
