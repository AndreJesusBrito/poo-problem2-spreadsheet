
public class Cell {
	private int isReferenced;
	private ICellContent content;
	
	public Cell(ICellContent content) {
		setIsReferenced(0);
		setContent(content);
	}
	
	public ICellContent getContent() {
		return content;
	}

	public void setContent(ICellContent content) {
		this.content = content;
	}
	
	public int getIsReferenced() {
		return isReferenced;
	}

	public void setIsReferenced(int isReferenced) {
		this.isReferenced = isReferenced;
	}
	
	public void incrementRef() {
		isReferenced++;
	}
	
	public void decrementRef() {
		isReferenced--;
	}
	
	public Number getValue() {
		return content.getValue();
	}

	public void onDelete() {
		content.onDelete();
	}
}
