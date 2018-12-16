public class CellPointer implements ICellContent {
    private Cell referencedCell;

    public CellPointer(Cell referencedCell) {
        setReferencedCell(referencedCell);
        referencedCell.incrementRef();
    }

    public void setReferencedCell(Cell referencedCell) {
        this.referencedCell = referencedCell;
    }

    @Override
    public String getContent() {
        return referencedCell.getReference();
    }

    @Override
    public Object getValue() {
        return referencedCell.getValue();
    }

    @Override
    public void onDelete() {
        referencedCell.decrementRef();
    }
}
