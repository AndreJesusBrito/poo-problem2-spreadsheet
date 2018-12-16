public class CellPointer implements ICellContent {
    private Cell cellPointer;
    private String pointer;
    
    public CellPointer(String pointer) {
    	
    }

	public void setNumber(Number newNum) {
    }

    public Object getValue() {
        return cellPointer.getValue();
    };

    public String getContent() {
        return Number.toString(pointer.);
    };

    public void onDelete() {};
}
