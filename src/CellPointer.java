
public class CellPointer implements ICellContent {
    private Cell referencedCell = null;

    public CellPointer(Cell referencedCell) {
        setReferencedCell(referencedCell);
        referencedCell.incrementRef();
    }

    public Cell getReferencedCell() {
        return referencedCell;
    }

    public void setReferencedCell(Cell referencedCell) {
        this.referencedCell = referencedCell;
    }

    @Override
    public String getContentString() {
        return referencedCell.getKey();
    }

    @Override
    public Object getValue() {
        return referencedCell.getValue();
    }

    public void onDelete() {
        if(referencedCell != null) referencedCell.decrementRef();
    }
}
