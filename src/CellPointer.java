public class CellPointer implements ICellContent {
    private Cell referencedCell;

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
    public String getContent() {
        return referencedCell.getReference();
    }

    @Override
    public Object getValue() {
        return referencedCell.getValue();
    }
    
	public boolean isDouble() {
		return referencedCell.getContent().isDouble();
	}

    public void onDelete() {
        referencedCell.decrementRef();
    }
}
