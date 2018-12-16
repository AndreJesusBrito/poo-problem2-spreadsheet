
public class CellPointer implements ICellContent {
	private Cell referencedCell;
	private String content;
	
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
	public Number getValue() {
		return referencedCell.getValue();
	}

	@Override
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		content = getValue().toString();
	}

	public void onDelete() {
		referencedCell.decrementRef();
	}
}
