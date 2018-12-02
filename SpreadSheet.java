public class Spreadsheet {
    LinkedList<LinkedList> cells = new LinkedList<LinkedList>();

    public Spreadsheet() {
        addRow();
        addColumn();
    };

    public void addRow() {};
    public void addRow(int index) {};
    public void removeRow(int index) {};
    public void removeColumn(int index) {};
    public void addColumn() {};
    public void addColumn(int index) {};
    public void setCell(int x, int y, String newData) {};
    public String toString() {};
}
